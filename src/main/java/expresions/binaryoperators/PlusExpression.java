package expresions.binaryoperators;

import expresions.Context;
import expresions.Expression;

public class PlusExpression extends BinaryOperator{

        public PlusExpression(Expression left, Expression right){
            super(left, right);
        }
        public PlusExpression(Expression left, Expression right, Context context){
            super(left, right, context);
        }

        @Override
        public double eval(Context higherContext) {
            context.merge(higherContext);
            return left.eval(context) + right.eval(context);
        }


}
