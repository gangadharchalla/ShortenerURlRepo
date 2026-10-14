package com.assignment.agent.impl;

import com.assignment.agent.*;
import java.util.Map;

public class DesignAgent implements Agent {
	public String name() {
		return "DesignAgent";
	}

	public AgentResult execute(AgentContext c) {
		c.getState().put("design", "Produced modular URL shortener design");
		return new AgentResult(AgentStatus.SUCCESS, "Produced modular URL shortener design", Map.of());
	}
}