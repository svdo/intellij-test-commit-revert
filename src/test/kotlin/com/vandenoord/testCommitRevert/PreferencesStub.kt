package com.vandenoord.testCommitRevert

class PreferencesStub : Preferences() {
    init {
        commitCommand = "commit"
        revertCommand = "revert"
    }
}