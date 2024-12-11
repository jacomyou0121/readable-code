package cleancode.minesweeper.tobe;

import cleancode.minesweeper.tobe.gamelevel.GameLevel;

import java.util.Arrays;
import java.util.Random;

public class GameBoard {
    private  final Cell[][] board;
    private final int landMineCount;

//    public GameBoard(int rowSize,int colSize) {
//        board = new Cell[rowSize][colSize];
//    }

    //런타임 시점에 뭐가 들어올지는 모르지만, 추상화된 스펙은 알고있어 게임을 유동적 실행
    // 변경에는 닫혀있고, 확장에는 닫혀있는
    public GameBoard(GameLevel gameLevel) {
        int rowSize = gameLevel.getRowSize();
        int colSize = gameLevel.getColSize();
        board = new Cell[rowSize][colSize];

        landMineCount = gameLevel.getLandMineCount();
    }

    public void flag(int rowIndex, int colIndex) {
        Cell cell = findCell(rowIndex, colIndex);
        cell.flag();
    }

    public boolean isLandMineCell(int selectedRowIndex, int selectedColIndex) {
        return findCell(selectedRowIndex, selectedColIndex).isLandMine();
    }
    public void open(int rowIndex, int colIndex) {
        Cell cell = findCell(rowIndex, colIndex);
        cell.open();
    }


    public void openSurroundedCells(int row, int col) {
        if (row < 0 || row >= getRowSize() || col < 0 || col >= getColSize()) {
            return;
        }
        //부정 연산자는 메서드로 해결할 수있는냐 ?
        if (isOpenedCell(row, col)) {
            return;
        }
        if (isLandMineCell(row, col)) {
            return;
        }
        open(row,col);

        if (doesCellHaveLandMineCount(row, col)) {
            return;
        }
        openSurroundedCells(row - 1, col - 1);
        openSurroundedCells(row - 1, col);
        openSurroundedCells(row - 1, col + 1);
        openSurroundedCells(row, col - 1);
        openSurroundedCells(row, col + 1);
        openSurroundedCells(row + 1, col - 1);
        openSurroundedCells(row + 1, col);
        openSurroundedCells(row + 1, col + 1);
    }

    private boolean doesCellHaveLandMineCount(int row, int col) {
        return findCell(row, col).hasLandMineCount();
    }

    private boolean isOpenedCell(int row, int col) {
        return findCell(row, col).isOpened();
    }

    public void initalizeGame() {
        int rowSize = getRowSize();
        int colSize = getColSize();
        for (int row = 0; row < rowSize; row++) {
            for (int col = 0; col < colSize; col++) {
               board[row][col] = Cell.create();
            }
        }
        for (int i = 0; i < landMineCount; i++) {
            int landMineCol = new Random().nextInt(colSize);
            int landMineRow = new Random().nextInt(rowSize);
            findCell(landMineRow, landMineCol).turnOnLandMine();
        }
        for (int row = 0; row < rowSize; row++) {
            for (int col = 0; col < colSize; col++) {
                //부정 연산자 안 써도 되는 상황
                //부정 연산자가 제거할 수 있는 상황이 온다면 best
                //메서드 자체에 부정의 자체를 넣어ㅏ
                if (isLandMineCell(row, col)) {
                    continue;
                }
                int count = countNearByandMines(row, col);
                findCell(row, col).updateNearByAndMineCount(count);
            }
        }
    }

    public String getSize(int rowIndex, int colIndex) {
        Cell cell = findCell(rowIndex, colIndex);
        return cell.getSign();
    }

    boolean isAllChecked() {

        return Arrays.stream(board)
                .flatMap(Arrays::stream) //평면화
                .allMatch(Cell::isChecked);
        // CLOSED_CELL_SIGN은 이미 상수여서 NPE가 발생 X
        //getter를 쓰고 싶을 때마다 의미있는 메소드로 구현
    }


    private Cell findCell(int rowIndex, int colIndex) {
        return board[rowIndex][colIndex];
    }

    private int countNearByandMines(int rowIndex, int colIndex) {
        int count = 0;
        int rowSize = getRowSize();
        int colSize = getColSize();

        if (rowIndex - 1 >= 0 && colIndex - 1 >= 0 && isLandMineCell(rowIndex - 1, colIndex - 1)) {
            count++;
        }
        if (rowIndex - 1 >= 0 && isLandMineCell(rowIndex - 1, colIndex)) {
            count++;
        }
        if (rowIndex - 1 >= 0 && colIndex + 1 < colSize && isLandMineCell(rowIndex - 1, colIndex + 1)) {
            count++;
        }
        if (colIndex - 1 >= 0 && isLandMineCell(rowIndex, colIndex - 1)) {
            count++;
        }
        if (colIndex + 1 < colSize && isLandMineCell(rowIndex, colIndex + 1)) {
            count++;
        }
        if (rowIndex + 1 < rowSize && colIndex - 1 >= 0 && isLandMineCell(rowIndex + 1, colIndex - 1)) {
            count++;
        }
        if (rowIndex + 1 < rowSize && isLandMineCell(rowIndex + 1, colIndex)) {
            count++;
        }
        if (rowIndex + 1 < rowSize && colIndex + 1 < colSize && isLandMineCell(rowIndex + 1, colIndex + 1)) {
            count++;
        }
        return count;
    }




    public int getRowSize() {
        return board.length;
    }

    public int getColSize() {
        return board[0].length;
    }



}
