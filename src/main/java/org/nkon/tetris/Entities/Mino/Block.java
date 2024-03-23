package org.nkon.tetris.Entities.Mino;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

import java.util.ArrayList;

public class Block {
    public static ArrayList<Block> staticBlocks = new ArrayList<>();

    protected int x, y;
    public static final int SIZE = 30;
    private final Color c;

    @SuppressWarnings("FieldCanBeLocal")
    private final int margin = 2;

    public Block(Color c) {
        this.c = c;
    }

    public Color getC() {
        return c;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public void draw(GraphicsContext graphicsContext) {
        graphicsContext.setFill(c);
        graphicsContext.fillRect(x + margin, y + margin, Block.SIZE - (margin*2), Block.SIZE - (margin*2));
    }

}
