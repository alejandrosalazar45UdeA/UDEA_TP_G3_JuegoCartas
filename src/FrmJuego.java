import java.awt.Color;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;

public class FrmJuego extends JFrame {

    public FrmJuego(){
        setSize(500,  300);
        setTitle("Juego de Cartas");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(null);

        JButton btnRepartir = new JButton("Repartir");
        btnRepartir.setBounds(10, 10, 100, 25);
        add(btnRepartir);

        JButton btnVerificar = new JButton("Verificar");
        btnVerificar.setBounds(120, 10, 100, 25);
        add(btnVerificar);

        // Definir una interfaz con varios paneles agrupados mediante pestañas

        JTabbedPane tpJugadores = new JTabbedPane();
        tpJugadores.setBounds(10,  45, 470, 200);
        add(tpJugadores);

        JPanel pnlJugador1 = new JPanel();
        tpJugadores.add("Martin Estrada Contreras", pnlJugador1);
        pnlJugador1.setBackground(new Color (0, 255, 0));

        JPanel pnlJugador2 = new JPanel();
        tpJugadores.add("Raúl Vidal hombre rico", pnlJugador2);
        pnlJugador2.setBackground(new Color (0, 255, 255));


    } 

}
