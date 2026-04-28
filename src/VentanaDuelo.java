import javax.swing.*;
import java.awt.*;

public class VentanaDuelo extends JFrame {

    private JLabel lblLPJ1;
    private JLabel lblLPJ2;
    private JLabel lblMazo1;
    private JLabel lblMazo2;
    private JLabel lblMano1;
    private JLabel lblMano2;
    private JLabel lblMonstruos1;
    private JLabel lblMonstruos2;
    private JLabel lblTrampa1;
    private JLabel lblTrampa2;
    private JLabel lblTurno;
    private JLabel lblLog;

    private JButton btnJugarCarta;
    private JButton btnAtacar;
    private JButton btnActivarTrampa;
    private JButton btnTerminarTurno;

    private Jugador jugador1;
    private Jugador jugador2;

    public JButton getBtnJugarCarta(){ return btnJugarCarta; }
    public JButton getBtnAtacar(){ return btnAtacar; }
    public JButton getBtnActivarTrampa(){ return btnActivarTrampa; }
    public JButton getBtnTerminarTurno(){ return btnTerminarTurno; }

    public VentanaDuelo(Jugador j1, Jugador j2) {
        this.jugador1 = j1;
        this.jugador2 = j2;

        setTitle("Yu-Gi-Oh! - Campo de Duelo");
        setSize(960, 640);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        add(crearHeader(), BorderLayout.NORTH);
        add(crearCentro(), BorderLayout.CENTER);
        add(crearFooter(), BorderLayout.SOUTH);

        actualizarInterfaz();
        setVisible(true);
    }

    private JPanel crearHeader() {
        JPanel header = new JPanel(new BorderLayout());
        header.setBackground(Color.BLACK);

        JLabel titulo = new JLabel("  YU-GI-OH!  CAMPO DE DUELO");
        titulo.setForeground(new Color(201, 168, 76));
        titulo.setFont(new Font("Serif", Font.BOLD, 18));
        header.add(titulo, BorderLayout.WEST);

        lblTurno = new JLabel("TURNO 1  ");
        lblTurno.setForeground(new Color(201, 168, 76));
        lblTurno.setFont(new Font("Serif", Font.BOLD, 14));
        lblTurno.setHorizontalAlignment(SwingConstants.RIGHT);
        header.add(lblTurno, BorderLayout.EAST);

        return header;
    }

    private JPanel crearCentro() {
        JPanel centro = new JPanel(new GridLayout(1, 3));
        // FIX: j1 siempre a la izquierda, j2 siempre a la derecha
        // Los labels se asignan correctamente: j1 -> lblLPJ1, j2 -> lblLPJ2
        centro.add(crearPanelJugador(jugador1, true));   // izquierda = j1
        centro.add(crearCampo());
        centro.add(crearPanelJugador(jugador2, false));  // derecha = j2
        return centro;
    }

    // esJ1: true = jugador1 (izquierda), false = jugador2 (derecha)
    private JPanel crearPanelJugador(Jugador jugador, boolean esJ1) {
        JPanel panel = new JPanel(new GridLayout(6, 1));
        panel.setBackground(new Color(18, 22, 30));

        JLabel lblNombre = new JLabel(jugador.getNombre(), SwingConstants.CENTER);
        lblNombre.setForeground(new Color(201, 168, 76));
        lblNombre.setFont(new Font("Serif", Font.BOLD, 14));
        lblNombre.setOpaque(true);
        lblNombre.setBackground(new Color(10, 12, 16));
        panel.add(lblNombre);

        panel.add(crearFila("LP:", String.valueOf(jugador.getPuntosVida()), new Color(77, 232, 122), esJ1, "lp"));
        panel.add(crearFila("Mazo:", cantMazo(jugador), new Color(232, 224, 204), esJ1, "mazo"));
        panel.add(crearFila("Mano:", cantMano(jugador), new Color(232, 224, 204), esJ1, "mano"));
        panel.add(crearFila("Campo:", cantMonstruos(jugador), new Color(232, 224, 204), esJ1, "monstruos"));
        panel.add(crearFila("Trampas:", cantTrampas(jugador), new Color(180, 140, 220), esJ1, "trampa"));

        return panel;
    }

    private JPanel crearFila(String etiqueta, String valor, Color colorValor, boolean esJ1, String tipo) {
        JPanel fila = new JPanel(new FlowLayout(FlowLayout.CENTER));
        fila.setBackground(new Color(18, 22, 30));
        JLabel et = new JLabel(etiqueta);
        et.setForeground(new Color(122, 112, 96));
        JLabel val = new JLabel(valor);
        val.setForeground(colorValor);
        val.setFont(new Font("Serif", Font.BOLD, 14));
        fila.add(et);
        fila.add(val);

        // Asignar referencia correctamente por jugador
        if (esJ1) {
            switch (tipo) {
                case "lp":        lblLPJ1       = val; break;
                case "mazo":      lblMazo1      = val; break;
                case "mano":      lblMano1      = val; break;
                case "monstruos": lblMonstruos1 = val; break;
                case "trampa":    lblTrampa1    = val; break;
            }
        } else {
            switch (tipo) {
                case "lp":        lblLPJ2       = val; break;
                case "mazo":      lblMazo2      = val; break;
                case "mano":      lblMano2      = val; break;
                case "monstruos": lblMonstruos2 = val; break;
                case "trampa":    lblTrampa2    = val; break;
            }
        }
        return fila;
    }

