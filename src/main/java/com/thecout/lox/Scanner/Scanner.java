package com.thecout.lox.Scanner;


import com.thecout.lox.Scanner.regex.TokenManager;
import com.thecout.lox.util.Tuple;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static com.thecout.lox.Scanner.TokenType.EOF;

public class Scanner {

    private final String source;
    private final List<Token> tokens = new ArrayList<>();
    private final TokenManager tokenManager = new TokenManager();

    public Scanner(String source) {
        this.source = source;
    }


    public List<Token> scanLine(String line, int lineNumber) {
        List<Token> returnToken = new ArrayList<>();

        do{
            Optional<Tuple<Token,String>> tuple = tokenManager.getToken(line, lineNumber);
            String finalLine = line;
            line = tuple
                    .map(t -> t.second)
                    .orElseThrow(() -> new RuntimeException("Error on line %d. Failed to parse Token '%s'.".formatted(lineNumber, finalLine)));
            returnToken.add(tuple.get().first);

        }while (line.length() > 0);


        //returnToken.forEach(System.out::print);
        //System.out.println();

        return returnToken;
    }

    public List<Token> scan() {
        String[] lines = source.split("\n");
        for (int i = 0; i < lines.length; i++) {
            tokens.addAll(scanLine(lines[i], i));
        }
        tokens.add(new Token(EOF, "", "", lines.length));
        return tokens;
    }

}
