import java.util.List;
import java.util.Random;
import java.util.Scanner;

public class Juego {
        
    private Jugador jugador1;
    private Jugador jugador2;
    private Jugador jugadorActual;
    private Jugador oponente;

    private boolean cartaJugadasEnTurno = false;
    private boolean ataqueRealizado = false;

    private Scanner scanner = new Scanner(System.in);

    public Juego(Jugador j1, Jugador j2) {
        this.jugador1 = j1;
        this.jugador2 = j2;

        if (new Random().nextBoolean()) {
            jugadorActual = jugador1;
            oponente = jugador2;
        } else {
            jugadorActual = jugador2;
            oponente = jugador1;
        }
    }

    public void iniciar() {
        System.out.println("Duelo entre " + jugador1.getNombre() + " y " + jugador2.getNombre());

        inicializarManos();

        while (true) {
            turno();

            if (oponente.getPuntosVida() <= 0) {
                System.out.println("Gana " + jugadorActual.getNombre());
                break;
            }

            cambiarTurno();
        }
    }

    private void inicializarManos() {
        for (int i = 0; i < 5; i++) {
            jugador1.getMano().add(jugador1.getMazo().robarCarta());
            jugador2.getMano().add(jugador2.getMazo().robarCarta());
        }
    }

    private void turno() {
        System.out.println("\nTurno de: " + jugadorActual.getNombre());

        Carta robada = jugadorActual.getMazo().robarCarta();
        if (robada == null) {
            System.out.println(jugadorActual.getNombre() + " no puede robar. Pierde");
            System.exit(0);
        }
        jugadorActual.getMano().add(robada);

        cartaJugadasEnTurno = false;
        ataqueRealizado = false;

        boolean turnoActivo = true;

        while (turnoActivo) {
            mostrarEstado();

            System.out.println("\n¿Qué deseas hacer?");
            System.out.println("1. Jugar carta");
            System.out.println("2. Atacar");
            System.out.println("3. Terminar turno");

            int opcion = scanner.nextInt();

            switch (opcion) {

                case 1:
                    if (!cartaJugadasEnTurno) {
                        jugarCarta();
                        cartaJugadasEnTurno = true;
                    } else {
                        System.out.println("Ya jugaste una carta este turno");
                    }
                    break;

                case 2:
                    if (jugadorActual.isPrimerTurno()) {
                        System.out.println("No puedes atacar en tu primer turno");
                    } else if (ataqueRealizado) {
                        System.out.println("Ya realizaste un ataque en este turno");
                    } else {
                        faseDeBatalla();
                        ataqueRealizado = true;
                    }
                    break;

                case 3:
                    turnoActivo = false;
                    break;

                default:
                    System.out.println("Opción inválida");
            }

            if (oponente.getPuntosVida() <= 0) {
                System.out.println("Gana " + jugadorActual.getNombre());
                System.exit(0);
            }
        }

        jugadorActual.setPrimerTurno(false);
    }

    private void jugarCarta() {
        System.out.println("Selecciona una carta para jugar:");

        List<Carta> mano = jugadorActual.getMano();
        if (mano.isEmpty()) { //Para evitar un error, por si hay una mano vacia
            System.out.println("No tienes cartas para jugar.");
            return;
        }

        for (int i = 0; i < mano.size(); i++) {
            System.out.println((i + 1) + ". " + mano.get(i).getNombre());
        }

        int opcion = scanner.nextInt() - 1;

        if (opcion < 0 || opcion >= mano.size()) {
            System.out.println("Selección inválida.");
            return;
        }

        Carta carta = mano.get(opcion);

        if (carta instanceof Monstruo) {
            jugadorActual.getCampo().colocarMonstruo((Monstruo) carta);
        } else if (carta instanceof CartaMagica) {
            ((CartaMagica) carta).activar(jugadorActual, oponente);
        }

        mano.remove(opcion);
    }

    private void faseDeBatalla() {

        if (jugadorActual.getCampo().getCantidadMonstruos() == 0) {
            System.out.println("No tienes monstruos para atacar.");
            return;
        }

        System.out.println("\n=== FASE DE BATALLA ===");

        System.out.println("Elige tu monstruo para atacar:");
        mostrarMonstruos(jugadorActual);
        int indexAtacante = scanner.nextInt();
        //Otra verifcacion de errores
        if (indexAtacante < 0 || indexAtacante >= jugadorActual.getCampo().getMonstruos().size()) {
            System.out.println("Selección inválida.");
            return;
        }

        Monstruo atacante = jugadorActual.getCampo().getMonstruos().get(indexAtacante);
        if (oponente.getCampo().getCantidadMonstruos() == 0) {
            System.out.println("¡Ataque directo!");
            oponente.setPuntosVida(oponente.getPuntosVida() - atacante.getAtk());
            return;
        }

        System.out.println("Elige monstruo enemigo:");
        mostrarMonstruos(oponente);
        int indexDefensor = scanner.nextInt();
        //Otra igual que arriba
        if (indexDefensor < 0 || indexDefensor >= oponente.getCampo().getMonstruos().size()) {
            System.out.println("Selección inválida.");
            return;
        }

        Monstruo defensor = oponente.getCampo().getMonstruos().get(indexDefensor);
        if (atacante.getAtk() > defensor.getAtk()) {
            int daño = atacante.getAtk() - defensor.getAtk();
            oponente.setPuntosVida(oponente.getPuntosVida() - daño);
            oponente.getCampo().quitarMonstruo(defensor);

            System.out.println("Destruiste el monstruo enemigo. Daño: " + daño);

        } else if (atacante.getAtk() < defensor.getAtk()) {
            int daño = defensor.getAtk() - atacante.getAtk();
            jugadorActual.setPuntosVida(jugadorActual.getPuntosVida() - daño);
            jugadorActual.getCampo().quitarMonstruo(atacante);

            System.out.println("Tu monstruo fue destruido. Recibes: " + daño);

        } else {
            jugadorActual.getCampo().quitarMonstruo(atacante);
            oponente.getCampo().quitarMonstruo(defensor);

            System.out.println("Ambos monstruos fueron destruidos.");
        }
    }

    private void mostrarMonstruos(Jugador jugador) {
        List<Monstruo> monstruos = jugador.getCampo().getMonstruos();

        for (int i = 0; i < monstruos.size(); i++) {
            Monstruo m = monstruos.get(i);
            System.out.println(i + ": " + m.getNombre() + " ATK:" + m.getAtk());
        }
    }

    private void cambiarTurno() {
        Jugador temp = jugadorActual;
        jugadorActual = oponente;
        oponente = temp;
    }

    private void mostrarEstado() {
        System.out.println("\nLP " + jugadorActual.getNombre() + ": " + jugadorActual.getPuntosVida());
        System.out.println("LP " + oponente.getNombre() + ": " + oponente.getPuntosVida());

        System.out.println("Monstruos en campo: " + jugadorActual.getCampo().getCantidadMonstruos());
    }
}