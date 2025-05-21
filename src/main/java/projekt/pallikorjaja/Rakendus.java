package projekt.pallikorjaja;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.transform.Scale;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


public class Rakendus extends Application {
    // protected protected protected
    AnimationTimer mängutsükkel;
    Mängija mängija = new Mängija(new Vektor2(260, 400), new Vektor2(16, 8), new Vektor2());
    List<Pall> pallid = new ArrayList<>();
    Vektor2 algneSuurus = new Vektor2(1080, 720); // see tähendab 1:1 mõlemas suunas, selle järgi hiljem arvutame muud suurused jagades jms
    Vektor2 tegelikSuurus = new Vektor2(1080, 720);
    Vektor2 suuruseKordaja = new Vektor2(1, 1); // eelmisega seotud, võimalik, et läheb vaja (I du not wanna, dis is pain)

    Vektor2 mängijaAlamPiirid = new Vektor2(algneSuurus.getX()*0.1, algneSuurus.getY()*0.5);
    Vektor2 mängijaÜlemPiirid = new Vektor2(algneSuurus.getX()*0.9, algneSuurus.getY()*0.9);

    PunktiHoidja punktiinfo = new PunktiHoidja(0, "");
    Text skoor = new Text("Skoor: " + Integer.toString(punktiinfo.getHetkeneSkoor()));

    Seaded mänguseaded = new Seaded(20, 5, 5, 50, 10);
    int elud = mänguseaded.getAlgElud();

    Text eludTekst = new Text("Elud: " + elud);
    boolean paus;

