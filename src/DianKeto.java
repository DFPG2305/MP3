public class DianKeto extends CartaMagica {

    public DianKeto() {
        super("Dian Keto");
    }

    @Override
    public void activar(Jugador jugadorActivo, Jugador jugadorRival) {
        jugadorActivo.setPuntosVida(jugadorActivo.getPuntosVida() + 1000);
    }

    @Override
    public String getDescripcion() {
        return "Aumenta tus puntos de vida en 1000.";
    }

}
