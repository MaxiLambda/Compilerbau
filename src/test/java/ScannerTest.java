
import com.thecout.lox.Scanner;
import com.thecout.lox.Token;
import com.thecout.lox.TokenType;
import org.junit.jupiter.api.Test;


import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;


public class ScannerTest {
    static final String program = """
            fun printSum(a,b) {
            print a+b;
            }
            print 25+60;
            """;

    @Test
    void scanLineTest() {
        Scanner scanner = new Scanner(program);
        List<Token> actual = scanner.scanLine(program.split("\n")[0], 0);
        List<TokenType> expected = Arrays.asList(TokenType.FUN, TokenType.IDENTIFIER,
                TokenType.LEFT_PAREN, TokenType.IDENTIFIER,
                TokenType.COMMA, TokenType.IDENTIFIER,
                TokenType.RIGHT_PAREN, TokenType.LEFT_BRACE);
        for (int i = 0; i < actual.size(); i++) {
            assertEquals(expected.get(i), actual.get(i).type, "Expected " + expected.get(i) + ", got " + actual.get(i).type);
        }
        assertEquals(expected.size(), actual.size(), "Expected " + expected.size() + " tokens, got " + actual.size());
    }

    @Test
    void scanTest() {
        Scanner scanner = new Scanner(program);
        List<Token> actual = scanner.scan();
        assertEquals(20, actual.size(), "Expected 20 tokens, got " + actual.size());
    }

    @Test
    void scanNumber() {
        Scanner scanner = new Scanner("12.45");
        List<Token> actual = scanner.scan();
        assertEquals(2, actual.size(), "Expected 2 token, got " + actual.size());
        assertEquals(TokenType.NUMBER, actual.get(0).type, "Expected " + TokenType.NUMBER + ", got " + actual.get(0).type);
        assertEquals(12.45, actual.get(0).literal, "Expected 12.45, got " + actual.get(0).literal);
    }

    @Test
    void scanString() {
        Scanner scanner = new Scanner("print \"Hello World\";");
        List<Token> actual = scanner.scan();
        assertEquals(4, actual.size(), "Expected 4 token, got " + actual.size());
        assertEquals(TokenType.STRING, actual.get(1).type, "Expected " + TokenType.STRING + ", got " + actual.get(1).type);
        assertEquals("Hello World", actual.get(1).literal, "Expected Hello World got " + actual.get(1).literal);
    }
}
