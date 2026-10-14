package com.assignment.agent;

import java.util.*;

public class WorkflowNode {
	private final String id;
	private final Agent agent;
	private final List<String> dependencies;
	private volatile AgentStatus status = AgentStatus.PENDING;

	public WorkflowNode(String id, Agent agent, String... dependencies) {
		this.id = id;
		this.agent = agent;
		this.dependencies = List.of(dependencies);
	}

	public String getId() {
		return id;
	}

	public Agent getAgent() {
		return agent;
	}

	public List<String> getDependencies() {
		return dependencies;
	}

	public AgentStatus getStatus() {
		return status;
	}

	public void setStatus(AgentStatus s) {
		status = s;
	}
}
