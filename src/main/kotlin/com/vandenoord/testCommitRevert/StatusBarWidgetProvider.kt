package com.vandenoord.testCommitRevert

import com.intellij.openapi.project.Project
import com.intellij.openapi.wm.StatusBarWidget

class StatusBarWidgetProvider: com.intellij.openapi.wm.StatusBarWidgetProvider {
    override fun getWidget(project: Project): StatusBarWidget? {
        return ModeWidget()
    }
}
