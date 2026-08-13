# OENEXA Trading Platform

OENEXA is a high-performance, hybrid-architecture cryptocurrency exchange and trading platform. It utilizes a Domain-Driven UI Architecture on the frontend, and a highly scalable microservice architecture on the backend.

## 🏗️ Architecture Overview

The platform uses a polyglot microservice architecture to maximize performance where it's needed (matching engine) and maintain enterprise-grade reliability where required (wallets and KYC).

### Backend Services
1. **Wallet Service (Java 25 / Spring Boot 4.1.0)**
   - Handles deposits, withdrawals, and balance locking.
   - Built with Java for robust transactional integrity and rich enterprise libraries.
2. **Trading Service (Go)**
   - Acts as the API Gateway for trading interactions.
   - Maintains real-time WebSocket connections with clients for live orderbook and ticker updates.
   - Pushes user orders to the Kafka `orders` topic.
3. **Matching Engine (Go)**
   - The high-speed core of the exchange.
   - Consumes orders from Kafka, matches them in-memory, and broadcasts `trades` and `orderbook` updates back to Kafka for the Trading Service to distribute.

### Frontend Service
- **React UI (`oenexa-ui`)**
  - Built with React 19, TypeScript, Vite, and TailwindCSS.
  - Follows a strictly **Domain-Driven Architecture** (grouped by features rather than file types).
  - Designed with a Binance-inspired dark mode UI.

---

## 🛠️ Step-by-Step Development Process

### 1. Infrastructure Setup
- Configured a `docker-compose.yml` to spin up **Kafka** (for event streaming) and **PostgreSQL** (for persistent storage).
- Set up the root directory to hold the various backend services and the frontend monorepo.

### 2. Backend Initialization
- Bootstrapped the `oenexa-wallet-service` using Spring Boot, configuring JPA to map entities to PostgreSQL.
- Initialized the Go modules (`oenexa-trading-service` and `oenexa-matching-engine`).
- Integrated the Go services with `segmentio/kafka-go` to allow them to publish and subscribe to the Kafka event bus.

### 3. Frontend Domain-Driven Architecture
- Initialized the `oenexa-ui` directory using Vite.
- Implemented the Domain-Driven folder structure:
  - `src/features/trading` (OrderBook, PriceTicker, RecentTrades)
  - `src/features/wallet`
  - `src/components/landing` (MarketWidget, HeroSection)
- Mapped Tailwind colors to match a professional crypto-exchange dark theme.

### 4. Real-Time WebSockets
- Added `gorilla/websocket` to the Go Trading Service to establish a `/ws` endpoint.
- Built a background simulator in Go to broadcast live BTC prices over the WebSocket Hub.
- Created the `useMarketData` React hook to connect the frontend to the backend WebSocket stream, automatically updating the UI prices in real-time.

### 5. Unified Orchestration
- Created `run-all.sh` and `run-all.ps1` to easily start the entire ecosystem (Docker, Java, Go, and Node) concurrently with a single command.

---

## 🚀 How to Run Locally

### Prerequisites
- [Docker Desktop](https://www.docker.com/products/docker-desktop/) must be installed and running.
- Java 25 installed.
- Go 1.22+ installed.
- Node.js 20+ installed.

### Startup
1. Ensure Docker Desktop is running.
2. Open a terminal in the root directory.
3. Run the startup script:
   - **Windows:** `.\run-all.ps1`
   - **Linux/Mac:** `./run-all.sh`

This script will automatically boot the database, the Kafka broker, compile and run the backend microservices, and start the Vite dev server for the frontend.

Access the UI at: `http://localhost:5174` (or 5173).
