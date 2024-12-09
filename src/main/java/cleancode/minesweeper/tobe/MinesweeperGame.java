package cleancode.minesweeper.tobe;

import java.util.Arrays;
import java.util.Random;
import java.util.Scanner;

//메서드를 추상화하여 동등화
//실무에선 점진적 리펙토링
public class MinesweeperGame {
    public static final int BOARD_ROW_SIZE = 8;
    public static final int BOARD_COL_SIZE = 10;
    public static final Scanner SCANNER = new Scanner(System.in); //상수가 된 부분을 사용
    private static final Cell[][] BOARD = new Cell[BOARD_ROW_SIZE][BOARD_COL_SIZE];
    public static final int LAND_MINE_COUNT = 10;
     private static  int gameStatus = 0; // 0: 게임 중, 1: 승리, -1: 패배

    //공백라인 : 후손에게 이런 의미 구간으로 읽어라
    public static void main(String[] args) {
        showGameStartComments();
        initalizeGame();

        while (true) {
            try {
                showBoard();
                if (doesUserWinTheGame()) {
                    System.out.println("지뢰를 모두 찾았습니다. GAME CLEAR!");
                    break;
                }
                if (doesUserLoseTheGame()) {
                    System.out.println("지뢰를 밟았습니다. GAME OVER!");
                    break;
                }

                System.out.println();
                String cellInput = getCellInputFromUser();
                String userActionInput = getUserActionInputFromUser();


                actOnCell(cellInput, userActionInput);
            } catch (AppException e) {
                System.out.println(e.getMessage());
            } catch (Exception e) {
                System.out.println("프로그램에 문제 생김");
            }
        }
    }

    private static void actOnCell(String cellInput, String userActionInput) {
        //한줄이여도 추상화 함
        //의미가 더 중요
        int selectedColIndex = getSelectedColIndex(cellInput);
        int selectedRowIndex = getSelectedRowIndex(cellInput);

        if (doesUserChooesToPlantFlag(userActionInput)) {
            BOARD[selectedRowIndex][selectedColIndex].flag();
            checkIfGameIsOver();
            return;
        }

        if (docesUserChooseToOpenCell(userActionInput)) {
            if (isLandMineCell(selectedRowIndex, selectedColIndex)) {
                BOARD[selectedRowIndex][selectedColIndex].open();
                changeGameStatusToLose();
                return;
            }

            open(selectedRowIndex, selectedColIndex);
            checkIfGameIsOver();
            return;
        }
        throw new AppException("잘못된 번호를 선택하셨습니다.");

    }

    private static void changeGameStatusToLose() {
        gameStatus = -1;
    }

    private static boolean isLandMineCell(int selectedRowIndex, int selectedColIndex) {
        return BOARD[selectedRowIndex][selectedColIndex].isLandMine();
    }

    private static boolean docesUserChooseToOpenCell(String userActionInput) {
        return userActionInput.equals("1");
    }

    private static boolean doesUserChooesToPlantFlag(String userActionInput) {
        return userActionInput.equals("2");
    }

    private static int getSelectedRowIndex(String cellInput) {
        char cellInputRow = cellInput.charAt(1);
        return convertRowFrom(cellInputRow);
    }

    private static int getSelectedColIndex(String cellInput) {
        char cellInputCol = cellInput.charAt(0);
        return convertColFrom(cellInputCol);
    }

    private static String getUserActionInputFromUser() {
        System.out.println("선택한 셀에 대한 행위를 선택하세요. (1: 오픈, 2: 깃발 꽂기)");
        return SCANNER.nextLine();
    }

    private static String getCellInputFromUser() {
        System.out.println("선택할 좌표를 입력하세요. (예: a1)");
        return SCANNER.nextLine();
    }

    private static boolean doesUserLoseTheGame() {
        return gameStatus == -1;
    }

    private static boolean doesUserWinTheGame() {
        return gameStatus == 1;
    }

    //한 메서드안에 두가지 이상의 기능이 있을때, 이이름이 다 포괄해서 담을 수 있는가 ?
    private static void checkIfGameIsOver() {
        boolean isAllOpened = isAllChecked();
        if (isAllOpened) {
            changeGameStatusToWin();
        }
    }

    private static void changeGameStatusToWin() {
        gameStatus = 1;
    }

     private static boolean isAllCellOpened2() {
        boolean isAllOpened = true;
        for (int row = 0; row < BOARD_ROW_SIZE; row++) { //3중 배열
            for (int col = 0; col < BOARD_COL_SIZE; col++) {
                if (BOARD[row][col].isClosed()) {
                    isAllOpened = false;
                }
            }
        }
        return isAllOpened;
    }


