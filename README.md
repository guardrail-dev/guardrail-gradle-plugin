# Gradle Swagger Generator Plugin [![CircleCI](https://circleci.com/gh/head-thrash/guardrail-gradle-plugin/tree/master.svg?style=svg)](https://circleci.com/gh/head-thrash/guardrail-gradle-plugin/tree/master) [![Gradle Status](https://gradleupdate.appspot.com/head-thrash/guardrail-gradle-plugin/status.svg)](https://gradleupdate.appspot.com/head-thrash/guardrail-gradle-plugin/status)

Gradle plugin for [Twilio Guardrail](https://github.com/twilio/guardrail) code generation.

Effectively a wrapper over Guardrail CLI. 

Based on [int128/gradle-swagger-generator-plugin](https://github.com/int128/gradle-swagger-generator-plugin)

Usage
======

Example

1. Generate Petstore dropwizard client
2. Make java classes depend on generated sources

```build.gradle 
plugins {
    id('java')
    id('com.twilio.guardrail')
}

dependencies {
    ... // incert corresponding dependencies to compile
}

guardrail {
    petstore {
        inputFile = file('src/main/resources/pet_store_v3.yml')
        gen {
            packageName = 'com.foobar.generated.pet_store_v3'
            language = 'java'
            framework = 'dropwizard'
        } 
    }
}

compileJava.dependsOn guardrail.petstore.gen
sourceSets.main.java.srcDir "\${guardrail.petstore.gen.outputDir}"
```

Specs
======

To declare a generation

```gradle

guardrail {                                         // plugin declaration
   
    name {                                          // key of generation, e.g. petstore, myService, etc. 
        inputFile = file('myspec.yml')              // path of the file
        gen {                                       // task that instucts generation
            outputDir = file('...')                 // where to place output. default is
                                                    // file(project.buildDir/'guardrail-sources')
            packageName = 'com.example.arbitrary'   // package of classes to be packaged
            language = 'java'                       // or scala. default 'scala'
            kind = 'server'                         // or client. default 'client'
            framework = '...'                       // see table below. default 'akka-http'
            tracing = 'true'                        // or false, default false. adds lightstep integration to service
            customImports = ["com.myclasses.Blah", 
                             "com.classes.Blah"]    // optional list of classes to be used in generation
                                                    // see 'x-jvm-package' or 'x-scala-package' extension
            dtoPackage = 'dtos'                     // where to put DTO objects. Where to put your client's DTOs. 
                                                    // Effectively: "$${packageName}.definitions.$${dtoPackage}"
        }    

    }

}

```

Available frameworks
====================

If 'scala':

- akka-http
- endpoints
- http4s

If 'java':

- dropwizard

Multiple generations
====================

```gradle
guardrail {
    // generate a server stub in java with dropwizard resources
    petstoreServer {
        inputFile = file('src/main/resources/pet_store_v2.yml')
        gen {
            packageName = 'com.foobar.generated.petstore.server'
            kind = 'server'
            language = 'java'
            framework = 'dropwizard'
        }
    }
    // generate a scala http4s client
    petstoreClient {
        inputFile = file('src/main/resources/pet_store_v3.yml')
        gen {
            packageName = 'com.foobar.generated.petstore.client'
            language = 'scala'
            framework = 'http4s'
            kind = 'client'
        } 
    }
```


## Contributions

This is an open source software licensed under the MIT License.
Feel free to open issues or pull requests.

CircleCI builds the plugin continuously.
Following variables should be set.

Environment Variable        | Purpose
----------------------------|--------
`$GRADLE_PUBLISH_KEY`       | Publish the plugin to Gradle Plugins
`$GRADLE_PUBLISH_SECRET`    | Publish the plugin to Gradle Plugins
