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
                    reportingEnabled.set(notifierExtension.reportingEnabled)
                }
            }
        }
    }
}
