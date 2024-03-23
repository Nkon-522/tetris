package org.nkon.tetris.Entities;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

public class ScoreBoard {
    Canvas canvas;
    @SuppressWarnings("FieldCanBeLocal")
    private final int WIDTH = 200;

    @SuppressWarnings("FieldCanBeLocal")
    private final int HEIGHT = 300;
    private final Font font = new Font("Arial",30);
    private int x;
    private int y;
    public static int level = 1;
    public static int lines;
    public static int score;

    private void updateValues() {
        x =  ( (int) (canvas.getWidth()/2) - (Board.WIDTH/2)) + Board.WIDTH + 100;
        y = 50;
    }

    public ScoreBoard(Canvas canvas) {
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
        graphicsContext.fillText("LEVEL: "+level, x+20, y+75);
        graphicsContext.fillText("LINES: "+lines, x+20, y+150);
        graphicsContext.fillText("SCORE: "+score, x+20, y+225);

    }
}
