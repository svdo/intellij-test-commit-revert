package com.vandenoord.testCommitRevert;

import com.intellij.openapi.options.Configurable;
import com.intellij.openapi.options.ConfigurationException;
import com.intellij.openapi.project.Project;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;

public class PreferencesForm implements Configurable {
    private JTextField commitField;
    private JTextField revertField;
    private JPanel panel;
    private Preferences config;

    PreferencesForm(Project p) {
        config = Preferences.Companion.getInstance(p);
    }

//    @Nls(capitalization = Nls.Capitalization.Title)
    @Override
    public String getDisplayName() {
        return "Test && Commit || Revert";
    }

    @Nullable
    @Override
    public JComponent createComponent() {
        return panel;
    }

    @Override
    public boolean isModified() {
        boolean commitCommandChanged = !config.getCommitCommand().equals(commitField.getText());
        boolean revertCommandChanged = !config.getRevertCommand().equals(revertField.getText());
        return commitCommandChanged || revertCommandChanged;
    }

    @Override
    public void apply() throws ConfigurationException {
        config.setCommitCommand(commitField.getText());
        config.setRevertCommand(revertField.getText());
    }

    @Override
    public void reset() {
        commitField.setText(config.getCommitCommand());
        revertField.setText(config.getRevertCommand());
    }

}
