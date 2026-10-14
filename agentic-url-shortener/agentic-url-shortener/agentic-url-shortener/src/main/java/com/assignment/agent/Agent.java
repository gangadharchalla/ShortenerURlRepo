package com.assignment.agent;

public interface Agent {
	String name();

	AgentResult execute(AgentContext context) throws Exception;
}