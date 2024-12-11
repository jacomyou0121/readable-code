package cleancode.minesweeper.tobe;

import cleancode.minesweeper.game.GameInitializable;
import cleancode.minesweeper.game.GameRunnable;
import cleancode.minesweeper.tobe.gamelevel.GameLevel;
import cleancode.minesweeper.tobe.io.ConsoleInputHandler;
import cleancode.minesweeper.tobe.io.ConsoleOutputHandler;

//모든 게임로직 여기다 둠
//입출력 로직 분리
public class Minesweeper implements GameInitializable, GameRunnable {
    //공백라인 : 후손에게 이런 의미 구간으로 읽어라

    //상수에 사이즈 고정 으로 확장에 닫혀 있는 상황 -인터페이스로 활용
    //난이도가 조정될 때마다 인터페이스의 구현체로 변경된 난이도로 구현
    private static final int BOARD_ROW_SIZE = 8;
    private static final int BOARD_COL_SIZE = 10;
    private int gameStatus = 0; // 0: 게임 중, 1: 승리, -1: 패배
    private final GameBoard gameBoard;
    private final BoardIndexConvert boardIndexConvert = new BoardIndexConvert();



    //입출력과 관련된 로직은 여기서 관리
    private final ConsoleInputHandler consoleInputHandler = new ConsoleInputHandler();
    private final ConsoleOutputHandler consoleOutputHandler = new ConsoleOutputHandler();

    public Minesweeper(GameLevel gameLevel) {
        gameBoard = new GameBoard(gameLevel);
    }

    @Override
    public void initialize() {
        gameBoard.initalizeGame();
    }

    @Override
    public void run() {
        consoleOutputHandler.showGameStartComments();
        while (true) {
            try {
                consoleOutputHandler.showBoard(gameBoard);
                if (doesUserWinTheGame()) {
                    consoleOutputHandler.printGameWinningComment();
                    break;
                }
                if (doesUserLoseTheGame()) {
                    consoleOutputHandler.printGameLosingComment();
                    break;
                }

                String cellInput = getCellInputFromUser();
                String userActionInput = getUserActionInputFromUser();


                actOnCell(cellInput, userActionInput);
            } catch (GameException e) {
                consoleOutputHandler.printExceptionMessage(e);
                //consoleOutputHandler.printExceptionMessage(e.getMessage());
            } catch (Exception e) {
                //예외상황에 대한 ㅇ
                consoleOutputHandler.printSimpleMessage("프로그램에 문제 생김");
            }
        }
    }

    private void actOnCell(String cellInput, String userActionInput) {
        //한줄이여도 추상화 함
        //의미가 더 중요
        int selectedColIndex = boardIndexConvert.getSelectedColIndex(cellInput,gameBoard.getColSize());
        int selectedRowIndex = boardIndexConvert.getSelectedRowIndex(cellInput,gameBoard.getRowSize());

        if (doesUserChooesToPlantFlag(userActionInput)) {
            gameBoard.flag(selectedRowIndex, selectedColIndex);
            checkIfGameIsOver();
            return;
        }

        if (docesUserChooseToOpenCell(userActionInput)) {
            if (gameBoard.isLandMineCell(selectedRowIndex, selectedColIndex)) {
                gameBoard.open(selectedRowIndex, selectedColIndex);
                changeGameStatusToLose();
                return;
            }

            gameBoard.openSurroundedCells(selectedRowIndex, selectedColIndex);
            checkIfGameIsOver();
            return;
        }
        throw new GameException("잘못된 번호를 선택하셨습니다.");

    }


    private void changeGameStatusToLose() {
        gameStatus = -1;
    }

    private boolean docesUserChooseToOpenCell(String userActionInput) {
        return userActionInput.equals("1");
    }

    private boolean doesUserChooesToPlantFlag(String userActionInput) {
        return userActionInput.equals("2");
    }


    private String getUserActionInputFromUser() {
        consoleOutputHandler.printCommentForUserAction();
        return consoleInputHandler.getUserInput();
    }

    private String getCellInputFromUser() {
        consoleOutputHandler.printCommentForSelectingCell();
        return consoleInputHandler.getUserInput();
    }

    private boolean doesUserLoseTheGame() {
        return gameStatus == -1;
    }

    private boolean doesUserWinTheGame() {
        return gameStatus == 1;
    }

    //한 메서드안에 두가지 이상의 기능이 있을때, 이이름이 다 포괄해서 담을 수 있는가 ?
    private void checkIfGameIsOver() {
        if (gameBoard.isAllChecked()) {
            changeGameStatusToWin();
        }
    }

    private void changeGameStatusToWin() {
        gameStatus = 1;
    }


    //리펙토링 현명하게 하기
    // 복제 ->하나씩 바꾸면서 -> 메소드2-> 리펙토링 완료 -> 메소드 변수 수정하여 사용


    //alt +f8 디버그 : 실제 값 어떻게 됨?








}

