# Docker Deployment Guide - Product Ordering Microservices

## ✅ Docker Images Status

All 6 microservices have Docker images successfully built and ready for containerization.

### Docker Images Summary

| Service | Image Name | Size | Status |
|---------|-----------|------|--------|
| Discovery Server (Eureka) | `productordering/discovery-server:latest` | 327MB | ✅ Ready |
| Product Service | `productordering/product-service:latest` | 317MB | ✅ Ready |
| Order Service | `productordering/order-service:latest` | 372MB | ✅ Ready |
| Inventory Service | `productordering/inventory-service:latest` | 343MB | ✅ Ready |
| Notification Service | `productordering/notification-service:latest` | 331MB | ✅ Ready |
| API Gateway | `productordering/api-gateway:latest` | 319MB | ✅ Ready |

**Total Container Images:** 6  
**Total Storage:** ~2.0 GB

---

## 📋 Deployment Options

### Option 1: Docker Compose (Recommended for Development/Testing)

Deploy entire stack with Docker Compose:

```bash
# Start all services
docker-compose up -d

# View running containers
docker-compose ps

# View logs for all services
docker-compose logs -f

# Stop all services
docker-compose down

# Stop and remove volumes
docker-compose down -v
```

### Option 2: Individual Container Deployment

Deploy each service separately:

```bash
# Discovery Server (Eureka Registry)
docker run -d \
  --name discovery-server \
  -p 8761:8761 \
  productordering/discovery-server:latest

# Product Service
docker run -d \
  --name product-service \
  --link mongodb:mongodb \
  --link discovery-server:discovery-server \
  -e spring.data.mongodb.uri=mongodb://mongodb:27017/product-service \
  productordering/product-service:latest

# Inventory Service
docker run -d \
  --name inventory-service \
  --link mysql:mysql \
  --link discovery-server:discovery-server \
  -e spring.datasource.url=jdbc:mysql://mysql:3306/inventory-service \
  -e spring.datasource.username=root \
  -e spring.datasource.password=root@1234 \
  productordering/inventory-service:latest

# Order Service
docker run -d \
  --name order-service \
  -p 8081:8081 \
  --link mysql:mysql \
  --link discovery-server:discovery-server \
  --link kafka:kafka \
  -e spring.datasource.url=jdbc:mysql://mysql:3306/order-service \
  -e spring.datasource.username=root \
  -e spring.datasource.password=root@1234 \
  -e spring.kafka.bootstrap-servers=kafka:9092 \
  productordering/order-service:latest

# Notification Service
docker run -d \
  --name notification-service \
  --link discovery-server:discovery-server \
  --link kafka:kafka \
  -e spring.kafka.bootstrap-servers=kafka:9092 \
  productordering/notification-service:latest

# API Gateway
docker run -d \
  --name api-gateway \
  -p 8080:8080 \
  --link discovery-server:discovery-server \
  productordering/api-gateway:latest
```

### Option 3: Kubernetes Deployment

For production-grade deployments, create Kubernetes manifests for each service.

---

## 🔧 Service Configuration

### Environment Variables for Docker

Each service can be configured with environment variables:

**Product Service:**
- `spring.data.mongodb.uri` - MongoDB connection string
- `eureka.client.serviceUrl.defaultZone` - Eureka registry URL

**Order & Inventory Services:**
- `spring.datasource.url` - MySQL connection string
- `spring.datasource.username` - Database user
- `spring.datasource.password` - Database password
- `eureka.client.serviceUrl.defaultZone` - Eureka registry URL

**Order & Notification Services:**
- `spring.kafka.bootstrap-servers` - Kafka broker address
- `eureka.client.serviceUrl.defaultZone` - Eureka registry URL

---

## 📊 Access Points

Once deployed, access services at:

| Service | URL | Details |
|---------|-----|---------|
| API Gateway | `http://localhost:8080` | Main entry point |
| Discovery Server (Eureka) | `http://localhost:8761` | Service registry |
| Order Service | `http://localhost:8081` | Direct service access |

---

## 🚀 Quick Start with Docker Compose

### Prerequisites
- Docker Desktop 4.0+ installed
- Docker Daemon running
- 8GB RAM minimum
- 2.0GB disk space for images

### Deploy

```bash
# Navigate to project root
cd productOrdering-new

# Start all services
docker-compose up -d

# Wait 30 seconds for services to initialize
# Check status
docker ps

# Access Eureka Dashboard
# Open browser: http://localhost:8761
```

### Expected Output

All 6 services + 4 infrastructure services (MySQL, MongoDB, Kafka, Zookeeper) will be running.

---

## 📝 Base Images Used

All services use the Eclipse Temurin JDK image for optimal Java 17 support:

```
Base Image: eclipse-temurin:17.0.4.1_1-jre
- Lightweight JRE-only image
- Security hardened
- Regular updates
```

---

## ✅ Verification

To verify containers are running correctly:

```bash
# List all running containers
docker ps

# View logs
docker logs <container-name>

# Check service health
docker exec <container-name> curl http://localhost:8080/health

# Access Eureka to verify service registration
# URL: http://localhost:8761
```

---

## 🛑 Stopping Services

```bash
# Stop all running containers
docker-compose down

# Remove volumes (data will be lost)
docker-compose down -v

# Or manually stop individual containers
docker stop <container-name>
docker rm <container-name>
```

---

## 🐛 Troubleshooting

### Services not registering with Eureka
- Check network connectivity: `docker network ls`
- Verify Eureka is running: `docker logs discovery-server`
- Check service logs: `docker logs <service-name>`

### Database connection errors
- Verify MySQL/MongoDB are running and healthy
- Check network alias in docker-compose.yml
- Verify credentials match environment variables

### Kafka connection issues
- Ensure Zookeeper is running first
- Check Kafka logs: `docker logs kafka`
- Verify bootstrap servers match configuration

---

## 📚 Docker Image Details

Each image includes:
- Full Spring Boot application
- Embedded Tomcat/Netty server
- Pre-configured JVM settings for containers
- Health check endpoints
- Actuator endpoints for monitoring

**Image Structure:**
```
FROM eclipse-temurin:17.0.4.1_1-jre
├── Application JAR
└── Spring Boot loader
```

---

## Next Steps

1. **Push to Registry:** Push images to Docker Hub or private registry
   ```bash
   docker tag productordering/discovery-server:latest <your-registry>/discovery-server:latest
   docker push <your-registry>/discovery-server:latest
   ```

2. **Deploy to Production:** Use Docker Swarm or Kubernetes
3. **Monitor:** Implement Docker monitoring with Prometheus/Grafana
4. **CI/CD:** Automate image builds in GitHub Actions or Jenkins

---

**Status:** ✅ All Docker images ready for container deployment
