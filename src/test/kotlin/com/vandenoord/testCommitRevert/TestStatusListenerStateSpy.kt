package com.vandenoord.testCommitRevert

import com.intellij.openapi.command.impl.DummyProject
import com.intellij.openapi.project.Project

class TestStatusListenerStateSpy(override val preferencesFactory: PreferencesFactory = PreferencesFactory(),
                                 override val project: Project = DummyProject.getInstance(),
                                 override val commandRunner: CommandRunner = CommandRunnerSpy(project)
) : TestStatusListenerState {

    var lastTestResult: Boolean? = null

    override fun testSuiteFinished(success: Boolean) {
        this.lastTestResult = success
    }
}