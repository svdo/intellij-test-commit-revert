package com.vandenoord.testCommitRevert

import com.intellij.openapi.project.Project

class StubPreferencesFactory: PreferencesFactory() {
    override fun getPreferences(project: Project): Preferences {
        return PreferencesStub()
    }
}