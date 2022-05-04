package com.thecout.lox.Parser.Expr;

public class Grouping extends Expr {
    public Grouping(Expr expression) {
        this.expression = expression;
    }


    final Expr expression;

    @Override
    public String print() {
        return "(%s)".formatted(expression.print());
    }
}
