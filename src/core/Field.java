package core;

import java.io.*;
import java.util.Random;

public class Field implements Serializable {
    /**
     * Playing field tiles.
     */
    private final Tile[][] tiles;

    public Tile[][] getTiles() {
        return tiles;
    }

    private final int size;

    /**
     * Game state.
     */
    private GameState state = GameState.PLAYING;

    public Field(int size) {
        this.size = size;
        tiles = new Tile[size][size];

        //generate the field content
        generate();
    }


    public GameState getState() {
        return state;
    }

    public Tile getTiles(int row, int column) {
        return tiles[row][column];
    }

    public void updateState() {
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                tiles[i][j].setState(null);
            }
        }

        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                Tile tile = tiles[i][j];
                if ((j + 1 < size) && tiles[i][j + 1].getValue() == 0) tile.setState(Tile.State.ABLEMOVETORIGHT);
                if ((j - 1 >= 0) && tiles[i][j - 1].getValue() == 0) tile.setState(Tile.State.ABLEMOVETOLEFT);
                if ((i + 1 < size) && tiles[i + 1][j].getValue() == 0) tile.setState(Tile.State.ABLEMOVETODOWN);
                if ((i - 1 >= 0) && tiles[i - 1][j].getValue() == 0) tile.setState(Tile.State.ABLEMOVETOUP);
            }
        }

    }

    public void move(String action) {
        int tempValue;

        switch (action) {
            case "DOWN":
                for (int i = 0; i < size; i++) {
                    for (int j = 0; j < size; j++) {
                        if (tiles[i][j].getState() == Tile.State.ABLEMOVETOUP) {
                            tempValue = tiles[i][j].getValue();
                            tiles[i][j].setValue(tiles[i - 1][j].value);
                            tiles[i - 1][j].setValue(tempValue);
                            break;
                        }
                    }
                }
                break;

            case "UP":
                for (int i = 0; i < size; i++) {
                    for (int j = 0; j < size; j++) {
                        if (tiles[i][j].getState() == Tile.State.ABLEMOVETODOWN) {
                            tempValue = tiles[i][j].getValue();
                            tiles[i][j].setValue(tiles[i + 1][j].value);
                            tiles[i + 1][j].setValue(tempValue);
                            break;
                        }
                    }
                }
                break;

            case "RIGHT":
                for (int i = 0; i < size; i++) {
                    for (int j = 0; j < size; j++) {
                        if (tiles[i][j].getState() == Tile.State.ABLEMOVETOLEFT) {
                            int value = tiles[i][j].getValue();
                            tiles[i][j].setValue(tiles[i][j - 1].value);
                            tiles[i][j - 1].setValue(value);
                            break;
                        }
                    }
                }
                break;

            case "LEFT":
                for (int i = 0; i < size; i++) {
                    for (int j = 0; j < size; j++) {
                        if (tiles[i][j].getState() == Tile.State.ABLEMOVETORIGHT) {
                            int value = tiles[i][j].getValue();
                            tiles[i][j].setValue(tiles[i][j + 1].value);
                            tiles[i][j + 1].setValue(value);
                            break;
                        }
                    }
                }
                break;
        }
    }

    /**
     * Generates playing field.
     */
    private void generate() {
        Random rd = new Random();
        int count = 1;
        tiles[rd.nextInt(4)][rd.nextInt(4)] = new Tile();
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                Tile tile = new Tile();
                if (tiles[i][j] == null) {
                    tiles[i][j] = tile;
                    tile.setValue(count++);
                }
            }
        }
    }

    public boolean isSolved() {
        int count = 1;
        int idealCount = 1;
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                if (tiles[i][j].value == count)
                    idealCount++;
                count++;
            }
        }

        return count == idealCount;
    }

    public void savefield() {
        try (OutputStream is = new FileOutputStream("savings.txt");
             ObjectOutputStream ois = new ObjectOutputStream(is)) {
            ois.writeObject(this);
        } catch (IOException e) {
            e.getMessage();
        }
    }
}



