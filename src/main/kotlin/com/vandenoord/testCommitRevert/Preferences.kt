package com.vandenoord.testCommitRevert

import com.intellij.openapi.components.*
import com.intellij.openapi.project.Project
import com.intellij.util.xmlb.XmlSerializerUtil

@State(
    name = "TestCommitRevertPreferences",
    storages = [ Storage(StoragePathMacros.WORKSPACE_FILE) ])
open class Preferences: PersistentStateComponent<Preferences> {
    var commitCommand: String = GIT_COMMIT_COMMAND
    var revertCommand: String = GIT_REVERT_COMMAND

    override fun getState(): Preferences? {
        return this
    }

    override fun loadState(prefs: Preferences) {
        XmlSerializerUtil.copyBean(prefs, this)
    }

    companion object {
        fun getInstance(project: Project): Preferences {
            return ServiceManager.getService(project, Preferences::class.java)
        }

        const val GIT_COMMIT_COMMAND = "git commit -a -m \"All green\""
        const val GIT_REVERT_COMMAND = "git reset --hard"
    }
}