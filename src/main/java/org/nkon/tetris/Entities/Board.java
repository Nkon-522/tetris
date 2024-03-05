package org.nkon.tetris.Entities;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import org.nkon.tetris.Entities.Mino.Block;
import org.nkon.tetris.Entities.Mino.Mino;
import org.nkon.tetris.Entities.Mino.MinoL1;

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


    private void updateValues() {
        x =  ( (int) (canvas.getWidth()/2) - (WIDTH/2));
        y = 50;
        MINO_X_START_POSITION = x + (WIDTH/2) - Block.SIZE;
        MINO_Y_START_POSITION = y + Block.SIZE + 4;
        currentMino = new MinoL1();
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
