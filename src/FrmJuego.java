import java.awt.Color;
import java.util.Random;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;

public class FrmJuego extends JFrame {

    private JPanel pnlJugador1, pnlJugador2;
    private JTabbedPane tpJugadores;

    Jugador jugador1 = new Jugador();
    Jugador jugador2 = new Jugador();

    public FrmJuego() {
        setSize(500, 300);
        setTitle("Juego de Cartas");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(null);

        JButton btnRepartir = new JButton("Repartir");
        btnRepartir.setBounds(10, 10, 100, 25);
        add(btnRepartir);

        JButton btnVerificar = new JButton("Verificar");
        btnVerificar.setBounds(120, 10, 100, 25);
        add(btnVerificar);

        tpJugadores = new JTabbedPane();
        tpJugadores.setBounds(10, 45, 470, 200);
        add(tpJugadores);

        pnlJugador1 = new JPanel();
        tpJugadores.add("Martín Estrada Contreras", pnlJugador1);
        pnlJugador1.setBackground(new Color(0, 255, 0));
        pnlJugador1.setLayout(null);

        pnlJugador2 = new JPanel();
        tpJugadores.add("Raúl Vidal", pnlJugador2);
        pnlJugador2.setBackground(new Color(0, 255, 255));
        pnlJugador2.setLayout(null);

        // Eventos
        btnRepartir.addActionListener(evento -> repartir());
        btnVerificar.addActionListener(evento -> verificar());
    }

    private void repartir() {
        Random r = new Random();
       
        Baraja baraja = new Baraja(1); 

        jugador1.repartir(baraja, r);
        jugador2.repartir(baraja, r);

        jugador1.mostrar(pnlJugador1);
        jugador2.mostrar(pnlJugador2);
    }

    private void verificar() {
        Jugador jugadorActual;

        if (tpJugadores.getSelectedIndex() == 0) {
            jugadorActual = jugador1;
        } else {
            jugadorActual = jugador2;
        }

        String mensaje = jugadorActual.getGrupos() + "\n" +
                         jugadorActual.getEscaleras() + "\n" +
                         "PUNTAJE (Cartas sobrantes): " + jugadorActual.getPuntaje() + " puntos";

        JOptionPane.showMessageDialog(null, mensaje);
    }
}
