package com.vandenoord.testCommitRevert

interface CommandRunner {
    fun runCommand(command: String)
}

class DefaultCommandRunner: CommandRunner {
    override fun runCommand(command: String) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}