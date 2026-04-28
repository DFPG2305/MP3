import java.util.List;
import javax.swing.JOptionPane;

public class ControladorJuego {

    private Juego juego;
    private VentanaDuelo ventana;

    public ControladorJuego(Juego juego, VentanaDuelo ventana) {
        this.juego = juego;
        this.ventana = ventana;

        ventana.getBtnJugarCarta().addActionListener(e -> {
            jugarCartaGUI();
        });

        ventana.getBtnAtacar().addActionListener(e -> {
            atacarGUI();
        });

        ventana.getBtnTerminarTurno().addActionListener(e -> {
            terminarTurnoGUI();
        });
    }

    private void jugarCartaGUI() {
        List<Carta> mano = juego.getJugadorActual().getMano();

        if (mano.isEmpty()) {
            ventana.setLog("No tienes cartas.");
            return;
        }

        String[] opciones = new String[mano.size()];

        for (int i = 0; i < mano.size(); i++) {
            opciones[i] = mano.get(i).getNombre();
        }

        int seleccion = JOptionPane.showOptionDialog(
                ventana,
                "Elige una carta",
                "Jugar carta",
                JOptionPane.DEFAULT_OPTION,
                JOptionPane.INFORMATION_MESSAGE,
                null,
                opciones,
                opciones[0]
        );

        if (seleccion != -1) {
            juego.jugarCartaDesdeMano(seleccion);
            ventana.actualizarInterfaz();
            ventana.setLog("Carta jugada: " + opciones[seleccion]);
        }
    }

    private void atacarGUI() {
        //Esto para pruebas, lo voy a dejar por ahora cuando terminen las pruebas lo pueden quitar(Aunque no afecta nada solo es consola)
        System.out.println("\n===== DEBUG ATAQUE =====");
        System.out.println("Jugador actual: " + juego.getJugadorActual().getNombre());
        System.out.println("Oponente: " + juego.getOponente().getNombre());

        Jugador actual = juego.getJugadorActual();
        Jugador rival = juego.getOponente();

        // DEBUG CAMPOS --Prueba
        System.out.println("Monstruos actual: " + actual.getCampo().getMonstruos().size());
        System.out.println("Monstruos rival: " + rival.getCampo().getMonstruos().size());

        if (actual.getCampo().getMonstruos().isEmpty()) {
            ventana.setLog("No tienes monstruos para atacar");
            return;
        }

        String[] atacantes = actual.getCampo().getMonstruos()
                .stream()
                .map(m -> m.getNombre() + " ATK:" + m.getAtk())
                .toArray(String[]::new);

        int indexAtacante = JOptionPane.showOptionDialog(
                ventana,
                "Elige tu monstruo atacante",
                "Atacar",
                JOptionPane.DEFAULT_OPTION,
                JOptionPane.INFORMATION_MESSAGE,
                null,
                atacantes,
                atacantes[0]
        );

        if (indexAtacante == -1) return;

        // DEBUG ATK -- Prueba
        Monstruo atacante = actual.getCampo().getMonstruos().get(indexAtacante);
        System.out.println("ATACANTE ELEGIDO: " + atacante.getNombre());

        if (rival.getCampo().getMonstruos().isEmpty()) {

            System.out.println("ATAQUE DIRECTO");

            String resultado = juego.atacar(indexAtacante, 0);
            ventana.setLog(resultado);
            ventana.actualizarInterfaz();

            verificarGanador(resultado);
            return;
        }

        String[] defensores = rival.getCampo().getMonstruos()
                .stream()
                .map(m -> m.getNombre() + " ATK:" + m.getAtk())
                .toArray(String[]::new);

        int indexDefensor = JOptionPane.showOptionDialog(
                ventana,
                "Elige el monstruo enemigo",
                "Defensor",
                JOptionPane.DEFAULT_OPTION,
                JOptionPane.INFORMATION_MESSAGE,
                null,
                defensores,
                defensores[0]
        );

        if (indexDefensor == -1) return;

        // DEBUG DEF -- Prueba
        Monstruo defensor = rival.getCampo().getMonstruos().get(indexDefensor);
        System.out.println("DEFENSOR ELEGIDO: " + defensor.getNombre());

        // VALIDACIÓN 
        if (actual == rival) {
            System.out.println("ERROR: actual y rival son el mismo objeto");
            ventana.setLog("Error interno: jugador incorrecto");
            return;
        }

        String resultado = juego.atacar(indexAtacante, indexDefensor);

        ventana.setLog(resultado);
        ventana.actualizarInterfaz();

        verificarGanador(resultado);
    }

    private void terminarTurnoGUI() {

        juego.cambiarTurno();

        Jugador actual = juego.getJugadorActual();
        //Consola
        System.out.println("\n===== CAMBIO DE TURNO =====");
        System.out.println("Nuevo jugador actual: " + actual.getNombre());

        Carta robada = actual.getMazo().robarCarta();

        if (robada != null) {
            actual.getMano().add(robada);
            ventana.setLog("Turno de " + actual.getNombre() + " | Roba: " + robada.getNombre());
        } else {
            ventana.setLog(actual.getNombre() + " no puede robar. Pierde.");
            return;
        }

        ventana.actualizarInterfaz();
    }

    private void verificarGanador(String resultado) {
        if (juego.getOponente().getPuntosVida() <= 0) {
            ventana.setLog(resultado + " | Gana " + juego.getJugadorActual().getNombre());

            ventana.getBtnAtacar().setEnabled(false);
            ventana.getBtnJugarCarta().setEnabled(false);
            ventana.getBtnTerminarTurno().setEnabled(false);

            System.out.println("===== FIN DEL JUEGO =====");//Tambien consla
        }
    }
}