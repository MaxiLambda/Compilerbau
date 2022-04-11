package expresions;

public abstract class Expression {

    protected final Context context;

    protected Expression(Context context) {
        this.context = context;
    }

    abstract public double eval(Context context);

    public double eval(){
        return eval(new Context());
    }
}
