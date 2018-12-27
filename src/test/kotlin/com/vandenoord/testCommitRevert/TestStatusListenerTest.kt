package com.vandenoord.testCommitRevert

import com.intellij.execution.testframework.sm.runner.SMTestProxy
import com.intellij.openapi.command.impl.DummyProject
import com.intellij.openapi.project.Project
import com.intellij.openapi.util.IconLoader
import io.mockk.every
import io.mockk.mockk
import io.mockk.mockkStatic
import io.mockk.unmockkAll
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import java.awt.Component
import java.awt.Graphics
import javax.swing.Icon
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertNotNull
import kotlin.test.assertTrue

class TestStatusListenerTest {
    private lateinit var testStatusListener: TestStatusListener
    private lateinit var project: Project

    @BeforeEach fun setUp() {
        val i: Icon = mockk()
        mockkStatic(IconLoader::class)
        every { IconLoader.getIcon(any<String>()) } returns i
        testStatusListener = TestStatusListener()
        project = DummyProject.getInstance()
    }

    @AfterEach fun tearDown() {
        unmockkAll()
    }

    @Test fun itConfiguresItself() {
        testStatusListener.testSuiteFinished(null, project)
        assertEquals(project, testStatusListener.project)
        assertNotNull(testStatusListener.state as? TestStatusListenerStateDisabled)
    }

    @Test fun itCanChangeStateToGreen() {
        testStatusListener.testSuiteFinished(null, project)
        testStatusListener.changeState(PluginState.Green)
        assertNotNull(testStatusListener.state as? TestStatusListenerStateGreen)
    }

    @Test fun itCanChangeStateToRed() {
        testStatusListener.testSuiteFinished(null, project)
        testStatusListener.changeState(PluginState.Red)
        assertNotNull(testStatusListener.state as? TestStatusListenerStateRed)
    }

    @Test fun itCanChangeStateToDisabled() {
        testStatusListener.testSuiteFinished(null, project)
        testStatusListener.changeState(PluginState.Red)
        testStatusListener.changeState(PluginState.Disabled)
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
        val tests = SMTestProxy.SMRootTestProxy()
        val test = SMTestProxy("dummy", false, null)
        test.setFinished()
        tests.addChild(test)
        return tests
    }

    private fun failedTestResult(): SMTestProxy.SMRootTestProxy {
        val tests = SMTestProxy.SMRootTestProxy()
        val test = SMTestProxy("dummy", false, null)
        test.setTestFailed("failed", null, false)
        tests.addChild(test)
        return tests
    }
}
