
import java.util.Random;

public class GameOfLife {
    static int height = 0, width = 0, speed = 0, generation = 0;
    static String population;
    static int[][] table;
    static boolean validtable = false, contieneh = false, contienew = false, contienes = false, contieneg = false, contienep = false;

    public static void main(String[] args) {
        System.out.println(" ");
        System.out.println(" ");
        System.out.print("==================================");
        System.out.print("***** The Game Of The Life *******");
        System.out.print("==================================");
        System.out.println(" ");
        System.out.println(" ");

        if (args.length > 5) {
            System.out.println("Debes ingresar al menos 5 parámetros: altura, ancho, velocidad, generación y población.");
            System.out.println();
        }

        readArgs(args);
        validaciones();
    }


    public static void readArgs(String[] args) {
        for (String arg : args) {
            if (arg.startsWith("h=")) {
                height = validateInput(arg.substring(2));
                contieneh = true;
            }
            if (arg.startsWith("w=")) {
                width = validateInput(arg.substring(2));
                contienew = true;
            }
            if (arg.startsWith("s=")) {
                speed = validateInput(arg.substring(2));
                contienes = true;
            }
            if (arg.startsWith("g=")) {
                generation = validateInput(arg.substring(2));
                contieneg = true;
            }
            if (arg.startsWith("p=")) {
                population = arg.substring(2);
                contienep = true;
            }
        }
    }

    public static int validateInput(String input) {
        try {
            return Integer.parseInt(input);
        } catch (NumberFormatException e) {
            return -1;
        }
    }

    public static boolean isValidValue(int value, int[] validValues) {
        for (int validValue : validValues) {
            if (value == validValue) {
                return true;
            }
        }
        return false;
    }

    public static boolean isValidSpeed(int speed) {
        return speed >= 250 && speed <= 1000;
    }

    public static String generateRandom(int limit) {
        Random random = new Random();
        StringBuilder randomPopulation = new StringBuilder();

        while (randomPopulation.length() < limit) {
            randomPopulation.append(random.nextInt(2));
            if (randomPopulation.length() < limit && random.nextInt(2) == 0) {
                randomPopulation.append("#");
            }
        }
        return randomPopulation.toString();
    }

    public static void poblacionValida(String population) {
        int filas = 0;
        int columnas = 0;
        int maxnum = 0;

        for (char poblacionv : population.toCharArray()) {
            if (poblacionv == '#') {
                filas++;
                if (columnas > maxnum) {
                    maxnum = columnas;
                }
                columnas = 0;
            } else if (poblacionv == '0' || poblacionv == '1') {
                columnas++;
            } else {

                return;
            }
        }
        if (filas < height) {
            if (maxnum <= width && columnas <= width) {
                validtable = !population.isEmpty(); //ambos son boleanos si esta vacio es falso y asi no llama ala tabla
            }
        }
    }

    public static int[][] matris() {
        int[][] matriss = new int[height][width];
        int fila = 0;
        int columna = 0;
        int index = 0;
        while (index < population.length()) {
            char poblacionv = population.charAt(index);
            if (poblacionv == '0' || poblacionv == '1') {
                if(fila<height && columna<width) {
                    matriss[fila][columna] = Character.getNumericValue(poblacionv);
                    columna++;
                }
            } else if (poblacionv == '#') {
                fila++;
                columna = 0;
            }
            index++;
        }
        return matriss;
    }

    public static void tableshow() {

        table = new int[height][width];
        table = matris();
        System.out.println();
        for (int[] filas : table) {
            for (int columnas : filas) {
                if (columnas == 0) {
                    System.out.printf("%s", "⬛");
                } else {
                    System.out.printf("%s", "⬜");
                }
            }
            System.out.println();
        }
    }

    public static void validaciones() {

        if (contieneh) {
            if (isValidValue(height, new int[]{10, 20, 40})) {
                System.out.println("- Valor de altura: [" + height + "]");
            } else {
                contieneh=false;
                System.out.println("- Valor de altura: [Inválido]");
            }
        } else {
            System.out.println("- Valor de altura: [No presente]");
        }

        if (contienew) {
            if (isValidValue(width, new int[]{10, 20, 40, 80})) {
                System.out.println("- Valor de ancho [" + width + "]");
            } else {
                contienew=false;
                System.out.println("- Valor de ancho: [Inválido]");
            }
        } else {
            System.out.println("- Valor de ancho: [No presente]");
        }

        if (contienes) {
            if (isValidSpeed(speed)) {
                System.out.println("- Valor de velocidad:  [" + speed + "]");
            } else {
                contienes=false;
                System.out.println("- Valor de velocidad: [Inválido]");
            }
        } else {
            System.out.println("- Valor de velocidad: [No presente]");
        }

        if (contieneg) {
            if (generation == 0) {
                System.out.println("- Generacion ingresada [Generaciones infinitas]");
            } else if(generation>0){
                System.out.println("- Generación ingresada: [" + generation + "]");
            } else {
                contieneg=false;
                System.out.println("- Generación ingresada: [Inválido]");
            }
        } else {
            System.out.println("- valor de generación: [No presente]");}

        if (contienep) {
            poblacionValida(population);
            if (population.equals("rdn") || population.equals("rnd")) {
                population = generateRandom(height);
                System.out.println("- Población generada aleatoriamente: [" + population + "]");

            } else if (validtable) {
                if (isValidValue(height, new int[]{10, 20, 40}) && isValidValue(width, new int[]{10, 20, 40, 80})) {
                    System.out.printf("- Población válida: [" + population + "]");
                    System.out.println();
                    poblacionValida(population);
                    System.out.println();
                }
            } else {
                contienep=false;
                System.out.println("- Población ingresada: [Inválido]");
            }
        } else {
            System.out.println("- Poblacion ingresada: [No presente]");
        }
        if(contieneg && contieneh && contienep && contienes && contienew){
            tableshow();
        }else {
            System.out.println();
            System.out.println("[Todos los Valores deben ser validos para mostrar la matriz]");
        }
    }
}