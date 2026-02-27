# Product Ordering Microservices

A modular e-commerce backend implemented as a set of Spring Boot microservices. Each service is a self-contained module that supports independent development, testing, and deployment. The project includes service discovery, an API gateway, persistence layers, and asynchronous messaging.

## Project Description

This repository contains a sample product-ordering application split into microservices:

- **Discovery Server**: Eureka service registry for locating services
- **API Gateway**: Spring Cloud Gateway for routing and edge concerns
- **Product Service**: Product catalog backed by MongoDB
- **Order Service**: Order processing backed by MySQL and emits Kafka events
- **Inventory Service**: Inventory management backed by MySQL
- **Notification Service**: Consumes Kafka events and handles notifications

The services are designed for local development (via `docker-compose`) and for deployment to container platforms or Kubernetes.

## Architecture

- Service discovery: Eureka (Discovery Server)
- Gateway: Spring Cloud Gateway
- Persistence: MongoDB for product data; MySQL for order/inventory data
- Messaging: Kafka (with Zookeeper)
- Containerization: Docker images for all services and `docker-compose.yml` for local stack

High-level flow:
1. Client -> API Gateway
2. Gateway routes to services registered in Eureka
3. Order Service records orders in MySQL and publishes events to Kafka
4. Notification Service consumes Kafka events and processes them (e.g., email)

## Prerequisites

- Java 17 JDK (recommended for running locally)
- Maven 3.8+
- Docker & Docker Compose (for containerized runs)
- Git

## Quick Start

1. Build the project:

```bash
mvn clean install -DskipTests
```

2. Start the full stack (MySQL, MongoDB, Kafka, Zookeeper + all services):

```bash
docker-compose up -d
```

3. Check services:

```bash
# Eureka dashboard
open http://localhost:8761

# API Gateway
open http://localhost:8080
```

Replace `open` with your platform's browser command or just visit the URL in a browser.

## File Structure

```
productOrdering-new/
├── api-gateway/
├── discovery-server/
├── product-service/
├── order-service/
├── inventory-service/
├── notification-service/
├── docker-compose.yml
├── DOCKER_DEPLOYMENT.md
└── README.md
```

Each service directory contains a `pom.xml`, `src/` and a `Dockerfile` (for container builds).

## Notes

- Sensitive values (DB passwords, Eureka creds) are currently stored in `application.properties` for development convenience; for production, use environment variables or a secrets manager.
- The project was containerized so images can be pushed to a registry and deployed to Kubernetes or any container host.

## Thank You

Thank you for using this sample Product Ordering microservices project. If you want help pushing the code to GitHub, setting up CI/CD, or creating Kubernetes manifests, tell me which platform you prefer and I will help.
