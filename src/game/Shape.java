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

    public Shape() {
        this.shape = setRandomShape();
        this.setCoords(this.shape);
    }

    public int[][] getCoords() {
        return coords;
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

    private Tetrominoes setRandomShape() {
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
                        {0, color, 0},
                        {0, color, 0},
                        {0, color, 0},
                        {0, color, 0},
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