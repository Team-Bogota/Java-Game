package game;

import java.util.Random;

public class Shape {
    private enum Tetrominoes {
        NoShape,
        IShape,
        OShape,
        TShape,
        JShape,
        LShape,
        SShape,
        ZShape
    }

    ;

    private Tetrominoes shape;

    private int[][] coords;


    private int x;
    private int y;

    public Shape() {
        this.shape = getRandomShape();
        this.setCoords(this.shape);
        this.x = 4;
        this.y = 0;
    }

    public int[][] getCoords() {
        return coords;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public void rotateLeft() {
        int[][] rotatedCoords = new int[coords.length][coords[0].length];
        for (int row = 0; row < coords.length; row++) {
            for (int col = 0; col < coords[0].length; col++) {
                rotatedCoords[col][row] = this.coords[row][col];
            }
        }
        coords = rotatedCoords;
    }

    public void rotateRight() {
        int[][] rotatedCoords = new int[coords.length][coords[0].length];
        for (int row = 0; row < coords.length; row++) {
            for (int col = 0; col < coords[0].length; col++) {
                rotatedCoords[col][coords.length - 1 - row] = this.coords[row][col];
            }
        }
        coords = rotatedCoords;
    }

    private Tetrominoes getRandomShape() {
        Random rnd = new Random();
        int x = rnd.nextInt(7) + 1;
        Tetrominoes[] values = Tetrominoes.values();
        return values[x];
    }

    private void setCoords(Tetrominoes shape) {
        int color = shape.ordinal();
        switch (shape) {
            case IShape:
                this.coords = new int[][]{
                        {0, color, 0, 0},
                        {0, color, 0, 0},
                        {0, color, 0, 0},
                        {0, color, 0, 0},
                };
                break;
            case OShape:
                this.coords = new int[][]{
                        {color, color},
                        {color, color},
                };
                break;
            case TShape:
                this.coords = new int[][]{
                        {0, color, 0},
                        {color, color, color},
                        {0, 0, 0},
                };
                break;
            case JShape:
                this.coords = new int[][]{
                        {0, color, 0},
                        {0, color, 0},
                        {color, color, 0},
                };
                break;
            case LShape:
                this.coords = new int[][]{
                        {0, color, 0},
                        {0, color, 0},
                        {0, color, color},
                };
                break;
            case SShape:
                this.coords = new int[][]{
                        {0, color, color},
                        {color, color, 0},
                        {0, 0, 0},
                };
                break;
            case ZShape:
                this.coords = new int[][]{
                        {color, color, 0},
                        {0, color, color},
                        {0, 0, 0},
                };
                break;
        }
    }
}