package com.withplum.notifier.assignment

val spiderMan = GithubUser("spiderMan")
val wonderWoman = GithubUser("wonderWoman")

val assignments: Map<GithubUser, List<String>> = mapOf(
    spiderMan to listOf("junit"),
    wonderWoman to listOf("kotlin", "koin")
)
