package cleancode.minesweeper.cell;

public class NumberCell extends Cell {
    protected int nearbyLandMineCount;

    public NumberCell(int nearbyLandMineCount) {
        this.nearbyLandMineCount = nearbyLandMineCount;
    }


    @Override
    public boolean isLandMine() {
        return false;
    }

    @Override
    public boolean hasLandMineCount() {
        return true;
    }

    @Override
    public String getSign() {
        if (isOpened) {
            return String.valueOf(nearbyLandMineCount);
        }
        if (isFlagged) {

            return FLAG_SIGN;
        }
        return UNCKECKED_SIGN;
    }
}
