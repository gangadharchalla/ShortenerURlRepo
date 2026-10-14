package com.assignment.agent;

import java.util.concurrent.atomic.AtomicBoolean;

public class ApprovalGate {
	private final AtomicBoolean approved = new AtomicBoolean(false);

	public void approve() {
		approved.set(true);
	}

	public void revoke() {
		approved.set(false);
	}

	public boolean isApproved() {
		return approved.get();
	}
}
