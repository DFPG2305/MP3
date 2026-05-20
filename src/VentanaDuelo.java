import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.util.List;

/**
 * VISTA del duelo — Con integración de renderizado dinámico de cartas en mano,
 * slots de campo funcionales y compatibilidad directa con ControladorJuego.
 */
public class VentanaDuelo extends JFrame {

    // Colores de la paleta (Estilo campo de duelo oscuro)
    private static final Color C_BG_DARK   = new Color( 10,  12,  16);
    private static final Color C_BG_PANEL  = new Color( 18,  22,  30);
    private static final Color C_BG_CAMPO  = new Color( 14,  16,  24);
    private static final Color C_DORADO    = new Color(201, 168,  76);
    private static final Color C_GRIS      = new Color(122, 112,  96);
    private static final Color C_VERDE_LP  = new Color( 77, 232, 122);
    private static final Color C_LILA      = new Color(180, 140, 220);
    private static final Color C_TEXTO     = new Color(232, 224, 204);

    // Labels de estado / estadísticas laterales
    private JLabel lblLPJ1, lblLPJ2;
    private JLabel lblMazo1, lblMazo2;
    private JLabel lblMano1, lblMano2;
    private JLabel lblMonstruos1, lblMonstruos2;
    private JLabel lblTrampa1, lblTrampa2;
    private JLabel lblTurno, lblLog;

    // Botones de acción del footer
    private JButton btnJugarCarta;
    private JButton btnAtacar;
    private JButton btnActivarTrampa;
    private JButton btnTerminarTurno;

    // Paneles dinámicos de las manos
    private JPanel panelManoJ1;
    private JPanel panelManoJ2;

    // Paneles dinámicos de las zonas del tablero
    private JPanel panelCampoMonstruosJ1;
    private JPanel panelCampoMonstruosJ2;
    private JPanel panelCampoMagiaJ1;
    private JPanel panelCampoMagiaJ2;

    // Datos del modelo de juego 
    private final Jugador jugador1;
    private final Jugador jugador2;

    // Constructor de la ventana
    public VentanaDuelo(Jugador j1, Jugador j2) {
        this.jugador1 = j1;
        this.jugador2 = j2;

        setTitle("Yu-Gi-Oh! - Campo de Duelo");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());
        getContentPane().setBackground(C_BG_DARK);

        // Ensamblaje de la interfaz con la distribución oficial
        add(crearHeader(),  BorderLayout.NORTH);
        add(crearCentro(),  BorderLayout.CENTER);
        add(crearFooter(),  BorderLayout.SOUTH);

