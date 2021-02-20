package com.twilio.guardrail

import groovy.util.logging.Slf4j
import org.gradle.api.DefaultTask
import org.gradle.api.tasks.*

@Slf4j
@CacheableTask
class GuardrailGen extends DefaultTask {

    @InputFiles
    @SkipWhenEmpty
    @PathSensitive(value = PathSensitivity.RELATIVE)
    File inputFile

    @OutputDirectory
    File outputDir

    @Input
    @Optional
    String language = "scala"

    @Input
    @Optional
    String kind = "client"

    @Input
    String packageName

    @Input
    @Optional
    String dtoPackage

    @Input
    @Optional
    Boolean tracing = false

    @Input
    @Optional
    String framework = "akka-http"

    @Input
    @Optional
    Boolean skip = false

    @Input
    @Optional
    List<String> customImports = []

    @Input
    @Optional
    List<String> modules = []

    private CLICommon cli = CLI$.MODULE$

    GuardrailGen() {
        outputDir = new File(project.buildDir, 'guardrail-sources')
    }

    @TaskAction
    void exec() {
        def args = []

        if (language) {
            args <<  language
        }

        args << "--$kind"
        args << '--specPath' << inputFile.path
        args << '--outputPath' << outputDir.path
        args << '--packageName' << packageName

        if (tracing) {
            args << '--tracing'
        }

        if (dtoPackage) {
            args << '--dtoPackage' << dtoPackage
        }

        if (framework) {
            args << '--framework' << framework
        }

        customImports.each {
            args << '--import'
            args << it
        }

        modules.each {
            args << '--module'
            args << it
        }

        cli.processArgs(args as String[])
    }
}
