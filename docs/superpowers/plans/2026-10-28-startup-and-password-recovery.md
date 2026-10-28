# Startup and Password Recovery Implementation Plan

> **For agentic workers:** REQUIRED SUB-SKILL: Use superpowers:executing-plans to implement this plan task-by-task. Steps use checkbox (`- [ ]`) syntax for tracking.

**Goal:** Make `npm start` reuse a healthy existing API and add safe account recovery by one-time code over SMTP email or verified WhatsApp Cloud.

**Architecture:** Keep the current Spring Boot/PostgreSQL and static Python frontend. The startup supervisor checks the API health endpoint before starting another API, and only stops processes it created. Recovery uses persisted, expiring, single-use codes with bounded attempts, neutral responses, configurable senders, and phone verification before WhatsApp reset.

**Tech Stack:** Python 3 standard library; Java 21, Spring Boot 3.5, JPA, Flyway, Spring Mail; browser JavaScript modules.

**Spec:** User request on 2026-10-28; delivery selection: SMTP email plus optional Meta WhatsApp Cloud.

## Global Constraints

- Do not return recovery codes or disclose whether an account exists.
- Recovery codes expire after 10 minutes, are one-use, and allow at most five validation attempts.
- WhatsApp recovery is allowed only for a verified phone number already associated with the account.
- The `dev.py` supervisor never terminates a healthy API it did not start.
- SMTP and Meta credentials come only from environment configuration.

## Review Focus

- Existing healthy API on port 8080: supervisor reuses it and does not spawn another Java process.
- Unhealthy service occupying port 8080: startup reports the conflict before launching a duplicate process.
- Unknown email/phone: recovery initiation response is indistinguishable from a valid account.
- Expired, used, and over-attempted recovery code: reset fails and credential version stays unchanged.
- Unverified or changed phone number: WhatsApp recovery is unavailable until verified.

### Task 1: Idempotent local startup

**Files:** `scripts/dev.py`, `Frontend/scripts/start.py`, `scripts/test_dev.py`.

- [ ] Test a healthy existing API is reused and not stopped by supervisor cleanup.
- [ ] Test unavailable and occupied API port paths report useful failures.
- [ ] Implement API health preflight and process ownership-aware cleanup.
- [ ] Report the actual frontend port rather than assuming 5173.
- [ ] Run Python tests and syntax checks.

### Task 2: Persisted recovery codes and senders

**Files:** `Backend/src/main/resources/db/migration-common/V14__password_recovery.sql`, identity recovery classes, `Backend/src/main/resources/application.yml`, `Backend/pom.xml`, `Backend/src/test/java/br/com/fooddelivery/identity/`.

- [ ] Add tests for expiry, single use, and the five-attempt limit.
- [ ] Add account phone verification state and a recovery-code entity/repository.
- [ ] Add configurable SMTP and WhatsApp Cloud sender adapters, with provider errors kept out of public responses.
- [ ] Add neutral request, verify, and reset endpoints; increment credential version on reset.
- [ ] Add authenticated phone verification endpoints and enforce verified phone for WhatsApp recovery.
- [ ] Run focused tests and verify Flyway/schema validation under PostgreSQL and H2.

### Task 3: Login recovery and administration

**Files:** `Frontend/src/main.js`, `Frontend/src/api.js`, `Frontend/styles.css`, account/admin controllers and tests.

- [ ] Test/code-check the request → verify code → choose new password flow.
- [ ] Add accessible recovery steps to the login view with email/WhatsApp selection and code entry.
- [ ] Preserve administrator reset as a separate temporary-password action; verify it invalidates existing tokens.
- [ ] Add config and setup details to `.env.example` and project documentation.
- [ ] Run full backend tests, frontend syntax checks, migration checks, and startup verification.
