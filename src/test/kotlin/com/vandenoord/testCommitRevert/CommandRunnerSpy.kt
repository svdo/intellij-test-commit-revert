package com.vandenoord.testCommitRevert

class CommandRunnerSpy : CommandRunner {
    var runCommand = ""

    override fun runCommand(command: String) {
        this.runCommand = command
    }
}