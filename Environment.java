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
        if (!this.variables.containsKey(name) && (this.parent != null && !this.parent.variables.containsKey(name))) {
            throw new RuntimeException("No se reconoce la variable " + name + " en el scope actual.");
        }
        String varString = this.variables.get(name).getValue().toString();
        String varType = this.variables.get(name).getType();
        switch(varType) {
            case "int":
                return Integer.parseInt(varString);
            case "double":
                return Double.parseDouble(varString);
            case "boolean":
                return Boolean.parseBoolean(varString);
            case "string":
                return varString;
            default:
                throw new RuntimeException("No existe el tipo de dato " + this.variables.get(name).getType());
        }
    }

    public void defineFunction(String name, LispFunction function) {
        this.functions.put(name, function);
    }

    public LispFunction getFunction(String name) throws RuntimeException {
        if (!this.functions.containsKey(name)) {
            throw new RuntimeException("No existe la función " + name + " en el scope actual.");
        }
        return this.functions.get(name);
    }

    public HashMap<String, LispFunction> getFunctionsHashMap(){
        return this.functions;
    }

    public HashMap<String, LispVariable> getVariablesHashMap(){
        return this.variables;
    }
}
