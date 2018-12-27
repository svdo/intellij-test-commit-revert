package com.vandenoord.testCommitRevert

import com.intellij.execution.testframework.AbstractTestProxy
import com.intellij.openapi.project.Project

class TestStatusListener: com.intellij.execution.testframework.TestStatusListener() {
    private lateinit var commandRunner: CommandRunner
    private var preferencesFactory = PreferencesFactory()
    lateinit var state: TestStatusListenerState
    lateinit var project: Project

    override fun testSuiteFinished(root: AbstractTestProxy?, project: Project) {
        this.project = project
        this.commandRunner = DefaultCommandRunner(project)
        this.state = TestStatusListenerStateDisabled(preferencesFactory, project, commandRunner)
        testSuiteFinished(root)
    }

    override fun testSuiteFinished(root: AbstractTestProxy?) {
        if (root != null) {
            state.testSuiteFinished(determineTestResult(root))
        }
    }

    private fun determineTestResult(root: AbstractTestProxy?): Boolean {
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

    fun changeState(state: State) {
        when (state) {
            State.Green -> this.state = TestStatusListenerStateGreen(preferencesFactory, project, commandRunner)
            State.Red -> this.state = TestStatusListenerStateRed(preferencesFactory, project, commandRunner)
            State.Disabled -> this.state = TestStatusListenerStateDisabled(preferencesFactory, project, commandRunner)
        }
    }

    enum class State {
        Disabled, Green, Red
    }
}
