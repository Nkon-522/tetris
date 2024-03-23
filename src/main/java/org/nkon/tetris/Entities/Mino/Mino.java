package org.nkon.tetris.Entities.Mino;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import org.nkon.tetris.Managers.KeyHandlerManager;

import java.util.Random;

abstract public class Mino {
    @SuppressWarnings("FieldCanBeLocal")
    private final int margin = 4;
    @SuppressWarnings("FieldCanBeLocal")
    public static int dropInterval = 120;
    private int autoDropCounter = 0;
    private boolean leftCollision, rightCollision, bottomCollision;
    @SuppressWarnings("FieldCanBeLocal")
    public boolean active = true;
    public boolean deactivating;
    private int deactivateCounter = 0;
    public static int leftBorder, rightBorder, bottomBorder;
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
        for (int i = 0; i < tempB.length; i++) {
            b[i].x = tempB[i].x;
            b[i].y = tempB[i].y;
        }
    }

    private void handleRotation() {
        int new_direction = (direction + 1) % 4;
        switch (new_direction) {
            case 0 -> getDirection0();
            case 1 -> getDirection1();
            case 2 -> getDirection2();
            case 3 -> getDirection3();
        }
        checkRotationCollision();
        if (!rightCollision && !leftCollision && !bottomCollision) {
            updateBlocks();
            direction = new_direction;
        }
    }

    private void checkRotationCollision() {
        leftCollision = rightCollision = bottomCollision = false;
        checkStaticBlockCollision();
        for (Block block : tempB) {
            if (block.x < leftBorder) {
                leftCollision = true;
            }
            if (block.x + Block.SIZE > rightBorder) {
                rightCollision = true;
            }
            if (block.y + Block.SIZE > bottomBorder) {
                bottomCollision = true;
            }
        }
    }

    private void checkStaticBlockCollision() {
        for (int i = 0; i < Block.staticBlocks.size(); i++) {
            int targetX = Block.staticBlocks.get(i).x;
            int targetY = Block.staticBlocks.get(i).y;
            for (Block block : b) {
                if (block.y + Block.SIZE == targetY && block.x == targetX) {
                    bottomCollision = true;
                }
                if (block.x - Block.SIZE == targetX && block.y == targetY) {
                    leftCollision = true;
                }
                if (block.x + Block.SIZE == targetX && block.y == targetY) {
                    rightCollision = true;
                }
            }
        }
    }

    private void checkMovementCollision() {
        leftCollision = rightCollision = bottomCollision = false;
        checkStaticBlockCollision();
        for (Block block : b) {
            if (block.x == leftBorder) {
                leftCollision = true;
            }
            if (block.x + Block.SIZE == rightBorder) {
                rightCollision = true;
            }
            if (block.y + Block.SIZE + margin >= bottomBorder) {
                bottomCollision = true;
            }
        }
    }

    private void deactiveBlock() {
        deactivateCounter++;
        if (deactivateCounter == 45) {
            deactivateCounter = 0;
            checkMovementCollision();

            if (bottomCollision) {
                active = false;
            }
        }
    }

    public void update() {
        if (deactivating) {
            deactiveBlock();
        }

        if (KeyHandlerManager.upPressed) {
            handleRotation();
            KeyHandlerManager.upPressed = false;
        }

        checkMovementCollision();
        if (KeyHandlerManager.leftPressed) {
            if (!leftCollision) {
                for (Block block: b) {
                    block.x -= Block.SIZE;
                }
            }
            KeyHandlerManager.leftPressed = false;
        }
        if (KeyHandlerManager.rightPressed) {
            if (!rightCollision) {
                for (Block block: b) {
                    block.x += Block.SIZE;
                }
            }
            KeyHandlerManager.rightPressed = false;
        }
        if (KeyHandlerManager.downPressed) {
            if (!bottomCollision) {
                for (Block block: b) {
                    block.y += Block.SIZE;
                }
                autoDropCounter = 0;
            }
            KeyHandlerManager.downPressed = false;
        }
        if (bottomCollision) {
            this.deactivating = true;

        } else {
            handleDrop();
        }
    }

    public void draw(GraphicsContext graphicsContext){
        graphicsContext.setFill(b[0].getC());
        for (Block block : b) {
            block.draw(graphicsContext);
        }
    }

    public static Mino getRandomMino() {
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

    public Block[] getBlocks() {
        return b;
    }
}
