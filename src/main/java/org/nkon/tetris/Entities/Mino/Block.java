package org.nkon.tetris.Entities.Mino;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class Block {
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

    public void draw(GraphicsContext graphicsContext) {
        graphicsContext.setFill(c);
        graphicsContext.fillRect(x + margin, y + margin, Block.SIZE - (margin*2), Block.SIZE - (margin*2));
    }

}
