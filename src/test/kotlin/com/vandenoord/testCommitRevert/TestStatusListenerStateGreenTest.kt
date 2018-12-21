package com.vandenoord.testCommitRevert

import com.intellij.openapi.command.impl.DummyProject
import com.intellij.openapi.project.Project
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

class TestStatusListenerStateGreenTest {

    lateinit var preferencesFactory: StubPreferencesFactory
    lateinit var p: Project
    lateinit var commandRunner: CommandRunnerSpy
    lateinit var green: TestStatusListenerStateGreen

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
        assertEquals("commit", commandRunner.ranCommand)
    }

    @Test
    fun itRunsRevertCommandOnFailure() {
        green.testSuiteFinished(false)
        assertEquals("revert", commandRunner.ranCommand)
    }
}