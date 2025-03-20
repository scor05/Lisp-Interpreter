package lispinterpreter;

/*
 * Clase de parser que tokeniza el codigo
*/

import java.util.ArrayList;
import java.util.List;

public class Parser {
    public static Object parse(String expression) {
        expression = expression.trim();
        if (expression.startsWith("(") && expression.endsWith(")")) {
            expression = expression.substring(1, expression.length() - 1).trim();
            List<Object> list = new ArrayList<>();
            while (!expression.isEmpty()) {
                if (expression.startsWith("(")) {
                    int endIndex = findMatchingParenthesis(expression);
                    String subExpr = expression.substring(0, endIndex + 1);
                    list.add(parse(subExpr));
                    expression = expression.substring(endIndex + 1).trim();

                } else if (expression.startsWith("'")) {
                    // Listas quote
                    int endIndex = findMatchingParenthesis(expression.substring(1));
                    String subExpr = expression.substring(1, endIndex + 2);
                    list.add(List.of("quote", parse(subExpr)));
                    expression = expression.substring(endIndex + 2).trim();

                } else if (expression.startsWith("\"")) {
                    // Manejo de cadenas de texto con espacios
                    int endIndex = findMatchingQuote(expression);
                    String stringToken = expression.substring(0, endIndex + 1);
                    list.add(parseAtom(stringToken));
                    expression = expression.substring(endIndex + 1).trim();
                    
                } else {
                    int spaceIndex = expression.indexOf(' ');
                    if (spaceIndex == -1) {
                        list.add(parseAtom(expression));
                        expression = "";
                    } else {
                        String token = expression.substring(0, spaceIndex).trim();
                        list.add(parseAtom(token));
                        expression = expression.substring(spaceIndex + 1).trim();
                    }
                }
            }
            return list;
        } else {
            return parseAtom(expression);
        }
    }

    private static Object parseAtom(String expression) {
        if (expression.startsWith("'")) {
            return List.of("quote", parse(expression.substring(1)));
        } else if (expression.matches("-?\\d+")) { // Formato de números enteros
            return Integer.parseInt(expression);
        } else if (expression.matches("-?\\d+\\.\\d+")) { // Formato de números decimales
            return Double.parseDouble(expression);
        } else if (expression.equalsIgnoreCase("T")) {
            return true;
        } else if (expression.equalsIgnoreCase("nil")) {
            return false;
        } else {
            return expression;
        }
    }

    private static int findMatchingParenthesis(String expression) {
        int count = 0;
        for (int i = 0; i < expression.length(); i++) {
            if (expression.charAt(i) == '(') {
                count++;
            } else if (expression.charAt(i) == ')') {
                count--;
                if (count == 0) {
                    return i;
                }
            }
        }
        throw new RuntimeException("Paréntesis incompleto en expresión: " + expression);
    }

    private static int findMatchingQuote(String expression) {
        // Asumimos que la expresión comienza con una comilla doble
        boolean escaped = false;
        for (int i = 1; i < expression.length(); i++) {
            char c = expression.charAt(i);
            if (c == '\\') {
                escaped = !escaped;
            } else if (c == '"' && !escaped) {
                return i;
            } else {
                escaped = false;
            }
        }
        throw new RuntimeException("Comilla incompleta en expresión: " + expression);
    }
}