package org.nkon.tetris.Entities.Mino;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import org.nkon.tetris.Managers.KeyHandlerManager;

abstract public class Mino {
    @SuppressWarnings("FieldCanBeLocal")
    private final int dropInterval = 120;
    private int autoDropCounter = 0;
    protected int direction = 0;

    protected Block[] b = new Block[4];
    protected Block[] tempB = new Block[4];

    protected Mino(Color c) {
        for (int i = 0; i < b.length; i++) {
            b[i] = new Block(c);
            tempB[i] = new Block(c);
        }
    }

    abstract public void setXY(int x, int y);

    private void handleDrop(){
        autoDropCounter++;
        if (autoDropCounter == dropInterval) {
            for (Block block: b) {
                block.y += Block.SIZE;
            }
            autoDropCounter = 0;
        }
    }

    abstract protected void getDirection0();
    abstract protected void getDirection1();
    abstract protected void getDirection2();
    abstract protected void getDirection3();

    private void updateBlocks() {
        System.arraycopy(tempB, 0, b, 0, b.length);
    }

    private void handleRotation() {
        direction = (direction + 1) % 4;
        switch (direction) {
            case 0 -> getDirection0();
            case 1 -> getDirection1();
            case 2 -> getDirection2();
            case 3 -> getDirection3();
        }
        updateBlocks();
    }

    public void update() {
        if (KeyHandlerManager.leftPressed) {
            for (Block block: b) {
                block.x -= Block.SIZE;
            }
            KeyHandlerManager.leftPressed = false;
        }
        if (KeyHandlerManager.rightPressed) {
            for (Block block: b) {
                block.x += Block.SIZE;
            }
            KeyHandlerManager.rightPressed = false;
        }
        if (KeyHandlerManager.downPressed) {
            for (Block block: b) {
                block.y += Block.SIZE;
            }
            autoDropCounter = 0;
            KeyHandlerManager.downPressed = false;
        }
        if (KeyHandlerManager.upPressed) {
            handleRotation();
            KeyHandlerManager.upPressed = false;
        }

        handleDrop();
    }

    public void draw(GraphicsContext graphicsContext){
        graphicsContext.setFill(b[0].getC());
        for (Block block : b) {
            block.draw(graphicsContext);
        }
    }
}
