package com.withplum.notifier.extensions

import org.gradle.api.model.ObjectFactory
import org.gradle.api.tasks.Internal

open class NotifierExtension @JvmOverloads constructor(
    // Needed for Gradle
    @get:Internal
    internal val name: String = "default",
    objects: ObjectFactory
) {

    companion object {

        const val NAME = "notifier"
    }
}
