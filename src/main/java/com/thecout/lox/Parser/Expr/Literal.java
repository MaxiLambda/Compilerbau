package com.thecout.lox.Parser.Expr;

public class Literal extends Expr {
    public Literal(Object value) {
        this.value = value;
    }


    public final Object value;

    @Override
    public String print() {
        return String.valueOf(value);
    }

    @Override
    public <R> R accept(ExprVisitor<R> exprVisitor) {
        return exprVisitor.visitLiteralExpr(this);
    }
}
