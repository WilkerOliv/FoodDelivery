# FoodDelivery Audit and Remediation Plan

> **For agentic workers:** REQUIRED SUB-SKILL: Use superpowers:executing-plans to implement this plan task-by-task. Steps use checkbox (`- [ ]`) syntax for tracking.

**Goal:** Audit the existing FoodDelivery startup, backend/API, database, frontend, and user flows; fix verified defects and document evidence and limits.

**Architecture:** Preserve the current Java Spring Boot + PostgreSQL/H2 backend and vanilla JavaScript frontend. First collect repeatable evidence without mutating persistent user data, then trace each reproducible defect to its cause, add focused regression coverage, apply small fixes, and update the existing project documentation.

**Tech Stack:** Java 21, Spring Boot 3.5, Spring Security/JWT, Spring Data JPA, Flyway, PostgreSQL 17, H2, Maven, JavaScript modules, Node test runner, Python startup scripts.

**Spec:** `Documentacao/Visao_e_Especificacao_do_Produto.md`, `Documentacao/Funcionamento_do_Projeto.md`, `Documentacao/Modelo_de_Dados.md`, plus the existing requested features in the current working tree.

## Global Constraints

- Preserve all existing uncommitted user work; do not reset, checkout, or overwrite unrelated files.
- Keep the established Spring Boot + Flyway + PostgreSQL/H2 and vanilla JavaScript architecture unless evidence proves a necessary change.
- Never expose credentials, recovery codes, payment tokens, CPF/CNPJ values, or customer addresses in logs or review artifacts.
- Do not mutate production or persistent local business data during audit; database checks begin read-only and migrations are verified in disposable test databases.
- Keep business authorization on the backend and validate every API input server-side.
- Do not add third-party identity, AI, chat, or map providers without a concrete existing integration need and available configuration.
- Treat the per-professional booking schedule as a separate scoped feature; the design exists at `docs/superpowers/specs/2026-10-28-agenda-por-profissional-design.md` and remains outside this audit's implementation unless the user approves that design.

## Review Focus

- Fresh startup with missing optional service credentials must fail clearly only when a required core dependency is absent; verify existing scripts and health endpoints.
- Account, store, order, reservation, ticket, payment, and delivery reads must enforce both role and record ownership; cover an authenticated user attempting another account/store's identifier.
- Database constraints and migration variants must agree for PostgreSQL and H2, including existing rows and uniqueness/integrity boundaries.
- Browser actions must preserve loading, validation, success, empty, error, and retry states and must not emit duplicate submissions on repeated clicks.
- Narrow/mobile and keyboard use must retain visible navigation, labels, focus, and readable content in critical buyer, seller, courier, and admin flows.

---

### Task 1: Record baseline and runtime configuration

**Files:**
- Inspect: `package.json`, `scripts/dev.py`, `Backend/pom.xml`, `Backend/src/main/resources/application.yml`, `Backend/compose.yaml`, `Backend/.env.example`, `Frontend/package.json`, `Frontend/scripts/start.py`
- Modify: `Documentacao/Revisao_visual_e_funcional.md`

**Interfaces:**
- Consumes: current repository scripts, profiles, environment templates, and service health endpoints.
- Produces: a dated inventory of runnable checks, environment prerequisites, and baseline outcomes.

- [ ] Run non-mutating repository status, dependency/runtime version, syntax, and startup-script checks; record exact commands and results without printing secret values.
- [ ] Run existing backend and frontend test suites before fixes; record failures verbatim and classify environmental versus product defects.
- [ ] Probe local API/frontend only if already running; do not kill or restart an unrelated process.
- [ ] Document unavailable live-provider checks (SMTP, WhatsApp, Mercado Pago, image generation) separately from local verification.

### Task 2: Trace backend security and API behavior

