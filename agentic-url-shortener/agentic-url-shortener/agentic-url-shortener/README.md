# Agentic URL Shortener

Java 17 / Spring Boot prototype that separates a small URL-shortener workload from a deterministic, governed agentic SDLC orchestration control plane.

## Features
- `POST /api/urls` creates an unpredictable 7-character short code.
- `GET /{code}` redirects and atomically records a click.
- `GET /api/urls/{code}/analytics` returns click count and creation time.
- DAG-based orchestration with explicit dependencies and parallel-ready nodes.
- Shared state and audit trail.
- Bounded retries, rollback markers, safe stop, human approval gate, and reliability metrics.
- Greenfield, brownfield, and ambiguous-requirement scenarios documented under `docs/`.

## Run
1. Use Java 17.
2. Import into Eclipse as **Existing Maven Projects**.
3. Wait for Maven dependencies to resolve.
4. Run `UrlShortenerApplication` as a Java application, or run `mvn spring-boot:run` if Maven CLI is available.
5. Run tests using **Run As > JUnit Test** or `mvn test`.

## API examples
Create: `POST http://localhost:8080/api/urls` body `{"url":"https://example.com"}`.
Redirect: `GET http://localhost:8080/{shortCode}`.
Analytics: `GET http://localhost:8080/api/urls/{shortCode}/analytics`.

## Architecture
The application workload is deliberately simple. The orchestration layer models lifecycle work as `WorkflowNode` objects containing an `Agent` and explicit dependency IDs. Ready nodes execute concurrently; dependent nodes become eligible only after all dependencies succeed. `AgentContext` carries cross-stage state and immutable audit snapshots. `WorkflowEngine` enforces bounded retry, safe-stop and rollback markers. `ApprovalGate` prevents a release node from proceeding without explicit human approval.

## Production limitations
This prototype intentionally uses volatile in-memory URL/workflow state. It does not include an external LLM, durable database, authentication/authorization, distributed locking, production observability backend, malware reputation service, or deployment engine. Agent implementations are deterministic seams that can later be replaced by LLM/tool-backed implementations while preserving governance contracts.

See `docs/architecture.md`, `docs/scenarios.md`, `docs/assumptions.md`, and `docs/demo.md`.
