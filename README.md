# Gradle Swagger Generator Plugin [![Build Status](https://github.com/guardrail-dev/guardrail-gradle-plugin/workflows/CI/badge.svg)](https://github.com/guardrail-dev/guardrail-gradle-plugin/actions?query=workflow%3ACI) [![Gradle Status](https://gradleupdate.appspot.com/guardrail-dev/guardrail-gradle-plugin/status.svg)](https://gradleupdate.appspot.com/guardrail-dev/guardrail-gradle-plugin/status)

Gradle plugin for [guardrail-dev/guardrail](https://github.com/guardrail-dev/guardrail) code generation.

Based on [int128/gradle-swagger-generator-plugin](https://github.com/int128/gradle-swagger-generator-plugin)

Usage
======

Common example of how to generate the client code and use it in your project:

1. Generate Petstore dropwizard client
2. Make java classes depend on generated sources

```build.gradle
plugins {
    id('java')
    id('dev.guardrail')
}

repositories {
    mavenCentral()
}

dependencies {
    ... // insert corresponding dependencies to compile
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

// Include the generated sources as part of the build
// Feel free to delete the one that does not apply to your project
sourceSets.main.scala.srcDirs += new File(buildDir, 'guardrail-sources/scala')
sourceSets.main.java.srcDirs  += new File(buildDir, 'guardrail-sources/java')
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

