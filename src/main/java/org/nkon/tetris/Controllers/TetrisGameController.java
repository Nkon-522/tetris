package org.nkon.tetris.Controllers;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.canvas.Canvas;
import javafx.scene.layout.Pane;
import org.nkon.tetris.Managers.GameManager;

import java.net.URL;
import java.util.ResourceBundle;

public class TetrisGameController implements Initializable {
    GameManager gameManager;

    @SuppressWarnings("unused")
    @FXML
    private Canvas gameCanvas;

    @SuppressWarnings("unused")
    @FXML
    private Pane gamePane;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        gameCanvas.widthProperty().bind(gamePane.widthProperty());
        gameCanvas.heightProperty().bind(gamePane.heightProperty());
        gameCanvas.sceneProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                gameManager = new GameManager(gameCanvas);
                gameManager.launchGame();
            }
        });
    }
}