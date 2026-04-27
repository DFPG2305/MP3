public class TrapHole extends CartaTrampa {

    public TrapHole() {
        super("Trap Hole");
    }

    // Se activa cuando el oponente invoca un monstruo con ATK mayor a 1000
    // Destruye el ultimo monstruo que el oponente puso en el campo
    @Override
    public void activar(Jugador jugadorActivo, Jugador jugadorRival) {
        if (jugadorRival.getCampo().getCantidadMonstruos() == 0) {
            System.out.println("Trap Hole no tiene objetivo. El oponente no tiene monstruos.");
            return;
        }

        // Obtiene el ultimo monstruo invocado (el ultimo de la lista)
        int ultimoIndex = jugadorRival.getCampo().getMonstruos().size() - 1;
        Monstruo objetivo = jugadorRival.getCampo().getMonstruos().get(ultimoIndex);

        if (objetivo.getAtk() > 1000) {
            jugadorRival.getCampo().quitarMonstruo(objetivo);
            System.out.println("Trap Hole activado! " + objetivo.getNombre() + " fue destruido.");
        } else {
            System.out.println("Trap Hole no puede destruir a " + objetivo.getNombre() + " (ATK menor o igual a 1000).");
        }
    }
}
