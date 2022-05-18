package com.thecout.lox.Parser.Expr;


public abstract class Expr {
    public abstract String print();
    public abstract <R> R accept(ExprVisitor<R> exprVisitor);

}

