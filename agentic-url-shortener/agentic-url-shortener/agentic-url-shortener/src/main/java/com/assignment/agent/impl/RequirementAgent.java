package com.assignment.agent.impl;

import com.assignment.agent.*;
import java.util.Map;

public class RequirementAgent implements Agent {
	public String name() {
		return "RequirementAgent";
	}

	public AgentResult execute(AgentContext c) {
		c.getState().put("requirement.normalized", "Normalized requirement and captured ambiguity");
		return new AgentResult(AgentStatus.SUCCESS, "Normalized requirement and captured ambiguity", Map.of());
	}
}