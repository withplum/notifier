package com.withplum.notifier.stdout

import com.withplum.notifier.DependencyReporter
import com.withplum.notifier.assignment.UpdatesForUser

/**
 * Reports all current versions and updates to the standard output.
 */
class StdOutDependencyReporter : DependencyReporter {

    override fun report(updates: List<UpdatesForUser>) {
        println("Dependency updates")
        updates.forEach { dependency ->
            println("Updates by ${dependency.githubUser.username}")
            dependency.updates.forEach { update ->
                println("Updates for ${update.dependency}")
                update.updates.forEach { version -> println("\t${version}") }
            }
        }
    }
}
