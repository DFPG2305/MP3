import java.util.ArrayList;
import java.util.List;

//Voy a poner comentarios para que Perea se guie, luego los borran

public class Campo {
    private List<Monstruo> monstruos;
    private List<CartaMagica> cartasMagicas;
    private List<CartaTrampa> cartasTrampa;

    public Campo() {
        this.monstruos = new ArrayList<>();
        this.cartasMagicas = new ArrayList<>();
        this.cartasTrampa = new ArrayList<>();
    }

    // Con esto se colocan los monstruos en el campo
    public void colocarMonstruo(Monstruo monstruo) {
        monstruos.add(monstruo);
        System.out.println("Monstruo " + monstruo.getNombre() + " colocado en el campo.");
        //System.out.println("Cantidad de monstruos ahora: " + monstruos.size());1  Si quieren verificar que no hay errores en cuanto a la lista de monstruos descomenten esto
    }

    // Con esto se colocan las cartas mágicas en el campo
    public void colocarCartaMagica(CartaMagica cartaMagica) {
        cartasMagicas.add(cartaMagica);
        System.out.println("Carta mágica " + cartaMagica.getNombre() + " colocada en el campo.");
    }

    // Con esto se colocan las cartas trampa en el campo
    public void colocarCartaTrampa(CartaTrampa cartaTrampa) {
        cartasTrampa.add(cartaTrampa);
        System.out.println("Carta trampa " + cartaTrampa.getNombre() + " colocada en el campo.");
    }

    // Con esto se quita un monstruo del campo
    public void quitarMonstruo(Monstruo monstruo) {
        monstruos.remove(monstruo);
        System.out.println("Monstruo " + monstruo.getNombre() + " retirado del campo.");
    }

    // Con esto se ve cuántos monstruos hay en el campo
    public int getCantidadMonstruos() {
        return monstruos.size();
    }

    //setters y getters
    public List<Monstruo> getMonstruos() {
        return monstruos;
    }

    public void setMonstruos(List<Monstruo> monstruos) {
        this.monstruos = monstruos;
    }

    public List<CartaMagica> getCartasMagicas() {
        return cartasMagicas;
    }

    public void setCartasMagicas(List<CartaMagica> cartasMagicas) {
        this.cartasMagicas = cartasMagicas;
    }
}