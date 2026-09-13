# Spring AI Tutor — Backend

A Spring Boot 4.1 backend for the Spring AI Tutor interactive tutorial. Provides REST APIs for 16 hands-on Spring AI 2.0.1 modules including chat, RAG, structured output, memory, tools, embeddings, and more.

## 🚀 Quick Start

### Prerequisites

- **Java 21+** ([Download](https://www.oracle.com/java/technologies/downloads/))
- **Gradle** (or use the included Gradle wrapper)
- **OpenRouter API key** — get a free key at [https://openrouter.ai/keys](https://openrouter.ai/keys)

### Setup

```bash
# Clone the repository
git clone https://github.com/iranna-m-31/springai.git
cd springai

# Configure your API key
cp .env.example .env
# Edit .env and add your OPENROUTER_API_KEY

# Build and run the server
./gradlew bootRun
```

The backend will start at **http://localhost:8080**.

### Verify the API

```bash
# Health check
curl http://localhost:8080/api/health

# Plain chat
curl "http://localhost:8080/ai?userInput=Tell%20me%20a%20joke"

# Streaming chat
curl -N "http://localhost:8080/ai/stream?userInput=Write%20a%20short%20poem"
```

## 📁 Project Structure

```
src/main/java/com/imm/springai/
├── SpringaiApplication.java    # Main Spring Boot application
├── ChatController.java          # Core chat endpoints
├── TutorController.java         # Interactive tutorial endpoints
├── RagController.java           # RAG/vector store endpoints
├── ToolController.java          # Function calling tools
├── MemoryController.java        # Conversation memory
├── EvalController.java          # Evaluation & structured output
├── EmbeddingController.java     # Embedding & vector search
├── DownloadController.java      # File download endpoints
├── CallLogController.java       # Call logging
├── FeatureCatalogController.java  # Feature catalog
├── HealthCheckController.java   # Health check
├── WebConfig.java               # Web configuration
├── tutor/                       # Tutor-specific components
│   ├── CallLogAdvisor.java      # Call logging advisor
│   ├── GlobalExceptionHandler.java  # Global error handler
│   └── ...
└── ChatClientConfig.java        # Spring AI ChatClient configuration
```

## 🔌 API Endpoints

| Endpoint | Method | Description |
|----------|--------|-------------|
| `/api/health` | GET | Health check |
| `/ai` | GET | Plain chat |
| `/ai/stream` | GET | Streaming chat |
| `/ai/rag` | GET | RAG search |
| `/ai/structured` | POST | Structured JSON output |
| `/ai/template` | GET | Prompt templates |
| `/ai/meta` | GET | Chat response metadata |
| `/api/tutor/*` | GET | Tutorial features |

## 🛠️ Configuration

The `.env` file controls the backend behavior:

```properties
# Required
OPENROUTER_API_KEY=sk-or-v1-...
OPENROUTER_BASE_URL=https://openrouter.ai/api/v1
OPENROUTER_CHAT_MODEL=openrouter/free

# Optional
PORT=8080
CORS_ALLOWED_ORIGINS=http://localhost:5173,http://localhost:3000,http://localhost:8080
VECTOR_STORE=simple
ACTUATOR_ENABLED=true
```

## 🏗️ Build

```bash
# Build JAR
./gradlew bootJar

# Run tests
./gradlew test
```

### Running the JAR (important)

The `java -jar` command does **not** automatically load `.env`. You must pass the API key as an environment variable:

```bash
# Option 1: Export env vars first
cp .env.example .env
source .env
java -jar build/libs/spring-ai-*.jar

# Option 2: Inline (Linux/macOS)
OPENROUTER_API_KEY=sk-or-v1-... java -jar build/libs/spring-ai-*.jar

```

Or use `./gradlew bootRun` which reads `.env` automatically:

```bash
./gradlew bootRun
```

## 🐳 Docker Deployment

### Local Deployment

```bash
# Build the image
docker build -t spring-ai-backend .

# Run with .env file
docker run -p 8080:8080 --env-file .env spring-ai-backend

# Or with environment variables directly
docker run -p 8080:8080 \
  -e OPENROUTER_API_KEY=sk-or-v1-... \
  -e PORT=8080 \
  spring-ai-backend
```

### Using Docker Compose (Recommended for Local)

```bash
# Start the backend
docker compose up -d

# View logs
docker compose logs -f spring-ai-backend

# Stop
docker compose down
```

### Production Deployment

For production, use the multi-stage Dockerfile which builds from source inside the container for a reproducible build:

```bash
# Build production image
docker build -t spring-ai-backend:latest .

# Run with environment variables (never commit .env to git!)
docker run -d \
  -p 8080:8080 \
  --env-file .env \
  --name spring-ai \
  spring-ai-backend:latest
```

**Production environment variables:**

```properties
# Required
OPENROUTER_API_KEY=sk-or-v1-...
OPENROUTER_BASE_URL=https://openrouter.ai/api/v1
OPENROUTER_CHAT_MODEL=openrouter/free

# Server
PORT=8080
CORS_ALLOWED_ORIGINS=https://yourdomain.com

# Optional: use pgvector or Qdrant instead of simple in-memory store
# VECTOR_STORE=pgvector
# SPRING_PROFILES_ACTIVE=production
```

**Deploying to Render:**

The project includes `render.yaml` for one-click Render deployment:

```bash
# Deploy to Render (requires Render CLI)
render deploy
```

Or connect your GitHub repo to Render with the Dockerfile.

### Docker Commands Reference

| Command | Description |
|---------|-------------|
| `docker build -t spring-ai-backend .` | Build the Docker image |
| `docker run -p 8080:8080 --env-file .env spring-ai-backend` | Run the container |
| `docker compose up -d` | Start with Docker Compose |
| `docker compose logs -f` | View logs |
| `docker compose down` | Stop all services |
| `docker ps` | List running containers |
| `docker stop spring-ai-backend` | Stop the container |

## 📚 Documentation

- [Spring AI Reference](https://docs.spring.io/spring-ai/reference/index.html)
- [Spring Boot Reference](https://docs.spring.io/spring-boot/4.1.1/reference/htmlsingle/)

## 🤝 Contributing

Contributions are welcome! Please feel free to submit a Pull Request.

## 📄 License

This project is open source and available under the [MIT License](LICENSE).
