import java.util.Random;

public class Baraja {

    private int totalBarajas;
    private int[] aparicionesCartas;

    public Baraja(int totalBarajas) {
        this.totalBarajas = totalBarajas;
        this.aparicionesCartas = new int[53]; 
    }

    
    public Carta repartirCarta(Random r) {
        int indiceCarta;
        do {
            indiceCarta = r.nextInt(52) + 1;
        } while (aparicionesCartas[indiceCarta] >= totalBarajas);

        aparicionesCartas[indiceCarta]++;
        return new Carta(indiceCarta);
    }
}