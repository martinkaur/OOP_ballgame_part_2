module projekt.pallikorjaja {
    requires javafx.controls;
    requires javafx.fxml;


    opens projekt.pallikorjaja to javafx.fxml;
    exports projekt.pallikorjaja;
}