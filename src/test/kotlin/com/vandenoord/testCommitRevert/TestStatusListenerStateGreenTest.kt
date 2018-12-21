package com.vandenoord.testCommitRevert

import com.intellij.openapi.command.impl.DummyProject
import com.intellij.openapi.project.Project
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

class TestStatusListenerStateGreenTest {

    private lateinit var preferencesFactory: StubPreferencesFactory
    private lateinit var p: Project
    private lateinit var commandRunner: CommandRunnerSpy
    private lateinit var green: TestStatusListenerStateGreen

    @BeforeEach
    fun setUp() {
        preferencesFactory = StubPreferencesFactory()
        p = DummyProject.getInstance()
        commandRunner = CommandRunnerSpy()
        green = TestStatusListenerStateGreen(preferencesFactory, p, commandRunner)
    }

    @Test
    fun itRunsCommitCommandOnSuccess() {
        green.testSuiteFinished(true)
        assertEquals("commit", commandRunner.runCommand)
    }

    @Test
    fun itRunsRevertCommandOnFailure() {
        green.testSuiteFinished(false)
        assertEquals("revert", commandRunner.runCommand)
    }
}