package com.thecout.lox.Parser.Stmts;

import com.thecout.lox.Parser.Expr.Expr;
import com.thecout.lox.Scanner.Token;

public class Return extends Stmt {
    public Return( Expr value) {
        this.value = value;
    }

    final Expr value;

    @Override
    public String print() {
        return "(return %s)".formatted(value.print());
    }
}
