package com.withplum.notifier.github

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class CreateIssue(
    val title: String = IssueMetaData.TITLE,
    val body: String = IssueMetaData.BODY_PLACEHOLDER,
    val labels: List<String> = listOf("Dependency Updates", "Bot")
)
