package org.nkon.tetris.Managers;

import javafx.animation.AnimationTimer;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class GameManager implements Runnable {

    private Canvas canvas;
    private final GraphicsContext graphicsContext;
    private final Scene gameScene;

    private Thread gameThread;

    private void initializeEventHandler() {
        gameScene.setOnKeyPressed(keyEvent -> {
            switch(keyEvent.getCode().toString()) {
                case "RIGHT"-> {System.out.println("RIGHT");}
                case "LEFT"-> {System.out.println("LEFT");}
                case "UP"-> {System.out.println("UP");}
                case "DOWN"-> {System.out.println("DOWN");}
            }
        });
    }

    public GameManager(Canvas canvas) {
        this.canvas = canvas;
        this.gameScene = this.canvas.getScene();
        this.graphicsContext = this.canvas.getGraphicsContext2D();
        initializeEventHandler();
    }

    private void draw() {
        graphicsContext.setFill(new Color(0.5,0.5,0.5,0.5));
        graphicsContext.fillRect(0,0,100,100);
    }

    private void update() {

    }

    public void launchGame() {
        gameThread = new Thread(this);
        gameThread.start();
    }

    @Override
    public void run() {
        AnimationTimer animationTimer = new AnimationTimer() {
            @Override
            public void handle(long currentTime) {
                update();
                draw();
            }
        };

        animationTimer.start();
    }
}
