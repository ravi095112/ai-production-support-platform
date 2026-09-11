# AI Production Support Platform

> A production-oriented AI platform for incident management, knowledge retrieval, log analysis, and intelligent production support — built with Java, Spring Boot, PostgreSQL, Kafka, Redis, Python/FastAPI, Gemini, OpenTelemetry, Docker, Kubernetes, and Vue 3.

[![Java](https://img.shields.io/badge/Java-17-orange)](https://www.oracle.com/java/)
[![Spring Boot](https://img.shields.io/badge/Spring%20Boot-Production--oriented-brightgreen)](https://spring.io/projects/spring-boot)
[![PostgreSQL](https://img.shields.io/badge/PostgreSQL-16-blue)](https://www.postgresql.org/)
[![Kafka](https://img.shields.io/badge/Apache%20Kafka-Event--Driven-black)](https://kafka.apache.org/)
[![Docker](https://img.shields.io/badge/Docker-Containerized-2496ED)](https://www.docker.com/)

## Overview

The **AI Production Support Platform** is a portfolio-grade distributed system designed around a realistic enterprise production-support workflow.

It combines traditional backend engineering with event-driven architecture, observability, retrieval-augmented generation (RAG), and AI-assisted incident investigation.

The platform is being developed incrementally, with each stage designed to demonstrate production engineering practices rather than simple CRUD functionality.

### Core goals

* Manage production incidents through a reliable REST API
* Publish domain events asynchronously using Kafka
* Build a searchable operational knowledge base
* Use vector search for relevant troubleshooting information
* Integrate Gemini for AI-assisted investigation and response generation
* Correlate incidents, logs, services, and knowledge articles
* Add distributed tracing and metrics with OpenTelemetry
* Use Redis for caching and performance optimization
* Demonstrate resilience patterns such as retry, timeout, circuit breaker, and bulkhead
* Provide a Vue 3 operations dashboard
* Containerize the platform and prepare it for Kubernetes deployment
* Maintain automated testing and CI/CD

---

## Architecture

### Target architecture

```text
                         ┌──────────────────────┐
                         │      Vue 3 UI        │
                         │ Operations Dashboard  │
                         └──────────┬───────────┘
                                    │
                                    ▼
                         ┌──────────────────────┐
                         │     API Gateway      │
                         │ Security / Routing   │
                         └──────────┬───────────┘
                                    │
              ┌─────────────────────┼─────────────────────┐
              │                     │                     │
              ▼                     ▼                     ▼
      ┌───────────────┐     ┌───────────────┐     ┌───────────────┐
      │   Incident    │     │   Knowledge   │     │     Log       │
      │   Service     │     │   Service     │     │   Service     │
      │ Java/Spring   │     │ Java/Spring   │     │ Java/Spring   │
      └───────┬───────┘     └───────┬───────┘     └───────┬───────┘
              │                     │                     │
              └─────────────────────┼─────────────────────┘
                                    │
                                    ▼
                              ┌───────────┐
                              │   Kafka   │
                              │   Events  │
                              └─────┬─────┘
                                    │
                    ┌───────────────┼────────────────┐
                    │               │                │
                    ▼               ▼                ▼
             ┌────────────┐  ┌────────────┐  ┌──────────────┐
             │ Notification│  │ AI / RAG   │  │ Future       │
             │ Service    │  │ FastAPI    │  │ Consumers    │
             └────────────┘  └─────┬──────┘  └──────────────┘
                                   │
                                   ▼
                             ┌───────────┐
                             │  Gemini   │
                             │    AI     │
                             └───────────┘

       ┌───────────────────┐       ┌───────────────────┐
       │ PostgreSQL        │       │ Redis             │
       │ + pgvector        │       │ Cache / state     │
       └───────────────────┘       └───────────────────┘

                         ┌──────────────────────┐
                         │ OpenTelemetry        │
                         │ Metrics / Tracing    │
                         └──────────────────────┘
```

---

## Technology Stack

| Area               | Technology                                |
| ------------------ | ----------------------------------------- |
| Backend            | Java 17                                   |
| Framework          | Spring Boot                               |
| API                | REST / JSON                               |
| Persistence        | Spring Data JPA / Hibernate               |
| Database           | PostgreSQL 16                             |
| Database Migration | Flyway                                    |
| Vector Search      | pgvector                                  |
| Messaging          | Apache Kafka                              |
| Cache              | Redis                                     |
| AI Service         | Python / FastAPI                          |
| LLM                | Gemini                                    |
| Frontend           | Vue 3                                     |
| Security           | Spring Security / JWT                     |
| Resilience         | Resilience4j                              |
| Observability      | OpenTelemetry                             |
| Containers         | Docker                                    |
| Orchestration      | Kubernetes                                |
| Testing            | JUnit / Spring Boot Test / Testcontainers |
| CI/CD              | GitHub Actions                            |

---

## Repository Structure

```text
ai-production-support-platform/
│
├── services/
│   ├── incident-service/
│   │   ├── src/
│   │   │   ├── main/
│   │   │   │   ├── java/
│   │   │   │   └── resources/
│   │   │   │       └── db/migration/
│   │   │   └── test/
│   │   ├── build.gradle
│   │   ├── settings.gradle
│   │   ├── gradlew
│   │   └── gradlew.bat
│   │
│   ├── knowledge-service/       # Planned
│   ├── log-service/             # Planned
│   ├── notification-service/    # Planned
│   └── ai-service/              # Planned
│
├── frontend/                    # Planned Vue 3 application
├── infrastructure/              # Planned Docker/Kubernetes configuration
├── docker-compose.yml
├── .env.example
└── README.md
```

---

# Current Implementation

## Incident Service

The first production-oriented service provides an incident management API.

### Implemented capabilities

* Incident creation
* Incident retrieval
* Incident update
* Incident deletion
* UUID primary keys
* Business incident numbers
* PostgreSQL sequence-backed incident numbering
* Incident severity
* Incident lifecycle status
* Service ownership
* Assignment
* Pagination
* Filtering
* Sorting
* Request validation
* Consistent API error responses
* Optimistic locking with JPA `@Version`
* Database schema management through Flyway
* Transaction boundaries using Spring `@Transactional`
* Actuator health endpoint
* Database indexes for frequently queried fields

### Incident lifecycle

```text
OPEN
  │
  ▼
INVESTIGATING
  │
  ▼
MITIGATED
  │
  ▼
RESOLVED
  │
  ▼
CLOSED
```

---

## API

Base URL:

```text
http://localhost:8081/api/v1/incidents
```

### Create incident

```http
POST /api/v1/incidents
Content-Type: application/json
```

Example:

```json
{
  "title": "Payment service latency increased",
  "description": "p99 latency increased above the production threshold.",
  "severity": "HIGH",
  "serviceName": "payment-service",
  "assignedTo": "platform-team"
}
```

### Get incident

```http
GET /api/v1/incidents/{id}
```

### Search incidents

```http
GET /api/v1/incidents?page=0&size=20
```

Supported filters:

```text
status
severity
serviceName
page
size
```

Example:

```http
GET /api/v1/incidents?status=OPEN&severity=HIGH&serviceName=payment-service&page=0&size=20
```

The API protects pagination from excessively large page sizes:

```text
1 <= size <= 100
```

### Update incident

```http
PATCH /api/v1/incidents/{id}
Content-Type: application/json
```

Example:

```json
{
  "status": "INVESTIGATING",
  "assignedTo": "platform-team"
}
```

### Delete incident

```http
DELETE /api/v1/incidents/{id}
```

Returns:

```text
204 No Content
```

---

# API Error Handling

The service returns a consistent error structure.

Example validation response:

```json
{
  "timestamp": "2026-09-10T18:30:00Z",
  "status": 400,
  "error": "BAD_REQUEST",
  "message": "Title is required",
  "path": "/api/v1/incidents"
}
```

Current handling includes:

* Validation failures → `400 Bad Request`
* Invalid pagination parameters → `400 Bad Request`
* Missing resources → `404 Not Found`
* Unexpected server errors → `500 Internal Server Error`

---

# Database Design

The Incident Service uses PostgreSQL.

### Main table

```text
incident
├── id
├── incident_number
├── title
├── description
├── severity
├── status
├── service_name
├── assigned_to
├── created_at
├── updated_at
└── version
```

### Indexes

Indexes currently exist for:

```text
status
severity
service_name
```

### Incident number generation

Incident numbers are generated using a PostgreSQL sequence:

```text
incident_number_seq
```

Example:

```text
INC-10001
INC-10002
INC-10003
```

This keeps the business identifier separate from the UUID database identifier.

---

# Database Migrations

Flyway manages database schema changes.

Current migrations:

```text
V1__create_incident_table.sql
V2__create_incident_number_sequence.sql
```

The application uses:

```properties
spring.jpa.hibernate.ddl-auto=validate
```

This is intentional.

Hibernate validates the schema rather than creating or modifying production database structures automatically. Schema changes are version-controlled through Flyway migrations.

---

# Local Development

## Prerequisites

Install:

* Java 17
* Docker Desktop
* Git

---

## 1. Clone the repository

```bash
git clone https://github.com/ravi095112/ai-production-support-platform.git
cd ai-production-support-platform
```

---

## 2. Configure environment variables

Create a local `.env` file from the example:

```bash
cp .env.example .env
```

Example:

```text
POSTGRES_DB=incident_db
POSTGRES_USER=incident_user
POSTGRES_PASSWORD=change-me
```

> `.env` is intentionally excluded from Git. Never commit real credentials, API keys, tokens, or passwords.

---

## 3. Start PostgreSQL

From the repository root:

```bash
docker compose up -d postgres
```

Check the container:

```bash
docker ps
```

Check PostgreSQL readiness:

```bash
docker exec ai-support-postgres pg_isready -U incident_user -d incident_db
```

Expected:

```text
accepting connections
```

---

## 4. Run the Incident Service

```bash
cd services/incident-service
```

Windows:

```bash
./gradlew.bat bootRun
```

The service starts on:

```text
http://localhost:8081
```

---

## 5. Verify health

```bash
curl http://localhost:8081/actuator/health
```

Expected:

```json
{
  "status": "UP"
}
```

---

# Testing

Run the complete test suite:

```bash
./gradlew.bat clean test
```

The project uses automated tests to validate application startup and database integration.

As the platform evolves, the test strategy will expand to include:

* Unit tests
* Repository tests
* Controller tests
* Integration tests
* Kafka integration tests
* Testcontainers
* Contract testing
* Resilience/failure scenarios

---

# Configuration and Secrets

Configuration follows environment-based principles.

Example:

```properties
spring.datasource.username=${POSTGRES_USER:incident_user}
spring.datasource.password=${POSTGRES_PASSWORD}
```

Secrets should never be committed to source control.

Tracked example:

```text
.env.example
```

Ignored local configuration:

```text
.env
```

For production deployments, secrets will be supplied through the deployment environment or a dedicated secrets-management mechanism.

---

# Engineering Practices

This project intentionally demonstrates production engineering patterns.

### API design

* Versioned API paths
* DTO-based request/response contracts
* Validation at the API boundary
* Consistent error responses
* Pagination
* Filtering
* Sorting

### Persistence

* PostgreSQL
* Flyway migrations
* JPA/Hibernate
* Database indexes
* Optimistic locking
* Explicit transaction boundaries
* `Instant` timestamps

### Reliability

Planned resilience capabilities include:

* Timeout
* Retry
* Circuit breaker
* Bulkhead
* Dead-letter queues
* Idempotent event processing
* Failure recovery

### Observability

Planned observability includes:

* Structured logging
* Correlation IDs
* Distributed tracing
* Metrics
* Request latency
* Error rates
* Database timing
* Kafka consumer metrics

---

# Roadmap

## Stage 1 — Incident Service

**Status: Completed**

* [x] Spring Boot service
* [x] PostgreSQL integration
* [x] Flyway migrations
* [x] Incident CRUD
* [x] Validation
* [x] Pagination
* [x] Filtering
* [x] Sorting
* [x] Error handling
* [x] Optimistic locking
* [x] Actuator health endpoint
* [x] Database-backed incident number generation

---

## Stage 2 — Event-Driven Architecture

**Next**

* [ ] Kafka integration
* [ ] Incident-created event
* [ ] Incident-updated event
* [ ] Event schema/versioning
* [ ] Idempotent consumers
* [ ] Retry strategy
* [ ] Dead-letter topic
* [ ] Consumer error handling
* [ ] Notification consumer
* [ ] Transaction/event consistency strategy

---

## Stage 3 — Knowledge and RAG

* [ ] Knowledge service
* [ ] Document ingestion
* [ ] Chunking
* [ ] Embeddings
* [ ] pgvector
* [ ] Semantic search
* [ ] Metadata filtering
* [ ] Retrieval pipeline
* [ ] Knowledge source tracking

---

## Stage 4 — AI Investigation Service

* [ ] Python/FastAPI service
* [ ] Gemini integration
* [ ] Incident summarization
* [ ] Root-cause investigation assistance
* [ ] Relevant knowledge retrieval
* [ ] Suggested remediation
* [ ] Confidence/grounding information
* [ ] AI response auditing

Target workflow:

```text
Incident
   │
   ├── Recent logs
   ├── Service metadata
   ├── Similar incidents
   └── Knowledge articles
             │
             ▼
        Retrieval Layer
             │
             ▼
           Gemini
             │
             ▼
     Investigation Summary
             │
             ├── Evidence
             ├── Possible cause
             ├── Recommended actions
             └── Confidence
```

---

## Stage 5 — Observability

* [ ] OpenTelemetry
* [ ] Distributed tracing
* [ ] Correlation IDs
* [ ] Metrics
* [ ] Trace propagation across services
* [ ] Slow database query visibility
* [ ] Kafka processing latency
* [ ] AI request latency

---

## Stage 6 — Performance and Resilience

* [ ] Redis caching
* [ ] Cache invalidation strategy
* [ ] Resilience4j
* [ ] Timeouts
* [ ] Retry policies
* [ ] Circuit breakers
* [ ] Bulkheads
* [ ] Load testing
* [ ] Performance baselines
* [ ] p95/p99 latency analysis

---

## Stage 7 — Vue Operations Dashboard

* [ ] Incident dashboard
* [ ] Incident search
* [ ] Filters
* [ ] Incident details
* [ ] Timeline
* [ ] AI investigation results
* [ ] Knowledge search
* [ ] Service health overview

---

## Stage 8 — Production Deployment

* [ ] Docker images
* [ ] Docker Compose development environment
* [ ] Kubernetes manifests
* [ ] ConfigMaps
* [ ] Secrets
* [ ] Readiness probes
* [ ] Liveness probes
* [ ] Horizontal scaling
* [ ] GitHub Actions CI/CD

---

# Production Scenarios

### High latency

```text
Payment API
    ↓
p99 latency increases
    ↓
Incident created
    ↓
Kafka event
    ↓
Log collection
    ↓
Similar incident retrieval
    ↓
RAG
    ↓
Gemini investigation
    ↓
Suggested remediation
```

### Repeated production failure

```text
New Incident
     ↓
Search historical incidents
     ↓
Find similar failure
     ↓
Retrieve previous resolution
     ↓
Generate investigation summary
     ↓
Support engineer reviews recommendation
```

### Downstream dependency failure

```text
Order Service
     ↓
Payment Service timeout
     ↓
Circuit breaker opens
     ↓
Incident created
     ↓
Event published
     ↓
Notification
     ↓
AI-assisted investigation
```

---

# Design Principles

The project follows several engineering principles:

1. **Production over toy implementations**
2. **Explicit transaction boundaries**
3. **Schema changes through migrations**
4. **No secrets in source control**
5. **Idempotent distributed processing**
6. **Observable systems**
7. **Failure-aware design**
8. **Backward-compatible event evolution**
9. **Test critical behavior**
10. **Prefer measurable performance over assumptions**

---

# Why This Project?

Modern production support requires more than CRUD APIs.

A real support platform needs to connect:

```text
Incidents
   +
Logs
   +
Services
   +
Historical incidents
   +
Knowledge
   +
Distributed tracing
   +
AI
```

The goal of this project is to demonstrate how these components can be combined into a maintainable, observable, resilient distributed system.

It also provides a practical demonstration of senior-level backend engineering concepts including:

* Java and Spring Boot
* Distributed systems
* Event-driven architecture
* Kafka delivery semantics
* Idempotency
* Database consistency
* Optimistic locking
* Distributed tracing
* Caching
* Resilience patterns
* RAG architecture
* LLM integration
* API design
* Containerization
* Kubernetes
* CI/CD

---

# Development Status

| Component              | Status         |
| ---------------------- | -------------- |
| Incident Service       | 🟢 Implemented |
| PostgreSQL             | 🟢 Implemented |
| Flyway                 | 🟢 Implemented |
| REST API               | 🟢 Implemented |
| Validation             | 🟢 Implemented |
| Pagination / Filtering | 🟢 Implemented |
| Kafka                  | 🟡 Planned     |
| Knowledge Service      | 🟡 Planned     |
| pgvector / RAG         | 🟡 Planned     |
| Gemini AI Service      | 🟡 Planned     |
| Redis                  | 🟡 Planned     |
| OpenTelemetry          | 🟡 Planned     |
| Vue Dashboard          | 🟡 Planned     |
| Kubernetes             | 🟡 Planned     |
| CI/CD                  | 🟡 Planned     |

---

# Author

**Ravi Kumar**

Java Backend Engineer focused on:

* Java
* Spring Boot
* Distributed Systems
* Event-Driven Architecture
* AI / GenAI
* RAG
* Cloud-native backend engineering

---

## License

This project is licensed under the MIT License. See `LICENSE` for details.
