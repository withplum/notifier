package com.withplum.notifier

import com.withplum.notifier.assignment.UpdatesForUser

/**
 * Classes implementing this interface are capable of reporting the dependency updates to their output.
 */
interface DependencyReporter {

    fun report(updates: List<UpdatesForUser>)

}
