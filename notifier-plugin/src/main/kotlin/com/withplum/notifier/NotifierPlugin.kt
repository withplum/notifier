package com.withplum.notifier

import com.withplum.notifier.extensions.NotifierExtension
import com.withplum.notifier.tasks.NotifyDependenciesTask
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.create

open class NotifierPlugin : Plugin<Project> {

    override fun apply(project: Project) {
        val notifierExtension = project.extensions.create<NotifierExtension>(NotifierExtension.NAME)
        project.tasks.register(NotifyDependenciesTask.NAME, NotifyDependenciesTask::class.java, notifierExtension)
    }
}
