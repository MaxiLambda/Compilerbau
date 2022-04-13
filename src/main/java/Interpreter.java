import expresions.*;
import expresions.binaryoperators.MinusExpression;
import expresions.binaryoperators.MultiExpression;
import expresions.binaryoperators.PlusExpression;

import java.util.AbstractMap;
import java.util.HashMap;
import java.util.Map;

import static java.util.Map.entry;

public class Interpreter {

    public static void main(String[] args) {
        Expression expression = new MinusExpression(new Literal(11d),new Literal(1d));

        Context globalContext = new Context(Map.ofEntries(entry("test", new Literal(5d))));
        Context localContext = new Context(Map.ofEntries(entry("test", new Literal(4d))));

        Expression expression2 = new PlusExpression(new Identifier("test",localContext),new Identifier("test") ,globalContext);

        Expression expression3 = new PlusExpression(new Identifier("test"),new ReAssign(new Identifier("test"),"test",new Literal(3d)) ,globalContext);
        Expression expression4 = new PlusExpression(new Identifier("test"),new Identifier("test"),globalContext);
        Expression expression5 = new Assign(new MultiExpression(new Identifier("test"),new Literal(3d)),"test",new Literal(4d));

        //System.out.println("exp: " + expression.eval());//test + test = 5 + 5 = 8
        //System.out.println("exp2: " + expression2.eval());//test + (local-test = 4; local-test) = 5 + 4 = 9
        //System.out.println("exp3: " + expression3.eval());//test + (test = 3; test) = 5 + 3 = 8
        //System.out.println("exp4: " + expression4.eval());//test + test = 3 + 3 = 6
        System.out.println("exp5: " + expression5.eval());
    }
}
