package lispinterpreter;
/**
 * Clase que se representa los scopes del programa, se instanciará una vez por cada función.
 */

import java.util.HashMap;

public class Environment {
    private HashMap<String, LispVariable> variables;
    private HashMap<String, LispFunction> functions;
    private Environment parent;

    public Environment() {
        this.variables = new HashMap<>();
        this.functions = new HashMap<>();
        this.parent = null;
    }
    
    public Environment(Environment parent) {
        this.variables = new HashMap<>();
        this.functions = new HashMap<>();
        this.parent = parent;
    }

    public void setVar(String name, String type, Object value) {
        this.variables.put(name, new LispVariable(name, type, value));
    }

    public Object getVar(String name) throws RuntimeException {
        if (this.variables.containsKey(name)) {
            LispVariable var = this.variables.get(name);
            String varType = var.getType();
            Object varValue = var.getValue();
            
            if (varValue instanceof Number) {
                if (varType.equals("integer")) {
                    return ((Number) varValue).intValue();
                } else if (varType.equals("double")) {
                    return ((Number) varValue).doubleValue();
                }
            }
            return varValue;

        } else if (this.parent != null) {
            return this.parent.getVar(name);
        } else {
            throw new RuntimeException("No se reconoce la variable " + name + " en el scope actual.");
        }
    }

    public void defineFunction(String name, LispFunction function) {
        this.functions.put(name, function);
    }

    public LispFunction getFunction(String name) throws RuntimeException {
        if (this.functions.containsKey(name)) {
            return this.functions.get(name);
        } else if (this.parent != null) {
            return this.parent.getFunction(name);
        } else {
            throw new RuntimeException("No existe la función " + name + " en el scope actual.");
        }
    }

    public HashMap<String, LispFunction> getFunctionsHashMap(){
        return this.functions;
    }

    public HashMap<String, LispVariable> getVariablesHashMap(){
        return this.variables;
    }
}
