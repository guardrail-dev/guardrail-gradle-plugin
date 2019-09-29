package com.twilio.guardrail

import org.gradle.api.Project
import org.gradle.api.Plugin

class GuardrailGradlePlugin implements Plugin<Project> {

    @Override
    void apply(Project project) {
        def guardrails = project.container(Guardrail)
        project.extensions.add('guardrail', guardrails)

        project.ext.GuardrailGen = GuardrailGen

        createGuardrailGen(project)

        guardrails.all {
            def guardrail = delegate as Guardrail
            guardrail.gen = createGuardrailGen(project, guardrail.name)

            project.tasks.guardrail.dependsOn(guardrail.gen)

            guardrail.gen.outputDir = new File(project.buildDir, "guardrail-${guardrail.name}")
        }
    }

    private static createGuardrailGen(Project project, String sourceName = null) {
        project.task("guardrail${sourceName ? sourceName.capitalize() : ''}",
            type: GuardrailGen,
            group: 'build',
            description: "Generates a source code from ${sourceName ?: 'the OpenAPI specification'}."
        ) as GuardrailGen
    }
}
