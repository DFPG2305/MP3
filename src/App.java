import java.util.ArrayList;

public class App {
    public static void main(String[] args) {
        Jugador j1 = new Jugador("Jugador 1", 8000, new ArrayList<>(), null, new Campo());
        Jugador j2 = new Jugador("Jugador 2", 8000, new ArrayList<>(), null, new Campo());
        InicializadorMazo.repartirCartas(j1, j2);
        // Repartir mano inicial
        for (int i = 0; i < 5; i++) {
            j1.getMano().add(j1.getMazo().robarCarta());
            j2.getMano().add(j2.getMazo().robarCarta());
        }
        VentanaDuelo ventana = new VentanaDuelo(j1, j2);
        Juego juego = new Juego(j1, j2);
        new ControladorJuego(juego, ventana);
    }
}