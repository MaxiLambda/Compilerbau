package com.thecout.lox.Traversal;

import com.thecout.lox.Parser.Expr.*;
import com.thecout.lox.Parser.Parser;
import com.thecout.lox.Parser.Stmts.*;
import com.thecout.lox.Scanner.Scanner;
import com.thecout.lox.Scanner.Token;

import java.util.List;
import java.util.stream.Collectors;

public class AstPrinter implements ExprVisitor<String>, StmtVisitor<String> {

    public static void main(String[] args) {
        final String looptest2 = """
                fun printSum(a,d) {
                    for(var i = 0; a < d; i = i+1){
                        a = a+1;
                    }
                    return a;
                }
                print printSum(2,5);
                            
                """;


        Scanner scanner = new Scanner(looptest2);
        List<Token> actual = scanner.scan();
        Parser parser = new Parser(actual);
        AstPrinter interpreter = new AstPrinter();
        List<Stmt> statements = parser.parse();
        System.out.println();
        System.out.println();
        System.out.println();
        statements.stream().map(interpreter::print).forEach(System.out::println);

    }


    public String print(Expr expr) {
        return expr.accept(this);
    }

    public String print(Stmt stmt) {
        return stmt.accept(this);
    }


    @Override
    public String visitAssignExpr(Assign expr) {
        return "Assign: %s to var %s\n".formatted(print(expr.value),expr.name.lexeme);
    }

    @Override
    public String visitBinaryExpr(Binary expr) {
        return "BinaryExpr: %s %s %s\n".formatted(print(expr.left),expr.operator.lexeme,print(expr.right));
    }

    @Override
    public String visitCallExpr(Call expr) {
        return "Call: %s with arguments %s\n".formatted(print(expr.callee),expr.arguments.stream().map(this::print).collect(Collectors.joining(", ")));
    }

    @Override
    public String visitGroupingExpr(Grouping expr) {
        return "Grouping: %s\n".formatted(print(expr));
    }

    @Override
    public String visitLiteralExpr(Literal expr) {
        return "Literal: %s\n".formatted(expr.value);
    }

    @Override
    public String visitLogicalExpr(Logical expr) {
        return "LogicalExpr: %s %s %s\n".formatted(print(expr.left),expr.operator.lexeme,print(expr.right));

    }

    @Override
    public String visitUnaryExpr(Unary expr) {
        return "UnaryExpr: %s %s\n".formatted(expr.operator.lexeme,print(expr.right));

    }

    @Override
    public String visitVariableExpr(Variable expr) {
        return "Variable: %s\n".formatted(expr.name);
    }

    @Override
    public String visitBlockStmt(Block stmt) {
        return "Block: %s END-BLOCK\n".formatted(stmt.statements.stream().map(this::print).collect(Collectors.joining(", ")));
    }

    @Override
    public String visitExpressionStmt(Expression stmt) {
        return "Expression: %s\n".formatted(print(stmt.expression));
    }

    @Override
    public String visitFunctionStmt(Function stmt) {
        return "Function: %s %s %s END-Function\n".formatted(stmt.name,
                stmt.parameters.stream().map(Token::toString).collect(Collectors.joining(", ")), stmt.body.stream().map(this::print).collect(Collectors.joining(", ")));
    }

    @Override
    public String visitIfStmt(If stmt) {
        return "If: %s then %s else %s\n".formatted(print(stmt.condition),print(stmt.thenBranch),print(stmt.elseBranch));
    }

    @Override
    public String visitPrintStmt(Print stmt) {
        return "Print: %s\n".formatted(print(stmt.expression));
    }

    @Override
    public String visitReturnStmt(Return stmt) {
        return "Return: %s\n".formatted(print(stmt.value));
    }

    @Override
    public String visitVarStmt(Var stmt) {
        return "Var: %s %s\n".formatted(stmt.name.toString(),print(stmt.initializer));
    }

    @Override
    public String visitWhileStmt(While stmt) {
        return "While: %s %s\n".formatted(print(stmt.condition),print(stmt.body));
    }
}