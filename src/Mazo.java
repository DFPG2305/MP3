import java.util.ArrayList;
import java.util.List;

//Voy a poner comentarios para que Perea se guie, luego los borran

public class Mazo {
    private List<Carta> cartas;

    public Mazo() {
        this.cartas = new ArrayList<>();
    }

    // Con esta funcion se agregan cartas al mazo
    public void agregarCarta(Carta carta) {
        cartas.add(carta);
    }

    // Con esta se roba la primer carta del mazo, si no hay se muesta el sout
    public Carta robarCarta() {
        if (cartas.isEmpty()) {
            System.out.println("El mazo está vacío.");
            return null;
        }
        return cartas.remove(0);
    }

    // Con esto se ven las cartas que hay en el mazo
    public int getCantidadCartas() {
        return cartas.size();
    }

    // Los getters y setters para las cartas
    public List<Carta> getCartas() {
        return cartas;
    }

    public void setCartas(List<Carta> cartas) {
        this.cartas = cartas;
    }
}