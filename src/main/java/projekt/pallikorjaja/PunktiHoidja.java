package projekt.pallikorjaja;

import java.io.*;
import java.util.Scanner;

public class PunktiHoidja {
    /*
    Tegeleb igasuguste teenitud skooride jms-ga
    isendiväljad:
         - praegune skoor (int)
         - praegune nimi (String)
         - parim skoor (int)
         - parima skoori omaniku nimi (String)
    meetodid:
        - skoori jälgimise meetodid, liitmine jne
        - tagastamine
        - tekstifaili kirjutamine <skoor>\t<nimi> kujul (ehk salvestamine)
        - võtab vastu (mängu alguses küsitud) mängija nime
        - parima skoori ja sellega seonduva nime leiab failist
     */

    ///////////// Kirjutada ümber Streamidele
    private int hetkeneSkoor;
    private String hetkeneNimi;
    private int parimSkoor;
    private String parimaNimi;

    public PunktiHoidja(int hetkeneSkoor, String hetkeneNimi, int parimSkoor, String parimaNimi) {
        this.hetkeneSkoor = hetkeneSkoor;
        this.hetkeneNimi = hetkeneNimi;
        this.parimSkoor = parimSkoor;
        this.parimaNimi = parimaNimi;
    }

    public PunktiHoidja(int hetkeneSkoor, String hetkeneNimi) {
        this.hetkeneSkoor = hetkeneSkoor;
        this.hetkeneNimi = hetkeneNimi;
    }

    public int getHetkeneSkoor() {
        return hetkeneSkoor;
    }

    public void setHetkeneSkoor(int hetkeneSkoor) {
        this.hetkeneSkoor = hetkeneSkoor;
    }

    public String getHetkeneNimi() {
        return hetkeneNimi;
    }

    public void setHetkeneNimi(String hetkeneNimi) {
        this.hetkeneNimi = hetkeneNimi;
    }

    public int getParimSkoor() {
        return parimSkoor;
    }

    public void setParimSkoor(int parimSkoor) {
        this.parimSkoor = parimSkoor;
    }

    public String getParimaNimi() {
        return parimaNimi;
    }

    public void setParimaNimi(String parimaNimi) {
        this.parimaNimi = parimaNimi;
    }

    /*meetodid:
            - skoori jälgimise meetodid, liitmine jne
        - tagastamine
        - tekstifaili kirjutamine <skoor>\t<nimi> kujul (ehk salvestamine)
        - võtab vastu (mängu alguses küsitud) mängija nime
        - parima skoori ja sellega seonduva nime leiab failist*/

    public int skoorijälgimine (int punktid) {
        hetkeneSkoor += punktid;
        return hetkeneSkoor;
    }

    public void salvestaSkoor(){
        try (FileWriter fail = new FileWriter("skoorid.txt", true);
             PrintWriter printWriter = new PrintWriter(fail)){
            printWriter.println(hetkeneSkoor + "\t" + hetkeneNimi);
        } catch (Exception e) {
            System.err.println("Viga skoori salvestamisel: " + e.getMessage());
        }
    }

    public void leiaParim () {
        parimSkoor = 0;
        parimaNimi = "";

        try (Scanner scanner = new Scanner(new File("skoorid.txt"), "UTF-8")){
            while (scanner.hasNextLine()){
                String rida = scanner.nextLine();
                String[] osad = rida.split("\t");

                int skoor = Integer.parseInt(osad[0]);
                String nimi = osad[1];

                if (skoor > parimSkoor){
                    parimSkoor = skoor;
                    parimaNimi = nimi;
                }
            }
        } catch (FileNotFoundException e) {
            System.err.println("Faili skoorid.txt ei leitud!");
        }
    }

    @Override
    public String toString() {
        return "Hetke tulemus:\n" +
                "Nimi: " + hetkeneNimi + "\n" +
                "Hetkene skoor: " + hetkeneSkoor + "\n" +
                "Parim skoor: " + parimSkoor + "\n" +
                "Parima skoori omanik: " + parimaNimi;
    }
}
