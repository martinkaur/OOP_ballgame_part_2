package projekt.pallikorjaja;

import javafx.scene.shape.Circle;

public class Pall extends MänguObjekt{
    /*
    See, mida püütakse.
     */
    private Circle ring;
    private boolean punanepall;
    private boolean rohelinepall;

    public Pall(float x, float y, float dx, float dy, int raadius) {
        super(new Vektor2(x, y), new Vektor2(dx, dy), new Vektor2(raadius, raadius));
        ring = new Circle(10);
        punanepall = false;
    }

    public Pall(Vektor2 koordinaadid, Vektor2 kiirus, int raadius) {
        super(koordinaadid, kiirus, new Vektor2(raadius, raadius));
        ring = new Circle(10);
        punanepall = false;
    }

    public Pall(Vektor2 koordinaadid, Vektor2 kiirus) {
        super(koordinaadid, kiirus, new Vektor2(1, 1));
        ring = new Circle(10);
        punanepall = false;
    }

    public Pall(Vektor2 koordinaadid) {
        super(koordinaadid, new Vektor2(0, 0), new Vektor2(1, 1));
        ring = new Circle(10);
        punanepall = false;
    }

    public Pall() {
        super(new Vektor2(0, 0), new Vektor2(0, 0), new Vektor2(0,0));
        ring = new Circle(10);
        punanepall = false;
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
        this.ring.setLayoutX(koordinaadid.getX());
        this.ring.setLayoutY(koordinaadid.getY());
    }

    public Circle getRing() {
        return ring;
    }

    public void setRing(Circle ring) {
        this.ring = ring;
    }

    public boolean isPunanepall() {
        return punanepall;
    }

    public void setPunanepall(boolean punanepall) {
        this.punanepall = punanepall;
    }

    public boolean isRohelinepall() {
        return rohelinepall;
    }

    public void setRohelinepall(boolean rohelinepall) {
        this.rohelinepall = rohelinepall;
    }

    @Override
    public String toString() {
        return "Pall: " +
                "Asukoht: " + koordinaadid.toString() +
                "Kiirus: " + kiirus.toString() +
                "Raadius: " + raadiused.toString();
    }
}
