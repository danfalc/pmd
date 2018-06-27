/**
 * BSD-style license; for more info see http://pmd.sourceforge.net/license.html
 */
/* Generated By:JJTree: Do not edit this line. ASTLocalVariableDeclaration.java */

package net.sourceforge.pmd.lang.java.ast;

import net.sourceforge.pmd.Rule;

public class ASTLocalVariableDeclaration extends AbstractJavaAccessNode implements Dimensionable, CanSuppressWarnings {

    public ASTLocalVariableDeclaration(int id) {
        super(id);
    }

    public ASTLocalVariableDeclaration(JavaParser p, int id) {
        super(p, id);
    }

    /**
     * Accept the visitor. *
     */
    @Override
    public Object jjtAccept(JavaParserVisitor visitor, Object data) {
        return visitor.visit(this, data);
    }

    @Override
    public boolean hasSuppressWarningsAnnotationFor(Rule rule) {
        for (int i = 0; i < jjtGetNumChildren(); i++) {
            if (jjtGetChild(i) instanceof ASTAnnotation) {
                ASTAnnotation a = (ASTAnnotation) jjtGetChild(i);
                if (a.suppresses(rule)) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * If true, this local variable declaration represents a declaration,
     * which makes use of local variable type inference, e.g. java10 "var".
     * You can receive the inferred type via {@link ASTVariableDeclarator#getType()}.
     *
     * @see ASTVariableDeclaratorId#isTypeInferred()
     */
    public boolean isTypeInferred() {
        return getTypeNode() == null;
    }

    @Override
    public boolean isArray() {
        return getArrayDepth() > 0;
    }

    @Override
    public int getArrayDepth() {
        return getArrayDimensionOnType() + getArrayDimensionOnDeclaratorId();
    }

    /**
     * Gets the type node for this variable declaration statement.
     * With Java10 and local variable type inference, there might be
     * no type node at all.
     * @return The type node or <code>null</code>
     * @see #isTypeInferred()
     */
    public ASTType getTypeNode() {
        return getFirstChildOfType(ASTType.class);
    }

    private int getArrayDimensionOnType() {
        ASTType typeNode = getTypeNode();
        if (typeNode != null) {
            return typeNode.getArrayDepth();
        }
        return 0;
    }

    private ASTVariableDeclaratorId getDecl() {
        return (ASTVariableDeclaratorId) jjtGetChild(jjtGetNumChildren() - 1).jjtGetChild(0);
    }

    private int getArrayDimensionOnDeclaratorId() {
        return getDecl().getArrayDepth();
    }

    /**
     * Gets the variable name of this field. This method searches the first
     * VariableDeclartorId node and returns it's image or <code>null</code> if
     * the child node is not found.
     *
     * @return a String representing the name of the variable
     */
    public String getVariableName() {
        ASTVariableDeclaratorId decl = getFirstDescendantOfType(ASTVariableDeclaratorId.class);
        if (decl != null) {
            return decl.getImage();
        }
        return null;
    }
}
