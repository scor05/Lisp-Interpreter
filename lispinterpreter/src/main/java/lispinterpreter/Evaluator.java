package lispinterpreter;
/**
 * Clase que evaluará tanto listas como expresiones atómicas
 */

import java.util.ArrayList;
import java.util.List;
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

    private Object defineFunction(List<?> list, Environment env) {
        String name = list.get(1).toString();
        @SuppressWarnings("unchecked")
        List<String> params = (List<String>) list.get(2);
        Object body = list.get(3);
        LispFunction function = new LispFunction(name, params, body);
        env.defineFunction(name, function);
        return name;
    }

    private Object setVariable(List<?> list, Environment env) {
        String name = list.get(1).toString();
        Object value = eval(list.get(2), env);
        env.setVar(name, value.getClass().getSimpleName().toLowerCase(), value);
        return value;
    }

    private Object isAtom(Object expression, Environment env) {
        Object value = eval(expression, env);
        return !(value instanceof List);
    }

    private Object isList(Object expression, Environment env) {
        Object value = eval(expression, env);
        return value instanceof List;
    }

    private Object isEqual(Object expr1, Object expr2, Environment env) {
        Object val1 = eval(expr1, env);
        Object val2 = eval(expr2, env);
        return val1.equals(val2);
    }

    private Object greaterThan(Object expr1, Object expr2, Environment env) {
        Object val1 = eval(expr1, env);
        Object val2 = eval(expr2, env);
        if (val1 instanceof Number && val2 instanceof Number) {
            return ((Number) val1).doubleValue() > ((Number) val2).doubleValue();
        }
        throw new RuntimeException("No se puede comparar valores con datos no numericos.");
    }
     private Object lessThan(Object expr1, Object expr2, Environment env) {
        Object val1 = eval(expr1, env);
        Object val2 = eval(expr2, env);
        if (val1 instanceof Number && val2 instanceof Number) {
            return ((Number) val1).doubleValue() < ((Number) val2).doubleValue();
        }
        throw new RuntimeException("No se puede comparar valores no numericos.");
    }

    private Object evaluateCond(List<?> conditions, Environment env) {
        for (Object condition : conditions) {
            List<?> cond = (List<?>) condition;
            Object test = eval(cond.get(0), env);
            if (test instanceof Boolean && (Boolean) test) {
                return eval(cond.get(1), env);
            }
        }
        return null;
    }

    private Object funcall(Object function, Object arg, Environment env) {
        LispFunction func = env.getFunction(function.toString());
        List<Object> args = new ArrayList<>();
        args.add(arg);
        return func.apply(args, env);
    }

    private Object evaluateFunction(String operator, List<?> args, Environment env) {
        LispFunction function = env.getFunction(operator);
        List<Object> evaluatedArgs = new ArrayList<>();
        for (Object arg : args) {
            evaluatedArgs.add(eval(arg, env));
        }
        return function.apply(evaluatedArgs, env);
    }
}
