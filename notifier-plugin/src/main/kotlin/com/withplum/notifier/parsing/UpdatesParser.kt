package com.withplum.notifier.parsing

import com.withplum.notifier.model.Dependency
import com.withplum.notifier.model.DependencyUpdates
import com.withplum.notifier.model.VersionStatus
import java.util.Properties

private const val DEPENDENCY_PREFIX = "##"
private const val DEPENDENCY_DELIMITER = "="

/**
 * Responsible for parsing declared version updates from a list of lines.
 */
class UpdatesParser {

    /**
     * @param versions A properties file which stores current dependency versions in a key: dependency - value: version format.
     * @param lines An input which assumes that if a dependency has updates, they are listed below it, at lines starting with [DEPENDENCY_PREFIX]
     * @return A list of [DependencyUpdates] which holds all the dependencies with their updates.
     */
    fun parseUpdates(
        versions: Properties,
        lines: List<String>,
        excludeVersionStates: List<VersionStatus> = emptyList()
    ): List<DependencyUpdates> {
        val dependencyUpdates = mutableListOf<DependencyUpdates>()
        versions.forEach { dependency, currentVersion ->
            val dependencyInfo = DependencyUpdates(
                dependency = dependency.toString(),
                currentVersion = currentVersion.toString(),
                updates = emptyList()
            )
            dependencyUpdates.add(getUpdatesForDependency(dependencyInfo, lines, excludeVersionStates))
        }
        return dependencyUpdates
    }

    private fun getUpdatesForDependency(
        dependencyUpdates: DependencyUpdates,
        lines: List<String>,
        excludeVersionStates: List<VersionStatus>
    ): DependencyUpdates {
        val indexOfDependency = lines.indexOfFirst { it.contains(dependencyUpdates.dependency) }

        val updates = mutableListOf<Dependency>()
        for (i in indexOfDependency + 1 until lines.size) {
            val newVersion = lines[i]
            if (hasReachedEndOfDependency(newVersion)) break
            if (shouldBeExcluded(newVersion, excludeVersionStates)) continue
            updates.add(Dependency(newVersion.split(DEPENDENCY_DELIMITER).last()))
        }

        return dependencyUpdates.copy(updates = updates)
    }

    /**
     * When we reach a line which doesn't start with [DEPENDENCY_PREFIX], then it meas
     * we finished with the available updates for a given dependency.
     */
    private fun hasReachedEndOfDependency(newVersion: String) = !newVersion.startsWith(DEPENDENCY_PREFIX)

    private fun shouldBeExcluded(newVersion: String, excludeVersionStates: List<VersionStatus>): Boolean {
        excludeVersionStates.forEach {
            if (newVersion.contains(it.status)) return true
        }
        return false
    }
}
