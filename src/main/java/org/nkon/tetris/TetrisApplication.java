package org.nkon.tetris;

import javafx.application.Application;
import javafx.stage.Stage;
import org.nkon.tetris.Managers.MainAppManager;

import java.io.IOException;

public class TetrisApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        MainAppManager.setMainApp(this);
        MainAppManager.setMainStage(stage);
        MainAppManager.loadNewScene("tetris-game-view.fxml");

        stage.setTitle("Simple Tetris!");
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}