.PHONY: up down logs dev build test clean

# Infrastructure
up:
	docker-compose -f infra/docker-compose.yml up -d

down:
	docker-compose -f infra/docker-compose.yml down

logs:
	docker-compose -f infra/docker-compose.yml logs -f

# Développement
dev: up
	@echo "Démarrage du développement..."
	@echo "Backend: http://localhost:8080"
	@echo "Frontend: http://localhost:8081"
	@echo "Kafka: localhost:9092"

# Build
build:
	cd backend && mvn clean package -DskipTests
	cd frontend && npm ci && npm run build

# Tests
test:
	cd backend && mvn test
	cd frontend && npm test

# Nettoyage
clean:
	docker-compose -f infra/docker-compose.yml down -v
	cd backend && mvn clean
	cd frontend && rm -rf node_modules dist
