package com.vandenoord.testCommitRevert

import com.intellij.openapi.project.Project

class TestStatusListenerStateRed(
    override val preferencesFactory: PreferencesFactory,
    override val project: Project,
    override val commandRunner: CommandRunner
) : TestStatusListenerState {
    override fun testSuiteFinished(success: Boolean) {
        val prefs = preferencesFactory.getPreferences(project)
        val command = if (success) prefs.revertCommand else prefs.commitCommand
        commandRunner.runCommand(command)
    }
}