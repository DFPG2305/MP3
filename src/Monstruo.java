public class Monstruo extends Carta {
    private int atk;
    private int def;
    private int nivel;
    private boolean enPosicionAtaque;

    public Monstruo(String nombre, int atk, int def, int nivel) {
        super(nombre);
        this.atk = atk;
        this.def = def;
        this.nivel = nivel;
        this.enPosicionAtaque = true;
    }

    public int getAtk() {
        return atk;
    }

    public void setAtk(int atk) {
        this.atk = atk;
    }

    public int getDef() {
        return def;
    }

    public void setDef(int def) {
        this.def = def;
    }

    public int getNivel() {
        return nivel;
    }

    public void setNivel(int nivel) {
        this.nivel = nivel;
    }

    public boolean isEnPosicionAtaque() {
        return enPosicionAtaque;
    }

    public void setEnPosicionAtaque(boolean enPosicionAtaque) {
        this.enPosicionAtaque = enPosicionAtaque;
    }
}