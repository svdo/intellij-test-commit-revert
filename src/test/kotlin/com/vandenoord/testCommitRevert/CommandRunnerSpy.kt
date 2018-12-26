package com.vandenoord.testCommitRevert

import com.intellij.openapi.project.Project

class CommandRunnerSpy(override val project: Project) : CommandRunner {
    var runCommand = ""

    override fun runCommand(command: String) {
        this.runCommand = command
    }
}