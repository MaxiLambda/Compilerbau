import expresions.*;
import expresions.binaryoperators.MinusExpression;
import expresions.binaryoperators.PlusExpression;

import java.util.Map;

import static java.util.Map.entry;

public class Interpreter {

    public static void main(String[] args) {
        Expression expression = new MinusExpression(new Literal(11d),new Literal(1d));

        Context globalContext = new Context(Map.ofEntries(entry("test", new Literal(5d))));
        Context localContext = new Context(Map.ofEntries(entry("test", new Literal(4d))));

        Expression expression2 = new PlusExpression(new Identifier("test",localContext),new Identifier("test") ,globalContext);

        System.out.println("exp: " + expression.eval());
        System.out.println("exp2: " + expression2.eval());
    }
}
