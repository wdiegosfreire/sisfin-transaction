# CLAUDE.md

This file provides guidance to Claude Code (claude.ai/code) when working with code in this repository.

This is one module of the `sisfin-environment` workspace — see `../CLAUDE.md` for how it relates to `sisfin-maintenance`, `sisfin-bypass`, `sisfin-mysql`, `sisfin-compose`, and `sisfin-config`. This file covers only what's specific to `sisfin-transaction`.

## What this service is

Spring Boot 2.5.6 / Java 11 service holding the core financial domain: banks, accounts, statements (+ patterns/types), payment methods, locations, objectives (+ items/movements), and summary/chart data. Runs on port `8081`. It has no user table of its own — auth is delegated to `sisfin-maintenance` (see Auth below).

## Commands

```bash
./mvnw clean package              # build (mvnw.cmd on native Windows shells)
./mvnw clean package -DskipTests  # build without tests — used in the EC2 deploy flow
./mvnw test                                                          # run all tests
./mvnw test -Dtest=SomeServiceTest                                   # run one test class
./mvnw test -Dtest=SomeServiceTest#someMethod                        # run one test method
./mvnw spring-boot:run             # run locally
```

There is currently **no `src/test` directory** in this module — unlike `sisfin-maintenance`, which has `@SpringBootTest`/`MockMvc` tests under `services/user` and `resources`. If asked to add tests here, mirror that module's `TestConfig` (`@SpringBootTest` + `@AutoConfigureMockMvc`) pattern rather than inventing a new one.

Local run needs the `SISFIN_*` env vars from `../sisfin-config/sisfin-backend.env` (notably `SISFIN_URL_MAINTENANCE`, `SISFIN_BACKEND_ENVIROMENT` for the active Spring profile). `bootstrap.yml`/`application.yml` point at an optional Config Server at `localhost:8888` that isn't part of this workspace — the service starts fine without it (`optional:configserver:...`).

Docker: `Dockerfile` copies `target/*.jar` into `eclipse-temurin:11-jdk`; `docker-compose.yaml` here runs this service alone against `../sisfin-config/sisfin-backend.env`. To run the full stack (mysql + maintenance + transaction + frontend) use `../sisfin-compose/docker-compose.yaml` instead.

Prod deploy on EC2 (from `../sisfin-artifact/application-update/`): `docker compose down` → `git pull` → `./mvnw clean package -DskipTests` → `docker build -t img_sisfin_transaction_dev:0.1 .` → `docker compose up -d`.

`feature-sql-script.sql` at the repo root is an ad-hoc migration script (adding FK columns to `stp_statement_pattern`) — not run automatically; treat new schema changes the same way unless told otherwise, and check `../sisfin-mysql/backup/` for the latest full dump to see current schema state.

## Architecture

Package root: `br.com.dfdevforge.sisfintransaction`. Each domain lives under either `statement/model/<domain>` (bank, statement, statementpattern, statementtype) or `transaction/model/<domain>` (account, location, objective, objectiveitem, objectivemovement, paymentmethod, summary), each with the same four sub-packages:

- **`entities/`** — JPA entities. Larger ones split `*Entity` (JPA mapping) from `*EntityProps` (field groupings) and `*EntityMethods`/`*EntityYaml` (behavior/serialization helpers) — see `statement`, `statementitem`, `objective`.
- **`repositories/`** — Spring Data JPA interfaces, plus a `*RepositoryCustomized` companion (interface + impl) for dynamic/criteria-style search that doesn't fit derived query methods. Some domains add purpose-built repos, e.g. `ObjectiveMovementRepositorySelectByPeriodAndDynamicFilters`, `AccountRepositorySearchAllTypeOutcoming`.
- **`services/`** — a `<Domain>BaseService` holding shared param/validation logic, plus one class per use case implementing `commons/services/CommonService`:
  - `AccessModuleService`, `AccessEditionService`, `AccessRegistrationService` — authorization checks before showing a module/form
  - `ExecuteSearchService`, `ExecuteRegistrationService`, `ExecuteEditionService`, `ExecuteExclusionService` — the actual operations
  `CommonService.execute()` is a template method: `validateUserAccess()` → `executeBusinessRule()` → `configureUserActions()` → `returnBusinessData()`. Concrete services only override the steps they need and accumulate output via `setArtifact(key, value)`.
- **`resources/`** (one per domain group, e.g. `BankResource`, `StatementResource`, `AccountResource`, `ObjectiveResource`, `ObjectiveMovementResource`, `PaymentMethodResource`, `LocationResource`, `SummaryResource`, `StatementPatternResource`, `StatementTypeResource`) — thin `@RestController`s. Every endpoint is `POST`, takes the entity as `@RequestBody` + `token` as `@RequestParam`, and just calls `service.setParams(entity, token); service.execute();`, wrapping the result in `ResourceDataEntity`.

Cross-cutting code lives in `commons/`: `BaseEntity`, `BaseException`, `BaseService` (also holds cross-domain lookups like `findBanksByUserIdentityOrderByNameAsc` used by other domains' services), `BasePersistence` (shared period start/end date helpers for statement queries), `CorsConfig`, `DatabaseConfig`, `ResourceExceptionHandler`, and utils (`Utils`, `DateUtils`, `DecryptUtils`, `LogUtils`, `ValueUtils`).

`transaction/charts/` (`BarChartData`, `LineChartData`, `PieChartData`, `DataSet`) are plain DTOs assembled by `SummaryBaseService`/`SummaryAccessModuleService` for dashboard chart data — not persisted entities.

**Auth**: `commons/feignclients/UserFeignClient` (`@FeignClient(url = "${SISFIN_URL_MAINTENANCE}")`) calls `GET /userfeignserver/{token}` on `sisfin-maintenance` to validate the `token` query/body param present on every request. `SisfinTransactionApplication` is annotated `@EnableFeignClients`.

**Errors**: `ResourceExceptionHandler` (`@ControllerAdvice`) maps `BaseException` subtypes and `DataIntegrityViolationException` (message-sniffed for MySQL duplicate-key vs. FK-constraint) to HTTP statuses; `UserUnauthorizedException` → 401, unhandled → 500 with a logged stack trace.

**DB naming convention**: tables/columns use a 3-letter entity prefix matching the Java entity, e.g. `acc_account`, `pam_payment_method`, `stt_statement_type`, `stp_statement_pattern`, `loc_location` — follow this when adding columns or new tables.

**Health check**: `GET /imrunning` (`ImRunning.java`) reports name/profile/version/timestamp, and additionally dumps all `SISFIN_*` env vars when `SISFIN_BACKEND_DEBUG_ACTIVATED=true`.

## Conventions

- Package/class names are English; comments and some log/exception strings are Portuguese — match the existing convention per file rather than normalizing.
- New use-case services should follow the existing `<Domain><Action>Service` naming and implement `CommonService` rather than adding ad-hoc controller logic.
