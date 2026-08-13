plugins {
    `java-library`
}

dependencies {
    api(libs.spring.boot.starter.web)
    api(libs.spring.boot.starter.validation)
    api(libs.spring.boot.starter.data.jpa)
    api(libs.commons.lang3)
    api(libs.guava)
    api(libs.spring.kafka)
    implementation("com.fasterxml.jackson.core:jackson-databind")
    implementation(libs.spring.boot.starter)
}

