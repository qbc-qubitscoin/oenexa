Write-Host "Starting OENEXA Project locally..." -ForegroundColor Cyan

# Start Docker Compose (Kafka, Postgres)
Write-Host "Starting Docker containers..." -ForegroundColor Green
docker-compose up -d

# Array to store job objects
$jobs = @()

Write-Host "Starting Java Wallet Service..." -ForegroundColor Yellow
$jobs += Start-Job -ScriptBlock {
    Set-Location -Path "c:\workspace\oenexa"
    .\gradlew :oenexa-wallet-service:bootRun
}

Write-Host "Starting Go Trading Service (with WebSocket Hub)..." -ForegroundColor Yellow
$jobs += Start-Job -ScriptBlock {
    Set-Location -Path "c:\workspace\oenexa\oenexa-trading-service"
    go run main.go
}

Write-Host "Starting Go Matching Engine..." -ForegroundColor Yellow
$jobs += Start-Job -ScriptBlock {
    Set-Location -Path "c:\workspace\oenexa\oenexa-matching-engine"
    go run main.go
}

Write-Host "Starting React UI..." -ForegroundColor Yellow
$jobs += Start-Job -ScriptBlock {
    Set-Location -Path "c:\workspace\oenexa\oenexa-ui"
    npm run dev
}

Write-Host "All services started in background jobs!" -ForegroundColor Cyan
Write-Host "To view logs, use: Receive-Job -Id <JobId>" -ForegroundColor Gray
Write-Host "To stop everything, press Ctrl+C and run: Stop-Job -State Running" -ForegroundColor Gray

# Wait indefinitely so the console stays open, or until Ctrl+C
try {
    Wait-Job -State Running
}
catch {
    Write-Host "Stopping all services..." -ForegroundColor Red
    $jobs | Stop-Job
}
