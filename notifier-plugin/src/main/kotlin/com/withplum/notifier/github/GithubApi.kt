package com.withplum.notifier.github

import com.withplum.notifier.github.model.InstallationAccessTokenParams
import com.withplum.notifier.github.model.InstallationAccessTokenResponse
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Headers
import retrofit2.http.PATCH
import retrofit2.http.POST
import retrofit2.http.Path

private const val ISSUE_NUMBER = "issue_number"
private const val INSTALLATION_ID = "installation_id"
private const val REPOSITORY_SLUG = "repository_slug"
private const val ACCEPT_HEADER = "Accept: application/vnd.github.v3.full+json"
private const val AUTHORIZATION_HEADER = "Authorization"
private const val ISSUES = "{$REPOSITORY_SLUG}/issues"
private const val ISSUE = "$ISSUES/{$ISSUE_NUMBER}"
private const val INSTALLATION_ACCESS_TOKEN = "/app/installations/{$INSTALLATION_ID}/access_tokens"

interface GithubApi {

    @POST(INSTALLATION_ACCESS_TOKEN)
    @Headers(ACCEPT_HEADER)
    fun getInstallationAccessToken(
        @Path(INSTALLATION_ID) installationId: String,
        @Body body: InstallationAccessTokenParams,
        @Header(AUTHORIZATION_HEADER) jwtToken: String
    ): Call<InstallationAccessTokenResponse>

    @GET(ISSUE)
    @Headers(ACCEPT_HEADER)
    fun getIssue(
        @Path(REPOSITORY_SLUG, encoded = true) repositorySlug: String,
        @Path(ISSUE_NUMBER) issueNumber: String,
        @Header(AUTHORIZATION_HEADER) accessToken: String
    ): Call<Issue>

    @POST(ISSUES)
    @Headers(ACCEPT_HEADER)
    fun createIssue(
        @Path(REPOSITORY_SLUG, encoded = true) repositorySlug: String,
        @Body params: CreateIssue,
        @Header(AUTHORIZATION_HEADER) accessToken: String
    ): Call<Issue>

    @PATCH(ISSUE)
    @Headers(ACCEPT_HEADER)
    fun updateIssue(
        @Path(REPOSITORY_SLUG, encoded = true) repositorySlug: String,
        @Path(ISSUE_NUMBER) issueNumber: String,
        @Body params: CreateIssue,
        @Header(AUTHORIZATION_HEADER) accessToken: String
    ): Call<Issue>
}
