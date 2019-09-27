package com.twilio.guardrail

import org.gradle.api.Project
import org.gradle.api.Plugin

class GuardrailGradlePlugin implements Plugin<Project> {
    void apply(Project project) {
        project.tasks.register("guardrail", GuardrailTask)
    }
}
