public class MirrorForce extends CartaTrampa {

    public MirrorForce() {
        super("Mirror Force");
    }

    @Override
    public void activar(Jugador jugadorActivo, Jugador jugadorRival) {
        jugadorRival.getCampo().getMonstruos().clear();
    }
}
