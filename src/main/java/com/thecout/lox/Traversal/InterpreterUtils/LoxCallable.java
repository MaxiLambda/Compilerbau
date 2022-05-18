package com.thecout.lox.Traversal.InterpreterUtils;


import com.thecout.lox.Traversal.Interpreter;

import java.util.List;

public interface LoxCallable {
    int arity();

    Object call(Interpreter interpreter, List<Object> arguments);
}