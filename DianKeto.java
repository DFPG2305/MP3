public class DianKeto extends CartaMagica {

    public DianKeto() {
        super("Dian Keto");
    }

    @Override
    public void activar(Jugador jugadorActivo, Jugador jugadorRival) {
        jugadorActivo.setPuntosVida(jugadorActivo.getPuntosVida() + 1000);
        System.out.println("Dian Keto activado! Recuperas 1000 LP.");
    }
}