plugins {
    alias(libs.plugins.spring.boot)
}

dependencies {
    implementation(project(":oenexa-common"))
    implementation(project(":oenexa-security-common"))
    implementation(libs.spring.cloud.starter.gateway)
    implementation(libs.spring.boot.starter.data.redis)
    implementation(libs.spring.boot.starter.actuator)
    implementation(libs.resilience4j.spring.boot)
    implementation(libs.micrometer.registry.prometheus)
}

