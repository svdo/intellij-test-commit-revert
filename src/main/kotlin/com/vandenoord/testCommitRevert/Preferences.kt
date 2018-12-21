package com.vandenoord.testCommitRevert

import com.intellij.openapi.components.PersistentStateComponent
import com.intellij.openapi.components.State
import com.intellij.openapi.components.Storage
import com.intellij.openapi.components.StoragePathMacros
import com.intellij.util.xmlb.XmlSerializerUtil

@State(
    name = "TestCommitRevertPreferences",
    storages = [ Storage(StoragePathMacros.WORKSPACE_FILE) ])
class Preferences: PersistentStateComponent<Preferences> {
    var commitCommand: String = DEFAULT_COMMIT_COMMAND
    var revertCommand: String = DEFAULT_REVERT_COMMAND

    override fun getState(): Preferences? {
        return this
    }

    override fun loadState(prefs: Preferences) {
        XmlSerializerUtil.copyBean(prefs, this)
    }

    companion object {
        const val DEFAULT_COMMIT_COMMAND = "git commit -a -m \"All green\""
        const val DEFAULT_REVERT_COMMAND = "git reset --hard"
    }
}