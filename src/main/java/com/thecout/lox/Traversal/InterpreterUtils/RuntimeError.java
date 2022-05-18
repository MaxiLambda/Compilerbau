package com.thecout.lox.Traversal.InterpreterUtils;


import com.thecout.lox.Scanner.Token;

public class RuntimeError extends RuntimeException {
    final Token token;

    public RuntimeError(Token token, String message) {
        super(message);
        this.token = token;
    }
}