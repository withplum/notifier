package com.withplum.notifier.model

data class DependencyUpdates(
    val dependency: String,
    val currentVersion: String,
    val updates: List<Dependency>
)

data class Dependency(val version: String)
