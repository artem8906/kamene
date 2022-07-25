package consoleui;

import core.Field;
import core.GameState;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;


public class ConsoleUI {

    private Field field;

    long startTime = System.currentTimeMillis();

    private BufferedReader input = new BufferedReader(new InputStreamReader(System.in));

    private String readLine() {
        try {
            return input.readLine();
        } catch (IOException e) {
            return null;
        }
    }

    public void newGameStarted(Field field) {
        this.field = field;

        do {
            update();
            processInput();
            field.savefield();

            if (field.isSolved()) {
                System.out.println("You win");
                System.exit(1);
            }
        } while (true);
    }


    public int getTimeOfPlay() {
        return (int) (System.currentTimeMillis() - startTime) / 1000;
    }

    public void update() {
        field.updateState();
        for (int i = 0; i < field.getTiles().length; i++) {
            for (int j = 0; j < field.getTiles().length; j++) {
                System.out.print(field.getTiles()[i][j]);
            }
            System.out.println();
        }
    }

    private void processInput() {
        System.out.printf("Time of play %d seconds\n", getTimeOfPlay());
        System.out.println("Press 'w', 's', 'a', 'd' for moving tiles, 'new' for new game or 'exit' for exit");
        String line = readLine();

        try {
            Input(line);
        } catch (WrongUserInputException e) {
            System.out.println("Try again!");
            processInput();
        }
    }

    private void Input(String input) throws WrongUserInputException {
        String action = input;
        switch (action) {
            case "w":
                field.move("UP");
                break;

            case "s":
                field.move("DOWN");
                break;

            case "a":
                field.move("LEFT");
                break;

            case "d":
                field.move("RIGHT");
                break;

            case "new":
                newGameStarted(new Field(4));
                break;
            case "exit":
                System.exit(1);
                break;

            default:
                throw new WrongUserInputException();
        }
    }
}


