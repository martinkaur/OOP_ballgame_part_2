package projekt.pallikorjaja;

import javafx.scene.paint.Color;
import javafx.scene.shape.Ellipse;

public class Mängija extends MänguObjekt{
    private Ellipse kujutus;

    public Mängija(Vektor2 asukoht) {
        super(asukoht);
        alustaOvaal();
    }

    public Mängija(Vektor2 asukoht, Vektor2 raadiused, Vektor2 kiirus) {
        super(asukoht, raadiused, kiirus);
        alustaOvaal();
    }

    public Ellipse getKujutus() {
        return kujutus;
    }

    private void alustaOvaal(){
        kujutus = new Ellipse();
        kujutus.setRadiusX(20);
        kujutus.setRadiusY(10);
        kujutus.setFill(Color.DARKGREEN);
        kujutus.setStroke(Color.DARKBLUE);
        Liigu();
    }

    // sellel pole hetkel mõtet, mängija liikumise arvutamiseks tuleks kogu kood restruktureerida ja nt "continue" pole võimalik siitanda
    // samas tahaks siin siis juba sisendit ka kontrollida
    public void Liigu(){
        koordinaadid.liidaVektor(kiirus);
        kujutus.setCenterX(this.koordinaadid.getX());
        kujutus.setCenterY(this.koordinaadid.getY());
    }

    @Override
    public String toString() {
        return "Mängija: " +
                "Asukoht: " + koordinaadid.toString() +
                "Kiirus: " + kiirus.toString() +
                "Raadius: " + raadiused.toString();
    }
}
