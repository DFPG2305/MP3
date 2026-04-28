import java.util.List;
import java.util.Random;

public class Juego {
        
    private Jugador jugador1;
    private Jugador jugador2;
    private Jugador jugadorActual;
    private Jugador oponente;

    private boolean cartaJugadasEnTurno = false;
    private boolean ataqueRealizado = false;


    public Jugador getJugadorActual() {
        return jugadorActual;
    }

    public Jugador getOponente() {
        return oponente;
    }

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

        jugadorActual.setPrimerTurno(true);
        oponente.setPrimerTurno(false);
    }

    public void jugarCartaDesdeMano(int index) {
        if (cartaJugadasEnTurno) {
            //System.out.println("Ya jugaste una carta este turno");
            return;
        }

        List<Carta> mano = jugadorActual.getMano();

        if (mano.isEmpty()) {
            //System.out.println("No tienes cartas.");
            return;
        }

        if (index < 0 || index >= mano.size()) {
            //System.out.println("Índice inválido.");
            return;
        }

        Carta carta = mano.get(index);

        if (carta instanceof Monstruo) {
            jugadorActual.getCampo().colocarMonstruo((Monstruo) carta);
        } else if (carta instanceof CartaMagica) {
            ((CartaMagica) carta).activar(jugadorActual, oponente);
        }

        mano.remove(index);
        cartaJugadasEnTurno = true;
    }

    public String atacar(int indexAtacante, int indexDefensor) {

        if (ataqueRealizado) {
            return "Ya atacaste este turno";
        }

        if (jugadorActual.isPrimerTurno()) {
            return "No puedes atacar en el primer turno";
        }

        if (jugadorActual.getCampo().getCantidadMonstruos() == 0) {
            return "No tienes monstruos para atacar";
        }

        Monstruo atacante = jugadorActual.getCampo().getMonstruos().get(indexAtacante);

        // Ataque directo
        if (oponente.getCampo().getCantidadMonstruos() == 0) {
            oponente.setPuntosVida(oponente.getPuntosVida() - atacante.getAtk());
            ataqueRealizado = true;
            return "Ataque directo de " + atacante.getNombre() + " por " + atacante.getAtk();
        }

        Monstruo defensor = oponente.getCampo().getMonstruos().get(indexDefensor);

        if (atacante.getAtk() > defensor.getAtk()) {
            int daño = atacante.getAtk() - defensor.getAtk();
            oponente.setPuntosVida(oponente.getPuntosVida() - daño);
            oponente.getCampo().quitarMonstruo(defensor);
            ataqueRealizado = true;
            return "Destruiste " + defensor.getNombre() + " y causaste " + daño + " de daño";

        } else if (atacante.getAtk() < defensor.getAtk()) {
            int daño = defensor.getAtk() - atacante.getAtk();
            jugadorActual.setPuntosVida(jugadorActual.getPuntosVida() - daño);
            jugadorActual.getCampo().quitarMonstruo(atacante);
            ataqueRealizado = true;
            return "Tu monstruo fue destruido. Recibes " + daño + " de daño";

        } else {
            jugadorActual.getCampo().quitarMonstruo(atacante);
            oponente.getCampo().quitarMonstruo(defensor);
            ataqueRealizado = true;
            return "Ambos monstruos fueron destruidos";
        }
    }
    
    public void cambiarTurno() {
        Jugador temp = jugadorActual;
        jugadorActual = oponente;
        oponente = temp;
        jugadorActual.setPrimerTurno(false);

        cartaJugadasEnTurno = false;
        ataqueRealizado = false;
    }
    

}