package expresions;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class Context{

    private final Map<String,Literal> vars;
    private Map<String, Literal> higherVars = new HashMap<>();

    public Context(){
        this(new HashMap<>());
    }

    public Context(Map<String,Literal> vars){
        this.vars = new HashMap<>(vars);
    }

    public Context merge(Context higherContext){
        higherVars = higherContext.getVars();
        return this;
    }

    public Literal getVar(String identifier){
        Literal var = vars.get(identifier);
        if (var == null)
            var = higherVars.get(identifier);
        if (var == null)
            throw new RuntimeException("Invalid identifier: " + identifier);
        return var;
    }

    public void setVar(Map.Entry<String,Literal> updateLiteral){

            if (vars.containsKey(updateLiteral.getKey())) {
                vars.put(updateLiteral.getKey(), updateLiteral.getValue());
            } else if (higherVars.containsKey(updateLiteral.getKey())) {
                higherVars.put(updateLiteral.getKey(), updateLiteral.getValue());
            } else {
                throw new IllegalArgumentException("Identifier %s cant be resigned, because it is not in the current scope.");
            }

    }

    private Map<String, Literal> getVars(){
        return vars;
    }

}
