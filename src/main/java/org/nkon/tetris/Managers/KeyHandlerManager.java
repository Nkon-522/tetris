package org.nkon.tetris.Managers;

import javafx.scene.Scene;

public class KeyHandlerManager {

    @SuppressWarnings("FieldCanBeLocal")
    private final Scene gameScene;

    public static boolean rightPressed, upPressed, leftPressed, downPressed;
    public KeyHandlerManager(Scene gameScene) {
        this.gameScene = gameScene;

        this.gameScene.setOnKeyPressed(keyEvent -> {
            switch(keyEvent.getCode().toString()) {

                case "D","RIGHT"-> rightPressed = true;
                case "A","LEFT"-> leftPressed = true;
                case "W","UP"-> upPressed = true;
                case "S","DOWN"-> downPressed = true;
            }
        });
    }
}
