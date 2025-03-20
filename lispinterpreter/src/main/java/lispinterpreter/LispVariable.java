package lispinterpreter;
/**
 * Clase que contiene una variable programada por el usuario
 */

public class LispVariable {
    private String name;
    private String type;
    private Object value;

    public LispVariable(String name, String type, Object value) {
        this.name = name;
        this.type = type;
        this.value = value;
    }

    public String getName() {
        return name;
    }

    public String getType() {
        return type;
    }

    public Object getValue() {
        return value;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setValue(Object value) {
        this.value = value;
    }

    public void setType(String type) {
        this.type = type;
    }
}
