package com.thecout.lox.Traversal;

import com.thecout.lox.Parser.Expr.*;
import com.thecout.lox.Parser.Stmts.*;

public class AstPrinter implements ExprVisitor<String>, StmtVisitor<String> {
    public String print(Expr expr) {
        return expr.accept(this);
    }

    public String print(Stmt stmt) {
        return stmt.accept(this);
    }


    @Override
    public String visitAssignExpr(Assign expr) {
        return null;
    }

    @Override
    public String visitBinaryExpr(Binary expr) {
        return null;
    }

    @Override
    public String visitCallExpr(Call expr) {
        return null;
    }

    @Override
    public String visitGroupingExpr(Grouping expr) {
        return null;
    }

    @Override
    public String visitLiteralExpr(Literal expr) {
        return null;
    }

    @Override
    public String visitLogicalExpr(Logical expr) {
        return null;
    }

    @Override
    public String visitUnaryExpr(Unary expr) {
        return null;
    }

    @Override
    public String visitVariableExpr(Variable expr) {
        return null;
    }

    @Override
    public String visitBlockStmt(Block stmt) {
        return null;
    }

    @Override
    public String visitExpressionStmt(Expression stmt) {
        return null;
    }

    @Override
    public String visitFunctionStmt(Function stmt) {
        return null;
    }

    @Override
    public String visitIfStmt(If stmt) {
        return null;
    }

    @Override
    public String visitPrintStmt(Print stmt) {
        return null;
    }

    @Override
    public String visitReturnStmt(Return stmt) {
        return null;
    }

    @Override
    public String visitVarStmt(Var stmt) {
        return null;
    }

    @Override
    public String visitWhileStmt(While stmt) {
        return null;
    }
}