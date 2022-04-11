package expresions;

public class Identifier extends Expression{

    private final String identifier;

    public Identifier(String identifier, Context context) {
        super(context);
        this.identifier = identifier;
    }

    public Identifier(String identifier) {
        this(identifier,new Context());
    }

    @Override
    public double eval(Context higherContext) {
       return context.merge(higherContext).getVar(identifier).eval(context);
    }
}
