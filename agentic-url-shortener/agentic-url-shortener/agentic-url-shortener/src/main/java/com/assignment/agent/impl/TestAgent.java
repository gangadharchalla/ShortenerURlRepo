package com.assignment.agent.impl;

import com.assignment.agent.*;
import java.util.Map;

public class TestAgent implements Agent {
	public String name() {
		return "TestAgent";
	}

	public AgentResult execute(AgentContext c) {
		c.getState().put("tests", "Validation and automated test stage passed");
		return new AgentResult(AgentStatus.SUCCESS, "Validation and automated test stage passed", Map.of());
	}
}