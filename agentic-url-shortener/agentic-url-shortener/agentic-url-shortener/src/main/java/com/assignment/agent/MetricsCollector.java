package com.assignment.agent;

import java.util.concurrent.atomic.*;

public class MetricsCollector {
	private final AtomicLong executions = new AtomicLong(), successes = new AtomicLong(), failures = new AtomicLong(),
			retries = new AtomicLong(), rollbacks = new AtomicLong(), totalLatencyMs = new AtomicLong();

	public void execution() {
		executions.incrementAndGet();
	}

	public void success() {
		successes.incrementAndGet();
	}

	public void failure() {
		failures.incrementAndGet();
	}

	public void retry() {
		retries.incrementAndGet();
	}

	public void rollback() {
		rollbacks.incrementAndGet();
	}

	public void latency(long ms) {
		totalLatencyMs.addAndGet(ms);
	}

	public long executions() {
		return executions.get();
	}

	public long successes() {
		return successes.get();
	}

	public long failures() {
		return failures.get();
	}

	public long retries() {
		return retries.get();
	}

	public long rollbacks() {
		return rollbacks.get();
	}

	public long totalLatencyMs() {
		return totalLatencyMs.get();
	}

	public double successRate() {
		return executions.get() == 0 ? 0.0 : (100.0 * successes.get() / executions.get());
	}
}
