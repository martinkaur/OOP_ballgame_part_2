package projekt.pallikorjaja;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


public class Rakendus extends Application {
    AnimationTimer mängutsükkel;
    Mängija mängija = new Mängija(new Vektor2(260, 400), new Vektor2(16, 8), new Vektor2());
    List<Pall> pallid = new ArrayList<>();
    Vektor2 algneSuurus = new Vektor2(640, 480); // see tähendab 1:1 mõlemas suunas, selle järgi hiljem arvutame muud suurused jagades jms
    Vektor2 tegelikSuurus = new Vektor2(640, 480);
    Vektor2 suuruseKordaja = new Vektor2(1, 1); // eelmisega seotud, võimalik, et läheb vaja (I du not wanna, dis is pain)

    Vektor2 mängijaAlamPiirid = new Vektor2(20, 320);
    Vektor2 mängijaÜlemPiirid = new Vektor2(620, 420);

    PunktiHoidja punktiinfo = new PunktiHoidja(0, "");
    Text skoor = new Text("Skoor: " + Integer.toString(punktiinfo.getHetkeneSkoor()));

    Seaded mänguseaded = new Seaded(30, 5, 5, 40, 10);
    int elud = mänguseaded.getAlgElud();

    Text eludTekst = new Text("Elud: " + elud);

    @Override
    public void start(Stage peaLava) throws IOException {
        Group juurmäng = new Group();
        juurmäng.getChildren().add(mängija.getKujutus());

        Scene mängustseen = new Scene(juurmäng, 480, 640, Color.DARKGRAY);

        Group juurava = new Group();
        juurava.minHeight(480);
        juurava.minWidth(640);

        Scene avaekraan = new Scene(juurava, algneSuurus.getY(), algneSuurus.getX(), Color.LIGHTGRAY);
        TextField mängijasisend = new TextField("Nimi");
        mängijasisend.setLayoutX(tegelikSuurus.getX()/2 - 78);
        mängijasisend.setLayoutY(120);
        juurava.getChildren().add(mängijasisend);
        Button alustanupp = new Button("ALUSTA");
        juurava.getChildren().add(alustanupp);
        alustanupp.setMinSize(80, 30);
        alustanupp.setLayoutX(tegelikSuurus.getX()/2 - 40);
        alustanupp.setLayoutY(200);

        peaLava.setTitle("Pallimäng");
        peaLava.setHeight(algneSuurus.getY());
        peaLava.setWidth(algneSuurus.getX());
        peaLava.setScene(avaekraan);
        peaLava.show();

        // muu setup
        loopallid(juurmäng, mängustseen);
        Text nimi = new Text(punktiinfo.getHetkeneNimi());
        Text parimskoor = new Text("Parim skoor: " + Integer.toString(punktiinfo.getParimSkoor()));

        juurmäng.getChildren().add(nimi);
        juurmäng.getChildren().add(skoor);
        juurmäng.getChildren().add(parimskoor);
        juurmäng.getChildren().add(eludTekst);

        nimi.setLayoutX(20);
        nimi.setLayoutY(20);
        nimi.setFont(Font.font ("Arial", 14));
        skoor.setLayoutX(160);
        skoor.setLayoutY(20);
        skoor.setFont(Font.font ("Arial", 14));
        parimskoor.setLayoutX(280);
        parimskoor.setLayoutY(20);
        parimskoor.setFont(Font.font ("Arial", 14));
        eludTekst.setLayoutX(480);
        eludTekst.setLayoutY(20);
        eludTekst.setFont(Font.font ("Arial", 16));
        eludTekst.setFill(Color.GREEN);


        alustanupp.setOnMouseClicked(event -> {
            peaLava.setScene(mängustseen);
            punktiinfo.setHetkeneNimi(mängijasisend.getText());
            nimi.setText(punktiinfo.getHetkeneNimi());
            alusta(mängustseen);
        });

        // kõikide asjade suuruseid peaks iga kaader tegelt korrutama suurusekordajaga ka....
        peaLava.widthProperty().addListener((OV, vanaLaius, uusLaius) -> {
            //System.out.println("Laius: " + uusLaius);
            tegelikSuurus.setX((double) uusLaius);
            suuruseKordaja.setX(tegelikSuurus.getX()/algneSuurus.getX());
            // TODO: actually kasuta seda suurusekordajat kõige peal
            muudaSuuruseid();
        });
        peaLava.heightProperty().addListener((OV, vanaKõrgus, uusKõrgus) -> {
            //System.out.println("Kõrgus: " + uusKõrgus);
            tegelikSuurus.setY((double) uusKõrgus);
            suuruseKordaja.setY(tegelikSuurus.getY()/algneSuurus.getY());
            // TODO: actually kasuta seda suurusekordajat kõige peal (oeh)
            muudaSuuruseid();
        });


        mängustseen.setOnKeyPressed(keyEvent -> {
            if(keyEvent.getCode() == KeyCode.UP){
                if(mängija.getKiirus().getY()>-4) mängija.getKiirus().liidaVektor(new Vektor2(0, -4));
            }
            if(keyEvent.getCode() == KeyCode.DOWN){
                if(mängija.getKiirus().getY()<4) mängija.getKiirus().liidaVektor(new Vektor2(0, 4));
            }
            if(keyEvent.getCode() == KeyCode.LEFT){
                if(mängija.getKiirus().getX()>-4) mängija.getKiirus().liidaVektor(new Vektor2(-4, 0));
            }
            if(keyEvent.getCode() == KeyCode.RIGHT){
                if(mängija.getKiirus().getX()<4) mängija.getKiirus().liidaVektor(new Vektor2(4, 0));
            }
        });

        mängustseen.setOnKeyReleased(keyEvent -> {
            if(keyEvent.getCode() == KeyCode.UP || keyEvent.getCode() == KeyCode.DOWN){
                mängija.getKiirus().setY(0);
            }
            if(keyEvent.getCode() == KeyCode.LEFT || keyEvent.getCode() == KeyCode.RIGHT){
                mängija.getKiirus().setX(0);
            }

        });

        // maybe TODO: lõpuekraan, edetabel seal (võib edetabeli ka avaekraanile panna)
    }

