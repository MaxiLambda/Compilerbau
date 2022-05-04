package com.thecout.lox.Parser;


import com.thecout.lox.Parser.Expr.Expr;
import com.thecout.lox.Parser.Expr.Logical;
import com.thecout.lox.Parser.Stmts.Block;
import com.thecout.lox.Parser.Stmts.Function;
import com.thecout.lox.Parser.Stmts.If;
import com.thecout.lox.Parser.Stmts.Stmt;
import com.thecout.lox.Scanner.Token;
import com.thecout.lox.Scanner.TokenType;

import java.util.ArrayList;
import java.util.List;

import static com.thecout.lox.Scanner.TokenType.*;

public class Parser {
    private static class ParseError extends RuntimeException {
    }

    private final List<Token> tokens;
    private int current = 0;

    public Parser(List<Token> tokens) {
        this.tokens = tokens;
    }

    public List<Stmt> parse() {
        List<Stmt> statements = new ArrayList<>();
        while (!isAtEnd()) {
            statements.add(declaration());
        }

        return statements;
    }

    private Expr expression() {
        return assignment();
    }

    private Stmt declaration() {
        try {
            if (match(FUN)) return function("function");
            if (match(VAR)) return varDeclaration();

            return statement();
        } catch (ParseError error) {
            return null;
        }
    }

    private Stmt statement() {
        if (match(FOR)) return forStatement();
        if (match(IF)) return ifStatement();
        if (match(PRINT)) return printStatement();
        if (match(RETURN)) return returnStatement();
        if (match(WHILE)) return whileStatement();
        if (match(LEFT_BRACE)) return new Block(block());

        return expressionStatement();
    }

    private Stmt forStatement() {

        return null;
    }

    private Stmt ifStatement() {
        consume(LEFT_PAREN, "Expect '(' after 'if'.");
        Expr condition = expression();
        consume(RIGHT_PAREN, "Expect ')' after if condition."); // [parens]

        Stmt thenBranch = statement();
        Stmt elseBranch = null;
        if (match(ELSE)) {
            elseBranch = statement();
        }

        return new If(condition, thenBranch, elseBranch);
    }

    private Stmt printStatement() {
        return null;
    }

    private Stmt returnStatement() {
        return null;
    }

    private Stmt varDeclaration() {
        return null;
    }

    private Stmt whileStatement() {
        return null;
    }

    private Stmt expressionStatement() {
        return null;
    }

    private Function function(String kind) {
        return null;
    }

    private List<Stmt> block() {
        return null;
    }

    private Expr assignment() {
        return null;
    }

    private Expr or() {
        Expr expr = and();

        while (match(OR)) {
            Token operator = previous();
            Expr right = and();
            expr = new Logical(expr, operator, right);
        }

        return expr;
    }

    private Expr and() {
        return null;
    }

    private Expr equality() {
        return null;
    }

    private Expr comparison() {
        return null;
    }

    private Expr addition() {
        return null;
    }

    private Expr multiplication() {
        return null;
    }

    private Expr unary() {
        return null;
    }

    private Expr finishCall(Expr callee) {
        return null;
    }

    private Expr call() {
        return null;
    }

    private Expr primary() {
        return null;
    }

    private boolean match(TokenType... types) {
        for (TokenType type : types) {
            if (check(type)) {
                advance();
                return true;
            }
        }

        return false;
    }

    private Token consume(TokenType type, String message) {
        if (check(type)) return advance();

        throw error(peek(), message);
    }

    private boolean check(TokenType tokenType) {
        if (isAtEnd()) return false;
        return peek().type == tokenType;
    }

    private Token advance() {
        if (!isAtEnd()) current++;
        return previous();
    }

    private boolean isAtEnd() {
        return peek().type == EOF;
    }

    private Token peek() {
        return tokens.get(current);
    }

    private Token previous() {
        return tokens.get(current - 1);
    }

    private ParseError error(Token token, String message) {
        ParserError.error(token, message);
        return new ParseError();
    }


}