     //리펙토링 현명하게 하기
     // 복제 ->하나씩 바꾸면서 -> 메소드2-> 리펙토링 완료 -> 메소드 변수 수정하여 사용
     private static boolean isAllChecked() {

         return Arrays.stream(BOARD)
                 .flatMap(Arrays::stream) //평면화
                 .allMatch(Cell::isChecked);
         // CLOSED_CELL_SIGN은 이미 상수여서 NPE가 발생 X
         //getter를 쓰고 싶을 때마다 의미있는 메소드로 구현
     }



     //alt +f8 디버그 : 실제 값 어떻게 됨?
    private static int convertRowFrom(char cellInputRow) {
        int rowIndex = Character.getNumericValue(cellInputRow) - 1;
        if (rowIndex >= BOARD_ROW_SIZE) {
            throw new AppException("잘못된 입력입니다.");
        }
        return rowIndex;
    }

    private static int convertColFrom(char cellInputCol) {
        switch (cellInputCol) {
            case 'a':
                return 0;
            case 'b':
                return 1;
            case 'c':
                return 2 ;
            case 'd':
                return 3;
            case 'e':
                return 4;
            case 'f':
                return 5;
            case 'g':
                return 6;
            case 'h':
                return 7;
            case 'i':
                return 8;
            case 'j':
                return 9;
            default:
                throw new AppException("잘못된 입력입니다.");
        }
    }

    private static void showBoard() {
        System.out.println("   a b c d e f g h i j");
        for (int row = 0; row < BOARD_ROW_SIZE; row++) {
            System.out.printf("%d  ", row + 1);
            for (int col = 0; col < BOARD_COL_SIZE; col++) {
                System.out.print(BOARD[row][col].getSign() + " ");
            }
            System.out.println();
        }
    }

    private static void initalizeGame() {
        for (int row = 0; row < BOARD_ROW_SIZE; row++) {
            for (int col = 0; col < BOARD_COL_SIZE; col++) {
                BOARD[row][col] = Cell.create();
            }
        }
        for (int i = 0; i < LAND_MINE_COUNT; i++) {
            int col = new Random().nextInt(BOARD_COL_SIZE);
            int row = new Random().nextInt(BOARD_ROW_SIZE);
            BOARD[row][col].turnOnLandMine();
        }
        for (int row = 0; row < BOARD_ROW_SIZE; row++) {
            for (int col = 0; col < BOARD_COL_SIZE; col++) {
                //부정 연산자 안 써도 되는 상황
                //부정 연산자가 제거할 수 있는 상황이 온다면 best
                //메서드 자체에 부정의 자체를 넣어ㅏ
                if (isLandMineCell(row, col)) {
                    continue;
                }
                int count = countNearByandMines(row, col);
                BOARD[row][col].updateNearByAndMineCount(count);
            }
        }
    }

    private static int countNearByandMines(int row, int col) {
        int count = 0;
        if (row - 1 >= 0 && col - 1 >= 0 && isLandMineCell(row - 1, col - 1)) {
            count++;
        }
        if (row - 1 >= 0 && isLandMineCell(row - 1, col)) {
            count++;
        }
        if (row - 1 >= 0 && col + 1 < BOARD_COL_SIZE && isLandMineCell(row - 1, col + 1)) {
            count++;
        }
        if (col - 1 >= 0 && isLandMineCell(row, col - 1)) {
            count++;
        }
        if (col + 1 < BOARD_COL_SIZE && isLandMineCell(row, col + 1)) {
            count++;
        }
        if (row + 1 < BOARD_ROW_SIZE && col - 1 >= 0 && isLandMineCell(row + 1, col - 1)) {
            count++;
        }
        if (row + 1 < BOARD_ROW_SIZE && isLandMineCell(row + 1, col)) {
            count++;
        }
        if (row + 1 < BOARD_ROW_SIZE && col + 1 < BOARD_COL_SIZE && isLandMineCell(row + 1, col + 1)) {
            count++;
        }
        return count;
    }

    private static void showGameStartComments() {
        System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
        System.out.println("지뢰찾기 게임 시작!");
        System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
    }

    private static void open(int row, int col) {
        if (row < 0 || row >= BOARD_ROW_SIZE || col < 0 || col >= BOARD_COL_SIZE) {
            return;
        }
        //부정 연산자는 메서드로 해결할 수있는냐 ?
        if (BOARD[row][col].isOpened()) {
            return;
        }
        if (isLandMineCell(row, col)) {
            return;
        }
        BOARD[row][col].open();

        if (BOARD[row][col].hasLandMineCount()) {
            return;
        }
        open(row - 1, col - 1);
        open(row - 1, col);
        open(row - 1, col + 1);
        open(row, col - 1);
        open(row, col + 1);
        open(row + 1, col - 1);
        open(row + 1, col);
        open(row + 1, col + 1);
    }

}
