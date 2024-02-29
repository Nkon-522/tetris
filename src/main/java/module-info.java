module org.nkon.tetris {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;

    opens org.nkon.tetris to javafx.fxml;
    exports org.nkon.tetris;
    exports org.nkon.tetris.Controllers;
    opens org.nkon.tetris.Controllers to javafx.fxml;
}