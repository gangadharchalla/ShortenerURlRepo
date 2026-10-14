package com.assignment.agent;

import java.util.Map;

public record AgentResult(AgentStatus status, String message, Map<String, Object> outputs) {
	public static AgentResult success(String m) {
		return new AgentResult(AgentStatus.SUCCESS, m, Map.of());
	}

	public static AgentResult failed(String m) {
		return new AgentResult(AgentStatus.FAILED, m, Map.of());
	}
}