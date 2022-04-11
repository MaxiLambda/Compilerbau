package expresions;

public class Literal extends Expression {

    private final double value;

    public Literal(double value, Context context) {
        super(context);
        this.value = value;
    }
    public Literal(double value) {
        super(new Context());
        this.value = value;
    }

    @Override
    public double eval(Context higherContext) {
        return value;
    }
}
