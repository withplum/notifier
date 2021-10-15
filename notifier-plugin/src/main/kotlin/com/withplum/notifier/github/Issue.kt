package com.withplum.notifier.github

import com.squareup.moshi.JsonClass

object IssueMetaData {

    const val TITLE = "Dependency Status"
    const val BODY_PLACEHOLDER = "Here you can see what can be updated."
}

@JsonClass(generateAdapter = true)
data class Issue(
    val title: String = IssueMetaData.TITLE,
    val body: String = IssueMetaData.BODY_PLACEHOLDER,
    val labels: List<Label> = emptyList()
)

@JsonClass(generateAdapter = true)
data class Label(val name: String)
