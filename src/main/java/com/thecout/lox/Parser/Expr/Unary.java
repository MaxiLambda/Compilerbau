package com.thecout.lox.Parser.Expr;


import com.thecout.lox.Scanner.Token;

public class Unary extends Expr {
    public Unary(Token operator, Expr right) {
        this.operator = operator;
        this.right = right;
    }


    final Token operator;
    final Expr right;

    @Override
    public String print() {
        return "%s%s".formatted(operator.lexeme, right.print());
    }
}
