plugins {
    `java-library`
}

dependencies {
    api(project(":oenexa-common"))
    api(libs.spring.boot.starter.security)
    api(libs.bundles.jjwt)
    api(libs.spring.boot.starter.data.redis)
    implementation(libs.bouncycastle)
}

