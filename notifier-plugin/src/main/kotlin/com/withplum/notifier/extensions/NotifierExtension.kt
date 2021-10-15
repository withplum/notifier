package com.withplum.notifier.extensions

import org.gradle.api.provider.MapProperty
import org.gradle.api.provider.Property

interface NotifierExtension {

    val versionsFilePath: Property<String>

    val stdOutEnabled: Property<Boolean>

    val githubEnabled: Property<Boolean>

    val githubInstallationId: Property<String>

    val githubRepositorySlug: Property<String>

    val githubRepository: Property<String>

    val githubIssueId: Property<String>

    val githubDependencyAssignments: MapProperty<String, List<String>>

    companion object {

        const val NAME = "versionsNotifier"
    }
}
