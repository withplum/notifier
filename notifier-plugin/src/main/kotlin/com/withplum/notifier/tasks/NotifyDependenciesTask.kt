package com.withplum.notifier.tasks

import com.withplum.notifier.assignment.Assigner
import com.withplum.notifier.assignment.UpdatesForUser
import com.withplum.notifier.assignment.assignments
import com.withplum.notifier.model.VersionStatus
import com.withplum.notifier.parsing.UpdatesParser
import com.withplum.notifier.stdout.StdOutDependencyReporter
import org.gradle.api.DefaultTask
import org.gradle.api.logging.Logger
import org.gradle.api.logging.Logging
import org.gradle.api.provider.Property
import org.gradle.api.tasks.Input
import org.gradle.api.tasks.TaskAction
import org.gradle.api.tasks.options.Option
import org.gradle.workers.WorkAction
import org.gradle.workers.WorkParameters
import java.io.File
import java.util.Properties

internal abstract class NotifyDependenciesTask : DefaultTask() {

    companion object {

        const val NAME = "notifyDependencies"
    }

    @get:Input
    abstract val versionsFilePath: Property<String>

    @get:Input
    abstract val reportingEnabled: Property<Boolean>

    @get:Input
    @set:Option(
        option = "jwtToken",
        description = "The jwt token for the bot. Refer to https://docs.github.com/en/developers/apps/building-github-apps/authenticating-with-github-apps#authenticating-as-a-github-app"
    )
    var jwtToken: String = ""
        set(value) {
            field = value.trim()
        }

    @TaskAction
    fun parseAndNotify() {
        val versionsFilePath = versionsFilePath.get()
        val versionsFile = File(versionsFilePath)
        val versionProperties = Properties()
        val inputStream = versionsFile.inputStream()
        versionProperties.load(inputStream).also { inputStream.close() }
        val fileLines = versionsFile.readLines()

        val userAssignments = parseAndConstructUpdates(versionProperties, fileLines)

        if (reportingEnabled.get()) {
            StdOutDependencyReporter().report(userAssignments)
            // TODO add github integration
        }
    }

    private fun parseAndConstructUpdates(versionProperties: Properties, fileLines: List<String>): List<UpdatesForUser> {
        val parser = UpdatesParser()
        val updates = parser.parseUpdates(versionProperties, fileLines, listOf(VersionStatus.ALPHA))
        return Assigner(assignments = assignments).assign(updates)
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
