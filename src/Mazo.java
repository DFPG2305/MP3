import java.util.ArrayList;
import java.util.List;

public class Mazo {
    private List<Carta> cartas;

    public Mazo() {
        this.cartas = new ArrayList<>();
    }

    public void agregarCarta(Carta carta){ 
        cartas.add(carta); 
    }
    public int getCantidadCartas(){ 
        return cartas.size(); 
    }
    public boolean estaVacio(){ 
        return cartas.isEmpty(); 
    }
    public List<Carta> getCartas(){ 
        return cartas; 
    }
    public void setCartas(List<Carta> c){ 
        this.cartas = c; 
    }

    public Carta robarCarta() {
        if (cartas.isEmpty()) return null;
        return cartas.remove(0);
    }
}
