# Release Process

1. Acquire permissions to publish the package to Bintray and set the keys "bintrayUserName" and "bintrayApiKey" in `~/.gradle/gradle.properties`.
1. Update `CHANGES.md`
1. Ensure that there is a milestone for the version, and that appropriate issues are associated with the milestone.
1. Run `./gradlew clean bintrayUpload -PreleaseVersion=guardrail-0.59.0-1` (change the version to desired)
1. Go to the [Bintray page](https://bintray.com/twilio/maven/guardrail-gradle-plugin), verify the files, and click "Publish".
1. Push tag
1. Go to the [GitHub Releases page](https://github.com/twilio/guardrail-gradle-plugin/releases), click "Draft a new release", select the tag version, use the version number as the title, copy the relevant segment from `CHANGES.md` into the description, and click "Publish release".

