# Notifier Gradle Plugin

This gradle plugin serves the need of automating how dependencies are handles in a project.

More specifically, it functions using the output of [Refresh Versions](https://jmfayard.github.io/refreshVersions/) plugin.
By parsing the `versions.properites` file, in conjunction with pre-determined list of dependency assignments, it will
output a report detailing who has to update what.

## Reporting

At its current state, the plugin can output reports to:
- Standard output
- GitHub issue: It requires a GitHub App to be created and installed in the repository. This is done because for the plugin
to function, it needs to be authenticated and thus act on behalf of that GitHub App. It will also require a `jwt` token.
More information can be found in GitHub's [documentation](https://docs.github.com/en/developers/apps/building-github-apps/authenticating-with-github-apps)
# Usage

## Apply the plugin

```kotlin
plugins {
    id("com.withplum.notifier") version "0.1.0"
}
```

## Configure

```kotlin
configure<com.withplum.notifier.extensions.NotifierExtension> {
    stdOutEnabled.value(true) // For reporting to the standard output
    githubEnabled.value(false) // For reporting to a github issue
    versionsFilePath.set(rootDir.path + File.separator + "versions.properties") // the file from which to read
    githubInstallationId.set("numeric_id") // The GitHub App's installation id
    githubRepository.set("<repository>") // Your repository, e.g.: notifier
    githubRepositorySlug.set("/repos/<org-name>/<repository>") // e.g. /repos/withplum/app-android
    githubIssueId.set("<issue-id>") // The id of a GitHub issue that you'd like to post reports to
    githubDependencyAssignments.set( // A map of github usernames (or anything else you'd like) with assigned dependencies
        mapOf(
            "spiderMan" to listOf("junit"),
            "wonderWoman" to listOf("kotlin", "koin")
        )
    )
}
```

## Execute

Reminder: In order to post updates to GitHub issue, a jwt token has to first be generated.

```
./gradlew notifyDependencies --jwtToken="<the token you generated from the pem file>"
```
