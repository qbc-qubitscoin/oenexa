#!/bin/bash
echo "Starting OENEXA Project locally..."

# Start Kafka & Postgres (Docker Compose)
echo "Starting Docker containers..."
docker-compose up -d

echo "Starting Java Wallet Service..."
./gradlew :oenexa-wallet-service:bootRun &
WALLET_PID=$!

echo "Starting Go Trading Service (with WebSocket Hub)..."
# shellcheck disable=SC2164
cd oenexa-trading-service
go run main.go &
TRADING_PID=$!
cd ..

echo "Starting Go Matching Engine..."
# shellcheck disable=SC2164
cd oenexa-matching-engine
go run main.go &
MATCHING_PID=$!
cd ..

echo "Starting React UI..."
# shellcheck disable=SC2164
cd oenexa-ui
npm run dev &
UI_PID=$!
cd ..

echo "All services started! Press Ctrl+C to stop."

# Wait for termination signal
# shellcheck disable=SC2064
trap "kill $WALLET_PID $TRADING_PID $MATCHING_PID $UI_PID; exit" SIGINT SIGTERM
wait
