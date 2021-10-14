package com.withplum.notifier.assignment

import com.withplum.notifier.model.DependencyUpdates

data class UpdatesForUser(val githubUser: GithubUser, val updates: List<DependencyUpdates>)
