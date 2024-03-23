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

    private boolean gameOver;

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
        int lineCount = 0;

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

                    lineCount++;
                    ScoreBoard.lines++;

                    if (ScoreBoard.lines % 10 == 0 && Mino.dropInterval > 1) {
                        ScoreBoard.level++;
                        if (Mino.dropInterval > 10) {
                            Mino.dropInterval -= 10;
                        } else {
                            Mino.dropInterval -= 1;
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
        if (lineCount > 0) {
            int singleLineScore = 10 * ScoreBoard.level;
            ScoreBoard.score += singleLineScore * lineCount;
        }
    }

    public void update() {
        if (!currentMino.active) {
            Block.staticBlocks.addAll(Arrays.asList(currentMino.getBlocks()));

            if (currentMino.getBlocks()[0].getX() == MINO_X_START_POSITION && currentMino.getBlocks()[0].getY() == MINO_Y_START_POSITION) {
                gameOver = true;
            }

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

        if (gameOver) {
            graphicsContext.setFill(Color.FLORALWHITE);
            graphicsContext.setFont(new Font("Arial",50));
            graphicsContext.fillText("GAME OVER", x + 40, y + 320);
        } else if (KeyHandlerManager.pausePressed) {
            graphicsContext.setFill(Color.YELLOW);
            graphicsContext.setFont(new Font("Arial",50));
            graphicsContext.fillText("PAUSED", x + 50, y + 320);
        }
    }

    public boolean isGameOver() {
        return gameOver;
    }
}
