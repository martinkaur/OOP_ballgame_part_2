package projekt.pallikorjaja;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

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

    public void salvestaSkoor() throws Exception {
        Map<String, Integer> skoorid = new LinkedHashMap<>();

        File fail = new File("skoorid.txt");

        if (fail.exists()) {
            try (BufferedReader reader = new BufferedReader(new InputStreamReader(
                    new FileInputStream(fail), "UTF-8"))) {
                String rida;
                while ((rida = reader.readLine()) != null) {
                    String[] osad = rida.split("\t");
                    if (osad.length == 2) {
                        String nimi = osad[1];
                        int skoor = Integer.parseInt(osad[0]);
                        if (nimi.equals(hetkeneNimi)) {
                            if (hetkeneSkoor > skoor) {
                                skoorid.put(nimi, hetkeneSkoor);
                            } else {
                                skoorid.put(nimi, skoor);
                            }
                        } else {
                            skoorid.put(nimi, skoor);
                        }
                    }
                }
            }
        }

        if(!skoorid.containsKey(hetkeneNimi)){
            skoorid.put(hetkeneNimi,hetkeneSkoor);
        }

        try (BufferedWriter out = new BufferedWriter(new OutputStreamWriter(
                new FileOutputStream("skoorid.txt",false), StandardCharsets.UTF_8))) {
            for (Map.Entry<String, Integer> tulemused: skoorid.entrySet()) {
                out.write(tulemused.getValue() + "\t" + tulemused.getKey());
                out.newLine();
            }
        }
    }

    public void leiaParim() throws Exception {
        int parimaSkoor = 0;
        parimaNimi = "";

        File fail = new File("skoorid.txt");
        if (!fail.exists()) {
            System.err.println("Faili skoorid.txt ei leitud!");
            return;
        }

        try (BufferedReader in = new BufferedReader(new InputStreamReader(
                new FileInputStream(fail), StandardCharsets.UTF_8))) {
            String rida;
            while ((rida = in.readLine()) != null) {
                String[] osad = rida.split("\t");
                if (osad.length == 2) {
                    try {
                        int skoor = Integer.parseInt(osad[0]);
                        String nimi = osad[1];
                        if (skoor > parimaSkoor) {
                            parimaSkoor = skoor;
                            parimaNimi = nimi;
                        }
                    } catch (NumberFormatException e) {
                        throw new RuntimeException();
                    }
                }
            }
        }

        this.parimSkoor = parimaSkoor;
    }

    /**
     *
     * @return tagastab failist loetud tulemused String massiivina (skoor, nimi) järjestatuna parimast alates.
     */
    public List<String[]> edetabel(){
        List<String[]> tulemused = new ArrayList<>();
        File fail = new File("skoorid.txt");

        if (!fail.exists()) {
            return tulemused;
        }
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(
                new FileInputStream(fail), StandardCharsets.UTF_8))) {
            String rida;
            while ((rida = reader.readLine()) != null) {
                String[] osad = rida.split("\t");
                if (osad.length == 2) {
                    tulemused.add(osad);
                }
            }
            tulemused.sort((a, b) -> Integer.compare(Integer.parseInt(b[0]), Integer.parseInt(a[0])));

        } catch (IOException e) {
            throw  new RuntimeException();
        }
        return tulemused;
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
