package com.assignment.agent;

import java.util.*;
import java.util.concurrent.*;

public class AgentContext {
	private volatile String requirement;
	private final Map<String, Object> state = new ConcurrentHashMap<>();
	private final List<AuditEvent> auditTrail = new CopyOnWriteArrayList<>();

	public AgentContext(String requirement) {
		this.requirement = requirement;
	}

	public String getRequirement() {
		return requirement;
	}

	public void setRequirement(String r) {
		requirement = r;
	}

	public Map<String, Object> getState() {
		return state;
	}

	public List<AuditEvent> getAuditTrail() {
		return List.copyOf(auditTrail);
	}

	public void audit(AuditEvent e) {
		auditTrail.add(e);
	}
}
