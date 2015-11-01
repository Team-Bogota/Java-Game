package states;

import display.Display;
import game.*;
import game.Shape;
import gfx.ImageLoader;

import java.awt.*;
import java.awt.image.BufferStrategy;
import java.util.Random;

import gfx.SpriteSheet;

// This is the main state // AleksandarTanev
public class GameState extends State {

    private Display display;
    private BufferStrategy bs;
    private Graphics graphics;
    private SpriteSheet spsh;

    private int[][] board;

    private Shape currentShape;
    private Shape nextShape;

    private int x;
    private int y;


    private Font statsFont = new Font("Monospaced", Font.BOLD | Font.ITALIC, 22);


    public GameState(String title, int width, int height) {

        this.display = new Display("GameState", title, width, height);
        this.spsh = new SpriteSheet(ImageLoader.loadImage("/images/blocks.png"));

        this.board = new int[20][10];

        this.currentShape = new Shape();
        this.nextShape = new Shape();
    }


    @Override
    public void tick() {
        try {
            currentShape.setY(currentShape.getY() + 1);
            if (currentShape.getY() > 18) {
                this.currentShape = this.nextShape;
                this.nextShape = new Shape();
            }
            Thread.sleep(400);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
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
}