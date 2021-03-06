package com.vandenoord.testCommitRevert

import com.intellij.openapi.project.Project

class TestStatusListenerStateGreen(
    override val preferencesFactory: PreferencesFactory,
    override val project: Project,
    override val commandRunner: CommandRunner
) : TestStatusListenerState {
    override fun testSuiteFinished(success: Boolean) {
        val prefs = preferencesFactory.getPreferences(project)
        val command = if (success) prefs.commitCommand else prefs.revertCommand
        commandRunner.runCommand(command)
    }
}