package com.thecout.lox.Parser;


import com.thecout.lox.Parser.Expr.*;
import com.thecout.lox.Parser.Stmts.*;
import com.thecout.lox.Parser.first.FirstTokens;
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
            if (match(FUN)) return function();
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
        consume(LEFT_PAREN,"Expected '('");
        List<Stmt> returnStmts = new ArrayList<>();
        if(FirstTokens.VAR_DECL.containsTokenType(peek().type)){
            returnStmts.add(varDeclaration());
        }else if(FirstTokens.EXPR_STATEMENT.containsTokenType(peek().type)){
            returnStmts.add(expressionStatement());
        }else{
            consume(SEMICOLON,"Expected ';'");
        }
        Expr condition = null;
        if(FirstTokens.EXPR.containsTokenType(peek().type)){
            condition = expression();
        }
        consume(SEMICOLON,"Expected ';'");
        Expr mutator = null;
        if(FirstTokens.EXPR.containsTokenType(peek().type)){
            mutator = expression();
        }

        condition = condition == null ? new Literal(true) : condition;
        consume(RIGHT_PAREN, "Expect ')'");
        Stmt blockStmt = new Block(List.of(statement(),new Expression(mutator)));
        Stmt whileStmt = new While(condition, blockStmt);
        returnStmts.add(whileStmt);
        return new Block(returnStmts);
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
        Expr expr = expression();
        consume(SEMICOLON,"Expected ';'");
        return new Print(expr);
    }

    private Stmt returnStatement() {
        Expr expr = new Literal(null);
        if(FirstTokens.EXPR.containsTokenType(peek().type)){
            expr = expression();
        }
        consume(SEMICOLON,"Expected ';'");
        return new Return(expr);
    }

    private Stmt varDeclaration() {
        Token name = consume(IDENTIFIER,"Expected identifier");
        Expr expr = new Literal(null);
        if(match(EQUAL)){
            expr = expression();
        }
        consume(SEMICOLON,"Expected ';'");
        return new Var(name, expr);
    }

    private Stmt whileStatement() {
        consume(LEFT_PAREN,"Expected '('");
        Expr expr = expression();
        consume(RIGHT_PAREN, "Expect ')'");
        return new While(expr, statement());
    }

    private Stmt expressionStatement() {
        Expr expr = expression();
        consume(SEMICOLON,"Expected ';'");
        return new Expression(expr);
    }

    private Function function() {
        Token name = consume(IDENTIFIER,"Expected identifier");
        consume(LEFT_PAREN,"Expected '('");
        List<Token> parameters = new ArrayList<>();
        while (!peekMatch(RIGHT_PAREN)){
            parameters.add(consume(IDENTIFIER,"Expected identifier"));
            if(!match(COMMA)) break;
        }
        consume(RIGHT_PAREN, "Expect ')'");
        consume(LEFT_BRACE,"Expected '{'");
        return new Function(name, parameters, block());
    }

    private List<Stmt> block() {
        List<Stmt> body = new ArrayList<>();
        while(FirstTokens.STATEMENT.containsTokenType(peek().type)){
            body.add(declaration());
        }
        consume(RIGHT_BRACE,"Expected '}'");
        return body;
    }

    private Expr assignment() {
        Token name = consume(IDENTIFIER,"Expected identifier");
        consume(EQUAL,"Expected '='");
        Expr expr;
        if(FirstTokens.ASSIGNMENT.containsTokenType(peek().type)){
            expr = assignment();
        }else{
            expr = or();
        }
        return new Assign(name, expr);
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
        Expr expr = equality();
        while(match(AND)){
            Token operator = previous();
            Expr right = equality();
            expr = new Logical(expr, operator, right);
        }
        return expr;
    }

    private Expr equality() {
        Expr expr = comparison();
        while(match(BANG_EQUAL, EQUAL_EQUAL)){
            Token operator = previous();
            Expr right = comparison();
            expr = new Logical(expr, operator, right);
        }
        return expr;
    }

    private Expr comparison() {
        Expr expr = addition();
        while(match(GREATER, GREATER_EQUAL, LESS, LESS_EQUAL)){
            Token operator = previous();
            Expr right = addition();
            expr = new Logical(expr, operator, right);
        }
        return expr;
    }

    private Expr addition() {
        Expr expr = multiplication();
        while(match(PLUS,MINUS)){
            Token operator = previous();
            Expr right = multiplication();
            expr = new Binary(expr, operator, right);
        }
        return expr;
    }

    private Expr multiplication() {
        Expr expr = unary();
        while(match(STAR,SLASH)){
            Token operator = previous();
            Expr right = unary();
            expr = new Binary(expr, operator, right);
        }
        return expr;
    }

    private Expr unary() {
        if(match(BANG,MINUS)){
            Token operator = previous();
            if(FirstTokens.CALL.containsTokenType(operator.type)){
                return new Unary(operator, call());
            }
            return new Unary(operator, unary());
        }
        throw error(previous(),"Expected 'unary operator'");
    }

    private Expr call() {
        Expr expr = primary();
        List<Expr> arguments = new ArrayList<>();
        consume(LEFT_PAREN,"Expected '('");
        while (!peekMatch(RIGHT_PAREN)){
            arguments.add(expression());
            if(!match(COMMA)) break;
        }
        consume(RIGHT_PAREN,"Expected ')'");
        return new Call(expr,arguments);
    }

    private Expr primary() {

        if(match(TRUE)) return new Literal(true);
        if(match(FALSE)) return new Literal(true);
        if(match(NIL)) return new Literal(null);
        if(check(NUMBER)) return new Literal(consume(NUMBER,"Expected Number").literal);
        if(check(STRING)) return new Literal(consume(STRING,"Expected String").literal);
        if(check(IDENTIFIER)) return new Variable(consume(IDENTIFIER, "Expected Identifier"));
        if(match(LEFT_PAREN)){
            Expr expr = expression();
            consume(RIGHT_PAREN,"Expected ')'");
            return expr;
        }

        throw new ParseError();
    }

    private boolean peekMatch(TokenType... types) {
        for (TokenType type : types) {
            if (check(type)) {
                return true;
            }
        }

        return false;
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
