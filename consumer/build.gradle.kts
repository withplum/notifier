plugins {
    id("com.withplum.notifier") version "0.5.0"
}


configure<com.withplum.notifier.extensions.NotifierExtension> {
    reportingEnabled.value(true)
    versionsFilePath.set(rootDir.path + File.separator + "versions.properties")
}
