package com.assignment.agent;

import java.time.Instant;
import java.util.*;
import java.util.concurrent.*;

public class WorkflowEngine implements AutoCloseable {
	private final Map<String, WorkflowNode> nodes = new LinkedHashMap<>();
	private final MetricsCollector metrics;
	private final ApprovalGate approval;
	private final ExecutorService pool = Executors.newFixedThreadPool(4);
	private final int maxRetries;

	public WorkflowEngine(MetricsCollector metrics, ApprovalGate approval, int maxRetries) {
		this.metrics = metrics;
		this.approval = approval;
		this.maxRetries = maxRetries;
	}

	public void add(WorkflowNode n) {
		nodes.put(n.getId(), n);
	}

	public Collection<WorkflowNode> nodes() {
		return List.copyOf(nodes.values());
	}

	public void execute(AgentContext ctx) {
		Set<String> remaining = new LinkedHashSet<>(nodes.keySet());
		while (!remaining.isEmpty()) {
			List<WorkflowNode> ready = remaining.stream().map(nodes::get).filter(
					n -> n.getDependencies().stream().allMatch(d -> nodes.get(d).getStatus() == AgentStatus.SUCCESS))
					.toList();
			if (ready.isEmpty()) {
				safeStop(ctx, remaining, "No runnable nodes: blocked dependency or failed gate");
				return;
			}
			List<CompletableFuture<Void>> futures = ready.stream()
					.map(n -> CompletableFuture.runAsync(() -> runNode(n, ctx), pool)).toList();
			futures.forEach(CompletableFuture::join);
			for (WorkflowNode n : ready)
				remaining.remove(n.getId());
			if (ready.stream().anyMatch(n -> n.getStatus() != AgentStatus.SUCCESS)) {
				rollback(ctx, ready);
				safeStop(ctx, remaining, "Workflow stopped after node failure");
				return;
			}
		}
	}

	private void runNode(WorkflowNode n, AgentContext ctx) {
		if ("release".equals(n.getId()) && !approval.isApproved()) {
			n.setStatus(AgentStatus.WAITING_APPROVAL);
			audit(ctx, n, "Human approval required");
			return;
		}
		n.setStatus(AgentStatus.RUNNING);
		audit(ctx, n, "Started");
		long start = System.nanoTime();
		metrics.execution();
		for (int attempt = 0; attempt <= maxRetries; attempt++) {
			try {
				AgentResult r = n.getAgent().execute(ctx);
				if (r.outputs() != null)
					ctx.getState().putAll(r.outputs());
				if (r.status() == AgentStatus.SUCCESS) {
					n.setStatus(AgentStatus.SUCCESS);
					metrics.success();
					audit(ctx, n, r.message());
					metrics.latency((System.nanoTime() - start) / 1_000_000);
					return;
				}
				throw new IllegalStateException(r.message());
			} catch (Exception e) {
				if (attempt < maxRetries) {
					metrics.retry();
					audit(ctx, n, "Retry " + (attempt + 1) + ": " + e.getMessage());
				} else {
					n.setStatus(AgentStatus.FAILED);
					metrics.failure();
					audit(ctx, n, "Failed after bounded retries: " + e.getMessage());
					metrics.latency((System.nanoTime() - start) / 1_000_000);
				}
			}
		}
	}

	private void rollback(AgentContext ctx, List<WorkflowNode> latest) {
		latest.stream().filter(n -> n.getStatus() == AgentStatus.SUCCESS).forEach(n -> {
			n.setStatus(AgentStatus.ROLLED_BACK);
			metrics.rollback();
			audit(ctx, n, "State rollback marker applied");
		});
	}

	private void safeStop(AgentContext ctx, Collection<String> ids, String msg) {
		for (String id : ids) {
			WorkflowNode n = nodes.get(id);
			if (n.getStatus() == AgentStatus.PENDING)
				n.setStatus(AgentStatus.SAFE_STOPPED);
		}
		ctx.audit(new AuditEvent(Instant.now(), "workflow", "WorkflowEngine", AgentStatus.SAFE_STOPPED, msg));
	}

	private void audit(AgentContext c, WorkflowNode n, String m) {
		c.audit(new AuditEvent(Instant.now(), n.getId(), n.getAgent().name(), n.getStatus(), m));
	}

	public void close() {
		pool.shutdown();
	}
}
