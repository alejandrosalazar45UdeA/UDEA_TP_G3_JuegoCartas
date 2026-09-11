import java.util.Random;

import javax.swing.JPanel;

public class Jugador {

    private final int TOTAL_CARTAS = 10;
    private final int MARGEN = 10;
    private final int DISTANCIA = 40;

    private Random r = new Random();
    private Carta[] cartas = new Carta[TOTAL_CARTAS];

    public void repartir() {
        for (int i = 0; i < TOTAL_CARTAS; i++) {
            cartas[i] = new Carta(r);
        }
    }

    public void mostrar(JPanel pnl) {
        pnl.removeAll();
        int posicionX = MARGEN + TOTAL_CARTAS * DISTANCIA;
        for (Carta carta : cartas) {
            posicionX -= DISTANCIA;
            carta.mostrar(pnl, posicionX, MARGEN);
            System.out.println(carta.getNombre() + " de " + carta.getPinta());
        }
        pnl.repaint();

    }

    public String getGrupos() {
        String respuesta = "No se encontraron grupos";

        // arreglo de contadores de cartas por el nombre
        int[] contadores = new int[NombreCarta.values().length];

        boolean hayGrupos = false;
        for (Carta carta : cartas) {
            int posicion = carta.getNombre().ordinal();
            contadores[posicion]++;
            if (!hayGrupos && contadores[posicion] >= 2)
                hayGrupos = true;
        }

        if (hayGrupos) {
            respuesta = "Se encontraron los siguientes grupos:\n";
            for (int i=0;i<contadores.length;i++) {
            //for (int contador : contadores) {
                //if (contador >= 2) {
                if (contadores[i] >= 2) {
                    //respuesta += Grupo.values()[contador] + " de "+ NombreCarta.values()[] + "\n";
                    respuesta += Grupo.values()[contadores[i]] + " de "+ NombreCarta.values()[i] + "\n";
                }
            }
        }

        return respuesta;
    }

}