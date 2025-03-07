/**
 * Clase que evaluará tanto listas como expresiones atómicas
 */

public class Evaluator {
    private Environment env;

    public Evaluator(Environment env) {
        this.env = env;
    }

    /**
     * Método que evalúa expresiones atómicas.
     * @param expression
     * @param env
     * @return
     */
    public Object eval(Object expression, Environment env){
        return null; // TODO: Placeholder
    }
    
}
