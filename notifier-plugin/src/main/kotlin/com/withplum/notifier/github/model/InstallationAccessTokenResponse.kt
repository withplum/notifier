package com.withplum.notifier.github.model

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class InstallationAccessTokenResponse(val token: String)
