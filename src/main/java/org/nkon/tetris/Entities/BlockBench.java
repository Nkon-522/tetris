package org.nkon.tetris.Entities;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

public class BlockBench {
    Canvas canvas;

    @SuppressWarnings("FieldCanBeLocal")
    private final int WIDTH = 200;

    @SuppressWarnings("FieldCanBeLocal")
    private final int HEIGHT = 200;

    private final Font font = new Font("Arial",30);

    private int x;
    private int y;

    private void updateValues() {
        x =  ( (int) (canvas.getWidth()/2) - (Board.WIDTH/2)) + Board.WIDTH + 100;
        y = 50 + Board.HEIGHT - 200;
    }

    public BlockBench(Canvas canvas) {
        this.canvas = canvas;
        canvas.widthProperty().addListener((observableValue, number, t1) -> updateValues());
        canvas.heightProperty().addListener((observableValue, number, t1) -> updateValues());
    }

    public void draw(GraphicsContext graphicsContext) {
        graphicsContext.setStroke(Color.WHITE);
        graphicsContext.setLineWidth(4);
        graphicsContext.strokeRect(x,y, WIDTH, HEIGHT);

        graphicsContext.setFont(font);
        graphicsContext.setFill(Color.WHITE);
        graphicsContext.fillText("NEXT", x + 20, y + 60);
    }
}
