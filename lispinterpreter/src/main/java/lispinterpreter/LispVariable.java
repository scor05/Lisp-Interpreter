package lispinterpreter;
/**
 * Clase que contiene una variable programada por el usuario
 */

public class LispVariable {
    private String name;
    private String type;
    private Object value;

    /**
     * 
     * @param name
     * @param type
     * @param value
     */
    public LispVariable(String name, String type, Object value) {
        this.name = name;
        this.type = type;
        this.value = value;
    }

    /**
     * @return String del nombre
     */
    public String getName() {
        return name;
    }

    /**
     * 
     * @return String del tipo, sera siempre el getSimpleName() en minusculas.
     */
    public String getType() {
        return type;
    }

    /**
     * @return Object del valor
     */
    public Object getValue() {
        return value;
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
     * @param value
     */
    public void setValue(Object value) {
        this.value = value;
    }

    /**
     * 
     * @param type
     */
    public void setType(String type) {
        this.type = type;
    }
}
