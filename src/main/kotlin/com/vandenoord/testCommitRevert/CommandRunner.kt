package com.vandenoord.testCommitRevert

import com.intellij.notification.Notification
import com.intellij.notification.NotificationType
import com.intellij.notification.Notifications
import com.intellij.openapi.project.Project
import java.io.File
import java.io.IOException

interface CommandRunner {
    val project: Project
    fun runCommand(command: String)
}

class DefaultCommandRunner(override val project: Project) : CommandRunner {
    override fun runCommand(command: String) {
        val env:Array<String> = ProcessBuilder().environment().map {
                entry -> "${entry.key}=${entry.value}"
        }.toTypedArray()
        val cwd = File(project.basePath!!)

        try {
            Runtime.getRuntime().exec(command, env, cwd)
            notify("Command", command, NotificationType.INFORMATION)
        } catch (e: IOException) {
            e.printStackTrace()
            notify("Error", e.localizedMessage, NotificationType.ERROR)
        }
    }

    private fun notify(title: String, content: String, type: NotificationType) {
        val notification = createNotification(title, content, type)
        Notifications.Bus.notify(
            notification,
            project
        )
    }

    private fun createNotification(
        title: String,
        content: String,
        type: NotificationType
    ): Notification {
        return Notification(
            "Test && Commit || Reject",
            title,
            content,
            type
        )
    }
}