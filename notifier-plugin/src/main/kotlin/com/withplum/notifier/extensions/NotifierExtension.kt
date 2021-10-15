package com.withplum.notifier.extensions

import org.gradle.api.provider.Property

interface NotifierExtension {

    val versionsFilePath: Property<String>

    val reportingEnabled: Property<Boolean>

    val githubInstallationId: Property<String>

    val githubRepositorySlug: Property<String>

    val githubRepository: Property<String>

    val githubIssueId: Property<String>

    companion object {

        const val NAME = "versionsNotifier"
    }
}
