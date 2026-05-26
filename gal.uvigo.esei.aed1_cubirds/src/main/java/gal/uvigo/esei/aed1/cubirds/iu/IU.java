package gal.uvigo.esei.aed1.cubirds.iu;


import java.util.Scanner;

public class IU {

    private final Scanner keyboard;

    public IU() {
        keyboard = new Scanner(System.in);
    }

    public int pedirEspecie(int tamMano){
        int especie;
        do {
            especie = readNumber("Elige la especie a jugar: ");
        } while (especie < 0 || especie >= tamMano);
        return especie;
    }

    public int pedirFila(){
        int fila;
        do {
            fila = readNumber("Elige una fila (1-4): ");
        } while (fila < 1 || fila > 4);
        return fila;
    }

    public boolean pedirLado(){
        String lado;
        do {
            lado = readString("¿Derecha? (s/n): ");
        } while (!lado.equalsIgnoreCase("s") && !lado.equalsIgnoreCase("n"));
        return lado.equalsIgnoreCase("s");
    }

    public int pedirNumJugadores(){
        int numJugadores;
        do{
            numJugadores=readNumber("Introduce el numero de jugadores (2-5): ");
        }while(numJugadores<2 || numJugadores>5);
        return numJugadores;
    }
    public String pedirNombreJugador(int numero){
        return readString("Nombre del jugador "+numero+": ");
    }
    /**
     * Lee un num. de teclado
     *
     * @param msg El mensaje a visualizar.
     * @return El num., como entero
     */
    public int readNumber(String msg) {
        boolean repeat;
        int toret = 0;

        do {
            repeat = false;
            System.out.print(msg);
            try {
                toret = Integer.parseInt(keyboard.nextLine());
            } catch (NumberFormatException exc) {
                repeat = true;
            }
        } while (repeat);

        return toret;
    }

    /**
     * Lee un string de teclado
     *
     * @param msg mensaje a mostrar antes de la lectura
     * @return el string leido
     */
    public String readString(String msg) {
        String toret;
        System.out.print(msg);
        toret = keyboard.nextLine();
        return toret;
    }

    /**
     * muestra un mensaje por pantalla
     *
     * @param msg el mensaje a mostrar
     */
    public void displayMessage(String msg) {
        System.out.println(msg);
    } 

}
