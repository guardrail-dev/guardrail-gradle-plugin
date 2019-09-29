package com.twilio.guardrail

import groovy.transform.ToString

@ToString(includes = 'name', includePackage = false)
class Guardrail {

    final String name

    def Guardrail(String name) {
        this.name = name
    }

    GuardrailGen gen

    GuardrailGen gen(@DelegatesTo(GuardrailGen) Closure closure) {
        gen.configure(closure)
        gen
    }

    void setInputFile(File inputFile) {
        [gen]*.inputFile = inputFile
    }
}
