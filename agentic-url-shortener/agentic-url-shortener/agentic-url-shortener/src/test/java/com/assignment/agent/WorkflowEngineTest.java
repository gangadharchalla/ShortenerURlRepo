package com.assignment.agent;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import com.assignment.agent.impl.*;

class WorkflowEngineTest {
	@Test
	void executesDependencyGraph() {
		MetricsCollector m = new MetricsCollector();
		ApprovalGate g = new ApprovalGate();
		g.approve();
		try (WorkflowEngine e = new WorkflowEngine(m, g, 1)) {
			e.add(new WorkflowNode("requirements", new RequirementAgent()));
			e.add(new WorkflowNode("plan", new PlannerAgent(), "requirements"));
			e.add(new WorkflowNode("design", new DesignAgent(), "plan"));
			e.add(new WorkflowNode("implementation", new ImplementationAgent(), "design"));
			e.add(new WorkflowNode("security", new SecurityAgent(), "design"));
			e.add(new WorkflowNode("tests", new TestAgent(), "implementation", "security"));
			e.add(new WorkflowNode("release", new ReleaseAgent(), "tests"));
			e.add(new WorkflowNode("documentation", new DocumentationAgent(), "release"));
			AgentContext c = new AgentContext("Build a URL shortener with analytics");
			e.execute(c);
			assertEquals(8, m.successes());
			assertTrue(c.getAuditTrail().size() >= 16);
		}
	}

	@Test
	void approvalGateStopsRelease() {
		MetricsCollector m = new MetricsCollector();
		ApprovalGate g = new ApprovalGate();
		try (WorkflowEngine e = new WorkflowEngine(m, g, 0)) {
			e.add(new WorkflowNode("release", new ReleaseAgent()));
			AgentContext c = new AgentContext("release");
			e.execute(c);
			assertEquals(AgentStatus.WAITING_APPROVAL, e.nodes().iterator().next().getStatus());
		}
	}
}
