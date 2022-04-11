package expresions.binaryoperators;

import expresions.Context;
import expresions.Expression;

public class DivExpression extends BinaryOperator {

    public DivExpression(Expression left, Expression right) {
        super(left, right);
    }
    public DivExpression(Expression left, Expression right, Context context) {
        super(left, right, context);
    }

    @Override
    public double eval(Context higherContext) {
        this.context.merge(higherContext);
        return left.eval(context) / right.eval(context);
    }
}
