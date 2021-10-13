val kotlinVersion: String by extra { "1.5.30" }
val junitVersion: String by extra { "5.8.1" }

plugins {
    `kotlin-dsl`
    id("com.gradle.plugin-publish") version "0.16.0"
    kotlin("jvm") version "1.5.30"
}

repositories {
    google()
    mavenCentral()
}

group = "com.withplum.notifier"
version = "0.5.0"

dependencies {
    implementation(gradleApi())
    implementation("org.jetbrains.kotlin:kotlin-stdlib:$kotlinVersion")

    testImplementation("org.junit.jupiter:junit-jupiter-api:$junitVersion")
    testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine:$junitVersion")
}

gradlePlugin {
    plugins {
        create("notifierPlugin") {
            id = "com.withplum.notifier"
            displayName = "Parse and notify about dependency updates"
            description = "Parses the versions.properties file and notifies if there are updates."
            implementationClass = "com.withplum.notifier.NotifierPlugin"
        }
    }
}

pluginBundle {
    website = "https://github.com/withplum/notifier"
    vcsUrl = "https://github.com/withplum/notifier"
    tags = listOf("gradle", "refreshVersions", "android")
}
