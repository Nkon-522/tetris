package org.nkon.tetris.Managers;

import javafx.animation.AnimationTimer;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import org.nkon.tetris.Entities.Background;
import org.nkon.tetris.Entities.BlockBench;
import org.nkon.tetris.Entities.Board;
import org.nkon.tetris.Entities.ScoreBoard;

public class GameManager implements Runnable {

    private final Canvas canvas;
    private final GraphicsContext graphicsContext;
    private final Scene gameScene;

    @SuppressWarnings({"unused", "FieldCanBeLocal"})
    private KeyHandlerManager keyHandlerManager;

    @SuppressWarnings("FieldCanBeLocal")
    private Thread gameThread;

    private final Background background;
    private final Board board;
    private final BlockBench blockBench;
    private final ScoreBoard scoreBoard;

    private void initializeEventHandler() {
        keyHandlerManager = new KeyHandlerManager(gameScene);
    }

    public GameManager(final Canvas canvas) {
        this.canvas = canvas;
        this.gameScene = this.canvas.getScene();
        this.graphicsContext = this.canvas.getGraphicsContext2D();
        // Entities
        background = new Background(canvas);
        board = new Board(canvas);
        blockBench = new BlockBench(canvas);
        scoreBoard = new ScoreBoard(canvas);

        // Initializers
        initializeEventHandler();
    }

    private void clearCanvas() {
        graphicsContext.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
        background.draw(graphicsContext);
    }

    private void draw() {
        clearCanvas();
        background.draw(graphicsContext);
        board.draw(graphicsContext);
        blockBench.draw(graphicsContext);
        scoreBoard.draw(graphicsContext);
    }

    private void update() {
        if (!KeyHandlerManager.pausePressed && !board.isGameOver()) {
            board.update();
        }
    }

    public void launchGame() {
        gameThread = new Thread(this);
        gameThread.start();

        SoundManager.playBackground();
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
