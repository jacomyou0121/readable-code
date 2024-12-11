package cleancode.minesweeper.cell;


public class EmptyCell extends Cell {

    protected static final String ENPTY_SIGN = "■";


    @Override
    public boolean isLandMine() {
        return false;
    }

    @Override
    public boolean hasLandMineCount() {
        return false;
    }

    @Override
    public String getSign() {
        if (isOpened) {
            return ENPTY_SIGN;
        }
        if (isFlagged) {

            return FLAG_SIGN;
        }
        return UNCKECKED_SIGN;    }
}
