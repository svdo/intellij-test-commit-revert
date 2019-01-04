package com.vandenoord.testCommitRevert

import com.intellij.execution.testframework.AbstractTestProxy
import com.intellij.openapi.project.Project

class TestResultHandler(val project: Project) {

    private var preferencesFactory = PreferencesFactory()
    private var commandRunner = DefaultCommandRunner(project)
    var state: TestStatusListenerState = TestStatusListenerStateDisabled(preferencesFactory, project, commandRunner)

    fun testSuiteFinished(root: AbstractTestProxy?) {
        if (root != null) {
            state.testSuiteFinished(determineTestResult(root))
        }
    }

    protected fun determineTestResult(root: AbstractTestProxy?): Boolean {
        var testsRun = 0
        var testsSucceeded = 0

        root?.let {
            for (test in it.allTests) {
                if (test.isLeaf) {
                    testsRun++

                    if (test.isPassed) {
                        testsSucceeded++
                    }
                }
            }
        }

        return testsRun == testsSucceeded
    }

    fun changeState(pluginState: PluginState) {
        when (pluginState) {
            PluginState.Green -> this.state = TestStatusListenerStateGreen(preferencesFactory, project, commandRunner)
            PluginState.Red -> this.state = TestStatusListenerStateRed(preferencesFactory, project, commandRunner)
            PluginState.Disabled -> this.state = TestStatusListenerStateDisabled(preferencesFactory, project, commandRunner)
        }
    }

    companion object {
        fun forProject(project: Project): TestResultHandler {
            return TestResultHandler(project)
        }
    }
}