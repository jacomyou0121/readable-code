package cleancode.minesweeper.tobe.io;

import java.util.Scanner;

public class ConsoleInputHandler {
    public static final Scanner SCANNER = new Scanner(System.in); //상수가 된 부분을 사용

    public String getUserInput() {
        return  SCANNER.nextLine();
    }
}