**Files:**
- Inspect: `Backend/src/main/java/br/com/fooddelivery/config/SecurityConfig.java`, `Backend/src/main/java/br/com/fooddelivery/api/ApiExceptionHandler.java`, controllers and services under `Backend/src/main/java/br/com/fooddelivery/`
- Test: existing `Backend/src/test/java/` plus focused tests for any confirmed defect.

**Interfaces:**
- Consumes: authenticated account identity, role grants, resource ownership, domain state transitions.
- Produces: evidence-backed findings and regression cases for authorization, validation, transitions, idempotency, and error mapping.

- [ ] Map each endpoint to anonymous/authenticated roles and ownership rules; compare controller annotations and repository predicates against that map.
- [ ] Trace selected critical flows end-to-end: login/recovery, store approval and catalog, order/payment/refund, reservation, delivery offer/accept/status/location, support ticket, and notifications.
- [ ] Add a failing test before each confirmed behavior fix; implement the smallest change and run its focused test.
- [ ] Avoid provider changes; verify only integration seams already present in the project.

### Task 3: Audit schema, migrations, and stored data invariants

**Files:**
- Inspect: `Backend/src/main/resources/db/migration-common/`, `migration-postgres/`, `migration-h2/`, JPA entities/repositories, `Backend/src/test/java/br/com/fooddelivery/RecoverySchemaTest.java`
- Test: disposable PostgreSQL and H2 migration contexts or existing schema tests.

**Interfaces:**
- Consumes: Flyway history and declared entity/table/index/constraint mappings.
- Produces: parity and integrity findings; migration fixes with a forward-only version and test evidence when required.

- [ ] Compare common and engine-specific migration versions, names, indexes, constraints, and entity mappings.
- [ ] Check key uniqueness, foreign-key coverage, nullability, status/value ranges, and timestamp/currency conventions on disposable databases.
- [ ] Verify Flyway upgrade from an empty schema and, where fixtures allow, from a populated prior schema without editing the application's real database.
- [ ] Fix only demonstrated schema defects; never rewrite an already-applied migration.

### Task 4: Audit frontend interaction and responsive presentation

**Files:**
- Inspect: `Frontend/src/main.js`, `Frontend/src/adminSections.js`, `Frontend/src/api.js`, `Frontend/src/actionGuard.js`, `Frontend/src/inputMasks.js`, `Frontend/styles.css`, `Frontend/theme.css`, `Frontend/index.html`
- Test: `Frontend/tests/` and a fresh browser capture of available application states.

**Interfaces:**
- Consumes: current routes/actions and API response shapes.
- Produces: screen-by-screen evidence, interaction coverage, accessibility risks visible to inspection, and regression tests for verified UI defects.

- [ ] Capture the current browser state before navigation and inspect buyer, account, seller, admin, support, and booking surfaces that are reachable without destructive data changes.
- [ ] Check click targets, navigation, form validation, loading/error/empty states, duplicate-submit guards, and narrow viewport layout.
- [ ] For any verified broken action, add a behavior test first, fix the smallest cause, and recapture that flow.
- [ ] Do not claim full accessibility conformance from screenshots; distinguish visual inspection from keyboard/DOM checks.

### Task 5: Consolidate fixes, documentation, and final verification

**Files:**
- Modify: affected source/tests only; `Documentacao/Funcionamento_do_Projeto.md`, `Documentacao/Modelo_de_Dados.md`, `Documentacao/Revisao_visual_e_funcional.md`

**Interfaces:**
- Consumes: verified findings and fixes from Tasks 1–4.
- Produces: one detailed review with severity, evidence, remediation, verification commands, known external prerequisites, and residual risks.

- [ ] Re-run all project-provided test suites and build/compile checks after fixes; report every failure, including pre-existing failures.
- [ ] Re-run the exact startup and API health checks that were available at baseline.
- [ ] Ensure documentation matches code and migrations and separates observed evidence from assumptions or unavailable live checks.
- [ ] Review the final working-tree diff to confirm no pre-existing edits were reverted and no secrets/test customer data were added.

