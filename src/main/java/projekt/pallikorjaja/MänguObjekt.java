package projekt.pallikorjaja;

abstract class MänguObjekt {
    /*
    See, mis püüab. Esimeses versioonis näidatud '_'-ga.
    Sisaldab isendivälju:
        Vektor2 (float, float) - tüüpi koordinaadid asukoha hoidmiseks;
        Vektor2 (float, float) - tüüpi raadiused, mille piires tehakse kokkupõrkekontroll;
        Vektor2 (float, float) - tüüpi kiirus.
     */
    //esimeses versioonis on kiirus ja raadius konstantne

    // klass alustamaks lihtsamalt selliseid, nagu pall ja mängija, mis implementeerivad sarnast loogikat
    protected Vektor2 koordinaadid;
    protected Vektor2 raadiused;
    protected Vektor2 kiirus;

    public MänguObjekt(Vektor2 asukoht, Vektor2 raadiused, Vektor2 kiirus) {
        this.koordinaadid = asukoht;
        this.raadiused = raadiused;
        this.kiirus = kiirus;
    }

    public MänguObjekt(Vektor2 asukoht) {
        this.koordinaadid = asukoht;
        this.raadiused = new Vektor2(); // 0, 0 - kontrollime ainult hetkeasukohta esialgu
        this.kiirus = new Vektor2(1, 1);
    }

    public Vektor2 getKoordinaadid() {
        return this.koordinaadid;
    }

    public void setKoordinaadid(Vektor2 koordinaadid) {
        this.koordinaadid = koordinaadid;
    }

    public void setKiirus(Vektor2 kiirus) {
        this.kiirus = kiirus;
    }

    public Vektor2 getRaadiused() {
        return raadiused;
    }

    public void setRaadiused(Vektor2 raadiused) {
        this.raadiused = raadiused;
    }

    abstract void Liigu();

    @Override
    public String toString() {
        return "MänguObjekt: " +
                "Asukoht: " + koordinaadid.toString() +
                "Kiirus: " + kiirus.toString() +
                "Raadius: " + raadiused.toString();
    }
}
