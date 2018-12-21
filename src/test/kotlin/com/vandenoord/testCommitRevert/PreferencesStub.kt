package com.vandenoord.testCommitRevert

class PreferencesStub: Preferences {
    constructor() {
        commitCommand = "commit"
        revertCommand = "revert"
    }
}