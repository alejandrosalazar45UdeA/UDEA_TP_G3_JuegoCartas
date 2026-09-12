import java.util.Random;
import javax.swing.JPanel;

public class Jugador {

    private final int TOTAL_CARTAS = 10;
    private final int MARGEN = 10;
    private final int DISTANCIA = 40;

    private Carta[] cartas = new Carta[TOTAL_CARTAS];

    
    public void repartir(Baraja baraja, Random r) {
        for (int i = 0; i < TOTAL_CARTAS; i++) {
            cartas[i] = baraja.repartirCarta(r);
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
        int[] contadores = new int[NombreCarta.values().length];

        for (Carta carta : cartas) {
            if (carta != null) {
                int posicion = carta.getNombre().ordinal();
                contadores[posicion]++;
            }
        }

        String respuesta = "--- GRUPOS ---\n";
        boolean hayGrupos = false;

        for (int i = 0; i < contadores.length; i++) {
            int cantidad = contadores[i];

            if (cantidad >= 2) {
                hayGrupos = true;
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
            respuesta += "No se encontraron grupos por valor.\n";
        }

        return respuesta; 
    }

    
    public String getEscaleras() {
        int[][] matriz = new int[4][13];

        for (Carta carta : cartas) {
            if (carta != null) {
                int filaPinta = carta.getPinta().ordinal();
                int colNombre = carta.getNombre().ordinal();
                matriz[filaPinta][colNombre] = 1; 
            }
        }

        String respuesta = "--- ESCALERAS ---\n";
        boolean hayEscalera = false;

        for (int p = 0; p < 4; p++) {
            int consecutivas = 0;
            int inicioSecuencia = -1;

            for (int n = 0; n < 13; n++) {
                if (matriz[p][n] == 1) {
                    if (consecutivas == 0) {
                        inicioSecuencia = n;
                    }
                    consecutivas++;
                } else {
                    if (consecutivas >= 2) { 
                        hayEscalera = true;
                        respuesta += Grupos.values()[consecutivas] + " de " + Pinta.values()[p] + 
                                     " (" + NombreCarta.values()[inicioSecuencia] + " a " + NombreCarta.values()[n-1] + ")\n";
                    }
                    consecutivas = 0;
                }
            }
            if (consecutivas >= 2) {
                hayEscalera = true;
                respuesta += Grupos.values()[consecutivas] + " de " + Pinta.values()[p] + 
                             " (" + NombreCarta.values()[inicioSecuencia] + " a " + NombreCarta.values()[12] + ")\n";
            }
        }

        if (!hayEscalera) {
            respuesta += "No se encontraron escaleras.\n";
        }

        return respuesta;
    }

    
    public int getPuntaje() {
        int[] contadoresNombre = new int[NombreCarta.values().length];

        for (Carta carta : cartas) {
            if (carta != null) {
                contadoresNombre[carta.getNombre().ordinal()]++;
            }
        }

        int puntajeTotal = 0;

        for (Carta carta : cartas) {
            if (carta != null) {
                int posNombre = carta.getNombre().ordinal();

                
                if (contadoresNombre[posNombre] == 1) {
                    int valorCarta = posNombre + 1; // AS=1, DOS=2...

                    // AS (1), DIEZ (10), JACK (11), QUEEN (12), KING (13) valen 10
                    if (valorCarta == 1 || valorCarta >= 10) { 
                        puntajeTotal += 10;
                    } else {
                        puntajeTotal += valorCarta;
                    }
                }
            }
        }

        return puntajeTotal;
    }
}