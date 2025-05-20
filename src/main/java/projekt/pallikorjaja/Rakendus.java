package projekt.pallikorjaja;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Rakendus extends Application {
    AnimationTimer mängutsükkel;
    List<Pall> pallid = new ArrayList<>();
    Vektor2 tegelikSuurus = new Vektor2(640, 480); // see tähendab 1:1 mõlemas suunas, selle järgi hiljem arvutame muud suurused jagades jms
    Vektor2 suuruseKordaja = new Vektor2(1, 1); // eelmisega seotud, võimalik, et läheb vaja, TODO: event suuruse muutmise jaoks

    Seaded mänguseaded = new Seaded(30, 5, 10, 40, 20);

    @Override
    public void start(Stage peaLava) throws IOException {
        Group juurmäng = new Group();
        juurmäng.minHeight(480);
        juurmäng.minWidth(640);

        Group juurava = new Group();
        juurava.minHeight(480);
        juurava.minWidth(640);

        Scene avaekraan = new Scene(juurava, 480, 640, Color.LIGHTGRAY);
        TextField mängijasisend = new TextField("Nimi");
        mängijasisend.setLayoutX(240);
        mängijasisend.setLayoutY(120);
        juurava.getChildren().add(mängijasisend);
        Button alustanupp = new Button("ALUSTA");
        juurava.getChildren().add(alustanupp);
        alustanupp.setMinSize(80, 30);
        alustanupp.setLayoutX(320);
        alustanupp.setLayoutY(200);

        Scene mängustseen = new Scene(juurmäng, 480, 640, Color.DARKGRAY);

        peaLava.setTitle("Pallimäng");
        peaLava.setHeight(480);
        peaLava.setWidth(640);
        peaLava.setScene(avaekraan);
        peaLava.show();

        // muu setup
        loopallid(juurmäng, mängustseen);

        // taimer käima ja läks TODO: õigesse kohta see palun
        alustanupp.setOnMouseClicked(event -> {
            peaLava.setScene(mängustseen);
            alusta();
        });



        Olek mänguolek = Olek.AVAEKRAAN; // kas seda on vaja tehniliselt
        // TODO: tsükkel state-machine mänguoleku põhjal - millist stseeni näidata
        // kokku vaja 3 stseeni teha - avastseen, mängustseen, lõpustseen
    }

    public static void main(String[] args) {
        launch();
    }

    /**
     * Kuvab animatsiooni taimeri põhjal asju ja muudab väärtusi jne
     */
    private void alusta(){
         mängutsükkel = new AnimationTimer(){
            @Override
            public void handle(long aeg) {
                // siin teha kõik asjad - pallide liigutamine, mängija liigutamine
                for(Pall pall : pallid){
                    pall.Liigu();
                }
            }
        };

         mängutsükkel.start();
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
     * @param väljak - viide juurele, et saaks suuruseid kontrollida
     */
    public void juhuslikPall(Pall pall, Group väljak, Scene stseen){
        float x = (float) (Math.random() * (stseen.getWidth()));
        float y = (float) (Math.random() * (stseen.getHeight()));
        Vektor2 koordinaadid = new Vektor2(x, y);
        float dx = (float) ((Math.random()-0.5) * (mänguseaded.getMaxKiirus()));
        float dy = (float) ((Math.random()-0.5) * (mänguseaded.getMaxKiirus()));
        Vektor2 kiirus = new Vektor2(dx, dy);
        pall.setKoordinaadid(koordinaadid);
        pall.setKiirus(kiirus);
    }

    /**
     * Seda meetodit kutsutakse 1 kord pallide algseks tegemiseks ja lisamiseks listi.
     * @param juur - viide juure grupile, kuhu pallid panna
     */
    public void loopallid(Group juur, Scene stseen){
        for(int i = 0; i<= mänguseaded.getPallideArv(); i++){
            //Genereerime juhuarvudega palli

            Pall pall = new Pall();
            juhuslikPall(pall, juur, stseen);

            // lisame palli pallide listi
            pallid.add(pall);
            //System.out.println(pall);

            // lisame siin pallid ka hetkel juurele
            Circle ring = new Circle(8, Color.ORANGE);
            ring.setLayoutX(pall.getKoordinaadid().getX());
            ring.setLayoutY(pall.getKoordinaadid().getY());
            pall.setRing(ring);
            juur.getChildren().add(ring);
        }
    }
}
