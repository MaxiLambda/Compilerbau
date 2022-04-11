package expresions.binaryoperators;

import expresions.Context;
import expresions.Expression;

public abstract class BinaryOperator extends Expression {
    protected final Expression left;
    protected final Expression right;

    public BinaryOperator(Expression left, Expression right) {
        this(left,right,new Context());
    }

    public BinaryOperator(Expression left, Expression right, Context context) {
        super(context);
        this.left = left;
        this.right = right;
    }

}
