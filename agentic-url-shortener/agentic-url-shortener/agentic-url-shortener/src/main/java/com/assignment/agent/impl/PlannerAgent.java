package com.assignment.agent.impl;

import com.assignment.agent.*;
import java.util.Map;

public class PlannerAgent implements Agent {
	public String name() {
		return "PlannerAgent";
	}

	public AgentResult execute(AgentContext c) {
		c.getState().put("plan", "Created dependency-aware engineering plan");
		return new AgentResult(AgentStatus.SUCCESS, "Created dependency-aware engineering plan", Map.of());
	}
}