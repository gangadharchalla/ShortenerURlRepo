# Three Evaluation Scenarios

## 1. Greenfield
Requirement: Build URL shortening, redirection and click analytics.
Decomposition: requirement -> plan -> design -> implementation/security in parallel -> tests -> human release gate -> documentation.
Validation: URL validation, redirect behavior, atomic click count, unit tests, audit trail and metrics.

## 2. Brownfield
Change: Add link expiration to the existing service.
Impact analysis: `UrlMapping` needs expiry state; create request/response may expose expiry; `UrlService.resolve` must reject expired links; tests must cover boundary behavior; docs/API contract must change. Replanning should invalidate the affected design, implementation, tests and documentation while preserving unrelated history.
Validation: expiry boundary tests, existing redirect/analytics regression tests, security review, fresh approval before release.

## 3. Ambiguous
Request: “Make shortened links secure.”
The requirement agent must not silently pick a meaning. Possible interpretations include allowed URL schemes, authenticated creation, rate limiting, destination reputation, expiry and short-code unpredictability. The governed outcome is to record questions/assumptions and enter a human decision gate before high-impact implementation.
