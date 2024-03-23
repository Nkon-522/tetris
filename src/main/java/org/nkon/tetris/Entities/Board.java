package org.nkon.tetris.Entities;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import org.nkon.tetris.Entities.Mino.Block;
import org.nkon.tetris.Entities.Mino.Mino;
import org.nkon.tetris.Managers.KeyHandlerManager;

import java.util.ArrayList;
import java.util.Arrays;

public class Board {

    Canvas canvas;
    static public final int WIDTH = 360;
    static public final int HEIGHT = 608;
    private int x;
    private int y;

    private boolean effectCounterOn;
    private int effectCounter;
    private final ArrayList<Integer> effectY = new ArrayList<>();

    private Mino currentMino;
    @SuppressWarnings("FieldCanBeLocal")
    private int MINO_X_START_POSITION;
    @SuppressWarnings("FieldCanBeLocal")
    private int MINO_Y_START_POSITION;

    private void updateValues() {
        x =  ( (int) (canvas.getWidth()/2) - (WIDTH/2));
        y = 50;
        Mino.leftBorder = x;
        Mino.rightBorder = x + WIDTH;
        Mino.bottomBorder = y + HEIGHT;

        MINO_X_START_POSITION = x + (WIDTH/2) - Block.SIZE;
        MINO_Y_START_POSITION = y + Block.SIZE + 4;

        currentMino = Mino.getRandomMino();
        currentMino.setXY(MINO_X_START_POSITION, MINO_Y_START_POSITION);
    }

    public Board(Canvas canvas) {
        this.canvas = canvas;
        canvas.widthProperty().addListener((observableValue, number, t1) -> updateValues());
        canvas.heightProperty().addListener((observableValue, number, t1) -> updateValues());
    }

    public void checkDelete() {
        int x = this.x;
        int y = this.y + 4;
        int blockCount = 0;

        while (x < this.x + WIDTH && y < this.y + HEIGHT) {
            for (int i = 0; i < Block.staticBlocks.size(); i++) {
                if (Block.staticBlocks.get(i).getX() == x && Block.staticBlocks.get(i).getY() == y) {
                    blockCount++;
                }
            }
            x += Block.SIZE;

            if (x == this.x + WIDTH) {

                if (blockCount == 12) {

                    effectCounterOn = true;
                    effectY.add(y);

                    for (int i = Block.staticBlocks.size() - 1; i > -1; i--) {
                        if (Block.staticBlocks.get(i).getY() == y) {
                            Block.staticBlocks.remove(i);
                        }
                    }

                    for (int i = 0; i < Block.staticBlocks.size(); i++) {
                        if (Block.staticBlocks.get(i).getY() < y) {
                            Block.staticBlocks.get(i).setY(Block.staticBlocks.get(i).getY() + Block.SIZE);
                        }
                    }
                }

                blockCount = 0;
                x = this.x;
                y += Block.SIZE;
            }
        }
    }

    public void update() {
        if (!currentMino.active) {
            Block.staticBlocks.addAll(Arrays.asList(currentMino.getBlocks()));

            currentMino.deactivating = false;

            currentMino = BlockBench.getNextMino();
            currentMino.setXY(MINO_X_START_POSITION, MINO_Y_START_POSITION);

            checkDelete();
        } else {
            currentMino.update();
        }
    }

    public void draw(GraphicsContext graphicsContext) {
        graphicsContext.setStroke(Color.WHITE);
        graphicsContext.setLineWidth(4);
        graphicsContext.strokeRect(x,y, WIDTH, HEIGHT);
        if (currentMino != null) {
            currentMino.draw(graphicsContext);
        }

        for (Block staticBlock : Block.staticBlocks) {
            staticBlock.draw(graphicsContext);
        }

        if (effectCounterOn) {
            effectCounter++;

            for (Integer integer : effectY) {
                if (effectCounter % 2 == 0) {
                    graphicsContext.setFill(Color.WHITE);

                } else {
                    graphicsContext.setFill(Color.RED);
                }
                graphicsContext.fillRect(this.x, integer, WIDTH, Block.SIZE);
            }
            if (effectCounter == 10) {
                effectCounterOn = !effectCounterOn;
                effectCounter = 0;
                effectY.clear();
            }
        }

        if (KeyHandlerManager.pausePressed) {
            graphicsContext.setFill(Color.YELLOW);
            graphicsContext.setFont(new Font("Arial",50));
            graphicsContext.fillText("PAUSED", x + 70, y + 320);
        }
    }
}
