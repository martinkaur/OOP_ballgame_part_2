package projekt.pallikorjaja;


public class Seaded {
    private int pallideArv;
    private int maxPallideArv;
    private double maxKiirus;
    private int algElud;
    private int lisaIntervall;

    public Seaded() {
    }

    public Seaded(int lisaIntervall, int algElud, double maxKiirus, int maxPallideArv, int pallideArv) {
        this.lisaIntervall = lisaIntervall;
        this.algElud = algElud;
        this.maxKiirus = maxKiirus;
        this.maxPallideArv = maxPallideArv;
        this.pallideArv = pallideArv;
    }

    public int getPallideArv() {
        return pallideArv;
    }

    public void setPallideArv(int algPallideArv) {
        this.pallideArv = algPallideArv;
    }

    public int getMaxPallideArv() {
        return maxPallideArv;
    }

    public void setMaxPallideArv(int maxPallideArv) {
        this.maxPallideArv = maxPallideArv;
    }

    public double getMaxKiirus() {
        return maxKiirus;
    }

    public void setMaxKiirus(double maxKiirus) {
        this.maxKiirus = maxKiirus;
    }

    public int getAlgElud() {
        return algElud;
    }

    public void setAlgElud(int algElud) {
        this.algElud = algElud;
    }

    public int getLisaIntervall() {
        return lisaIntervall;
    }

    public void setLisaIntervall(int lisaIntervall) {
        this.lisaIntervall = lisaIntervall;
    }
}
