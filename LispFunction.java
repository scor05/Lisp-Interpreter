/*
 * Clase que contiene una función programada por el usuario
 */

import java.util.List;

public class LispFunction{
    private String name;
    private List<String> params; 
    private Object body;
    
    public LispFunction(String name, List<String> params, Object body) {
        this.name = name;
        this.params = params;
        this.body = body;
    }
    
    public Object apply(List<Object> args, Environment env){
        if (this.body == null){
            throw new RuntimeException("No se puede aplicar una función vacía.");
        }
        for (int i = 0; i < this.params.size(); i++) {
            env.setVar(this.params.get(i), args.get(i).getClass().getSimpleName().toLowerCase(), args.get(i));
        }

        Environment funcEnv = new Environment(env);

        Evaluator e = new Evaluator(env);
        if (this.body instanceof List){
            Object result = null;
            List<?> bodyList = (List<?>) this.body; // Parsear a list
            for (Object exp : bodyList) {
                result = e.eval(exp, funcEnv);
            }
            return result;
        }
        return e.eval(this.body, funcEnv); // Funciones de solo una línea
    }

    public String getName() {
        return name;
    }
    
    public List<String> getParams() {
        return params;
    }
    
    public Object getBody() {
        return body;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setParams(List<String> params) {
        this.params = params;
    }

    public void setBody(Object body) {
        this.body = body;
    }
}
