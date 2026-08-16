plugins {
    alias(libs.plugins.spring.boot)
}

dependencies {
    implementation(project(":oenexa-common"))
    implementation(project(":oenexa-security-common"))
    implementation(libs.spring.boot.starter.web)
    implementation(libs.spring.boot.starter.data.jpa)
    implementation(libs.spring.boot.starter.security)
    implementation(libs.bundles.jjwt)
    implementation(libs.spring.boot.starter.validation)
    implementation(libs.spring.boot.starter.data.redis)
    implementation(libs.spring.boot.starter.actuator)
    implementation(libs.spring.kafka)
    implementation(libs.flyway.core)
    implementation(libs.flyway.mysql)
    runtimeOnly(libs.mysql.connector.java)
    
    testImplementation("org.springframework.boot:spring-boot-starter-test")
    testImplementation("org.springframework.boot:spring-boot-resttestclient")
    testImplementation("org.springframework.boot:spring-boot-data-jpa-test")
    testImplementation("com.h2database:h2")
    testImplementation("io.cucumber:cucumber-java:7.34.6")
    testImplementation("io.cucumber:cucumber-spring:7.34.6")
    testImplementation("io.cucumber:cucumber-junit-platform-engine:7.34.6")
    testImplementation("org.junit.platform:junit-platform-suite")
}
