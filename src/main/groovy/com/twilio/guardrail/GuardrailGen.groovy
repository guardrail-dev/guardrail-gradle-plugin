package com.twilio.guardrail

import groovy.util.logging.Slf4j
import org.gradle.api.DefaultTask
import org.gradle.api.tasks.*

@Slf4j
@CacheableTask
class GuardrailGen extends DefaultTask {

    @InputFiles
    @SkipWhenEmpty
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

    CLICommon cli = CLI$.MODULE$

    GuardrailGen() {
        outputDir = new File(project.buildDir, 'guardrail-sources')
    }

    /**
     * Examples:
     *   Generate a client, put it in src/main/scala under the com.twilio.messaging.console.clients package, with
     *   OpenTracing support:
     *     guardrail --specPath client-specs/account-events-api.json --outputPath src/main/scala --packageName com
     *     .twilio.messaging.console.clients --tracing
     *
     *   Generate two clients, put both in src/main/scala, under different packages, one with tracing, one without:
     *     guardrail \\
     *       --client --specPath client-specs/account-events-api.json --outputPath src/main/scala --packageName com
     *       .twilio.messaging.console.clients.events \\
     *       --client --specPath client-specs/account-service.json --outputPath src/main/scala --packageName com
     *       .twilio.messaging.console.clients.account --tracing
     *
     *   Generate client and server routes for the same specification:
     *     guardrail \\
     *       --client --specPath client-specs/account-events-api.json --outputPath src/main/scala --packageName com
     *       .twilio.messaging.console.clients.events \\
     *       --server --specPath client-specs/account-events-api.json --outputPath src/main/scala --packageName com
     *       .twilio.messaging.console.clients.events
     */
    @TaskAction
    void exec() {
        def args = []

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

        if (language) {
            args <<  language
        }

        cli.main(args as String[])
    }
}
