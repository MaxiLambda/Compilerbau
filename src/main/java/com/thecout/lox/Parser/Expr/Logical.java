package com.thecout.lox.Parser.Expr;

import com.thecout.lox.Token;

public class Logical extends Expr {
    public Logical(Expr left, Token operator, Expr right) {
        this.left = left;
        this.operator = operator;
        this.right = right;
    }


    final Expr left;
    final Token operator;
    final Expr right;

    @Override
    public String print() {
        return "(%s %s %s)".formatted(operator.lexeme, left.print(), right.print());
    }
}
