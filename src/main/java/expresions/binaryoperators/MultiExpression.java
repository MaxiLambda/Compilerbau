package expresions.binaryoperators;

import expresions.Context;
import expresions.Expression;

public class MultiExpression extends BinaryOperator {

    public MultiExpression(Expression left, Expression right) {
        super(left, right);
    }
    public MultiExpression(Expression left, Expression right, Context context) {
        super(left, right, context);
    }

    @Override
    public double eval(Context higherContext) {
        context.merge(higherContext);
        return left.eval(context) * right.eval(context);
    }
}
