import javax.swing.*;
import java.awt.*;


public class VentanaDuelo extends JFrame {

    // Etiquetas
    private JLabel lblLJugador1;
    private JLabel lblLJugador2;
    private JLabel lblMazo1;
    private JLabel lblMazo2;
    private JLabel lblMano1;
    private JLabel lblMano2;
    private JLabel lblMonstruos1;
    private JLabel lblMonstruos2;
    private JLabel lblTurno;
    private JLabel lblLog;

    // Botones
    private JButton btnJugarCarta;
    private JButton btnAtacar;
    private JButton btnTerminarTurno;

    // Jugadores
    private Jugador jugador1;
    private Jugador jugador2;

    public VentanaDuelo(Jugador j1, Jugador j2) {
        this.jugador1 = j1;
        this.jugador2 = j2;

        setTitle("Yu-Gi-Oh! - Campo de Duelo");
        setSize(900, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        add(crearHeader(),   BorderLayout.NORTH);
        add(crearCentro(),   BorderLayout.CENTER);
        add(crearFooter(),   BorderLayout.SOUTH);

        actualizarInterfaz();
        setVisible(true);
    }

    // Título y turno
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

    
    //  CENTRO — columnas
    
    private JPanel crearCentro() {
        JPanel centro = new JPanel(new GridLayout(1, 3));

        centro.add(crearPanelJugador(jugador1, false));
        centro.add(crearCampo());
        centro.add(crearPanelJugador(jugador2, true));

        return centro;
    }

    
    //  PANEL LATERAL DE JUGADOR
    
    private JPanel crearPanelJugador(Jugador jugador, boolean esActivo) {
        JPanel panel = new JPanel(new GridLayout(5, 1));
        panel.setBackground(new Color(18, 22, 30));

        // Fila 1 — Nombre
        JLabel lblNombre = new JLabel(jugador.getNombre(), SwingConstants.CENTER);
        lblNombre.setForeground(new Color(201, 168, 76));
        lblNombre.setFont(new Font("Serif", Font.BOLD, 14));
        lblNombre.setOpaque(true);
        lblNombre.setBackground(new Color(10, 12, 16));
        panel.add(lblNombre);

        // Fila 2 — LP
        JPanel filaLP = new JPanel(new FlowLayout(FlowLayout.CENTER));
        filaLP.setBackground(new Color(18, 22, 30));
        JLabel etLP = new JLabel("LP: ");
        etLP.setForeground(new Color(122, 112, 96));
        JLabel valLP = new JLabel(String.valueOf(jugador.getPuntosVida()));
        valLP.setForeground(new Color(77, 232, 122));
        valLP.setFont(new Font("Serif", Font.BOLD, 16));
        filaLP.add(etLP);
        filaLP.add(valLP);
        if (esActivo) { lblLJugador1 = valLP; } else { lblLJugador2 = valLP; }
        panel.add(filaLP);

        // Fila 3 — Mazo
        JPanel filaMazo = new JPanel(new FlowLayout(FlowLayout.CENTER));
        filaMazo.setBackground(new Color(18, 22, 30));
        JLabel etMazo = new JLabel("Mazo: ");
        etMazo.setForeground(new Color(122, 112, 96));
        int cantMazo = jugador.getMazo() != null ? jugador.getMazo().getCantidadCartas() : 0;
        JLabel valMazo = new JLabel(cantMazo + " cartas");
        valMazo.setForeground(new Color(232, 224, 204));
        filaMazo.add(etMazo);
        filaMazo.add(valMazo);
        if (esActivo) { lblMazo1 = valMazo; } else { lblMazo2 = valMazo; }
        panel.add(filaMazo);

        // Fila 4 — Mano
        JPanel filaMano = new JPanel(new FlowLayout(FlowLayout.CENTER));
        filaMano.setBackground(new Color(18, 22, 30));
        JLabel etMano = new JLabel("Mano: ");
        etMano.setForeground(new Color(122, 112, 96));
        int cantMano = jugador.getMano() != null ? jugador.getMano().size() : 0;
        JLabel valMano = new JLabel(cantMano + " cartas");
        valMano.setForeground(new Color(232, 224, 204));
        filaMano.add(etMano);
        filaMano.add(valMano);
        if (esActivo) { lblMano1 = valMano; } else { lblMano2 = valMano; }
        panel.add(filaMano);

        // Fila 5 — Monstruos en campo
        JPanel filaMonstruos = new JPanel(new FlowLayout(FlowLayout.CENTER));
        filaMonstruos.setBackground(new Color(18, 22, 30));
        JLabel etMonstruos = new JLabel("Campo: ");
        etMonstruos.setForeground(new Color(122, 112, 96));
        int cantMonstruos = jugador.getCampo().getCantidadMonstruos();
        JLabel valMonstruos = new JLabel(cantMonstruos + " monstruos");
        valMonstruos.setForeground(new Color(232, 224, 204));
        filaMonstruos.add(etMonstruos);
        filaMonstruos.add(valMonstruos);
        if (esActivo) { lblMonstruos1 = valMonstruos; } else { lblMonstruos2 = valMonstruos; }
        panel.add(filaMonstruos);

        return panel;
    }

    
    //  CAMPO DE BATALLA — zona rival | divisor | zona jugador
    
    private JPanel crearCampo() {
        JPanel campo = new JPanel(new GridLayout(3, 1));
        campo.setBackground(new Color(14, 16, 24));

        // Zona del rival (arriba)
        JPanel zonaRival = crearZona("ZONA DEL OPONENTE");
        campo.add(zonaRival);

        // Divisor VS (centro)
        JLabel vs = new JLabel("─── VS ───", SwingConstants.CENTER);
        vs.setForeground(new Color(122, 112, 96));
        vs.setFont(new Font("Serif", Font.PLAIN, 12));
        vs.setOpaque(true);
        vs.setBackground(new Color(10, 12, 16));
        campo.add(vs);

        // Zona del jugador (abajo)
        JPanel zonaJugador = crearZona("TU ZONA");
        campo.add(zonaJugador);

        return campo;
    }

    private JPanel crearZona(String titulo) {
        JPanel zona = new JPanel(new GridLayout(3, 1));
        zona.setBackground(new Color(14, 16, 24));

        // Etiqueta de zona
        JLabel etiqueta = new JLabel(titulo, SwingConstants.CENTER);
        etiqueta.setForeground(new Color(122, 112, 96));
        etiqueta.setFont(new Font("Serif", Font.PLAIN, 10));
        zona.add(etiqueta);

        // Fila de slots de monstruos (5 slots)
        JPanel filaMonstruos = new JPanel(new GridLayout(1, 5, 4, 0));
        filaMonstruos.setBackground(new Color(14, 16, 24));
        for (int i = 0; i < 5; i++) {
            filaMonstruos.add(crearSlotMonstruo());
        }
        zona.add(filaMonstruos);

        // Fila de slots de magia/trampa (5 slots)
        JPanel filaMagia = new JPanel(new GridLayout(1, 5, 4, 0));
        filaMagia.setBackground(new Color(14, 16, 24));
        for (int i = 0; i < 5; i++) {
            filaMagia.add(crearSlotMagia());
        }
        zona.add(filaMagia);

        return zona;
    }

    private JLabel crearSlotMonstruo() {
        JLabel slot = new JLabel("M", SwingConstants.CENTER);
        slot.setOpaque(true);
        slot.setBackground(new Color(14, 26, 14));
        slot.setForeground(new Color(30, 58, 30));
        slot.setFont(new Font("Serif", Font.BOLD, 11));
        slot.setBorder(BorderFactory.createLineBorder(new Color(30, 58, 30), 1));
        return slot;
    }

    private JLabel crearSlotMagia() {
        JLabel slot = new JLabel("S/T", SwingConstants.CENTER);
        slot.setOpaque(true);
        slot.setBackground(new Color(10, 14, 26));
        slot.setForeground(new Color(26, 30, 58));
        slot.setFont(new Font("SansSerif", Font.PLAIN, 9));
        slot.setBorder(BorderFactory.createLineBorder(new Color(26, 30, 58), 1));
        return slot;
    }

    
    //  FOOTER — botones y log
    
    private JPanel crearFooter() {
        JPanel footer = new JPanel(new BorderLayout());
        footer.setBackground(new Color(10, 12, 16));

        // Botones a la izquierda
        JPanel botones = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 8));
        botones.setBackground(new Color(10, 12, 16));

