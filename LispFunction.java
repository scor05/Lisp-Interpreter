/*
 * Clase que contiene una función programada por el usuario
 */

import java.util.List;

public class LispFunction<T> {
    private String name;
    private List<T> params; 
    private Object body;
    
    public LispFunction(String name, List<T> params, Object body) {
        this.name = name;
        this.params = params;
        this.body = body;
    }
    
    public String getName() {
        return name;
    }
    
    public List<T> getParams() {
        return params;
    }
    
    public Object getBody() {
        return body;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setParams(List<T> params) {
        this.params = params;
    }

    public void setBody(Object body) {
        this.body = body;
    }
}
