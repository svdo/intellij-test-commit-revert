package com.vandenoord.testCommitRevert

import com.intellij.openapi.util.IconLoader
import javax.swing.Icon

enum class PluginState {
    Disabled {
        override val icon get() = disabledIcon
        override val tooltipText get() = "Test && Commit || Revert is disabled"
    },
    Green {
        override val icon get() = greenIcon
        override val tooltipText get() = "Test && Commit || Revert: tests must be green"
    }, Red {
        override val icon get() = redIcon
        override val tooltipText get() = "Test && Commit || Revert: tests must be red"
    };

    val disabledIcon = IconLoader.getIcon("grey.svg")
    val redIcon = IconLoader.getIcon("red.svg")
    val greenIcon = IconLoader.getIcon("green.svg")

    abstract val icon: Icon
    abstract val tooltipText: String

    fun toggle(): PluginState {
        if (this == Disabled || this == Red) {
            return Green
        }
        else {
            return Red
        }
    }
}