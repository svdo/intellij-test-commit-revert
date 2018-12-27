package com.vandenoord.testCommitRevert

import com.intellij.notification.Notifications
import com.intellij.openapi.project.Project
import io.mockk.*
import org.junit.After
import org.junit.Before
import org.junit.Test
import java.io.File

internal class DefaultCommandRunnerTest {

    private lateinit var runtime: Runtime
    private lateinit var project: Project
    private lateinit var currentDir: String
    private lateinit var runner: DefaultCommandRunner

    @Before fun beforeEach() {
        mockRuntime()
        mockProject()
        mockNotificationsBus()
        runner = DefaultCommandRunner(project)
    }

    private fun mockRuntime() {
        runtime = mockk(relaxed = true)
        val process: Process = mockk(relaxed = true)
        mockkStatic(Runtime::class)
        every { Runtime.getRuntime() } returns runtime
        every { runtime.exec(any<String>(), any(), any()) } returns process
    }

    private fun mockProject() {
        project = mockk()
        currentDir = System.getProperty("user.dir")
        every { project.basePath } returns currentDir
    }

    private fun mockNotificationsBus() {
        mockkStatic(Notifications.Bus::class)
        every { Notifications.Bus.notify(any(), project) } returns Unit
    }

    @After fun afterEach() {
        unmockkAll()
    }

    @Test fun runCommand() {
        runner.runCommand("some command")
        verify { runtime.exec("some command", any(), File(currentDir)) }
    }
}