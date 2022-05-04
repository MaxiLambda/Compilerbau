package com.thecout.lox.Parser.Expr;

import com.thecout.lox.Token;

public class Assign extends Expr {
    public Assign(Token name, Expr value) {
        this.name = name;
        this.value = value;
    }

    final Token name;
    final Expr value;

    @Override
    public String print() {
        return "(= %s %s)".formatted(name.lexeme, value.print());
    }
}
