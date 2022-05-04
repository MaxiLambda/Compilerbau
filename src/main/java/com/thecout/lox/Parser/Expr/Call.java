package com.thecout.lox.Parser.Expr;

import com.thecout.lox.Token;

import java.util.List;
import java.util.stream.Collectors;

public class Call extends Expr {
    public Call(Expr callee, Token paren, List<Expr> arguments) {
        this.callee = callee;
        this.paren = paren;
        this.arguments = arguments;
    }


    final Expr callee;
    final Token paren;
    final List<Expr> arguments;

    @Override
    public String print() {
        String args = arguments.stream().map(Expr::print).collect(Collectors.joining(" "));
        return "(%s %s)".formatted(callee.print(), args);
    }
}
