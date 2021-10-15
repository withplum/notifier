plugins {
    `kotlin-dsl`
    `java-gradle-plugin`
    id("maven-publish")
    id("com.gradle.plugin-publish") version "0.16.0"
    kotlin("kapt")
}

repositories {
    mavenCentral()
}

dependencies {
    implementation("org.jetbrains.kotlin:kotlin-stdlib:_")
    implementation(Square.retrofit2.retrofit)
    implementation(Square.okHttp3.loggingInterceptor)
    implementation(Square.retrofit2.converter.moshi)
    implementation(Square.moshi)
    kapt(Square.moshi.kotlinCodegen)

    testImplementation("org.junit.jupiter:junit-jupiter-api:_")
    testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine:_")
}

group = "com.withplum"
version = "0.1.0"

gradlePlugin {
    plugins {
        create("notifierPlugin") {
            id = "com.withplum.notifier"
            displayName = "Version Notifier Plugin"
            description = "Parses the versions.properties (created by the refreshVersions plugin) file and notifies if there are updates. Current implementation posts updates to a Github Issue."
            implementationClass = "com.withplum.notifier.NotifierPlugin"
        }
    }
}

pluginBundle {
    website = "https://github.com/withplum/notifier"
    vcsUrl = "https://github.com/withplum/notifier.git"
    tags = listOf("gradle", "refreshVersions", "github", "assignment")
}
