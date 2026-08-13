Write-Host "========================================="
Write-Host "Running all tests for OENEXA project..." -ForegroundColor Cyan
Write-Host "========================================="

# Run all tests using Gradle wrapper
.\gradlew.bat test

if ($LASTEXITCODE -eq 0) {
    Write-Host "========================================="
    Write-Host "✅ All tests completed successfully!" -ForegroundColor Green
    Write-Host "========================================="
} else {
    Write-Host "========================================="
    Write-Host "❌ Some tests failed. Please check the logs above." -ForegroundColor Red
    Write-Host "========================================="
    exit 1
}
