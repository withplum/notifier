package com.withplum.notifier.github

import com.withplum.notifier.DependencyReporter
import com.withplum.notifier.assignment.UpdatesForUser
import com.withplum.notifier.github.model.InstallationAccessTokenParams
import org.gradle.kotlin.dsl.support.appendReproducibleNewLine
import retrofit2.Retrofit

/**
 * Reports all current versions and updates to a pre determined github issue.
 */
class GithubDependencyReporter(
    private val apiClient: Retrofit = ApiClient().provideRetrofit(),
    private val jwtToken: String,
    private val issueId: String,
    private val installationId: String,
    private val repositorySlug: String,
    private val repository: String
) : DependencyReporter {

    private val api: GithubApi by lazy { apiClient.create(GithubApi::class.java) }

    override fun report(updates: List<UpdatesForUser>) {
        val installationAccessTokenResponse =
            api.getInstallationAccessToken(
                jwtToken = "bearer $jwtToken",
                installationId = installationId,
                body = InstallationAccessTokenParams(repositories = listOf(repository))
            ).execute().body()
        val tokenHeader = "token ${installationAccessTokenResponse!!.token}"

        val dependencyIssue: Issue? = api.getIssue(repositorySlug, issueId, tokenHeader).execute().body()
        val issue = constructIssue(updates)

        updateOrCreateIssue(dependencyIssue, issue, tokenHeader)
    }

    private fun updateOrCreateIssue(dependencyIssue: Issue?, issue: CreateIssue, tokenHeader: String) {
        if (dependencyIssue != null) {
            api.updateIssue(repositorySlug, issueId, issue, tokenHeader).execute()
        } else {
            api.createIssue(repositorySlug, issue, tokenHeader).execute()
        }
    }

    private fun constructIssue(updates: List<UpdatesForUser>): CreateIssue {
        val content = constructIssueBody(updates)
        return CreateIssue(body = content.toString())
    }

    private fun constructIssueBody(updates: List<UpdatesForUser>): StringBuilder {
        val content = StringBuilder()
        content.appendReproducibleNewLine("## ${IssueMetaData.TITLE}")

        updates.filter { it.updates.isNotEmpty() }.forEach { userAssignment ->
            content.appendReproducibleNewLine("### Updates handled by: @${userAssignment.githubUser.username}")

            userAssignment.updates.forEach { update ->
                content.appendReproducibleNewLine("- Artifact: `${update.dependency}`")
                update.updates.forEach { version -> content.appendReproducibleNewLine("\t- ${version.version}") }
            }
        }

        return content
    }
}
