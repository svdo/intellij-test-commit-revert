package com.vandenoord.testCommitRevert

import com.intellij.openapi.command.impl.DummyProject
import com.intellij.openapi.project.Project
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

class TestStatusListenerStateRedTest {

    private lateinit var preferencesFactory: StubPreferencesFactory
    private lateinit var p: Project
    private lateinit var commandRunner: CommandRunnerSpy
    private lateinit var green: TestStatusListenerStateRed

    @BeforeEach
    fun setUp() {
        preferencesFactory = StubPreferencesFactory()
        p = DummyProject.getInstance()
        commandRunner = CommandRunnerSpy(p)
        green = TestStatusListenerStateRed(preferencesFactory, p, commandRunner)
    }

    @Test
    fun itRunsCommitCommandOnSuccess() {
        green.testSuiteFinished(true)
        assertEquals("revert", commandRunner.runCommand)
    }

    @Test
    fun itRunsRevertCommandOnFailure() {
        green.testSuiteFinished(false)
        assertEquals("commit", commandRunner.runCommand)
    }
}

