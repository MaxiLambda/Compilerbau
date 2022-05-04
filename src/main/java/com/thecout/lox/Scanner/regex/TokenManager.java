package com.thecout.lox.Scanner.regex;

import com.thecout.lox.Scanner.Token;
import com.thecout.lox.Scanner.TokenType;
import com.thecout.lox.util.Triplet;
import com.thecout.lox.util.Tuple;
import java.util.*;

public class TokenManager {

    List<PatternMatcher> matchers = new ArrayList<>();


    public TokenManager(){
        //Single char tokens -> Arithmetic
        matchers.add(new PatternMatcher("\\(",TokenType.LEFT_PAREN));
        matchers.add(new PatternMatcher("\\)",TokenType.RIGHT_PAREN));
        matchers.add(new PatternMatcher("\\{",TokenType.LEFT_BRACE));
        matchers.add(new PatternMatcher("\\}",TokenType.RIGHT_BRACE));
        matchers.add(new PatternMatcher(",",TokenType.COMMA));
        matchers.add(new PatternMatcher("\\.",TokenType.DOT));
        matchers.add(new PatternMatcher("-",TokenType.MINUS));
        matchers.add(new PatternMatcher("\\+",TokenType.PLUS));
        matchers.add(new PatternMatcher(";",TokenType.SEMICOLON));
        matchers.add(new PatternMatcher("/",TokenType.SLASH));
        matchers.add(new PatternMatcher("\\*",TokenType.STAR));

        //Logic Tokens
        matchers.add(new PatternMatcher("!",TokenType.BANG));
        matchers.add(new PatternMatcher("!=",TokenType.BANG_EQUAL));
        matchers.add(new PatternMatcher("=",TokenType.EQUAL));
        matchers.add(new PatternMatcher("==",TokenType.EQUAL_EQUAL));
        matchers.add(new PatternMatcher(">",TokenType.GREATER));
        matchers.add(new PatternMatcher(">=",TokenType.GREATER_EQUAL));
        matchers.add(new PatternMatcher("<",TokenType.LESS));
        matchers.add(new PatternMatcher("<=",TokenType.LESS_EQUAL));

        //Literals
        matchers.add(new PatternMatcher("[a-zA-Z_]\\w*", TokenType.IDENTIFIER));
        matchers.add(new PatternMatcher("\"[^\"]*\"", TokenType.STRING, string -> string.substring(1,string.length()-1)));
        matchers.add(new PatternMatcher("\\d+(?:\\.\\d+)?", TokenType.NUMBER, Double::parseDouble));

        //KEYWORDS
        matchers.add(new PatternMatcher("and", TokenType.AND,1));
        matchers.add(new PatternMatcher("else", TokenType.ELSE,1));
        matchers.add(new PatternMatcher("false", TokenType.FALSE,1));
        matchers.add(new PatternMatcher("fun", TokenType.FUN,1));
        matchers.add(new PatternMatcher("for", TokenType.FOR,1));
        matchers.add(new PatternMatcher("if", TokenType.IF,1));
        matchers.add(new PatternMatcher("nil", TokenType.NIL,1));
        matchers.add(new PatternMatcher("or", TokenType.OR,1));
        matchers.add(new PatternMatcher("print", TokenType.PRINT, 1));
        matchers.add(new PatternMatcher("return", TokenType.RETURN,1));
        matchers.add(new PatternMatcher("true", TokenType.TRUE,1));
        matchers.add(new PatternMatcher("var", TokenType.VAR,1));
        matchers.add(new PatternMatcher("while", TokenType.WHILE,1));

        //Comment
        matchers.add(new PatternMatcher("//.*$", TokenType.COMMENT,1));
        matchers.add(new PatternMatcher("/\\*.*\\*/", TokenType.COMMENT,1));




    }

    public Optional<Tuple<Token,String>> getToken(String line, int lineNumber){
        return matchers.stream()
                .map(patternMatcher -> patternMatcher.getToken(line,lineNumber))
                .filter(Optional::isPresent)
                .map(Optional::get)
                .max(Comparator.comparingInt((Triplet<Token,String,Integer> tokenTuple) -> tokenTuple.first.lexeme.length())
                        .thenComparingInt(tokenTuple -> tokenTuple.third))
                .map(triplet -> new Tuple<>(triplet.first,triplet.second));
    }


}
