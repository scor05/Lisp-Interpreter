package lispinterpreter;
/**
 * Clase principal del intérprete
 */
import java.util.ArrayList;
import java.util.List;
public class LispInterpreter {
    private Environment globalEnv;

    /**
     * Constructor que inicializa el env global y configura todas las funciones basicas de LISP.
     */
    public LispInterpreter() {
        this.globalEnv = new Environment();

        globalEnv.defineFunction("+", new LispFunction("+", List.of("a", "b"), null) {
            @Override
            public Object apply(List<Object> args, Environment env) {
                if (args.size() != 2) throw new RuntimeException("+ requiere  2 argumentos");
                
                if (args.get(0) instanceof Number && args.get(1) instanceof Number) {
                    double a = ((Number) args.get(0)).doubleValue();
                    double b = ((Number) args.get(1)).doubleValue();
                    return a + b;
                }
                
                // Handle string concatenation
                return args.get(0).toString() + args.get(1).toString();
            }
        });
        
        globalEnv.defineFunction("-", new LispFunction("-", List.of("a", "b"), null) {
            @Override
            public Object apply(List<Object> args, Environment env) {
                if (args.size() != 2) throw new RuntimeException("- requiere  2 argumentos");
                
                double a = ((Number) args.get(0)).doubleValue();
                double b = ((Number) args.get(1)).doubleValue();
                return a - b;
            }
        });
        
        globalEnv.defineFunction("*", new LispFunction("*", List.of("a", "b"), null) {
            @Override
            public Object apply(List<Object> args, Environment env) {
                if (args.size() != 2) throw new RuntimeException("* requiere  2 argumentos");
                
                double a = ((Number) args.get(0)).doubleValue();
                double b = ((Number) args.get(1)).doubleValue();
                return a * b;
            }
        });
        
        globalEnv.defineFunction("/", new LispFunction("/", List.of("a", "b"), null) {
            @Override
            public Object apply(List<Object> args, Environment env) {
                if (args.size() != 2) throw new RuntimeException("/ requiere  2 argumentos");
                
                double a = ((Number) args.get(0)).doubleValue();
                double b = ((Number) args.get(1)).doubleValue();
                
                if (b == 0) throw new RuntimeException("División por cero");
                
                return a / b;
            }
        });
        
        globalEnv.defineFunction("<", new LispFunction("<", List.of("a", "b"), null) {
            @Override
            public Object apply(List<Object> args, Environment env) {
                if (args.size() != 2) throw new RuntimeException("< requiere  2 argumentos");
                
                if (args.get(0) instanceof Number && args.get(1) instanceof Number) {
                    double a = ((Number) args.get(0)).doubleValue();
                    double b = ((Number) args.get(1)).doubleValue();
                    return a < b;
                }
                
                throw new RuntimeException("< requiere argumentos numéricos");
            }
        });
        
        globalEnv.defineFunction(">", new LispFunction(">", List.of("a", "b"), null) {
            @Override
            public Object apply(List<Object> args, Environment env) {
                if (args.size() != 2) throw new RuntimeException("> requiere  2 argumentos");
                
                if (args.get(0) instanceof Number && args.get(1) instanceof Number) {
                    double a = ((Number) args.get(0)).doubleValue();
                    double b = ((Number) args.get(1)).doubleValue();
                    return a > b;
                }
                
                throw new RuntimeException("> requiere argumentos numéricos");
            }
        });

        globalEnv.defineFunction("=", new LispFunction("=", List.of("a", "b"), null) {
            @Override
            public Object apply(List<Object> args, Environment env) {
                if (args.size() != 2) throw new RuntimeException("= requiere  2 argumentos");
                
                if (args.get(0) instanceof Number && args.get(1) instanceof Number) {
                    double a = ((Number) args.get(0)).doubleValue();
                    double b = ((Number) args.get(1)).doubleValue();
                    return a == b;
                }
                
                throw new RuntimeException("= requiere argumentos numéricos");
            }
        });
    }
    
    /**
     * Itera por cada expresion en la arrayList del archivo leido, las parsea y ejecuta.
     * @param code
     */
    public void execute(ArrayList<String> code) {
        Evaluator evaluator = new Evaluator(globalEnv);
        for (String expression : code) {
            Object parsed = Parser.parse(expression);
            Object result = evaluator.eval(parsed, globalEnv);
            if (result != null && !(result instanceof String && globalEnv.getFunctionsHashMap().containsKey(result.toString()))) {
                System.out.println(result);
            }
        }
    }
    
    public Environment getGlobalEnvironment(){
        return this.globalEnv;
    }
}