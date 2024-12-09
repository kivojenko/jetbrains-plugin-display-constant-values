package com.kivojenko.plugin.display;

import com.intellij.lang.ASTNode;
import com.intellij.lang.folding.FoldingBuilderEx;
import com.intellij.lang.folding.FoldingDescriptor;
import com.intellij.openapi.editor.Document;
import com.intellij.openapi.project.DumbAware;
import com.intellij.psi.PsiElement;
import com.jetbrains.python.psi.PyFile;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class PythonConstantFoldingBuilder extends FoldingBuilderEx implements DumbAware {
    @Override
    public @NotNull FoldingDescriptor @NotNull [] buildFoldRegions(@NotNull PsiElement root, @NotNull Document document, boolean quick) {
        if (!(root instanceof PyFile)) {
            return FoldingDescriptor.EMPTY_ARRAY;
        }
        var visitor = new PythonConstantFoldingVisitor();
        root.accept(visitor);
        return visitor.getDescriptors().toArray(FoldingDescriptor[]::new);
    }


    @Override
    public @Nullable String getPlaceholderText(@NotNull ASTNode node) {
        return "";
    }

    @Override
    public boolean isCollapsedByDefault(@NotNull ASTNode node) {
        return true;
    }

    @Override
    public boolean isCollapsedByDefault(@NotNull FoldingDescriptor foldingDescriptor) {
        return true;
    }
}
