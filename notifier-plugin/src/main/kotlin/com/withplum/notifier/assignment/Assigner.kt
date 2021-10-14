package com.withplum.notifier.assignment

import com.withplum.notifier.model.DependencyUpdates

class Assigner(private val assignments: Map<GithubUser, List<String>>) {

    /**
     * Assigns dependency updates to a [GithubUser].
     */
    fun assign(updates: List<DependencyUpdates>): List<UpdatesForUser> =
        assignments.map { (user, assignment) -> user.assignUpdates(updates, assignment) }

    private fun GithubUser.assignUpdates(updates: List<DependencyUpdates>, ass: List<String>): UpdatesForUser {
        val assignedUpdates = updates
            .filter { update -> ass.any { update.dependency.contains(it) } }
            .filter { it.updates.isNotEmpty() }
        return UpdatesForUser(this, assignedUpdates)
    }
}
