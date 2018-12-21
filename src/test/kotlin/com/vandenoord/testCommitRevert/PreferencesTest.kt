package com.vandenoord.testCommitRevert

import com.intellij.openapi.components.State
import com.intellij.openapi.components.StoragePathMacros
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

internal class PreferencesTest {
    @Test fun itHasDefaultCommitCommand() {
        assertEquals(Preferences.DEFAULT_COMMIT_COMMAND, Preferences().commitCommand)
    }

    @Test fun itHasDefaultRevertCommand() {
        assertEquals(Preferences.DEFAULT_REVERT_COMMAND, Preferences().revertCommand)
    }

    @Test fun itCanGetState() {
        val prefs = Preferences()
        assertEquals(prefs, prefs.state)
    }

    @Test fun itCanLoadState() {
        val prefs = Preferences()
        val source = Preferences()
        source.commitCommand = "new command"
        prefs.loadState(source)
        assertEquals(source.commitCommand, prefs.commitCommand)
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
        val annotation = annotations.first { a -> a is State } as? State
        return annotation
    }
}