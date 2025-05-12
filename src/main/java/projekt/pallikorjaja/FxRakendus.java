package p7.np7;

import javafx.animation.*;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ListView;
import javafx.scene.effect.Bloom;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;
import jdk.incubator.vector.VectorOperators;

import java.util.List;
import java.util.Random;
import java.util.Timer;
import java.util.concurrent.TimeUnit;


public class FxRakendus extends Application {
    public class Indeks{
        // sest see on lihtne võimalus int-i muuta kaudselt
        private int indeks;

        public Indeks(int indeks) {
            this.indeks = indeks;
        }

        public int getIndeks() {
            return indeks;
        }

        public void setIndeks(int indeks) {
            this.indeks = indeks;
        }

        public void inkrementeeri(){
            this.indeks++;
        }
    }

    public static void väärtusta(Indeks indeks, CheckBox[] vastusevariandid, String[][] kirjed){
        for(int i = 0; i<4; i++){
            vastusevariandid[i].setText(kirjed[indeks.getIndeks()][i]);
        }
    }

    public static void inkrementeeri(Indeks indeks, int maxindeks){
        if(indeks.getIndeks()<maxindeks) indeks.inkrementeeri();
        else indeks.setIndeks(0);
    }

    public static void paneküsimus(Indeks indeks, String[] küsimused, Text tekst){
        tekst.setText(küsimused[indeks.getIndeks()]);
    }

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage peaLava){
        Group juur = new Group();
        juur.minHeight(480);
        juur.minWidth(640);


        // Ülesanne 3
        /*
        Circle ring1 = new Circle(100, 100, 100, Color.RED);

        ring1.setOnMouseEntered(event -> {
                System.out.println("Hiir läks peale");
                ring1.setCenterX(ring1.getCenterX() + 5);
                ring1.setCenterY(ring1.getCenterY() + 5);
        });
        ring1.setOnMouseClicked(event -> {
            System.out.println("Hiireklikk");
            ring1.setCenterX(100);
            ring1.setCenterY(100);
            if(ring1.getFill() == Color.RED) ring1.setFill(Color.BLUE);
            else ring1.setFill(Color.RED);
        }); */
        //juur.getChildren().add(ring1);


        // Ülesanne 4
        /*
        Random suvataja = new Random();
        Button nupp = new Button("Nupukas");
        nupp.setLayoutX(320);
        nupp.setLayoutY(220);
        nupp.setMinWidth(60);
        nupp.setMinHeight(30);

        juur.getChildren().add(nupp);
        Scene stseen1 = new Scene(juur, 480, 640, Color.DARKGRAY);

        nupp.setOnMouseClicked(event -> {
            double xkordaja = suvataja.nextDouble();
            double ykordaja = suvataja.nextDouble();
            int X = (int)(xkordaja * (stseen1.getWidth() - nupp.getWidth()));
            int Y = (int)(ykordaja * (stseen1.getHeight() - nupp.getHeight()));
            nupp.setLayoutX(X);
            nupp.setLayoutY(Y);
        });*/

        // Ülesanne 6 (advanced)
        // juba tegin ära, aga see ehk väärib poolt lisapunkti neile, kes tegid :)
        // küsimusi lisada saab, kui tahta, "edasi"-nupuga saab ringi peale teha
        String[] küsimused = new String[] {
                "Milline maiustus on parim?",
                "Mis kirjeldab hästi Eesti kliimat?",
                "Kas ma valetan, kui ma ütlen, et ma valetan?"};

        // nn lõimekindel lahendus
        Indeks küsimuseIndeks = new Indeks(0);

        //Vastusevariandid, tehniliselt
        String[][] vastused = new String[][]{
                {"Juustukook!", "Shokolaad", "Marjad", "Koriander"},
                {"Igav", "Põnev", "Tuuline", "Soe"},
                {"Jah", "Ei", "Ma ei tea", "Pole võimalik teada"}
        };

        //Lahtiütlemine siinkohal: ei pruugi vastata reaalsusele, aga andsin parima.
        boolean[][] vastusteMask = new boolean[][]{
                {true, true, true, false},
                {true, false, true, false},
                {true, true, true, true},
        };

        BorderPane paan = new BorderPane();

        CheckBox cb1 = new CheckBox("Valik1");
        CheckBox cb2 = new CheckBox("Valik2");
        CheckBox cb3 = new CheckBox("Valik3");
        CheckBox cb4 = new CheckBox("Valik4");

        CheckBox[] vastusevariandid = new CheckBox[]{cb1, cb2, cb3, cb4};

        väärtusta(küsimuseIndeks, vastusevariandid, vastused);

        ListView<CheckBox> list = new ListView<CheckBox>();
        ObservableList<CheckBox> asjad1 = FXCollections.observableArrayList(vastusevariandid); // jee, see töötab, wiiiiiiiii

        list.setItems(asjad1);
        paan.setCenter(list);

        Text küss = new Text("See on küsimus.?");
        küss.setFont(Font.font(20));
        paan.setTop(küss);
        paneküsimus(küsimuseIndeks, küsimused, küss);

        Button nupp_kontrolli = new Button("Kontrolli");
        Button nupp_lähtesta = new Button("Lähtesta");
        Button nupp_edasi = new Button("Edasi");

        nupp_edasi.setOnMouseClicked(event -> {
            inkrementeeri(küsimuseIndeks, küsimused.length-1);
            väärtusta(küsimuseIndeks, vastusevariandid, vastused);
            paneküsimus(küsimuseIndeks, küsimused, küss);

            for(CheckBox c : vastusevariandid){
                c.setSelected(false);
                c.setStyle("-fx-background-color: none; "); // natuke css-i ka
            }
            });

        nupp_lähtesta.setOnMouseClicked(event -> {
            for(CheckBox c : vastusevariandid){
                c.setSelected(false);
                c.setStyle("-fx-background-color: none; ");
            }
        });

        nupp_kontrolli.setOnMouseClicked(event -> {
            int i = 0;
            for(CheckBox c : vastusevariandid){
                if((c.isSelected() && vastusteMask[küsimuseIndeks.getIndeks()][i])
                    || (!c.isSelected() && !vastusteMask[küsimuseIndeks.getIndeks()][i])){
                    c.setStyle("-fx-background-color: limegreen; ");
                }
                else c.setStyle("-fx-background-color: orangered; ");
                i++;
            }
        });

        FlowPane nupupaigutus = new FlowPane(nupp_lähtesta, nupp_kontrolli, nupp_edasi);
        nupupaigutus.setHgap(100);
        paan.setBottom(nupupaigutus);

        Scene stseen1 = new Scene(paan, 480, 640, Color.DARKGRAY);

        peaLava.setTitle("Küsimused, millele peaks mõtlema");
        peaLava.setHeight(480);
        peaLava.setWidth(640);
        peaLava.setScene(stseen1);
        peaLava.show();
    }
}
