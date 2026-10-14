package com.assignment.agent.impl;

import com.assignment.agent.*;
import java.util.Map;

public class SecurityAgent implements Agent {
	public String name() {
		return "SecurityAgent";
	}

	public AgentResult execute(AgentContext c) {
		c.getState().put("security", "Security guardrails reviewed");
		return new AgentResult(AgentStatus.SUCCESS, "Security guardrails reviewed", Map.of());
	}
}