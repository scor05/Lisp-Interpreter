/**
 * Clase que evaluará tanto listas como expresiones atómicas
 */

public class Evaluator {
    private Environment env;

    public Evaluator(Environment env) {
        this.env = env;
    }

   public Object eval(Object expression, Environment env) {
        if (expression instanceof List) {
            List<?> list = (List<?>) expression;
            if (list.isEmpty()) {
                return null;
            }
            String operator = list.get(0).toString();
            switch (operator) {
                case "defun":
                    return defineFunction(list, env);
                case "setq":
                    return setVariable(list, env);
                case "quote":
                    return list.get(1);
                case "atom":
                    return isAtom(list.get(1), env);
                case "list":
                    return isList(list.get(1), env);
                case "equal":
                    return isEqual(list.get(1), list.get(2), env);
                case ">":
                    return greaterThan(list.get(1), list.get(2), env);
                case "<":
                    return lessThan(list.get(1), list.get(2), env);
                case "cond":
                    return evaluateCond(list.subList(1, list.size()), env);
                case "funcall":
                    return funcall(list.get(1), list.get(2), env);
                default:
                    return evaluateFunction(operator, list.subList(1, list.size()), env);
            }
        } else if (expression instanceof String) {
            try {
                return env.getVar(expression.toString());
            } catch (RuntimeException e) {
                try {
                    return env.getFunction(expression.toString());
                } catch (RuntimeException e2) {
                    return expression;
                }
            }
        } else {
            return expression;
        }
    }
}
