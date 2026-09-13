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
        boolean[] marcada = new boolean[TOTAL_CARTAS];
 
        marcarCartasEnGrupos(marcada);
        marcarCartasEnEscaleras(marcada);
 
        int puntajeTotal = 0;
 
        for (int i = 0; i < TOTAL_CARTAS; i++) {
            if (cartas[i] != null && !marcada[i]) {
                int posNombre = cartas[i].getNombre().ordinal();
                int valorCarta = posNombre + 1; // AS=1, DOS=2...
 
                // AS (1), DIEZ (10), JACK (11), QUEEN (12), KING (13) valen 10
                if (valorCarta == 1 || valorCarta >= 10) {
                    puntajeTotal += 10;
                } else {
                    puntajeTotal += valorCarta;
                }
            }
        }
 
        return puntajeTotal;
    }
 
    // marca en 'marcada' las posiciones (0 a 9) de cartas que se repiten
    // por nombre 2 o mas veces (par, terna, cuarta, etc.)
    private void marcarCartasEnGrupos(boolean[] marcada) {
        int[] contadoresNombre = new int[NombreCarta.values().length];
 
        for (Carta carta : cartas) {
            if (carta != null) {
                contadoresNombre[carta.getNombre().ordinal()]++;
            }
        }
 
        for (int i = 0; i < TOTAL_CARTAS; i++) {
            if (cartas[i] != null && contadoresNombre[cartas[i].getNombre().ordinal()] >= 2) {
                marcada[i] = true;
            }
        }
    }
 
    // marca en 'marcada' las posiciones (0 a 9) de cartas que forman parte
    // de una escalera de la misma pinta (2 o mas cartas consecutivas del mismo palo,
    // igual al criterio usado en getEscaleras())
    private void marcarCartasEnEscaleras(boolean[] marcada) {
        int[][] matriz = new int[4][13];
 
        for (Carta carta : cartas) {
            if (carta != null) {
                matriz[carta.getPinta().ordinal()][carta.getNombre().ordinal()] = 1;
            }
        }
 
        for (int p = 0; p < 4; p++) {
            int consecutivas = 0;
            int inicioSecuencia = -1;
 
            for (int n = 0; n <= 13; n++) {
                boolean presente = (n < 13) && (matriz[p][n] == 1);
                if (presente) {
                    if (consecutivas == 0) {
                        inicioSecuencia = n;
                    }
                    consecutivas++;
                } else {
                    if (consecutivas >= 2) {
                        marcarRangoDeCartas(marcada, p, inicioSecuencia, n - 1);
                    }
                    consecutivas = 0;
                }
            }
        }
    }
 
    // marca todas las cartas de la mano que pertenecen a la pinta 'pinta' y cuyo
    // nombre (ordinal) esta entre 'desde' y 'hasta', ambos inclusive
    private void marcarRangoDeCartas(boolean[] marcada, int pinta, int desde, int hasta) {
        for (int i = 0; i < TOTAL_CARTAS; i++) {
            if (cartas[i] != null) {
                int ordinalPinta = cartas[i].getPinta().ordinal();
                int ordinalNombre = cartas[i].getNombre().ordinal();
                if (ordinalPinta == pinta && ordinalNombre >= desde && ordinalNombre <= hasta) {
                    marcada[i] = true;
                }
            }
        }
    }
}