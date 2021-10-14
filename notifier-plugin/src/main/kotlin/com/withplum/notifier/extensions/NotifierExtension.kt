package com.withplum.notifier.extensions

import org.gradle.api.provider.Property

interface NotifierExtension {

    val versionsFilePath: Property<String>

    val reportingEnabled: Property<Boolean>


    companion object {

        const val NAME = "versionsNotifier"
    }
}
