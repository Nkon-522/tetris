package org.nkon.tetris.Entities;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import org.nkon.tetris.Entities.Mino.*;

import java.util.Random;

public class Board {

    Canvas canvas;
    static public final int WIDTH = 360;
    static public final int HEIGHT = 600;

    private int x;
    private int y;

    private Mino currentMino;
    @SuppressWarnings("FieldCanBeLocal")
    private int MINO_X_START_POSITION;
    @SuppressWarnings("FieldCanBeLocal")
    private int MINO_Y_START_POSITION;

    private Mino getRandomMino() {
        return switch (new Random().nextInt(7)) {
            case 0 -> new MinoL1();
            case 1 -> new MinoL2();
            case 2 -> new MinoSquare();
            case 3 -> new MinoBar();
            case 4 -> new MinoT();
            case 5 -> new MinoZ1();
            case 6 -> new MinoZ2();
            default -> throw new IllegalStateException("Unexpected value: " + new Random().nextInt(7));
        };
    }

    private void updateValues() {
        x =  ( (int) (canvas.getWidth()/2) - (WIDTH/2));
        y = 50;
        MINO_X_START_POSITION = x + (WIDTH/2) - Block.SIZE;
        MINO_Y_START_POSITION = y + Block.SIZE + 4;
        currentMino = getRandomMino();
        currentMino.setXY(MINO_X_START_POSITION, MINO_Y_START_POSITION);
    }

    public Board(Canvas canvas) {
        this.canvas = canvas;
        canvas.widthProperty().addListener((observableValue, number, t1) -> updateValues());
        canvas.heightProperty().addListener((observableValue, number, t1) -> updateValues());

    }

    public void update() {
        currentMino.update();
    }

    public void draw(GraphicsContext graphicsContext) {
        graphicsContext.setStroke(Color.WHITE);
        graphicsContext.setLineWidth(4);
        graphicsContext.strokeRect(x,y, WIDTH, HEIGHT);
        if (currentMino != null) {
            currentMino.draw(graphicsContext);
        }
    }
}
