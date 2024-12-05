package com.kivojenko.plugin.display;

import com.intellij.lang.ASTNode;
import com.intellij.lang.folding.FoldingBuilderEx;
import com.intellij.lang.folding.FoldingDescriptor;
import com.intellij.openapi.editor.Document;
import com.intellij.openapi.project.DumbAware;
import com.intellij.openapi.util.TextRange;
import com.intellij.psi.PsiElement;
import com.jetbrains.python.psi.*;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

public class PythonConstantFoldingBuilder extends FoldingBuilderEx implements DumbAware {
    @Override
    public @NotNull FoldingDescriptor @NotNull [] buildFoldRegions(@NotNull PsiElement root, @NotNull Document document, boolean quick) {
        // Only process Python files
        if (!(root instanceof PyFile)) {
            return FoldingDescriptor.EMPTY_ARRAY;
        }

        List<FoldingDescriptor> descriptors = new ArrayList<>();

        // Iterate through all elements in the file to find usages of constants
        root.accept(new PyRecursiveElementVisitor() {
            @Override
            public void visitElement(@NotNull PsiElement element) {
                super.visitElement(element);

                // Check if this is a reference to a constant
                if (element instanceof PyReferenceExpression reference) {
                    String referencedName = reference.getName();
                    if (referencedName != null) {
                        if (isPartOfImport(reference)) {
                            return; // Do not fold imports
                        }

                        PsiElement resolved = reference.getReference().resolve();
                        if (resolved instanceof PyTargetExpression target) {
                            PsiElement parent = target.getParent();
                            if (parent instanceof PyAssignmentStatement assignment) {
                                PsiElement valueElement = assignment.getAssignedValue();
                                if (valueElement != null) {
                                    String valueText = valueElement.getText(); // Get the value as a string
                                    addFoldingDescriptor(reference, valueText, descriptors);
                                }
                            }

                        }
                    }
                }
            }
        });

        return descriptors.toArray(FoldingDescriptor[]::new);
    }


    private boolean isPartOfImport(PyReferenceExpression reference) {
        PsiElement parent = reference.getParent();
        return parent instanceof PyImportElement || parent instanceof PyFromImportStatement;
    }

    private void addFoldingDescriptor(PyReferenceExpression reference, String placeholder, List<FoldingDescriptor> descriptors) {
        TextRange range = reference.getTextRange(); // Get the range of the usage
        descriptors.add(new FoldingDescriptor(reference.getNode(), range, null) {
            @Override
            public @Nullable String getPlaceholderText() {
                return placeholder; // Show the value as the folded text
            }
        });
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
