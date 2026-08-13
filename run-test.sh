#!/bin/bash

echo "========================================="
echo "Running all tests for OENEXA project..."
echo "========================================="

# Ensure gradlew has execute permission
chmod +x ./gradlew

# Run all tests using Gradle wrapper
./gradlew test

if [ $? -eq 0 ]; then
    echo "========================================="
    echo "✅ All tests completed successfully!"
    echo "========================================="
else
    echo "========================================="
    echo "❌ Some tests failed. Please check the logs above."
    echo "========================================="
    exit 1
fi
