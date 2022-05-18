package com.thecout.lox.Parser.Stmts;

public abstract class Stmt {
    public abstract String print();
    public abstract <R> R accept(StmtVisitor<R> stmtVisitor);
}

