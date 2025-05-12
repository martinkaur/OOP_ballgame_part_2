package projekt.pallikorjaja;

public class Pall extends MänguObjekt{
    /*
    See, mida püütakse.
     */

    public Pall(float x, float y, float dx, float dy, int raadius) {
        super(new Vektor2(x, y), new Vektor2(dx, dy), new Vektor2(raadius, raadius));
    }

    public Pall(Vektor2 koordinaadid, Vektor2 kiirus, int raadius) {
        super(koordinaadid, kiirus, new Vektor2(raadius, raadius));
    }

    public Pall(Vektor2 koordinaadid, Vektor2 kiirus) {
        super(koordinaadid, kiirus, new Vektor2(1, 1));
    }

    public Pall(Vektor2 koordinaadid) {
        super(koordinaadid, new Vektor2(0, 0), new Vektor2(1, 1));
    }

    public Pall() {
        super(new Vektor2(0, 0), new Vektor2(0, 0), new Vektor2(1, 1));
    }


    public void setKoordinaadid(float x, float y) {
        this.koordinaadid = new Vektor2(x, y);
    }

    public Vektor2 getKiirus() {
        return kiirus;
    }

    public void setKiirus(float dx, float dy) {
        this.kiirus = new Vektor2(dx, dy);
    }

    public void Liigu(){
        this.koordinaadid.liidaVektor(kiirus);
    }

    @Override
    public String toString() {
        return "Pall: " +
                "Asukoht: " + koordinaadid.toString() +
                "Kiirus: " + kiirus.toString() +
                "Raadius: " + raadiused.toString();
    }
}
