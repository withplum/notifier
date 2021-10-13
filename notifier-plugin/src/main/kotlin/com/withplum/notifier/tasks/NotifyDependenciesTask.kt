package com.withplum.notifier.tasks

import com.withplum.notifier.extensions.NotifierExtension
import org.gradle.api.DefaultTask
import org.gradle.api.logging.Logger
import org.gradle.api.logging.Logging
import org.gradle.api.tasks.TaskAction
import org.gradle.workers.WorkAction
import org.gradle.workers.WorkParameters
import javax.inject.Inject

internal abstract class NotifyDependenciesTask @Inject constructor(private val extension: NotifierExtension) :
    DefaultTask() {

    companion object {

        const val NAME = "notifyDependencies"
    }

    @TaskAction
    fun parseAndNotify() {
        println("Hello Plum!")
    }

    class Params : WorkParameters

    abstract class NotifyDependenciesWorkAction : WorkAction<Params> {

        private val logger: Logger = Logging.getLogger(
            "withPlum:NotifierPlugin:${NotifyDependenciesTask::class.java.simpleName}"
        )

        override fun execute() {
            println("Hello Plum!")
        }
    }
}
