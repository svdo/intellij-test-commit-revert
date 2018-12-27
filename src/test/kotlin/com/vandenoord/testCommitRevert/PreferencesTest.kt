package com.vandenoord.testCommitRevert

import com.intellij.openapi.components.State
import com.intellij.openapi.components.StoragePathMacros
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

class PreferencesTest {
    @Test fun itHasDefaultCommitCommand() {
        assertEquals(Preferences.GIT_COMMIT_COMMAND, Preferences().commitCommand)
    }

    @Test fun itHasDefaultRevertCommand() {
        assertEquals(Preferences.GIT_REVERT_COMMAND, Preferences().revertCommand)
    }

    @Test fun itCanGetState() {
        val preferences = Preferences()
        assertEquals(preferences, preferences.state)
    }

    @Test fun itCanLoadState() {
        val preferences = Preferences()
        val source = Preferences()
        source.commitCommand = "new command"
        preferences.loadState(source)
        assertEquals(source.commitCommand, preferences.commitCommand)
    }

    @Test fun stateAnnotationHasProperName() {
        assertEquals("TestCommitRevertPreferences", stateAnnotation()?.name)
    }

    @Test fun itIsStoredInWorkspaceFile() {
        assertEquals(StoragePathMacros.WORKSPACE_FILE,
            stateAnnotation()?.storages?.first()?.value)
    }

    private fun stateAnnotation(): State? {
        val annotations = Preferences().javaClass.declaredAnnotations
        return annotations.first { a -> a is State } as? State
    }
}