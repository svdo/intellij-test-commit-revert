package com.vandenoord.testCommitRevert

import com.intellij.notification.Notifications
import com.intellij.openapi.project.Project
import io.mockk.*
import org.junit.After
import org.junit.Before
import org.junit.Test
import java.io.File

internal class DefaultCommandRunnerTest {

    lateinit var runtime: Runtime
    lateinit var project: Project
    lateinit var currentDir: String
    lateinit var runner: DefaultCommandRunner

    @Before fun beforeEach() {
        mockRuntime()
        mockProject()
        mockNotificationsBus()
        runner = DefaultCommandRunner(project)
    }

    private fun mockRuntime() {
        runtime = mockk<Runtime>(relaxed = true)
        var process: Process = mockk<Process>(relaxed = true)
        mockkStatic(Runtime::class)
        every { Runtime.getRuntime() } returns runtime
        every { runtime.exec(any<String>(), any(), any()) } returns process
    }

    private fun mockProject() {
        project = mockk<Project>()
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