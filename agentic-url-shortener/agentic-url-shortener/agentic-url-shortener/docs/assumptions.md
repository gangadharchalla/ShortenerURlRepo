# Assumptions and Trade-offs
- Java 17 is the required runtime.
- In-memory storage keeps the prototype runnable with minimal infrastructure; data is lost on restart.
- Short codes use `SecureRandom` and a reduced-alphabet 7-character token; uniqueness is checked against the current repository.
- Only HTTP/HTTPS destinations are accepted.
- Click analytics count redirects, not unique visitors.
- Agents are deterministic Java components. This intentionally demonstrates orchestration and governance without coupling the prototype to an LLM vendor/API.
- Rollback in this prototype is a workflow-state compensation marker, not a deployment rollback.
- A production version needs durable workflow state, authorization, rate limits, abuse protection, URL reputation checks, metrics/tracing export, and robust deployment/change-management integrations.
