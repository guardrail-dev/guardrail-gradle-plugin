# Release Process

1. Update `CHANGES.md`
1. Run `./gradlew clean bintrayUpload -PreleaseVersion=<VERSION> -PbintrayUser=<BINTRAY_USER> -PbintrayApiKey=<BINTRAY_API_KEY>` (change the version to desired). Add `-PdryRun` to execute upload in dry-run mode.
1. Go to the [Bintray page](https://bintray.com/twilio/releases/guardrail-gradle-plugin), verify the files, and click "Publish".
1. Push tag
1. Go to the [GitHub Releases page](https://github.com/twilio/guardrail-gradle-plugin/releases), click "Draft a new release", select the tag version, use the version number as the title, copy the relevant segment from `CHANGES.md` into the description, and click "Publish release".

