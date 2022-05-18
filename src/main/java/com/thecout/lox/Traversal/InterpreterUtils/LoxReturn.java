package com.thecout.lox.Traversal.InterpreterUtils;

public class LoxReturn extends RuntimeException {
    final Object value;

    public LoxReturn(Object value) {
        super(null, null, false, false);
        this.value = value;
    }
}
