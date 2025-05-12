package projekt.pallikorjaja;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Rakendus extends Application {
    static Random random = new Random();
    AnimationTimer mängutsükkel;
    List<Pall> pallid = new ArrayList<>();
    Seaded mänguseaded = new Seaded(30, 5, 10, 40, 10);

    @Override
    public void start(Stage peaLava) throws IOException {
        Group juur = new Group();
        juur.minHeight(480);
        juur.minWidth(640);

        Scene stseen = new Scene(juur, 480, 640, Color.DARKGRAY);

        peaLava.setTitle("Pallimäng");
        peaLava.setHeight(480);
        peaLava.setWidth(640);
        peaLava.setScene(stseen);
        peaLava.show();



        alusta();
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
                // siin teha kõik asjad - pallide väärtustamine jms
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
    public static void juhuslikPall(Pall pall, Group väljak){
        float x = (float) (Math.random() * (väljak.getLayoutX()));
        float y = (float) (Math.random() * (väljak.getLayoutY()));
        Vektor2 koordinaadid = new Vektor2(x, y);
        float dx = (float) (Math.random() * (10));
        float dy = (float) (Math.random() * (10));
        Vektor2 kiirus = new Vektor2(dx, dy);
        pall.setKoordinaadid(koordinaadid);
        pall.setKiirus(kiirus);
    }
}
