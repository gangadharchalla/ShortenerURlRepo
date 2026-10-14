# Architecture

## Control flow
```text
Requirement -> Plan -> Design -> +-> Implementation -+
                               |                  |
                               +-> Security ------+-> Tests -> Human Release Gate -> Documentation
```
Implementation and security are independent after design, so the engine may execute them in parallel and synchronizes at tests.

## Core model
- `Agent`: bounded unit of engineering work.
- `AgentContext`: requirement, shared cross-stage state, audit trail.
- `WorkflowNode`: agent plus explicit dependencies and lifecycle state.
- `WorkflowEngine`: scheduler/governor; not an AI model.
- `ApprovalGate`: human-in-the-loop release control.
- `MetricsCollector`: executions, successes, failures, retries, rollbacks and end-to-end node latency totals.

## Governance
Release is blocked when approval is absent. Failed nodes retry only up to the configured bound. Exhausted failures stop downstream execution. Successful nodes from the current failed wave are marked rolled back as a demonstrable compensation/rollback control. All state transitions create audit events.

## Replanning design
For a production extension, store a hash/version per node input. When an upstream requirement changes, invalidate only descendants whose effective inputs changed, preserve prior audit/decision lineage, require new approval for high-impact changes, then reschedule the affected subgraph. The prototype documents this mechanism rather than pretending to perform source-code generation itself.
