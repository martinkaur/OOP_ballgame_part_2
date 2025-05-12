package projekt.pallikorjaja;

public class Mängija extends MänguObjekt{


    public Mängija(Vektor2 asukoht) {
        super(asukoht);

    }

    public Mängija(Vektor2 asukoht, Vektor2 raadiused, Vektor2 kiirus) {
        super(asukoht, raadiused, kiirus);
    }

    // sellel pole hetkel mõtet, mängija liikumise arvutamiseks tuleks kogu kood restruktureerida ja nt "continue" pole võimalik siitanda
    // samas tahaks siin siis juba sisendit ka kontrollida
    public void Liigu(){
        koordinaadid = koordinaadid;
    }

    @Override
    public String toString() {
        return "Mängija: " +
                "Asukoht: " + koordinaadid.toString() +
                "Kiirus: " + kiirus.toString() +
                "Raadius: " + raadiused.toString();
    }
}
