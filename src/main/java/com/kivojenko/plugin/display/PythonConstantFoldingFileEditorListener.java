package com.kivojenko.plugin.display;

import com.intellij.openapi.application.ApplicationManager;
import com.intellij.openapi.editor.Editor;
import com.intellij.openapi.editor.FoldRegion;
import com.intellij.openapi.editor.FoldingModel;
import com.intellij.openapi.fileEditor.FileEditorManager;
import com.intellij.openapi.fileEditor.FileEditorManagerListener;
import org.jetbrains.annotations.NotNull;


public class PythonConstantFoldingFileEditorListener implements FileEditorManagerListener {
    @Override
    public void fileOpened(@NotNull FileEditorManager source, @NotNull com.intellij.openapi.vfs.VirtualFile file) {
        Editor editor = source.getSelectedTextEditor();

        if (editor != null) setFoldingStateToTrueOnFileOpen(editor);
    }

    private void setFoldingStateToTrueOnFileOpen(Editor editor) {
        ApplicationManager.getApplication().invokeLater(() -> collapseFoldingModel(editor));
    }

    private void collapseFoldingModel(Editor editor) {
        FoldingModel foldingModel = editor.getFoldingModel();
        foldingModel.runBatchFoldingOperation(() -> collapseFoldRegions(foldingModel));
    }

    private void collapseFoldRegions(FoldingModel foldingModel) {
        var foldRegions = foldingModel.getAllFoldRegions();
        for (FoldRegion foldRegion : foldRegions) {
            collapseFoldRegion(foldRegion);
        }
    }

    private void collapseFoldRegion(FoldRegion foldRegion) {
        if (foldRegion.isExpanded() && foldRegion.getGroup() != null && !foldRegion.getPlaceholderText().equals("...")) {
            foldRegion.setExpanded(false);
        }
    }
}
