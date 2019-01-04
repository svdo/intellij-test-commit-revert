package com.vandenoord.testCommitRevert

import com.intellij.execution.testframework.AbstractTestProxy
import com.intellij.openapi.project.Project

class TestStatusListener: com.intellij.execution.testframework.TestStatusListener() {
    lateinit var project: Project

    override fun testSuiteFinished(root: AbstractTestProxy?, project: Project) {
        var handler = TestResultHandler.forProject(project)
        handler.testSuiteFinished(root)
    }

    override fun testSuiteFinished(root: AbstractTestProxy?) {
        System.err.println("[Test && Commit || Revert] unsupported call: testSuiteFinished without project parameter")
    }
}
