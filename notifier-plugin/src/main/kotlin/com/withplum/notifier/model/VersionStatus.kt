package com.withplum.notifier.model

/**
 * Denotes the status of a version.
 */
enum class VersionStatus(val status: String) {

    ALPHA("alpha"),
    BETA("beta"),
    STABLE("stable")
}
