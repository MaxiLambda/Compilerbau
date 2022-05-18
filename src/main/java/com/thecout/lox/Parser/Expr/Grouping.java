package com.thecout.lox.Parser.Expr;

public class Grouping extends Expr {
    public Grouping(Expr expression) {
        this.expression = expression;
    }


    public final Expr expression;

    @Override
    public String print() {
        return "(%s)".formatted(expression.print());
    }

    @Override
    public <R> R accept(ExprVisitor<R> exprVisitor) {
        return exprVisitor.visitGroupingExpr(this);
    }
}
