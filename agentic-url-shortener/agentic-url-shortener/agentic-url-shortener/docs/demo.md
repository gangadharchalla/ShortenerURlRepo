# Demo Script
1. Import as Existing Maven Project and run tests.
2. Start `UrlShortenerApplication`.
3. POST a long HTTPS URL and note its short code.
4. GET the short URL twice and observe redirects.
5. GET analytics and show `clickCount` increased.
6. Walk through `WorkflowEngineTest`: explicit DAG, parallel-ready implementation/security paths, synchronization at tests, approval gate, audit trail and metrics.
7. Explain the brownfield expiry scenario and selective invalidation/replanning design.
8. Explain why the ambiguous security request stops for human clarification instead of allowing uncontrolled autonomous changes.
9. Close with limitations: deterministic agents and in-memory persistence are deliberate prototype constraints; interfaces provide replaceable seams for production integrations.
