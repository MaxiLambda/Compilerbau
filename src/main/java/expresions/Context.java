package expresions;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class Context{

    private final Map<String,Literal> vars;
    private Optional<Context> higherContext = Optional.empty();

    public Context(){
        this(new HashMap<>());
    }

    public Context(Map<String,Literal> vars){
        this.vars = new HashMap<>(vars);
    }

    public Context merge(Context higherContext){
        this.higherContext = Optional.of(higherContext);
        return this;
    }

    public Literal getVar(String identifier){
        Literal var = vars.get(identifier);
        if (var == null && higherContext.isPresent())
            var = higherContext.get().getVar(identifier);

        if (var == null)
            throw new RuntimeException("Invalid identifier: " + identifier);
        return var;
    }

    public void setVar(Map.Entry<String,Literal> updateIdentifier){
            if (vars.containsKey(updateIdentifier.getKey())) {
                vars.put(updateIdentifier.getKey(), updateIdentifier.getValue());
            } else if (higherContext.isPresent()) {
                if(higherContext.get().getVars().containsKey(updateIdentifier.getKey()))
                    higherContext.get().getVars().put(updateIdentifier.getKey(), updateIdentifier.getValue());
            } else {
                throw new IllegalArgumentException("Identifier %s cant be resigned, because it is not in the current scope.");
            }
    }

    public void addVar(Map.Entry<String,Literal> newIdentifier){
        if(vars.containsKey(newIdentifier.getKey())) throw new RuntimeException("You cant assign an already assigned Variable");
        System.out.println("added var");
        vars.put(newIdentifier.getKey(),newIdentifier.getValue());
        System.out.println("bp");
    }

    private Map<String, Literal> getVars(){
        return vars;
    }

}