    public static void main(String[] args) {
        launch();
    }

    /**
     * Kuvab animatsiooni taimeri põhjal asju ja muudab väärtusi jne
     */
    private void alusta(Scene stseen){
         mängutsükkel = new AnimationTimer(){
            @Override
            public void handle(long aeg) {
                // siin teha kõik asjad - pallide liigutamine, mängija liigutamine
                for(Pall pall : pallid){
                    // TODO: Idk, see läks ka meelest, aga midagi tähtsat...

                    // mängijakontroll
                    if(pall.getKoordinaadid().kasOnRaadiuses(mängija.getKoordinaadid(), 14)){

                        if(!pall.isPunanepall()){
                            punktiinfo.setHetkeneSkoor(punktiinfo.getHetkeneSkoor()+1);
                            if(punktiinfo.getHetkeneSkoor()%10 == 0){
                                elud++;
                                eludeTekst();
                            }

                            skoor.setText("Skoor: " + Integer.toString(punktiinfo.getHetkeneSkoor()));
                            // TODO: Palle juurde
                            // TODO: Uhh.. läks meelest, aga midagi oli
                        }
                        else {
                            elud--;
                            eludeTekst();

                            /*
                            TODO: ----------------------------
                            if(elud <= 0){
                                lõpetaMäng() //TODO: sh salvesta highscore jne
                            }
                             */
                        }
                        juhuslikPall(pall, stseen);

                    }

                    // põrkekontroll
                    if(pall.getKoordinaadid().getY() <= pall.getRaadiused().getX()) {
                        pall.getKiirus().põrgeHorisontaalneSein();
                        pall.getKoordinaadid().liidaVektor(new Vektor2(0, 2));
                    }
                    if(pall.getKoordinaadid().getY() >= tegelikSuurus.getY() - 40){
                        pall.getKiirus().põrgeHorisontaalneSein();
                        pall.getKoordinaadid().liidaVektor(new Vektor2(0, -2));
                    }
                    if(pall.getKoordinaadid().getX() <= pall.getRaadiused().getX()){
                        pall.getKiirus().põrgeVertikaalneSein();
                        pall.getKoordinaadid().liidaVektor(new Vektor2(2, 0));
                    }
                    if(pall.getKoordinaadid().getX() >= tegelikSuurus.getX() - 24){
                        pall.getKiirus().põrgeVertikaalneSein();
                        pall.getKoordinaadid().liidaVektor(new Vektor2(-2, 0));
                    }

                    for(Pall teine : pallid){
                        if(teine.equals(pall)) continue;
                        if(pall.getKoordinaadid().kasOnRaadiuses(teine.getKoordinaadid(), teine.getRaadiused().getX())){
                            pall.getKiirus().lihtnePõrge(teine.getKiirus());
                            // et vältida topeltpõrkeid...
                            while (pall.getKoordinaadid().kasOnRaadiuses(teine.getKoordinaadid(), teine.getRaadiused().getX())){
                                pall.Liigu();
                                teine.Liigu();
                            }
                        }
                    }

                    // viimaks: liikumine
                    pall.Liigu();
                }

                mängija.Liigu();
                // kontrollime, et mängija poleks oma kastist väljas
                if(mängija.getKoordinaadid().getY() > mängijaÜlemPiirid.getY()){
                    mängija.getKoordinaadid().setY(mängijaÜlemPiirid.getY());
                }
                if(mängija.getKoordinaadid().getX() > mängijaÜlemPiirid.getX()){
                    mängija.getKoordinaadid().setX(mängijaÜlemPiirid.getX());
                }
                if(mängija.getKoordinaadid().getY() < mängijaAlamPiirid.getY()){
                    mängija.getKoordinaadid().setY(mängijaAlamPiirid.getY());
                }
                if(mängija.getKoordinaadid().getX() < mängijaAlamPiirid.getX()){
                    mängija.getKoordinaadid().setX(mängijaAlamPiirid.getX());
                }

            }
        };

         mängutsükkel.start();
    }

