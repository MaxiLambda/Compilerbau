package com.thecout.lox.Scanner.regex;

import com.thecout.lox.Scanner.Token;
import com.thecout.lox.Scanner.TokenType;
import com.thecout.lox.util.Triplet;

import java.util.Optional;
import java.util.function.Function;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PatternMatcher {
    protected Pattern p;
    protected TokenType type;
    private Integer prio = 0;

    private Function<String,Object> parseLiteral = lexeme -> null;

    PatternMatcher(String regex, TokenType type, Function<String,Object> parseLiteral){
        this(regex, type);
        this.parseLiteral = parseLiteral;
    }

    PatternMatcher(String regex, TokenType type, int prio){
        this(regex, type);
        this.prio = prio;
    }

    PatternMatcher(String regex, TokenType type){
        p = Pattern.compile("^(" + regex + ")\\s*(.*)");
        this.type = type;
    }

    public Optional<Triplet<Token, String, Integer>> getToken(String line, int lineNumber) {
        Matcher m = p.matcher(line);

        if (m.matches()) {
            Token token = new Token(type, m.group(1), parseLiteral.apply(m.group(1)), lineNumber);
            String remainder = m.group(2);
            return Optional.of(new Triplet<>(token, remainder, prio));
        }

        return Optional.empty();
    }
}
