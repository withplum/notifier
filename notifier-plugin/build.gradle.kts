plugins {
    `kotlin-dsl`
    `java-gradle-plugin`
    id("com.gradle.plugin-publish") version "0.16.0"
    kotlin("kapt")
}

repositories {
    mavenCentral()
}

group = "com.withplum.notifier"
version = "0.5.0"

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
