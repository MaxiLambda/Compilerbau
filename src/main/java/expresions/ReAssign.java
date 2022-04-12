package expresions;

import java.util.Map;

public class ReAssign extends Expression{

    private final Expression expression;
    private final Map.Entry<String,Literal> newVar;

    public ReAssign(Expression expression, String identifier, Literal value) {
        super(new Context());
        newVar = Map.entry(identifier,value);
        this.expression = expression;
    }

    @Override
    public double eval(Context context) {
        context.setVar(newVar);
        return expression.eval(context);
    }
}
