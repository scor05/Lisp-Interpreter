/**
 * Clase main que ejecuta el intérprete y la UI
 */
import java.io.BufferedReader;
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
                ArrayList<String[]> tokens = readFile(fileName);
                if (tokens.size() == 0) {
                    System.out.println("No hay ninguna operación en el archivo.");
                }
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
     * Retorna una matriz de String[] en ArrayList con todas las líneas del archivo.
     * @param fileName
     * @return ArrayList<String[]>
     */
    public static ArrayList<String[]> readFile(String fileName) throws IOException{
        BufferedReader br = new BufferedReader(new java.io.FileReader(fileName));
        String line;
        ArrayList<String[]> tokens = new ArrayList<>();
        while((line = br.readLine()) != null) {
            String[] lines = line.split(" ");
            tokens.add(lines);
        }
        br.close();
        return tokens;
    }

}
