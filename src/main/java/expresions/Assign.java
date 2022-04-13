package expresions;

import java.util.Map;

public class Assign extends Expression{

    private final Expression expression;
    private final Map.Entry<String,Literal> newVar;

    public Assign(Expression expression, String identifier, Literal value){
        super(new Context());
        newVar = Map.entry(identifier,value);
        this.expression = expression;
    }

    @Override
    public double eval(Context higherContext) {
        context.merge(higherContext);
        context.addVar(newVar);
        return expression.eval(context);
    }
}
