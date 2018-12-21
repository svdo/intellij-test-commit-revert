package com.vandenoord.testCommitRevert

import com.intellij.openapi.command.impl.DummyProject
import com.intellij.openapi.project.Project
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

class TestStatusListenerStateDisabledTest {

    private lateinit var preferencesFactory: StubPreferencesFactory
    private lateinit var p: Project
    private lateinit var commandRunner: CommandRunnerSpy
    private lateinit var disabled: TestStatusListenerStateDisabled

    @BeforeEach
    fun setUp() {
        preferencesFactory = StubPreferencesFactory()
        p = DummyProject.getInstance()
        commandRunner = CommandRunnerSpy()
        disabled = TestStatusListenerStateDisabled(preferencesFactory, p, commandRunner)
    }

    @Test
    fun itRunsCommitCommandOnSuccess() {
        disabled.testSuiteFinished(true)
        assertEquals("", commandRunner.runCommand)
    }

    @Test
    fun itRunsRevertCommandOnFailure() {
        disabled.testSuiteFinished(false)
        assertEquals("", commandRunner.runCommand)
    }
}