        btnJugarCarta    = new JButton("JUGAR CARTA");
        btnAtacar        = new JButton("ATACAR");
        btnTerminarTurno = new JButton("TERMINAR TURNO");

        estilizarBoton(btnJugarCarta,    new Color(201, 168, 76));
        estilizarBoton(btnAtacar,        new Color(224, 64,  64));
        estilizarBoton(btnTerminarTurno, new Color(122, 112, 96));

        btnJugarCarta.addActionListener(e    -> accionJugarCarta());
        btnAtacar.addActionListener(e        -> accionAtacar());
        btnTerminarTurno.addActionListener(e -> accionTerminarTurno());

        botones.add(btnJugarCarta);
        botones.add(btnAtacar);
        footer.add(botones, BorderLayout.WEST);

        // Log en el centro
        lblLog = new JLabel("¡El duelo comienza!");
        lblLog.setForeground(new Color(122, 112, 96));
        lblLog.setFont(new Font("SansSerif", Font.ITALIC, 11));
        lblLog.setHorizontalAlignment(SwingConstants.CENTER);
        footer.add(lblLog, BorderLayout.CENTER);

        // Terminar turno a la derecha
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
    }

    private void accionJugarCarta() {
        setLog("Jugando una carta...");
    }

    private void accionAtacar() {
        setLog("Iniciando fase de batalla...");
        // TODO: llamar juego.faseDeBatalla() y después actualizarInterfaz()
    }

    private void accionTerminarTurno() {
        setLog("Turno terminado.");
        // TODO: llamar juego.cambiarTurno() y después actualizarInterfaz()
    }

    //  ACTUALIZAR INTERFAZ — Se llama después de cada acción para refrescar la pantalla
    
    public void actualizarInterfaz() {
        // LP
        lblLJugador1.setText(String.valueOf(jugador1.getPuntosVida()));
        lblLJugador2.setText(String.valueOf(jugador2.getPuntosVida()));

        // Mazo
        if (jugador1.getMazo() != null)
            lblMazo1.setText(jugador1.getMazo().getCantidadCartas() + " cartas");
        if (jugador2.getMazo() != null)
            lblMazo2.setText(jugador2.getMazo().getCantidadCartas() + " cartas");

        // Mano
        if (jugador1.getMano() != null)
            lblMano1.setText(jugador1.getMano().size() + " cartas");
        if (jugador2.getMano() != null)
            lblMano2.setText(jugador2.getMano().size() + " cartas");

        // Campo
        lblMonstruos1.setText(jugador1.getCampo().getCantidadMonstruos() + " monstruos");
        lblMonstruos2.setText(jugador2.getCampo().getCantidadMonstruos() + " monstruos");

        // Refrescar la ventana
        revalidate();
        repaint();
    }

    public void setLog(String mensaje) {
        lblLog.setText(mensaje);
    }

    public void setTurno(int numero) {
        lblTurno.setText("TURNO " + numero + "  ");
    }

    
    //  MAIN DE PRUEBA
    
    public static void main(String[] args) {
        java.util.List<Carta> mano1 = new java.util.ArrayList<>();
        java.util.List<Carta> mano2 = new java.util.ArrayList<>();

        Jugador j1 = new Jugador("Jugador 1", 8000, mano1, null, new Campo());
        Jugador j2 = new Jugador("Jugador 2", 8000, mano2, null, new Campo());

        InicializadorMazo.repartirCartas(j1, j2);

        for (int i = 0; i < 5; i++) {
            j1.getMano().add(j1.getMazo().robarCarta());
            j2.getMano().add(j2.getMazo().robarCarta());
        }

        new VentanaDuelo(j1, j2);
    }
}