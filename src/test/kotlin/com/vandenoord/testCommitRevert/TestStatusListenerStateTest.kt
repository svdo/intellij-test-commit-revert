package com.vandenoord.testCommitRevert

import com.intellij.openapi.command.impl.DummyProject
import com.intellij.openapi.project.Project
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

abstract class TestStatusListenerStateTest {
    protected lateinit var preferencesFactory: StubPreferencesFactory
    protected lateinit var p: Project
    protected lateinit var commandRunner: CommandRunnerSpy
    protected lateinit var state: TestStatusListenerState

    fun setUpProperties() {
        preferencesFactory = StubPreferencesFactory()
        p = DummyProject.getInstance()
        commandRunner = CommandRunnerSpy(p)
    }
}

class TestStatusListenerStateDisabledTest: TestStatusListenerStateTest() {

    @BeforeEach
    fun setUp() {
        setUpProperties()
        state = TestStatusListenerStateDisabled(preferencesFactory, p, commandRunner)
    }

    @Test
    fun itRunsCommitCommandOnSuccess() {
        state.testSuiteFinished(true)
        assertEquals("", commandRunner.runCommand)
    }

    @Test
    fun itRunsRevertCommandOnFailure() {
        state.testSuiteFinished(false)
        assertEquals("", commandRunner.runCommand)
    }
}

class TestStatusListenerStateGreenTest: TestStatusListenerStateTest() {

    @BeforeEach
    fun setUp() {
        setUpProperties()
        state = TestStatusListenerStateGreen(preferencesFactory, p, commandRunner)
    }

    @Test
    fun itRunsCommitCommandOnSuccess() {
        state.testSuiteFinished(true)
        assertEquals("commit", commandRunner.runCommand)
    }

    @Test
    fun itRunsRevertCommandOnFailure() {
        state.testSuiteFinished(false)
        assertEquals("revert", commandRunner.runCommand)
    }
}

class TestStatusListenerStateRedTest: TestStatusListenerStateTest() {

    @BeforeEach
    fun setUp() {
        setUpProperties()
        state = TestStatusListenerStateRed(preferencesFactory, p, commandRunner)
    }

    @Test
    fun itRunsCommitCommandOnSuccess() {
        state.testSuiteFinished(true)
        assertEquals("revert", commandRunner.runCommand)
    }

    @Test
    fun itRunsRevertCommandOnFailure() {
        state.testSuiteFinished(false)
        assertEquals("commit", commandRunner.runCommand)
    }
}

