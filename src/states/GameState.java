package states;

import com.sun.corba.se.impl.orbutil.ObjectWriter;
import display.Display;
import game.Config;
import game.InputHandler;
import game.Shape;
import gfx.ImageLoader;
import gfx.SpriteSheet;

import java.awt.*;
import java.awt.image.BufferStrategy;
import java.io.*;
import java.util.Objects;

// This is the main state // AleksandarTanev
public class GameState extends State {

    private Display display;
    private BufferStrategy bs;
    private Graphics graphics;
    private SpriteSheet spsh;
    private InputHandler inputHandler;

    private int score;
    private int lines;
    private int level;
    private int[][] board;

    private Shape currentShape;
    private Shape nextShape;

    private int ticks;
    private int speed;
    public static boolean isSaved;
    public static boolean isLoaded;


    public GameState(String title, int width, int height) {

        this.display = new Display("GameState", title, width, height);

        init();
    }

    //initialize all fields in this method and use it when start new game
    private void init() {
        this.score = 0;
        this.lines = 0;
        this.level = 1;
        this.ticks = 0;
        this.speed = 15;
        this.spsh = new SpriteSheet(ImageLoader.loadImage("/images/blocks.png"));
        this.inputHandler = new InputHandler(display);
        this.board = new int[20][10];
        this.currentShape = new Shape();
        this.nextShape = new Shape();
        this.display.newGame = false;

        this.isSaved = false;
    }

    public void loadGame(){
        File directory = new File(Config.getDefaultDirectory(), "/Tetris");
        File save = new File(directory, "/save.save");

        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(save))) {
            this.score = ois.readInt();
            this.lines = ois.readInt();
            this.level = ois.readInt();
            this.ticks = ois.readInt();
            this.speed = ois.readInt();

            this.board = (int[][])ois.readObject();
            this.currentShape = (Shape) ois.readObject();
            this.nextShape = (Shape) ois.readObject();
            this.display.newGame = ois.readBoolean();

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    //create file if dont exist and write current key config
    public void saveGame() {
        File directory = new File(Config.getDefaultDirectory(), "/Tetris");
        if (!directory.exists()) {
            directory.mkdirs();
        }
        File save = new File(directory, "/save.save");

        try (ObjectOutputStream oos =
                     new ObjectOutputStream(new BufferedOutputStream(
                             new FileOutputStream(save)))) {
            oos.writeInt(this.score);
            oos.writeInt(this.lines);
            oos.writeInt(this.level);
            oos.writeInt(this.ticks);
            oos.writeInt(this.speed);

            oos.writeObject(this.board);
            oos.writeObject(this.currentShape);
            oos.writeObject(this.nextShape);
            oos.writeBoolean(this.display.newGame);

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void tick() {

        if (display.newGame) {
            init();
        }

        if (isSaved){
            saveGame();
            isSaved = false;
        }

        if (isLoaded){
            loadGame();
            isLoaded = false;
        }

        //update speed
        this.speed = 15 - this.level;

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
                            this.board[blockY][blockX] = blockColor;
                        }
                    }
                }
                this.score += 2;
                RemoveSolidLine();
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
            inputHandler.left = false;
        }
        //check for move to right
        if (inputHandler.right) {

            if (canMove(currentShape, 1, 0)) {
                currentShape.setX(currentShape.getX() + 1);
            }
            inputHandler.right = false;
        }
        //check for move down
        if (inputHandler.down) {

            if (canMove(currentShape, 0, 1)) {
                currentShape.setY(currentShape.getY() + 1);
            }
            inputHandler.down = false;
        }
        //check for rotate
        if (inputHandler.rotate) {

            if (canRotate(currentShape)) {
                currentShape.rotateClockwise();
            }
            inputHandler.rotate = false;
        }

        if (inputHandler.instantDown) {
            while (canMove(currentShape, 0, 1)) {
                currentShape.setY(currentShape.getY() + 1);
            }
            inputHandler.instantDown = false;
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

        //Print Score, Lines and Level
        this.graphics.setFont(new Font("/fonts/BRADHITC.TTF", Font.BOLD, 20));
        int fontWidth = 8;
        String lv = String.format("%d", this.level);
        String ln = String.format("%d", this.lines);
        String sc = String.format("%d", this.score);
        this.graphics.drawString(lv, 350 - lv.length() / 2 * fontWidth, 330);
        this.graphics.drawString(ln, 350 - ln.length() / 2 * fontWidth, 390);
        this.graphics.drawString(sc, 350 - sc.length() / 2 * fontWidth, 450);

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

    private boolean canRotate(Shape shape) {
        try {
            Shape rotatedShape = (Shape) shape.clone();
            rotatedShape.rotateClockwise();

            int shapeX = rotatedShape.getX();
            int shapeY = rotatedShape.getY();

            for (int row = 0; row < rotatedShape.getCoords().length; row++) {
                for (int col = 0; col < rotatedShape.getCoords()[row].length; col++) {

                    int blockColor = rotatedShape.getCoords()[row][col];
                    int blockX = shapeX + col;
                    int blockY = shapeY + row;

                    if (blockColor != 0 && (blockY < 0 || blockY >= this.board.length || blockX < 0 || blockX >= this.board[blockY].length)) {
                        return false;
                    } else if (blockColor != 0 && this.board[blockY][blockX] != 0) {
                        return false;
                    }
                }
            }

        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
        }

        return true;
    }

    //fills the solid row with 0 and shifts it to the top
    public void RemoveSolidLine() {


        //int[] nullRow = new int[this.board[0].length];
        int counter = 0;

/*        for (int i = 0; i < nullRow.length; i++) {
            nullRow[i] = 0;
        }*/

        for (int row = 0; row < this.board.length; row++) {

            boolean isLine = true;

            for (int col = this.board[row].length - 1; col >= 0; col--) {

                if (this.board[row][col] == 0) {
                    isLine = false;
                    break;
                }
            }

            if (isLine) {
                //make the full row null
                //this.board[row] = nullRow;
                for (int col = 0; col < this.board[row].length; col++) {
                    this.board[row][col] = 0;
                }
                //update Score, Lines and Level
                counter++;
                this.lines++;
                this.level = (lines / 10) + 1;

                if (this.level > 10) {
                    this.level = 10;
                }


                int exchangedRow = row;
                // exchange rows
                for (int innerRow = row - 1; innerRow >= 0; innerRow--) {

                    //this.board[exchangedRow] = this.board[innerRow];
                    for (int col = 0; col < this.board[row].length; col++) {
                        this.board[exchangedRow][col] = this.board[innerRow][col];
                    }
                    //this.board[innerRow] = nullRow;
                    for (int col = 0; col < this.board[innerRow].length; col++) {
                        this.board[innerRow][col] = 0;
                    }

                    exchangedRow--;
                    if (exchangedRow < 0) {
                        break;
                    }
                }

                switch (counter) {
                    case 1:
                        score += 100;
                        break;
                    case 2:
                        score += 300;
                        break;
                    case 3:
                        score += 700;
                        break;
                    case 4:
                        score += 1100;
                        break;
                    default:
                        break;
                }

            }

        }

    }

    public boolean GameOver(){

        for (int i = 0; i < this.board[0].length; i++) {
            if (this.board[0][i] != 0){
                return true;
            }
        }

        return false;
    }
}