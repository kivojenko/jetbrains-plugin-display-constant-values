package com.kivojenko.plugin.display;

import com.intellij.lang.folding.FoldingDescriptor;
import com.intellij.openapi.editor.FoldingGroup;
import com.intellij.psi.PsiElement;
import com.intellij.psi.util.PsiTreeUtil;
import com.jetbrains.python.PyElementTypes;
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
        if (!(resolved instanceof PyTargetExpression target) || !(target.getParent() instanceof PyAssignmentStatement assignment)) {
            return;
        }


        if (isPartOfEnumeratedEnum(assignment) && isEnumeratedConst(assignment)) {
            addFoldingDescriptor(reference, target.getText());
        } else if (isUpperCase(target.getText())) {
            addConstFoldingDescriptor(reference, assignment.getAssignedValue());
        }

    }

    private boolean isPartOfImport(PyReferenceExpression reference) {
        var parent = reference.getParent();
        return parent instanceof PyImportElement || parent instanceof PyFromImportStatement;
    }

    private boolean isPartOfEnumeratedEnum(PyAssignmentStatement assignment) {
        var parentClass = PsiTreeUtil.getParentOfType(assignment, PyClass.class);
        if (parentClass == null) {
            return false;
        }

        return inheritsFromEnum(parentClass);
    }

    private boolean isEnumeratedConst(PyAssignmentStatement assignment) {
        PyExpression assignedValue = assignment.getAssignedValue();

        if (assignedValue instanceof PyNumericLiteralExpression numericLiteral) {
            return numericLiteral.getNode().getElementType() == PyElementTypes.INTEGER_LITERAL_EXPRESSION;
        }
        return false;
    }


    private boolean inheritsFromEnum(PyClass pyClass) {
        for (PyExpression expression : pyClass.getSuperClassExpressions()) {
            if (expression.getText().equals("Enum")) return true;
        }
        return false;
    }

    private boolean isUpperCase(String text) {
        return text != null && text.equals(text.toUpperCase());
    }

    private void addConstFoldingDescriptor(PyReferenceExpression reference, PyExpression valueElement) {
        if (valueElement != null) {
            addFoldingDescriptor(reference, valueElement.getText());
        }
    }


    private void addFoldingDescriptor(PyReferenceExpression reference, String placeholder) {
        var range = reference.getTextRange();
        var node = reference.getNode();
        var group = FoldingGroup.newGroup("Constant Folding " + placeholder);
        var descriptor = new FoldingDescriptor(node, range, group, placeholder);
        descriptors.add(descriptor);
    }
}
