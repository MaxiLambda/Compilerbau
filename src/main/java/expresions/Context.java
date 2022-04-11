package expresions;

import java.util.HashMap;
import java.util.Map;

public class Context{

    private Map<String,Literal> vars;

    public Context(){
        this(new HashMap<>());
    }

    public Context(Map<String,Literal> vars){
        this.vars = vars;
    }

    public Context merge(Context higherContext){
        HashMap<String,Literal> clone = new HashMap<>(higherContext.getVars());
        clone.putAll(vars);
        vars = clone;
        return this;
    }

    public Literal getVar(String identifier){
        Literal var = vars.get(identifier);
        if (var == null) throw new RuntimeException("Invalid identifier: " + identifier);
        return var;
    }

    private Map<String, Literal> getVars(){
        return vars;
    }

}
