package com.thecout.lox.Traversal;


import com.thecout.lox.Parser.Expr.*;
import com.thecout.lox.Parser.Stmts.*;
import com.thecout.lox.Traversal.InterpreterUtils.Environment;
import com.thecout.lox.Traversal.InterpreterUtils.LoxCallable;
import com.thecout.lox.Traversal.InterpreterUtils.RuntimeError;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Interpreter implements ExprVisitor<Object>,
        StmtVisitor<Void> {

    public final Environment globals = new Environment();
    private Environment environment = globals;




    public Interpreter() {
        globals.define("clock", new LoxCallable() {
            @Override
            public int arity() {
                return 0;
            }

            @Override
            public Object call(Interpreter interpreter,
                               List<Object> arguments) {
                return (double) System.currentTimeMillis() / 1000.0;
            }

            @Override
            public String toString() {
                return "<native fn>";
            }
        });
    }

    public void interpret(List<Stmt> statements) {
        try {
            for (Stmt statement : statements) {
                execute(statement);
            }
        } catch (RuntimeError error) {
            error.printStackTrace();
        }
    }

    public void executeBlock(List<Stmt> statements,
                             Environment environment) {
        Environment previous = this.environment;
        try {
            this.environment = environment;

            for (Stmt statement : statements) {
                execute(statement);
            }
        } finally {
            this.environment = previous;
        }
    }

    private Object evaluate(Expr expr) {
        return expr.accept(this);
    }

    public void execute(Stmt stmt) {
        stmt.accept(this);
    }


    @Override
    public Object visitAssignExpr(Assign expr) {
        return null;
    }

    @Override
    public Object visitBinaryExpr(Binary expr) {
        return null;
    }

    @Override
    public Object visitCallExpr(Call expr) {
        return null;
    }

    @Override
    public Object visitGroupingExpr(Grouping expr) {
        return null;
    }

    @Override
    public Object visitLiteralExpr(Literal expr) {
        return null;
    }

    @Override
    public Object visitLogicalExpr(Logical expr) {
        return null;
    }

    @Override
    public Object visitUnaryExpr(Unary expr) {
        return null;
    }

    @Override
    public Object visitVariableExpr(Variable expr) {
        return null;
    }

    @Override
    public Void visitBlockStmt(Block stmt) {
        return null;
    }

    @Override
    public Void visitExpressionStmt(Expression stmt) {
        return null;
    }

    @Override
    public Void visitFunctionStmt(Function stmt) {
        return null;
    }

    @Override
    public Void visitIfStmt(If stmt) {
        return null;
    }

    @Override
    public Void visitPrintStmt(Print stmt) {
        return null;
    }

    @Override
    public Void visitReturnStmt(Return stmt) {
        return null;
    }

    @Override
    public Void visitVarStmt(Var stmt) {
        return null;
    }

    @Override
    public Void visitWhileStmt(While stmt) {
        return null;
    }

}