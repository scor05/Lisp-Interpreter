package lispinterpreter;
/*
 * Clase que contiene una función programada por el usuario
 */

import java.util.List;

public class LispFunction{
    private String name;
    private List<String> params; 
    private Object body;
    
    /**
     * 
     * @param name
     * @param params
     * @param body
     */
    public LispFunction(String name, List<String> params, Object body) {
        this.name = name;
        this.params = params;
        this.body = body;
    }
    
    /**
     * Ejecuta las instrucciones de las funciones en su body y genera el environment hijo para variables locales.
     * @param args
     * @param env
     * @return
     */
    public Object apply(List<Object> args, Environment env) {
        if (this.body == null) {
            throw new RuntimeException("No se puede aplicar una función vacía.");
        }

        Environment funcEnv = new Environment(env);
    
        for (int i = 0; i < this.params.size(); i++) {
            String type = args.get(i).getClass().getSimpleName().toLowerCase();
            funcEnv.setVar(this.params.get(i), type, args.get(i));
        }
    
        Evaluator e = new Evaluator(funcEnv);
        Object result = null;
    
        if (this.body instanceof List) {
            result = e.eval(this.body, funcEnv);
        } else {
            result = e.eval(this.body, funcEnv);
        }
    
        return result;
    }

    /**
     * 
     * @return String
     */
    public String getName() {
        return name;
    }
    
    /**
     * 
     * @return List<String>
     */
    public List<String> getParams() {
        return params;
    }
    
    /**
     * @return Object
     */
    public Object getBody() {
        return body;
    }

    /**
     * 
     * @param name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * 
     * @param params
     */
    public void setParams(List<String> params) {
        this.params = params;
    }

    /**
     * 
     * @param body
     */
    public void setBody(Object body) {
        this.body = body;
    }
}
