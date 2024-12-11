package com.kivojenko.plugin.display;

import com.intellij.lang.folding.FoldingDescriptor;
import com.intellij.openapi.editor.FoldingGroup;
import com.intellij.psi.PsiElement;
import com.jetbrains.python.psi.*;
import lombok.Getter;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;


@Getter
public class PythonConstantFoldingVisitor extends PyRecursiveElementVisitor {
    private final List<FoldingDescriptor> descriptors = new ArrayList<>();

    @Override
    public void visitElement(@NotNull PsiElement element) {
        super.visitElement(element);

        if (!(element instanceof PyReferenceExpression reference) || isPartOfImport(reference)) {
            return;
        }

        var resolved = reference.getReference().resolve();
        if (!(resolved instanceof PyTargetExpression target) || !isUpperCase(target.getText())) {
            return;
        }

        if (target.getParent() instanceof PyAssignmentStatement assignment) {
            addFoldingDescriptor(reference, assignment);
        }
    }

    private boolean isPartOfImport(PyReferenceExpression reference) {
        var parent = reference.getParent();
        return parent instanceof PyImportElement || parent instanceof PyFromImportStatement;
    }

    private boolean isUpperCase(String text) {
        return text != null && text.equals(text.toUpperCase());
    }

    private void addFoldingDescriptor(PyReferenceExpression reference, PyAssignmentStatement assignment) {
        var valueElement = assignment.getAssignedValue();
        if (valueElement != null) {
            var descriptor = getFoldingDescriptor(reference, valueElement);
            descriptors.add(descriptor);
        }
    }

    private FoldingDescriptor getFoldingDescriptor(PyReferenceExpression reference, PyExpression value) {
        var placeholder = value.getText();
        var range = reference.getTextRange();
        var node = reference.getNode();
        var group = FoldingGroup.newGroup("Constant Folding " + placeholder);
        return new FoldingDescriptor(node, range, group, placeholder);
    }

}
