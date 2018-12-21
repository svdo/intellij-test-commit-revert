package com.vandenoord.testCommitRevert

class CommandRunnerSpy : CommandRunner {
    var ranCommand = ""

    override fun runCommand(command: String) {
        this.ranCommand = command
    }
}