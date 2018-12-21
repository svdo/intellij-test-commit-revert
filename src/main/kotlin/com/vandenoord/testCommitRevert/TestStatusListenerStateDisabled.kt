package com.vandenoord.testCommitRevert

import com.intellij.openapi.project.Project

class TestStatusListenerStateDisabled(
    override val preferencesFactory: PreferencesFactory,
    override val project: Project,
    override val commandRunner: CommandRunner
) : TestStatusListenerState {
    override fun testSuiteFinished(success: Boolean) {}
}
