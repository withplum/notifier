plugins {
    id("com.withplum.notifier") version "0.1.0"
}


configure<com.withplum.notifier.extensions.NotifierExtension> {
    reportingEnabled.value(true)
    versionsFilePath.set(rootDir.path + File.separator + "versions.properties")
    githubInstallationId.set("numeric_id")
    githubRepository.set("<repository>")
    githubRepositorySlug.set("/repos/<org-name>/<repository>")
    githubIssueId.set("<issue-id>")
}
