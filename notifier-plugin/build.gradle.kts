plugins {
    `kotlin-dsl`
    `java-gradle-plugin`
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

gradlePlugin {
    plugins {
        create("notifierPlugin") {
            id = "com.withplum.notifier"
            displayName = "Parse and notify about dependency updates"
            description = "Parses the versions.properties (created by the refreshVersions plugin) file and notifies if there are updates. Current implementation posts updates to a GithubIssue."
            implementationClass = "com.withplum.notifier.NotifierPlugin"
            version = "0.1.0"
        }
    }
}

pluginBundle {
    website = "https://github.com/withplum/notifier"
    vcsUrl = "https://github.com/withplum/notifier"
    tags = listOf("gradle", "refreshVersions", "github", "assignment")
}