    @Override
    public void start(Stage peaLava) throws IOException {
        Group juurmäng = new Group();
        juurmäng.getChildren().add(mängija.getKujutus());

        Scene mängustseen = new Scene(juurmäng, algneSuurus.getY(), algneSuurus.getX(), Color.DARKGRAY);

        Group juurava = new Group();

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
        Text pausTekst = new Text("Pausil");
        pausTekst.setVisible(false);

        juurmäng.getChildren().add(nimi);
        juurmäng.getChildren().add(skoor);
        juurmäng.getChildren().add(parimskoor);
        juurmäng.getChildren().add(eludTekst);
        juurmäng.getChildren().add(pausTekst);

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
        eludTekst.setLayoutY(24);
        eludTekst.setFont(Font.font ("Arial", 20));
        eludTekst.setFill(Color.GREEN);

        pausTekst.setLayoutX(algneSuurus.getX()/2-40);
        pausTekst.setLayoutY(algneSuurus.getY()/2);
        pausTekst.setFont(Font.font ("Arial", 32));
        pausTekst.setFill(Color.LIMEGREEN);
        pausTekst.setStroke(Color.GREEN);

        Scale scale = new Scale(1, 1);
        scale.setPivotX(0);
        scale.setPivotY(0);

        juurmäng.getTransforms().setAll(scale);
        juurava.getTransforms().setAll(scale);


        alustanupp.setOnMouseClicked(event -> {
            peaLava.setScene(mängustseen);
            punktiinfo.setHetkeneNimi(mängijasisend.getText());
            nimi.setText(punktiinfo.getHetkeneNimi());
            alusta(mängustseen);
            paus = false;
        });

        // kõikide asjade suuruseid peaks muutma akna suuruse muutmisel
        peaLava.widthProperty().addListener((OV, vanaLaius, uusLaius) -> {
            //System.out.println("Laius: " + uusLaius);
            tegelikSuurus.setX((double) uusLaius);
            suuruseKordaja.setX(tegelikSuurus.getX()/algneSuurus.getX());
            scale.setX(suuruseKordaja.getX());
            juurmäng.getTransforms().setAll(scale);
            juurava.getTransforms().setAll(scale);
        });
        peaLava.heightProperty().addListener((OV, vanaKõrgus, uusKõrgus) -> {
            //System.out.println("Kõrgus: " + uusKõrgus);
            tegelikSuurus.setY((double) uusKõrgus);
            suuruseKordaja.setY(tegelikSuurus.getY()/algneSuurus.getY());
            scale.setY(suuruseKordaja.getY());
            juurmäng.getTransforms().setAll(scale);;
            juurava.getTransforms().setAll(scale);
        });

        double mängijakiirus = 6;
        // mängija klaviatuurisisend: liikumine
        mängustseen.setOnKeyPressed(keyEvent -> {
            if(keyEvent.getCode() == KeyCode.UP){
                if(mängija.getKiirus().getY()>-mängijakiirus) mängija.getKiirus().liidaVektor(new Vektor2(0, -mängijakiirus));
            }
            if(keyEvent.getCode() == KeyCode.DOWN){
                if(mängija.getKiirus().getY()<mängijakiirus) mängija.getKiirus().liidaVektor(new Vektor2(0, mängijakiirus));
            }
            if(keyEvent.getCode() == KeyCode.LEFT){
                if(mängija.getKiirus().getX()>-mängijakiirus) mängija.getKiirus().liidaVektor(new Vektor2(-mängijakiirus, 0));
            }
            if(keyEvent.getCode() == KeyCode.RIGHT){
                if(mängija.getKiirus().getX()<mängijakiirus) mängija.getKiirus().liidaVektor(new Vektor2(mängijakiirus, 0));
            }
        });

        mängustseen.setOnKeyReleased(keyEvent -> {
            if(keyEvent.getCode() == KeyCode.UP || keyEvent.getCode() == KeyCode.DOWN){
                mängija.getKiirus().setY(0);
            }
            if(keyEvent.getCode() == KeyCode.LEFT || keyEvent.getCode() == KeyCode.RIGHT){
                mängija.getKiirus().setX(0);
            }
            if(keyEvent.getCode() == KeyCode.P){
                if(!paus) {
                    mängutsükkel.stop();
                    pausTekst.setVisible(true);
                    paus = true;
                }
                else{
                    mängutsükkel.start();
                    pausTekst.setVisible(false);
                    paus = false;
                }
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
                boolean lisadaVaja = false; // muidu concurrent modification, pole hea

                for(Pall pall : pallid){

                    // mängijakontroll
                    if(pall.getKoordinaadid().kasOnRaadiuses(mängija.getKoordinaadid(), 16)){

                        if(pall.isPunanepall()){
                            elud--;
                            eludeTekst();

                            /*
                            TODO: ----------------------------
                            if(elud <= 0){
                                lõpetaMäng() //TODO: sh salvesta highscore jne
                            }
                             */
                        } else if (pall.isRohelinepall()) {
                            elud++;
                            eludeTekst();
                            punktiinfo.setHetkeneSkoor(punktiinfo.getHetkeneSkoor()+1);

                        } else {
                            punktiinfo.setHetkeneSkoor(punktiinfo.getHetkeneSkoor()+1);
                            skoor.setText("Skoor: " + Integer.toString(punktiinfo.getHetkeneSkoor()));

                        }

                        // Lisa elusid ja/või palle
                        if(punktiinfo.getHetkeneSkoor()%10 == 0){
                            elud++;
                            eludeTekst();
                        }
                        if(punktiinfo.getHetkeneSkoor() % mänguseaded.getLisaIntervall() == 0){
                            if(mänguseaded.getMaxPallideArv() > mänguseaded.getPallideArv()){
                                mänguseaded.setPallideArv(mänguseaded.getPallideArv()+1);
                                mänguseaded.setMaxKiirus(mänguseaded.getMaxKiirus()+0.5);
                                lisadaVaja = true;
                            }
                        }

                        // ja "asusta praegune pall ümber"
                        juhuslikPall(pall, stseen);

                    }

                    // põrkekontroll
                    if(pall.getKoordinaadid().getY() <= pall.getRaadiused().getX()) {
                        pall.getKiirus().põrgeHorisontaalneSein();
                        pall.getKoordinaadid().liidaVektor(new Vektor2(0, 2));
                    }
                    if(pall.getKoordinaadid().getY() >= algneSuurus.getY() - 40){
                        pall.getKiirus().põrgeHorisontaalneSein();
                        pall.getKoordinaadid().liidaVektor(new Vektor2(0, -2));
                    }
                    if(pall.getKoordinaadid().getX() <= pall.getRaadiused().getX()){
                        pall.getKiirus().põrgeVertikaalneSein();
                        pall.getKoordinaadid().liidaVektor(new Vektor2(2, 0));
                    }
                    if(pall.getKoordinaadid().getX() >= algneSuurus.getX() - 24){
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

                // kui tekkis vajadus palli lisada:
                if(lisadaVaja) lisaPall((Group) stseen.getRoot(), stseen);

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

    private void lõpetaMäng(){
        mängutsükkel.stop();
        // TODO ....
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
        pall.setRohelinepall(false);
        float x = (float) (Math.random() * (stseen.getWidth()));
        float y = (float) (Math.random() * (stseen.getHeight()/4));
        Vektor2 koordinaadid = new Vektor2(x, y);
        float dx = (float) ((Math.random()-0.5) * (mänguseaded.getMaxKiirus())); // seda saab ka suurendada....
        float dy = (float) ((Math.random()-0.5) * (mänguseaded.getMaxKiirus()));
        Vektor2 kiirus = new Vektor2(dx, dy);
        pall.setKoordinaadid(koordinaadid);

        // Todo: maybe väiksemaks tõenäosust? pallitüüpe juurde?
        if(Math.random() > 0.8){
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

        // tahan, et häääästi väike võimalus oleks, praegu 0.2*0.3, seega veel realistlik
        if(Math.random() > 0.7 && pall.isPunanepall()){
            pall.setPunanepall(false);
            pall.setRohelinepall(true);
            pall.setKiirus(kiirus);
            pall.getRing().setStroke(Color.GREEN);
            pall.getRing().setFill(Color.LIMEGREEN);
        }
    }

    /**
     * Seda meetodit kutsutakse 1 kord pallide algseks tegemiseks ja lisamiseks listi.
     * @param juur - viide juure grupile, kuhu pallid panna
     */
    public void loopallid(Group juur, Scene stseen){
        for(int i = 0; i< mänguseaded.getPallideArv(); i++){
            //Genereerime juhuarvudega palli ja lisame selle
            lisaPall(juur, stseen);
        }
    }

    public void lisaPall(Group juur, Scene stseen){
        Pall pall = new Pall();
        juhuslikPall(pall, stseen);
        pall.setRaadiused(new Vektor2(8, 8));

        // lisame palli pallide listi
        pallid.add(pall);
        //System.out.println(pall);

        // lisame siin pallid ka hetkel juurele
        pall.getRing().setLayoutX(pall.getKoordinaadid().getX());
        pall.getRing().setLayoutY(pall.getKoordinaadid().getY());
        juur.getChildren().add(pall.getRing());
    }

    void eludeTekst(){
        eludTekst.setText("Elud: " + elud);
        if(elud == 2) eludTekst.setFill(Color.ORANGE);
        if(elud < 2) eludTekst.setFill(Color.RED);
    }
}
