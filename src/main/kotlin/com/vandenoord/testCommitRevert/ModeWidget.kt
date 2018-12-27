package com.vandenoord.testCommitRevert

import com.intellij.openapi.wm.StatusBar
import com.intellij.openapi.wm.StatusBarWidget
import com.intellij.util.Consumer
import java.awt.event.MouseEvent
import javax.swing.Icon

class ModeWidget : StatusBarWidget, StatusBarWidget.IconPresentation {
    private var statusBar: StatusBar? = null
    private var state: PluginState = PluginState.Disabled


    override fun ID(): String {
        return "StatusBar.TestCommitRevertMode"
    }

    override fun getPresentation(type: StatusBarWidget.PlatformType): StatusBarWidget.WidgetPresentation? {
        return this
    }

    override fun install(statusBar: StatusBar) {
        this.statusBar = statusBar
    }

    override fun dispose() {
        this.statusBar = null
    }

    override fun getTooltipText(): String? {
        return state.tooltipText
    }

    override fun getIcon(): Icon {
        return state.icon
    }

    override fun getClickConsumer(): Consumer<MouseEvent>? {
        return Consumer { _ ->
            state = state.toggle()
            statusBar?.updateWidget(ID())
        }
    }
}
