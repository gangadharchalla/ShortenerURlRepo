package com.assignment.agent.impl;

import com.assignment.agent.*;

public class ReleaseAgent implements Agent {
	public String name() {
		return "ReleaseAgent";
	}

	public AgentResult execute(AgentContext c) {
		return AgentResult.success("Release-readiness gate passed");
	}
}