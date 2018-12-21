package com.vandenoord.testCommitRevert

import com.intellij.openapi.project.Project

interface TestStatusListenerState {
    val preferencesFactory: PreferencesFactory
    val project: Project
    val commandRunner: CommandRunner
    fun testSuiteFinished(success: Boolean)
}