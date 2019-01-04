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
import javax.swing.Icon
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertNotNull
import kotlin.test.assertTrue

class TestResultHandlerTest {
    private lateinit var resultHandler: TestResultHandler
    private lateinit var project: Project

    @BeforeEach fun setUp() {
        val i: Icon = mockk()
        mockkStatic(IconLoader::class)
        every { IconLoader.getIcon(any<String>()) } returns i
        project = DummyProject.getInstance()
        resultHandler = TestResultHandler(project)
    }

    @AfterEach fun tearDown() {
        unmockkAll()
    }

    @Test fun itCanChangeStateToGreen() {
        resultHandler.testSuiteFinished(null)
        resultHandler.changeState(PluginState.Green)
        assertNotNull(resultHandler.state as? TestStatusListenerStateGreen)
    }

    @Test fun itCanChangeStateToRed() {
        resultHandler.testSuiteFinished(null)
        resultHandler.changeState(PluginState.Red)
        assertNotNull(resultHandler.state as? TestStatusListenerStateRed)
    }

    @Test fun itCanChangeStateToDisabled() {
        resultHandler.testSuiteFinished(null)
        resultHandler.changeState(PluginState.Red)
        resultHandler.changeState(PluginState.Disabled)
        assertNotNull(resultHandler.state as? TestStatusListenerStateDisabled)
    }

    @Test fun itCallsState() {
        val stateSpy = TestStatusListenerStateSpy()
        resultHandler.state = stateSpy
        resultHandler.testSuiteFinished(successfulTestResult())
        assertNotNull(stateSpy.lastTestResult)
    }

    @Test fun itCallsStateWithTestResultTrue() {
        val stateSpy = TestStatusListenerStateSpy()
        resultHandler.state = stateSpy
        resultHandler.testSuiteFinished(successfulTestResult())
        assertTrue(stateSpy.lastTestResult!!)
    }

    @Test fun itCallsStateWithTestResultFalse() {
        val stateSpy = TestStatusListenerStateSpy()
        resultHandler.state = stateSpy
        resultHandler.testSuiteFinished(failedTestResult())
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
