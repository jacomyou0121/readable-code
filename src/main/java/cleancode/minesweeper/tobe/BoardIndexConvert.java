package cleancode.minesweeper.tobe;

//인덱스만 관리하여 책임 분리
public class BoardIndexConvert {
    private static final int BOARD_ROW_SIZE = 8;
    private static final char BASE_CHAR_FOR_COL = 'a';

    public int getSelectedRowIndex(String cellInput, int rowSize) {
        String cellInputRow = cellInput.substring(1);

        return convertRowFrom(cellInputRow,rowSize);
    }

    public int getSelectedColIndex(String cellInput, int colSize) {
        char cellInputCol = cellInput.charAt(0);
        return convertColFrom(cellInputCol,colSize);
    }

    private int convertRowFrom(String cellInputRow, int rowSize) {
        int rowIndex = Integer.parseInt(cellInputRow) - 1;
        if (rowSize <0 || rowIndex >= rowSize) {
            throw new GameException("잘못된 입력입니다.");
        }
        return rowIndex;
    }

    private int convertColFrom(char cellInputCol, int colSize) { //'a'
        int colIndex = cellInputCol - BASE_CHAR_FOR_COL;
        if (colIndex < 0|| colIndex >= colSize ) {
            throw new GameException("잘못된 입력입니다.");
        }

        return colIndex;
    }

}
