public class MirrorForce extends CartaTrampa {

    public MirrorForce() {
        super("Mirror Force");
    }

    // Se activa cuando el oponente ataca: destruye todos sus monstruos del campo
    @Override
    public void activar(Jugador jugadorActivo, Jugador jugadorRival) {
        System.out.println("Mirror Force activado! Todos los monstruos de " + jugadorRival.getNombre() + " son destruidos.");
        jugadorRival.getCampo().getMonstruos().clear();
    }
}
