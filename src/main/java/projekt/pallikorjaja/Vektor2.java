package projekt.pallikorjaja;

public class Vektor2 {
    /*
    Vektor on nagu ennik kahe väärtuse salvestamiseks.
    Isendimeetodid lubavad tõlkida int ja double tüüpi väärtuste vahel (olla ettevaatlik).
    Kõik väärtused algväärtustatakse ja salvestatakse double-tüübiga.
    enamasti kasutusel x ja y koordinaatidena.

    esimeses versioonis enamasti ainult punktide hoidmiseks
     */
    
    private double x;
    private double y;

    public Vektor2() {
        this.x = 0;
        this.y = 0;
    }

    public Vektor2(double x, double y) {
        this.x = x;
        this.y = y;
    }

    public double getX() {
        return x;
    }

    public int getXasint(){
        return (int)(x);
    }

    public void setX(double x) {
        this.x = x;
    }

    public double getY() {
        return y;
    }

    public int getYasint(){
        return (int)(y);
    }

    public void setY(double y) {
        this.y = y;
    }
    
    public double Pikkus(){
        return Math.sqrt(this.x*this.x + this.y*this.y);
    }

    // enamasti on vaja võrrelda 2 punkti kaugust, selleks on järgmine meetod
    public double distnatsTeiseni(Vektor2 teine){
        return Math.sqrt(Math.pow((teine.x - this.x), 2) + Math.pow((teine.y - this.y), 2));
    }

    // Järgmine meetod tagastab vektori teise punktini (samuti vektor), märgiga x, y
    public Vektor2 uusVektorTeiseni(Vektor2 teine){
        double dx = teine.x - this.x;
        double dy = teine.x - this.x;

        return new Vektor2(dx, dy);
    }

    //meetod, mis liidab 2 vektorit
    public void liidaVektor(Vektor2 teine){
        this.x += teine.x;
        this.y += teine.y;
    }

    public void korrutaKonstandiga(double c){
        this.x *= c;
        this.y *= c;
    }

    // ################# siit edasi juba kindlasti teise etapi eeltöö ###################

    // et kontrollida, kas selle vektoriga esindatud punkt on teisele punktile lähemal või sama kaugel, kui antud raadius
    public boolean kasOnRaadiuses(Vektor2 punkt, double r){
        return distnatsTeiseni(punkt) <= r;
    }

    //Kokkupõrkel suuna muutmine, horisontaalsel ja vertikaalsel eraldi
    public void põrgeVertikaalneSein(){
        this.x = -this.x;
    }

    public void põrgeHorisontaalneSein(){
        this.y = -this.y;
    }

    //kokkupõrge teise palliga - ainult suuna muut,
    // sest meil elastsustegur on kokkuleppeliselt 1 ja massid võrdsed (kuigi võib ka panna vähem :P )
    // Niisiis...

    // Võrdsed pallide massid jms. Teeb põrke ka teise palli jaoks, ei ole vaj 2 korda kutsuda.
    public void põrgePalligaSuunamuut(Vektor2 teine){
        // vajab natuke mõtlemist...
        // alustuseks - igal pallil on impulss, mis on samuti vektor. kuna mass on 1, aga kiirused võivad olla erinevad...
        // ongi vaja vektorit, mis kirjeldaks nende omavahelise põrke tugevust - suhteline kiirusvektor teiseni ja tagasi
        Vektor2 siit = this.uusVektorTeiseni(teine);
        Vektor2 siia = teine.uusVektorTeiseni(this);

        // kui arvestada impulsis sisalduvat massi, tuleb teha 2 korrutustehet
        // need jätame järgmisesse meetodisse...

        // nüüd me teame nende suhteid, mõlemast mõlemale
        // edasine on tegelt hämmastavalt lihtne - meil on vaja lisada paegusele isendile vektori "siia"
        this.liidaVektor(siia);
        teine.liidaVektor(siit);
        // ja ongi kõik
    }

    // see meetod eksisteerib, sest suurem pall "võiks" olla raskem
    // m1 - selle isendi mass, mis omab seda vektorit, m2 teise vektori isendi mass
    public void põrgePalligaSuunamuutMassiga(Vektor2 teine, double m1, double m2){
        Vektor2 siit = this.uusVektorTeiseni(teine);
        Vektor2 siia = teine.uusVektorTeiseni(this);

        // kui siit lähtuva impulsi mass (m1) on suurem, kui teise oma, siis teine saab rohkem kiirust juurde
        // ja vastupidi, kuidas iganes m1 ja m2 on
        siit.korrutaKonstandiga(m1/m2);
        siia.korrutaKonstandiga(m2/m1);

        this.liidaVektor(siia);
        teine.liidaVektor(siit);

    }

    //seda ei juhtu küll kunagi...
    public boolean võrdub(Vektor2 v) {
        return v.getX() == this.x && v.getY() == this.y;
    }

    public boolean võrdubInt(Vektor2 v) {
        return (int)v.getX() == (int)this.x && (int)v.getY() == (int)this.y;
    }

    public String toString(){
        return "Vektor2(X: " + this.x + " Y: " + this.y + ")";
    }
}
