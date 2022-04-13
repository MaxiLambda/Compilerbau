package expresions;

import java.util.Map;

public class ReAssign extends Expression{

    private final Expression expression;
    private final Map.Entry<String,Literal> newVar;

    public ReAssign(Expression expression, String identifier, Literal value) {
        super(null);
        newVar = Map.entry(identifier,value);
        this.expression = expression;
    }

    @Override
    public double eval(Context higherContext) {
        //do not merge with this objects Context
        //ReAssign just Mutates an Identifier from the  ost local Context above itself
        higherContext.setVar(newVar);
        return expression.eval(higherContext);
    }
}
