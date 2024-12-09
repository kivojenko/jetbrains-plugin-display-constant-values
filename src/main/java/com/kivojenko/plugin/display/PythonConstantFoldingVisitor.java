package com.kivojenko.plugin.display;

import com.intellij.lang.folding.FoldingDescriptor;
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

        if (element instanceof PyReferenceExpression reference && !isPartOfImport(reference)) {
            var resolved = reference.getReference().resolve();
            if (resolved instanceof PyTargetExpression target) {
                var parent = target.getParent();
                if (parent instanceof PyAssignmentStatement assignment) {
                    addFoldingDescriptor(reference, assignment);
                }
            }
        }
    }


    private boolean isPartOfImport(PyReferenceExpression reference) {
        PsiElement parent = reference.getParent();
        return parent instanceof PyImportElement || parent instanceof PyFromImportStatement;
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
        return new FoldingDescriptor(node, range, null, placeholder);
    }

}
