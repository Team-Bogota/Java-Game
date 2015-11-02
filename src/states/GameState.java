package states;

import display.Display;
import game.InputHandler;
import game.Shape;
import gfx.ImageLoader;
import gfx.SpriteSheet;

import java.awt.*;
import java.awt.image.BufferStrategy;

// This is the main state // AleksandarTanev
public class GameState extends State {

    private Display display;
    private BufferStrategy bs;
    private Graphics graphics;
    private SpriteSheet spsh;
    private InputHandler inputHandler;

    private int[][] board;

    private Shape currentShape;
    private Shape nextShape;

    private int ticks = 0;
    private int speed = 15;

    public GameState(String title, int width, int height) {

        this.display = new Display("GameState", title, width, height);
        this.spsh = new SpriteSheet(ImageLoader.loadImage("/images/blocks.png"));
        this.inputHandler = new InputHandler(display);
        this.board = new int[20][10];

        this.currentShape = new Shape();
        this.nextShape = new Shape();
    }


    @Override
    public void tick() {

        //here goes shape moves logic

        //check if element has reached bottom of the board or another element
        if (ticks >= speed) {

            if (canMove(currentShape, 0, 1)) {
                currentShape.setY(currentShape.getY() + 1);

            } else {
                int shapeX = currentShape.getX();
                int shapeY = currentShape.getY();

                for (int row = 0; row < currentShape.getCoords().length; row++) {
                    for (int col = 0; col < currentShape.getCoords()[row].length; col++) {
                        int blockColor = currentShape.getCoords()[row][col];
                        int blockX = shapeX + col;
                        int blockY = shapeY + row;
                        if (blockColor != 0) {
                            board[blockY][blockX] = blockColor;
                        }
                    }
                }
                this.currentShape = this.nextShape;
                this.nextShape = new Shape();
            }

            ticks = 0;
        }

        //check for move to left
        if (inputHandler.left) {

            if (canMove(currentShape, -1, 0)) {
                currentShape.setX(currentShape.getX() - 1);
            }
        }
        //check for move to right
        if (inputHandler.right) {

            if (canMove(currentShape, 1, 0)) {
                currentShape.setX(currentShape.getX() + 1);
            }
        }
        //check for move down
        if (inputHandler.down) {

            if (canMove(currentShape, 0, 1)) {
                currentShape.setY(currentShape.getY() + 1);
            }
        }
        //check for rotate
        if (inputHandler.rotate) {
            currentShape.rotateLeft();
        }

        ticks++;
    }

    @Override
    public void render() {

        this.bs = this.display.getCanvas().getBufferStrategy();

        if (this.bs == null) {
            this.display.getCanvas().createBufferStrategy(2);
            return;
        }
        graphics = this.bs.getDrawGraphics();

        graphics.drawImage(ImageLoader.loadImage("/images/background.png"), 0, 25, null);

        // PRINT ALL BLOCKS FROM THE GAME BOARD
        for (int row = 0; row < this.board.length; row++) {
            for (int col = 0; col < this.board[row].length; col++) {
                int color = this.board[row][col];
                this.graphics.drawImage(this.spsh.crop(color * 24, 0, 24, 25), (1 + col) * 24, (2 + row) * 24, null);
            }
        }
        // PRINT CURRENT ELEMENT
        for (int row = 0; row < this.currentShape.getCoords().length; row++) {
            for (int col = 0; col < this.currentShape.getCoords()[row].length; col++) {
                int color = this.currentShape.getCoords()[row][col];
                if (color != 0) {
                    this.graphics.drawImage(this.spsh.crop(color * 24, 0, 24, 25), (1 + col + currentShape.getX()) * 24, (2 + row + currentShape.getY()) * 24, null);
                }
            }
        }
        // PRINT NEXT ELEMENT
        for (int row = 0; row < this.nextShape.getCoords().length; row++) {
            for (int col = 0; col < this.nextShape.getCoords()[row].length; col++) {
                int color = this.nextShape.getCoords()[row][col];
                if (color != 0) {
                    this.graphics.drawImage(this.spsh.crop(color * 24, 0, 24, 25), 322 + col * 24, 100 + row * 24, null);
                }
            }
        }

        //this.graphics.setFont(statsFont);
        //this.graphics.drawString("12", 270, 254);
        //this.graphics.drawString("3698", 270, 304);
        //this.graphics.drawString("987", 270, 354);

        this.bs.show();
        graphics.dispose();
    }

    private boolean canMove(Shape shape, int deltaX, int deltaY) {

        int shapeX = shape.getX();
        int shapeY = shape.getY();

        for (int row = 0; row < shape.getCoords().length; row++) {
            for (int col = 0; col < shape.getCoords()[row].length; col++) {

                int blockColor = shape.getCoords()[row][col];
                int blockX = shapeX + col;
                int blockY = shapeY + row;

                if (blockColor != 0 && (blockY + deltaY >= this.board.length || blockX + deltaX < 0 || blockX + deltaX >= this.board[blockY].length)) {
                    return false;
                } else if (blockColor != 0 && blockY + deltaY >= 0 && this.board[blockY + deltaY][blockX + deltaX] != 0) {
                    return false;
                }
            }
        }

        return true;
    }
}