    private JPanel crearCampo() {
        JPanel campo = new JPanel(new GridLayout(3, 1));
        campo.setBackground(new Color(14, 16, 24));

        campo.add(crearZona("ZONA DEL OPONENTE"));

        JLabel vs = new JLabel("─── VS ───", SwingConstants.CENTER);
        vs.setForeground(new Color(122, 112, 96));
        vs.setFont(new Font("Serif", Font.PLAIN, 12));
        vs.setOpaque(true);
        vs.setBackground(new Color(10, 12, 16));
        campo.add(vs);

        campo.add(crearZona("TU ZONA"));
        return campo;
    }

    private JPanel crearZona(String titulo) {
        JPanel zona = new JPanel(new GridLayout(3, 1));
        zona.setBackground(new Color(14, 16, 24));

        JLabel etiqueta = new JLabel(titulo, SwingConstants.CENTER);
        etiqueta.setForeground(new Color(122, 112, 96));
        etiqueta.setFont(new Font("Serif", Font.PLAIN, 10));
        zona.add(etiqueta);

        JPanel filaMonstruos = new JPanel(new GridLayout(1, 5, 4, 0));
        filaMonstruos.setBackground(new Color(14, 16, 24));
        for (int i = 0; i < 5; i++) filaMonstruos.add(crearSlot("M", new Color(14, 26, 14), new Color(30, 58, 30)));
        zona.add(filaMonstruos);

        JPanel filaMagia = new JPanel(new GridLayout(1, 5, 4, 0));
        filaMagia.setBackground(new Color(14, 16, 24));
        for (int i = 0; i < 5; i++) filaMagia.add(crearSlot("S/T", new Color(10, 14, 26), new Color(26, 30, 58)));
        zona.add(filaMagia);

        return zona;
    }

    private JLabel crearSlot(String texto, Color bg, Color fg) {
        JLabel slot = new JLabel(texto, SwingConstants.CENTER);
        slot.setOpaque(true);
        slot.setBackground(bg);
        slot.setForeground(fg);
        slot.setFont(new Font("SansSerif", Font.BOLD, 10));
        slot.setBorder(BorderFactory.createLineBorder(fg, 1));
        return slot;
    }

    private JPanel crearFooter() {
        JPanel footer = new JPanel(new BorderLayout());
        footer.setBackground(new Color(10, 12, 16));

        JPanel botones = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 8));
        botones.setBackground(new Color(10, 12, 16));

        btnJugarCarta    = new JButton("JUGAR CARTA");
        btnAtacar        = new JButton("ATACAR");
        btnActivarTrampa = new JButton("ACTIVAR TRAMPA");
        btnTerminarTurno = new JButton("TERMINAR TURNO");

        estilizarBoton(btnJugarCarta,    new Color(201, 168, 76));
        estilizarBoton(btnAtacar,        new Color(224, 64,  64));
        estilizarBoton(btnActivarTrampa, new Color(160, 80, 200));
        estilizarBoton(btnTerminarTurno, new Color(122, 112, 96));

        botones.add(btnJugarCarta);
        botones.add(btnAtacar);
        botones.add(btnActivarTrampa);
        footer.add(botones, BorderLayout.WEST);

        lblLog = new JLabel("¡El duelo comienza!");
        lblLog.setForeground(new Color(122, 112, 96));
        lblLog.setFont(new Font("SansSerif", Font.ITALIC, 11));
        lblLog.setHorizontalAlignment(SwingConstants.CENTER);
        footer.add(lblLog, BorderLayout.CENTER);

        JPanel der = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 8));
        der.setBackground(new Color(10, 12, 16));
        der.add(btnTerminarTurno);
        footer.add(der, BorderLayout.EAST);

        return footer;
    }

    private void estilizarBoton(JButton btn, Color color) {
        btn.setForeground(color);
        btn.setBackground(new Color(10, 12, 16));
        btn.setFont(new Font("SansSerif", Font.BOLD, 10));
        btn.setFocusPainted(false);
        btn.setBorder(BorderFactory.createLineBorder(color, 1));
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
    }

    public void actualizarInterfaz() {
        // LP — j1 siempre a la izquierda, j2 siempre a la derecha
        lblLPJ1.setText(String.valueOf(jugador1.getPuntosVida()));
        lblLPJ2.setText(String.valueOf(jugador2.getPuntosVida()));

        if (jugador1.getMazo() != null) lblMazo1.setText(jugador1.getMazo().getCantidadCartas() + " cartas");
        if (jugador2.getMazo() != null) lblMazo2.setText(jugador2.getMazo().getCantidadCartas() + " cartas");

        if (jugador1.getMano() != null) lblMano1.setText(jugador1.getMano().size() + " cartas");
        if (jugador2.getMano() != null) lblMano2.setText(jugador2.getMano().size() + " cartas");

        lblMonstruos1.setText(jugador1.getCampo().getCantidadMonstruos() + " monstruos");
        lblMonstruos2.setText(jugador2.getCampo().getCantidadMonstruos() + " monstruos");

        lblTrampa1.setText(jugador1.getCampo().getCartasTrampa().size() + " trampas");
        lblTrampa2.setText(jugador2.getCampo().getCartasTrampa().size() + " trampas");

        revalidate();
        repaint();
    }

    public void setLog(String mensaje){ lblLog.setText(mensaje); }
    public void setTurno(int numero){ lblTurno.setText("TURNO " + numero + "  "); }

    // Helpers
    private String cantMazo(Jugador j){ 
        return j.getMazo() != null ? j.getMazo().getCantidadCartas() + " cartas" : "0 cartas"; 
    }
    private String cantMano(Jugador j){ 
        return j.getMano() != null ? j.getMano().size() + " cartas" : "0 cartas"; 
    }
    private String cantMonstruos(Jugador j){ 
        return j.getCampo().getCantidadMonstruos() + " monstruos"; 
    }
    private String cantTrampas(Jugador j){ 
        return j.getCampo().getCartasTrampa().size() + " trampas"; 
    }
}
