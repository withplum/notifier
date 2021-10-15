package com.withplum.notifier

import com.withplum.notifier.extensions.NotifierExtension
import com.withplum.notifier.tasks.NotifyDependenciesTask
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.create
import org.gradle.kotlin.dsl.invoke
import org.gradle.kotlin.dsl.register

open class NotifierPlugin : Plugin<Project> {

    override fun apply(project: Project) {
        project.run {
            val notifierExtension = extensions.create<NotifierExtension>(NotifierExtension.NAME)

            tasks {
                register<NotifyDependenciesTask>(NotifyDependenciesTask.NAME) {
                    versionsFilePath.set(notifierExtension.versionsFilePath)
                    stdOutEnabled.set(notifierExtension.stdOutEnabled)
                    githubEnabled.set(notifierExtension.githubEnabled)
                    githubInstallationId.set(notifierExtension.githubInstallationId)
                    githubRepositorySlug.set(notifierExtension.githubRepositorySlug)
                    githubRepository.set(notifierExtension.githubRepository)
                    githubIssueId.set(notifierExtension.githubIssueId)
                    githubAssignments.set(notifierExtension.githubDependencyAssignments)
                }
            }
        }
    }
}
