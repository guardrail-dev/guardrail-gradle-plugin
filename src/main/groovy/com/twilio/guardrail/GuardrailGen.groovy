package dev.guardrail

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
    private File defaultOutputDir = null

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

    private dev.guardrail.cli.CLICommon cli = dev.guardrail.cli.CLI$.MODULE$

    GuardrailGen() {
        outputDir = new File(project.buildDir, 'guardrail-sources')
        defaultOutputDir = outputDir
    }

    @TaskAction
    void exec() {
        def args = []

        if (language) {
            args <<  language
        }

        args << "--$kind"
        args << '--specPath' << inputFile.path
        // Why do builds need to be able to override this?
        if (outputDir != defaultOutputDir) {
            args << '--outputPath' << outputDir.path
        } else {
            args << '--outputPath' << new File(outputDir, language).path
        }
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
