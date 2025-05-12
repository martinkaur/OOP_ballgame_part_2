package projekt.pallikorjaja;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Random;

public class Rakendus extends Application {
    static Random random = new Random();
    AnimationTimer mängutsükkel;

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

    private void alusta(){
         mängutsükkel = new AnimationTimer(){
            @Override
            public void handle(long aeg) {
                // siin teha kõik asjad - pallide väärtustamine jms
            }
        };

         mängutsükkel.start();
    }
}
