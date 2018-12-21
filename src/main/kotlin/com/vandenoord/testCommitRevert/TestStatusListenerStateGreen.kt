package com.vandenoord.testCommitRevert

import com.intellij.openapi.project.Project

class TestStatusListenerStateGreen(
    val preferencesFactory: PreferencesFactory,
    val project: Project,
    val commandRunner: CommandRunner
) {
    fun testSuiteFinished(success: Boolean) {
        val prefs = preferencesFactory.getPreferences(project)
        val command = if (success) prefs.commitCommand else prefs.revertCommand
        commandRunner.runCommand(command)
    }
}