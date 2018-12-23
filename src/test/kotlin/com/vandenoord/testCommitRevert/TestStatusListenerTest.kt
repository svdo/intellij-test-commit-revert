package com.vandenoord.testCommitRevert

import com.intellij.execution.testframework.sm.runner.SMTestProxy
import com.intellij.openapi.command.impl.DummyProject
import com.intellij.openapi.project.Project
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertNotNull
import kotlin.test.assertTrue

class TestStatusListenerTest {
    lateinit var testStatusListener: TestStatusListener
    lateinit var project: Project

    @BeforeEach fun setUp() {
        testStatusListener = TestStatusListener()
        project = DummyProject.getInstance()
    }

    @Test fun itConfiguresItself() {
        testStatusListener.testSuiteFinished(null, project)
        assertEquals(project, testStatusListener.project)
        assertNotNull(testStatusListener.state as? TestStatusListenerStateDisabled)
    }

    @Test fun itCanChangeStateToGreen() {
        testStatusListener.testSuiteFinished(null, project)
        testStatusListener.changeState(TestStatusListener.State.Green)
        assertNotNull(testStatusListener.state as? TestStatusListenerStateGreen)
    }

    @Test fun itCanChangeStateToRed() {
        testStatusListener.testSuiteFinished(null, project)
        testStatusListener.changeState(TestStatusListener.State.Red)
        assertNotNull(testStatusListener.state as? TestStatusListenerStateRed)
    }

    @Test fun itCanChangeStateToDisabled() {
        testStatusListener.testSuiteFinished(null, project)
        testStatusListener.changeState(TestStatusListener.State.Red)
        testStatusListener.changeState(TestStatusListener.State.Disabled)
        assertNotNull(testStatusListener.state as? TestStatusListenerStateDisabled)
    }

    @Test fun itCallsState() {
        val stateSpy = TestStatusListenerStateSpy()
        testStatusListener.state = stateSpy
        testStatusListener.testSuiteFinished(successfulTestResult())
        assertNotNull(stateSpy.lastTestResult)
    }

    @Test fun itCallsStateWithTestResultTrue() {
        val stateSpy = TestStatusListenerStateSpy()
        testStatusListener.state = stateSpy
        testStatusListener.testSuiteFinished(successfulTestResult())
        assertTrue(stateSpy.lastTestResult!!)
    }

    @Test fun itCallsStateWithTestResultFalse() {
        val stateSpy = TestStatusListenerStateSpy()
        testStatusListener.state = stateSpy
        testStatusListener.testSuiteFinished(failedTestResult())
        assertFalse(stateSpy.lastTestResult!!)
    }

    private fun successfulTestResult(): SMTestProxy.SMRootTestProxy {
        var tests = SMTestProxy.SMRootTestProxy()
        val test = SMTestProxy("dummy", false, null)
        test.setFinished()
        tests.addChild(test)
        return tests
    }

    private fun failedTestResult(): SMTestProxy.SMRootTestProxy {
        var tests = SMTestProxy.SMRootTestProxy()
        val test = SMTestProxy("dummy", false, null)
        test.setTestFailed("failed", null, false)
        tests.addChild(test)
        return tests
    }
}
