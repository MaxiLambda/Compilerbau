package com.thecout.lox.Parser.first;

import com.thecout.lox.Scanner.TokenType;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import static com.thecout.lox.Scanner.TokenType.*;


public enum FirstTokens {

    PRIMARY(TRUE, FALSE, NIL, NUMBER, STRING, IDENTIFIER, LEFT_PAREN),
    CALL(List.of(PRIMARY.tokenTypes)),
    UNARY(BANG,MINUS),

    VAR_DECL(VAR),
    FUN_DECL(FUN),
    ASSIGNMENT(IDENTIFIER),
    EXPR(List.of(ASSIGNMENT.tokenTypes)),
    EXPR_STATEMENT(List.of(EXPR.tokenTypes)),
    STATEMENT(IDENTIFIER,FUN,FUN,FOR,IF,WHILE,PRINT,RETURN,LEFT_BRACE),;

    private final List<TokenType> tokenTypes;

    FirstTokens(TokenType... tokenTypes){
        this.tokenTypes = List.of(tokenTypes);
    }

    FirstTokens(List<List<TokenType>> firstTokens){
        this.tokenTypes = firstTokens.stream()
                .flatMap(Collection::stream)
                .collect(Collectors.toList());
    }

    public boolean containsTokenType(TokenType tokenType){
        return tokenTypes.contains(tokenType);
    }

}
