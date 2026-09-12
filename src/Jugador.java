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
        }
        pnl.repaint();
    }

    public String getGrupos() {
        // Arreglo de contadores para cada nombre de carta (AS, DOS, TRES, etc.)
        int[] contadores = new int[NombreCarta.values().length];

        // 1. Llenar contadores de repeticiones
        for (Carta carta : cartas) {
            if (carta != null) {
                int posicion = carta.getNombre().ordinal();
                contadores[posicion]++;
            }
        }

        // 2. Construir el mensaje de respuesta
        String respuesta = "Se encontraron los siguientes grupos:\n";
        boolean hayGrupos = false;

        for (int i = 0; i < contadores.length; i++) {
            int cantidad = contadores[i];

            if (cantidad >= 2) {
                hayGrupos = true;
                
                // Determinamos el nombre del grupo según las repeticiones
                String nombreGrupo = "";
                switch (cantidad) {
                    case 2: nombreGrupo = "PAR"; break;
                    case 3: nombreGrupo = "TERNA"; break;
                    case 4: nombreGrupo = "CUARTA"; break;
                    case 5: nombreGrupo = "QUINTA"; break;
                    default: nombreGrupo = "GRUPO DE " + cantidad; break;
                }

                String nombreCarta = NombreCarta.values()[i].name();
                respuesta += nombreGrupo + " de " + nombreCarta + "\n";
            }
        }

        if (!hayGrupos) {
            respuesta = "No se encontraron grupos";
        }

        return respuesta; 
    }
}