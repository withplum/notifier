package com.withplum.notifier.tasks

import com.withplum.notifier.assignment.Assigner
import com.withplum.notifier.assignment.UpdatesForUser
import com.withplum.notifier.assignment.assignments
import com.withplum.notifier.github.GithubDependencyReporter
import com.withplum.notifier.model.VersionStatus
import com.withplum.notifier.parsing.UpdatesParser
import com.withplum.notifier.stdout.StdOutDependencyReporter
import org.gradle.api.DefaultTask
import org.gradle.api.provider.Property
import org.gradle.api.tasks.Input
import org.gradle.api.tasks.TaskAction
import org.gradle.api.tasks.options.Option
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
    abstract val githubInstallationId: Property<String>

    @get:Input
    abstract val githubRepositorySlug: Property<String>

    @get:Input
    abstract val githubRepository: Property<String>

    @get:Input
    abstract val githubIssueId: Property<String>

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
            GithubDependencyReporter(
                jwtToken = jwtToken,
                issueId = githubIssueId.get(),
                installationId = githubInstallationId.get(),
                repositorySlug = githubRepositorySlug.get(),
                repository = githubRepository.get()
            ).report(userAssignments)
        }
    }

    private fun parseAndConstructUpdates(versionProperties: Properties, fileLines: List<String>): List<UpdatesForUser> {
        val parser = UpdatesParser()
        val updates = parser.parseUpdates(versionProperties, fileLines, listOf(VersionStatus.ALPHA))
        return Assigner(assignments = assignments).assign(updates)
    }
}
