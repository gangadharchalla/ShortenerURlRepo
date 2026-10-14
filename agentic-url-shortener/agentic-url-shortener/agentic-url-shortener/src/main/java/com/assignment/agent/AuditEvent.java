package com.assignment.agent;

import java.time.Instant;

public record AuditEvent(Instant timestamp, String nodeId, String agent, AgentStatus status, String message) {
}