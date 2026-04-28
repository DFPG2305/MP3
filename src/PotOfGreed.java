public class PotOfGreed extends CartaMagica {

    public PotOfGreed() {
        super("Pot of Greed");
    }

    @Override
    public void activar(Jugador jugadorActivo, Jugador jugadorRival) {
        for (int i = 0; i < 2; i++) {
            Carta carta = jugadorActivo.getMazo().robarCarta();
            if (carta != null) jugadorActivo.getMano().add(carta);
        }
    }
}
