import consoleui.ConsoleUI;
import core.Field;

import java.io.*;

public class Kamene {
    private ConsoleUI userInterface;
    private final long startMillis = System.currentTimeMillis();
    private static Kamene instance;

    private Kamene() {
        Field field;
        try (InputStream is = new FileInputStream("savings.txt");
             ObjectInputStream ois = new ObjectInputStream(is)) {
            field = (Field) ois.readObject();
        } catch (Exception e) {
            field = new Field(4);
        }

        instance = this;
        userInterface = new ConsoleUI();
        userInterface.newGameStarted(field);
    }

    public static void main(String[] args) {
        new Kamene();
    }
}
