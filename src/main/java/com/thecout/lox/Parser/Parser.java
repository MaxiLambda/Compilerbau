package com.thecout.lox.Parser;


import com.thecout.lox.Parser.Expr.*;
import com.thecout.lox.Parser.Stmts.*;
import com.thecout.lox.Parser.first.FirstTokens;
import com.thecout.lox.Scanner.Token;
import com.thecout.lox.Scanner.TokenType;

import java.util.*;
import java.util.function.Supplier;
import java.util.stream.Collectors;

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
        Expr expr = expression();
        consume(SEMICOLON,"Expect ';' after expression");
        return new Print(expr);
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
        //if (match(PRIMAORY)) return forStatement();

        //????
//        if (match(FOR)) return forStatement();
//        if (match(IF)) return ifStatement();
//
//        //(call ".")? IDENTIFIER "=" assignment | logic_or
          //logic_and (or logic_and)
//        if(match())
//        // ;
        consume(IDENTIFIER,"Expect ';' after expression");
        consume(EQUAL,"Expect '=' after identifier");

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

    private Expr finishCall(Expr callee) {
        return null;
    }


    private Expr call() {
        Expr expr = primary();
        List<Expr> arguments = new ArrayList<>();
        while(peekMatch(LEFT_PAREN, DOT)){
            if(match(LEFT_PAREN)){
                arguments.add(arguments());
                consume(RIGHT_PAREN,"Expected ')' after arguments");
            }else if(match(DOT)){
                //arguments.add(new Variable(consume(IDENTIFIER))
            }
        }

       // new Call(expr, )

        return expr;
        //throw new ParseError();
        //return null;
    }

    private Expr arguments() {
        Expr expr = expression();
        while(match(COMMA)){
            Token operator = previous();
            Expr right = expression();
            expr = new LList(expr, right);
        }
        return expr;
    }


    private Expr primary() {

        if(match(TRUE)) return new Literal(true);
        if(match(FALSE)) return new Literal(true);
        if(match(NIL)) return new Literal(null);
        //match "this"
        //if(match(VAR)) return new Variable();
        if(check(NUMBER)) return new Literal(consume(NUMBER,"Expected Number").literal);
        if(check(STRING)) return new Literal(consume(STRING,"Expected String").literal);
        if(check(IDENTIFIER)) return new Variable(consume(IDENTIFIER, "Expected Identifier"));
        //if(match(LEFT_PAREN)) return new Grouping()

        throw new ParseError();
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

    private boolean peekMatch(TokenType... types) {
        for (TokenType type : types) {
            if (check(type)) {
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
