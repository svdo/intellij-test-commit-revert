package com.vandenoord.testCommitRevert

import com.intellij.openapi.project.Project

open class PreferencesFactory {
    open fun getPreferences(project: Project): Preferences {
        return Preferences.getInstance(project)
    }
}