package lispinterpreter;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintStream;
import java.util.ArrayList;
import static org.junit.Assert.*;
import org.junit.jupiter.api.Test;

/**
 * Unit test for simple App.
 */
public class LispTests {

    public LispInterpreter interpreter;

    @Test
    public void shouldReturnFileSeparatedByLines(){
        ArrayList<String> correctText = new ArrayList<>();
        correctText.add("(defun suma (a b) (+ a b))");
        correctText.add("(suma 2 3)");
        correctText.add("(setq mi-lista '(1 2 3 4))");

        ArrayList<String> test = new ArrayList<>();
        // Metodo de readFile de Driver
        try {
            BufferedReader br = new BufferedReader(new FileReader("lispTest.txt"));
            String line;
            
            StringBuilder currentExpr = new StringBuilder();
            while ((line = br.readLine()) != null) {
                line = line.trim();
                if (line.isEmpty()) {
                    continue;
                }
                currentExpr.append(line).append(" ");
                if (isCompleteExpression(currentExpr.toString())) {
                    test.add(currentExpr.toString().trim());
                    currentExpr = new StringBuilder();
                }
            }
            br.close();
        } catch (IOException e) {
        }
        assertEquals(correctText, test);
    }

    // Utilizado para shouldReturnFileSeparatedByLines
    public boolean isCompleteExpression(String expression) {
        int openParens = 0;
        for (char c : expression.toCharArray()) {
            if (c == '(') {
                openParens++;
            } else if (c == ')') {
                openParens--;
            }
        }
        return openParens == 0;
    }

    @Test
    public void shouldReturnIncompleteParenthesisError(){
        ArrayList<String> errorCode = new ArrayList<>();
        errorCode.add("(setq lista1 (1 2 3 )");
        try{
            this.interpreter.execute(errorCode);
        }catch (RuntimeException e){
            assertTrue(true);
            return;
        }
        assertTrue(false);
    }

    @Test
    public void shouldReturnIncompleteStringError(){
        ArrayList<String> errorCode = new ArrayList<>();
        errorCode.add("(setq prueba \"Hola)");
        try{
            this.interpreter.execute(errorCode);
        }catch (RuntimeException e){
            assertTrue(true);
            return;
        }
        assertTrue(false);
    }

    @Test
    public void shouldReturnUnknownVariable(){
        ArrayList<String> errorCode = new ArrayList<>();
        errorCode.add("(+ x 3 \"Hola)");
        try{
            this.interpreter.execute(errorCode);
        }catch (RuntimeException e){
            assertTrue(true);
            return;
        }
        assertTrue(false);
    }

    @Test
    public void shouldReturnFactorial(){
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));
        
        ArrayList<String> code = new ArrayList<>();
        code.add("(defun factorial (n) (cond ((= n 0) 1) (T (* n (factorial (- n 1))))))");
        code.add("(factorial 5)");
        
        // Ejecutar el intérprete
        LispInterpreter interpreter = new LispInterpreter();
        interpreter.execute(code);
        
        // Verificar que el resultado sea 120 (factorial de 5)
        String output = outContent.toString().trim();
        boolean containsResult = output.contains("120");
        
        assertTrue(containsResult);
    
    }

    @Test
    public void shouldNotStartRecursionWithCond() {
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));
        
        ArrayList<String> code = new ArrayList<>();
        code.add("(defun factorial (n) (cond ((= n 0) 1) (T (* n (factorial (- n 1))))))");
        code.add("(factorial 0)");
        
        // Ejecutar el intérprete
        LispInterpreter interpreter = new LispInterpreter();
        interpreter.execute(code);
        
        // Verificar que el resultado sea 120 (factorial de 5)
        String output = outContent.toString().trim();
        boolean containsResult = output.contains("1");

        assertTrue(containsResult);
    }
}
