plugins {
    alias(libs.plugins.spring.boot)
}

dependencies {
    implementation(project(":oenexa-common"))
    implementation(project(":oenexa-security-common"))
    implementation(libs.spring.boot.starter.web)
    implementation(libs.spring.boot.starter.websocket)
    implementation(libs.spring.boot.starter.data.redis)
    implementation(libs.spring.boot.starter.actuator)
    implementation(libs.spring.kafka)
    implementation(libs.micrometer.registry.prometheus)
}