    private void lõpeta(){
        mängutsükkel.stop();
    }

    /**
     * Teeb pallist nullitud palli.
     * @param pall - pall, mida nullida
     */
    public static void nulliPall(Pall pall){
        pall = new Pall();
    }

    /**
     * Teeb pallist juhuslikus asukohas oleva palli
     * @param pall - pall, mida muudetakse
     */
    public void juhuslikPall(Pall pall, Scene stseen){
        pall.setPunanepall(false);
        float x = (float) (Math.random() * (stseen.getWidth()));
        float y = (float) (Math.random() * (stseen.getHeight()/2));
        Vektor2 koordinaadid = new Vektor2(x, y);
        float dx = (float) ((Math.random()-0.5) * (mänguseaded.getMaxKiirus()));
        float dy = (float) ((Math.random()-0.5) * (mänguseaded.getMaxKiirus()));
        Vektor2 kiirus = new Vektor2(dx, dy);
        pall.setKoordinaadid(koordinaadid);

        // Todo: maybe väiksemaks tõenäosust? pallitüüpe juurde?
        if(Math.random() > 0.7){
            pall.setPunanepall(true);
            kiirus.korrutaKonstandiga(2);
            pall.setKiirus(kiirus);
            pall.getRing().setStroke(Color.DARKGRAY);
            pall.getRing().setFill(Color.DARKRED);
        }
        else{
            pall.setKiirus(kiirus);
            pall.getRing().setStroke(Color.DARKORANGE);
            pall.getRing().setFill(Color.ORANGE);
        }
    }

    /**
     * Seda meetodit kutsutakse 1 kord pallide algseks tegemiseks ja lisamiseks listi.
     * @param juur - viide juure grupile, kuhu pallid panna
     */
    public void loopallid(Group juur, Scene stseen){
        for(int i = 0; i< mänguseaded.getPallideArv(); i++){
            //Genereerime juhuarvudega palli

            Pall pall = new Pall();
            juhuslikPall(pall, stseen);
            pall.setRaadiused(new Vektor2(8, 8));

            // lisame palli pallide listi
            pallid.add(pall);
            //System.out.println(pall);

            // lisame siin pallid ka hetkel juurele
            Circle ring = new Circle(8, Color.ORANGE);
            ring.setStroke(Color.DARKORANGE);
            ring.setLayoutX(pall.getKoordinaadid().getX());
            ring.setLayoutY(pall.getKoordinaadid().getY());
            pall.setRing(ring);
            juur.getChildren().add(ring);
        }
    }

    // TODO: Liigu katsetuse faasist edasi...
    // Trick is: me peame algset suurust meeles pidama (so it makes sense)
    public void muudaSuuruseid(){
        Vektor2 r = new Vektor2(16, 8);
        r.elementKorruta(suuruseKordaja);
        mängija.setRaadiused(r);
        mängija.getKujutus().setRadiusX(mängija.getRaadiused().getX());
        mängija.getKujutus().setRadiusY(mängija.getRaadiused().getY());
    }

    void eludeTekst(){
        eludTekst.setText("Elud: " + elud);
        if(elud == 2) eludTekst.setFill(Color.ORANGE);
        if(elud < 2) eludTekst.setFill(Color.RED);
    }
}
