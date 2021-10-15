package com.withplum.notifier.github.model

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class InstallationAccessTokenParams(val repositories: List<String>)
