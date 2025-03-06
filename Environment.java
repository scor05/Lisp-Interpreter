/**
 * Clase que se representa los scopes del programa, se instanciará una vez por cada función.
 */

import java.rmi.NoSuchObjectException;
import java.util.HashMap;
import java.util.HashSet;

public class Environment<T> {
    private HashMap<String, LispVariable> variables;
    private HashSet<LispFunction<T>> functions;

    public Environment() {
        this.variables = new HashMap<>();
        this.functions = new HashSet<>();
    }

    public void setVar(String name, String type, Object value) {
        this.variables.put(name, new LispVariable(name, type, value));
    }

    public Object getVar(String name) throws NoSuchObjectException {
        if (!this.variables.containsKey(name)) {
            throw new NoSuchObjectException("No existe la variable " + name);
        }
        switch(this.variables.get(name).getType()) {
            case "int":
                return Integer.parseInt(this.variables.get(name).getValue().toString());
            case "double":
                return Double.parseDouble(this.variables.get(name).getValue().toString());
            case "boolean":
                return Boolean.parseBoolean(this.variables.get(name).getValue().toString());
            case "string":
                return this.variables.get(name).getValue().toString();
            default:
                throw new NoSuchObjectException("No existe el tipo de dato " + this.variables.get(name).getType());
        }
    }

    public void defineFunction(String name, LispFunction<T> function) {
        this.functions.add(function);
    }


}
