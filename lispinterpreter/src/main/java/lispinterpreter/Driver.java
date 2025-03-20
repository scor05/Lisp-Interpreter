package lispinterpreter;
/**
 * Clase main que ejecuta el intérprete y la UI
 */
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;


public class Driver {
    public static void main(String[] args) {
        @SuppressWarnings("resource") // Para que no alegue del 'scanner not closed'
        Scanner sc = new Scanner(System.in);
        boolean loop = true;
        do{
            System.out.println("-".repeat(50));
            System.out.println("\t\tIntérprete de LISP");
            System.out.println("-".repeat(50));

            System.out.print("\nIngrese el nombre del archivo que desea ejecutar (extensión .txt): \nR/ ");
            String fileName = sc.nextLine();
            try{
                ArrayList<String> code = readFile(fileName);
                if (code.isEmpty()) {
                    System.out.println("Output: \n");
                }
                LispInterpreter interpreter = new LispInterpreter();
                interpreter.execute(code);

            }catch (IOException e){
                System.out.println("Error al leer el archivo: " + e.getMessage() + ". \nRevise el nombre del archivo y si existe en el directorio.\n");
            }

            System.out.print("¿Desea ejecutar otro archivo? (Ingrese 'S' si desea hacerlo o cualquier otra letra para salir): \nR/ ");
            if (!sc.nextLine().toLowerCase().equals("s")) {
                loop = false;
                System.out.println("Gracias por utilizar nuestro intérprete :D");
            }
        }while(loop);
    }

    /**
     * Retorna un String con todas las líneas del archivo.
     * @param fileName
     * @return ArrayList<String>
     */
    public static ArrayList<String> readFile(String fileName) throws IOException {
        BufferedReader br = new BufferedReader(new FileReader(fileName));
        String line;
        ArrayList<String> code = new ArrayList<>();
        StringBuilder currentExpr = new StringBuilder();
        while ((line = br.readLine()) != null) {
            line = line.trim();
            if (line.isEmpty()) {
                continue;
            }
            currentExpr.append(line).append(" ");
            if (isCompleteExpression(currentExpr.toString())) {
                code.add(currentExpr.toString().trim());
                currentExpr = new StringBuilder();
            }
        }
        br.close();
        return code;
    }

    private static boolean isCompleteExpression(String expression) {
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

}