        // Sincronización inicial y configuración de dimensiones
        actualizarInterfaz();
        pack();
        setMinimumSize(new Dimension(1100, 720));
        setLocationRelativeTo(null);
        setVisible(true);
    }

    // --- GETTERS REQUERIDOS POR TU CONTROLADORJUEGO ---
    public JButton getBtnJugarCarta()    { return btnJugarCarta; }
    public JButton getBtnAtacar()        { return btnAtacar; }
    public JButton getBtnActivarTrampa() { return btnActivarTrampa; }
    public JButton getBtnTerminarTurno() { return btnTerminarTurno; }

    /**
     * Sincroniza dinámicamente toda la información del modelo con la vista.
     * Incluye validaciones de seguridad para evitar NullPointerException si el campo no está listo.
     */
    public void actualizarInterfaz() {
        if (jugador1 == null || jugador2 == null) return;

        // Actualización de Puntos de Vida (LP)
        lblLPJ1.setText(String.valueOf(jugador1.getPuntosVida()));
        lblLPJ2.setText(String.valueOf(jugador2.getPuntosVida()));

        // Actualización de Mazos y Manos en los paneles laterales
        lblMazo1.setText(cantMazo(jugador1));
        lblMazo2.setText(cantMazo(jugador2));
        lblMano1.setText(cantMano(jugador1));
        lblMano2.setText(cantMano(jugador2));

        // Actualización de estados del campo en paneles laterales (Protegido contra nulos)
        lblMonstruos1.setText(jugador1.getCampo() != null ? cantMonstruos(jugador1) : "0 monstruos");
        lblMonstruos2.setText(jugador2.getCampo() != null ? cantMonstruos(jugador2) : "0 monstruos");
        lblTrampa1.setText(jugador1.getCampo() != null ? cantTrampas(jugador1) : "0 trampas");
        lblTrampa2.setText(jugador2.getCampo() != null ? cantTrampas(jugador2) : "0 trampas");

        // Reconstrucción visual de las cartas en mano de ambos duelistas
        reconstruirMano(panelManoJ1, jugador1);
        reconstruirMano(panelManoJ2, jugador2);

        // Reconstrucción visual del tablero de juego (Monstruos y Magias/Trampas)
        if (jugador1.getCampo() != null) {
            reconstruirCampoMonstruos(panelCampoMonstruosJ1, jugador1);
            reconstruirCampoMagia(panelCampoMagiaJ1, jugador1);
        }
        if (jugador2.getCampo() != null) {
            reconstruirCampoMonstruos(panelCampoMonstruosJ2, jugador2);
            reconstruirCampoMagia(panelCampoMagiaJ2, jugador2);
        }

        // Forzar redibujado de la interfaz gráfica
        revalidate();
        repaint();
    }

    public void setLog(String mensaje)  { lblLog.setText(mensaje); }
    public void setTurno(int numero)    { lblTurno.setText("TURNO " + numero + "  "); }

    public void deshabilitarAcciones() {
        btnJugarCarta   .setEnabled(false);
        btnAtacar       .setEnabled(false);
        btnActivarTrampa.setEnabled(false);
        btnTerminarTurno.setEnabled(false);
    }

    // --- MÉTODOS DE CONSTRUCCIÓN DE LA INTERFAZ GRÁFICA ---

    private JPanel crearHeader() {
        JPanel h = new JPanel(new BorderLayout());
        h.setBackground(Color.BLACK);
        h.setBorder(new EmptyBorder(4, 8, 4, 8));

        JLabel titulo = new JLabel("  \u2666 YU-GI-OH!  CAMPO DE DUELO \u2666");
        titulo.setForeground(C_DORADO);
        titulo.setFont(new Font("Serif", Font.BOLD, 17));
        h.add(titulo, BorderLayout.WEST);

        lblTurno = new JLabel("TURNO 1  ");
        lblTurno.setForeground(C_DORADO);
        lblTurno.setFont(new Font("Serif", Font.BOLD, 14));
        h.add(lblTurno, BorderLayout.EAST);
        return h;
    }

    private JPanel crearCentro() {
        JPanel centro = new JPanel(new BorderLayout(6, 6));
        centro.setBackground(C_BG_DARK);
        centro.setBorder(new EmptyBorder(4, 4, 4, 4));

        // Paneles de estadísticas laterales (Jugador 1 Izquierda, Jugador 2 Derecha)
        centro.add(crearPanelStats(jugador1, true),  BorderLayout.WEST);
        centro.add(crearPanelStats(jugador2, false), BorderLayout.EAST);
        centro.add(crearAreaCentral(),               BorderLayout.CENTER);
        return centro;
    }

    private JPanel crearPanelStats(Jugador j, boolean esJ1) {
        JPanel p = new JPanel(new GridLayout(6, 1, 0, 2));
        p.setBackground(C_BG_PANEL);
        p.setPreferredSize(new Dimension(130, 0));
        p.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(40, 50, 70), 1),
                new EmptyBorder(4, 6, 4, 6)));

        JLabel nombre = new JLabel(j.getNombre(), SwingConstants.CENTER);
        nombre.setForeground(C_DORADO);
        nombre.setFont(new Font("Serif", Font.BOLD, 13));
        nombre.setOpaque(true);
        nombre.setBackground(C_BG_DARK);
        p.add(nombre);

        // Inyección automática de referencias a las etiquetas dinámicas de estado
        p.add(crearFilaStat("LP:",      String.valueOf(j.getPuntosVida()), C_VERDE_LP, esJ1, "lp"));
        p.add(crearFilaStat("Mazo:",    cantMazo(j),     C_TEXTO, esJ1, "mazo"));
        p.add(crearFilaStat("Mano:",    cantMano(j),     C_TEXTO, esJ1, "mano"));
        p.add(crearFilaStat("Campo:",   cantMonstruos(j),C_TEXTO, esJ1, "monstruos"));
        p.add(crearFilaStat("Trampas:", cantTrampas(j),  C_LILA,  esJ1, "trampa"));
        return p;
    }

    private JPanel crearFilaStat(String etiq, String val, Color cVal, boolean esJ1, String tipo) {
        JPanel f = new JPanel(new FlowLayout(FlowLayout.CENTER, 4, 2));
        f.setBackground(C_BG_PANEL);
        JLabel e = new JLabel(etiq); e.setForeground(C_GRIS); e.setFont(new Font("SansSerif", Font.PLAIN, 11));
        JLabel v = new JLabel(val);  v.setForeground(cVal);   v.setFont(new Font("Serif", Font.BOLD, 13));
        f.add(e); f.add(v);

        if (esJ1) {
            switch (tipo) {
                case "lp":        lblLPJ1       = v; break;
                case "mazo":      lblMazo1      = v; break;
                case "mano":      lblMano1      = v; break;
                case "monstruos": lblMonstruos1 = v; break;
                case "trampa":    lblTrampa1    = v; break;
            }
        } else {
            switch (tipo) {
                case "lp":        lblLPJ2       = v; break;
                case "mazo":      lblMazo2      = v; break;
                case "mano":      lblMano2      = v; break;
                case "monstruos": lblMonstruos2 = v; break;
                case "trampa":    lblTrampa2    = v; break;
            }
        }
        return f;
    }

    private JPanel crearAreaCentral() {
        JPanel area = new JPanel(new GridLayout(7, 1, 0, 4));
        area.setBackground(C_BG_CAMPO);
        area.setBorder(new EmptyBorder(4, 6, 4, 6));

        // Inicialización y asignación de contenedores del tablero
        panelManoJ2             = crearContenedorMano("Mano de " + jugador2.getNombre());
        panelCampoMonstruosJ2   = crearFilaCampo(new Color(14, 30, 14));
        panelCampoMagiaJ2       = crearFilaCampo(new Color(10, 14, 30));

        JLabel vs = new JLabel("\u2500\u2500\u2500 VS \u2500\u2500\u2500", SwingConstants.CENTER);
        vs.setForeground(C_GRIS);
        vs.setFont(new Font("Serif", Font.ITALIC, 12));
        vs.setOpaque(true);
        vs.setBackground(C_BG_DARK);

        panelCampoMagiaJ1       = crearFilaCampo(new Color(10, 14, 30));
        panelCampoMonstruosJ1   = crearFilaCampo(new Color(14, 30, 14));
        panelManoJ1             = crearContenedorMano("Mano de " + jugador1.getNombre());

        // Distribución vertical en el tablero (Espejo: J2 arriba, J1 abajo)
        area.add(panelManoJ2);
        area.add(panelCampoMonstruosJ2);
        area.add(panelCampoMagiaJ2);
        area.add(vs);
        area.add(panelCampoMagiaJ1);
        area.add(panelCampoMonstruosJ1);
        area.add(panelManoJ1);

        return area;
    }

    private JPanel crearContenedorMano(String titulo) {
        JPanel contenedor = new JPanel(new BorderLayout());
        contenedor.setBackground(new Color(12, 14, 22));
        contenedor.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(40, 50, 70), 1),
                new EmptyBorder(2, 4, 2, 4)));

        JLabel lbl = new JLabel(titulo, SwingConstants.LEFT);
        lbl.setForeground(C_GRIS);
        lbl.setFont(new Font("SansSerif", Font.PLAIN, 9));
        contenedor.add(lbl, BorderLayout.NORTH);

        JPanel cartas = new JPanel(new WrapLayout(FlowLayout.LEFT, 4, 2));
        cartas.setOpaque(false);
        contenedor.add(cartas, BorderLayout.CENTER);
        return contenedor;
    }

    private JPanel crearFilaCampo(Color bg) {
        JPanel p = new JPanel(new FlowLayout(FlowLayout.CENTER, 6, 2));
        p.setBackground(bg);
        p.setBorder(BorderFactory.createLineBorder(new Color(30, 40, 60), 1));
        return p;
    }

    // --- LOGICA DE RECONSTRUCCIÓN DINÁMICA DE COMPONENTES ---

    private void reconstruirMano(JPanel contenedor, Jugador jugador) {
        JPanel panelCartas = (JPanel) ((BorderLayout) contenedor.getLayout())
                .getLayoutComponent(BorderLayout.CENTER);
        panelCartas.removeAll();
        if (jugador.getMano() != null) {
            for (Carta c : jugador.getMano()) {
                panelCartas.add(new CartaVisual(c));
            }
        }
        panelCartas.revalidate();
        panelCartas.repaint();
    }

    private void reconstruirCampoMonstruos(JPanel fila, Jugador jugador) {
        fila.removeAll();
        if (jugador.getCampo() != null) {
            List<Monstruo> lista = jugador.getCampo().getMonstruos();
            for (Monstruo m : lista) {
                fila.add(new SlotCampoVisual(m, m.isEnPosicionAtaque()));
            }
            // Rellenar slots vacíos hasta completar los 5 reglamentarios de Monstruos (M)
            for (int i = lista.size(); i < 5; i++) {
                fila.add(crearSlotVacio("M"));
            }
        }
        fila.revalidate();
        fila.repaint();
    }

    private void reconstruirCampoMagia(JPanel fila, Jugador jugador) {
        fila.removeAll();
        if (jugador.getCampo() != null) {
            List<CartaMagica> magias  = jugador.getCampo().getCartasMagicas();
            List<CartaTrampa> trampas = jugador.getCampo().getCartasTrampa();
            
            for (CartaMagica m : magias)  fila.add(new SlotCampoVisual(m, true));
            for (CartaTrampa t : trampas) fila.add(new SlotCampoVisual(t, true));
            
            int n = magias.size() + trampas.size();
            // Rellenar slots vacíos hasta completar los 5 reglamentarios de Hechizos/Trampas (S/T)
            for (int i = n; i < 5; i++) {
                fila.add(crearSlotVacio("S/T"));
            }
        }
        fila.revalidate();
        fila.repaint();
    }

    private JLabel crearSlotVacio(String texto) {
        JLabel slot = new JLabel(texto, SwingConstants.CENTER);
        slot.setPreferredSize(new Dimension(
                ImagenCartaCache.ANCHO_CAMPO + 4,
                ImagenCartaCache.ALTO_CAMPO  + 4));
        slot.setOpaque(true);
        
        Color bg  = texto.equals("M") ? new Color(14, 30, 14) : new Color(10, 14, 30);
        Color brd = texto.equals("M") ? new Color(30, 58, 30) : new Color(26, 30, 58);
        
        slot.setBackground(bg);
        slot.setForeground(new Color(brd.getRed(), brd.getGreen(), brd.getBlue(), 180));
        slot.setFont(new Font("SansSerif", Font.BOLD, 10));
        slot.setBorder(BorderFactory.createLineBorder(brd, 1));
        return slot;
    }

    private JPanel crearFooter() {
        JPanel footer = new JPanel(new BorderLayout());
        footer.setBackground(C_BG_DARK);
        footer.setBorder(new EmptyBorder(4, 8, 6, 8));

        JPanel botones = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 4));
        botones.setBackground(C_BG_DARK);

        btnJugarCarta    = new JButton("JUGAR CARTA");
        btnAtacar        = new JButton("ATACAR");
        btnActivarTrampa = new JButton("ACTIVAR TRAMPA");
        btnTerminarTurno = new JButton("TERMINAR TURNO");

        estilizarBoton(btnJugarCarta,    C_DORADO);
        estilizarBoton(btnAtacar,        new Color(224,  64,  64));
        estilizarBoton(btnActivarTrampa, new Color(160,  80, 200));
        estilizarBoton(btnTerminarTurno, C_GRIS);

        botones.add(btnJugarCarta);
        botones.add(btnAtacar);
        botones.add(btnActivarTrampa);
        footer.add(botones, BorderLayout.WEST);

        lblLog = new JLabel("\u00a1El duelo comienza!");
        lblLog.setForeground(C_GRIS);
        lblLog.setFont(new Font("SansSerif", Font.ITALIC, 11));
        lblLog.setHorizontalAlignment(SwingConstants.CENTER);
        footer.add(lblLog, BorderLayout.CENTER);

        JPanel der = new JPanel(new FlowLayout(FlowLayout.RIGHT, 8, 4));
        der.setBackground(C_BG_DARK);
        der.add(btnTerminarTurno);
        footer.add(der, BorderLayout.EAST);

        return footer;
    }

    private void estilizarBoton(JButton btn, Color color) {
        btn.setForeground(color);
        btn.setBackground(C_BG_DARK);
        btn.setFont(new Font("SansSerif", Font.BOLD, 10));
        btn.setFocusPainted(false);
        btn.setBorder(BorderFactory.createLineBorder(color, 1));
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
    }

    // --- MÉTODOS HELPERS / DE CONTROL CON VALIDACIÓN ---

    private String cantMazo(Jugador j) { 
        return j.getMazo() != null ? j.getMazo().getCantidadCartas() + " cartas" : "0 cartas"; 
    }
    private String cantMano(Jugador j) { 
        return j.getMano() != null ? j.getMano().size() + " cartas" : "0 cartas"; 
    }
    private String cantMonstruos(Jugador j) { 
        return (j.getCampo() != null) ? j.getCampo().getCantidadMonstruos() + " monstruos" : "0 monstruos"; 
    }
    private String cantTrampas(Jugador j) { 
        return (j.getCampo() != null) ? j.getCampo().getCartasTrampa().size() + " trampas" : "0 trampas"; 
    }
}