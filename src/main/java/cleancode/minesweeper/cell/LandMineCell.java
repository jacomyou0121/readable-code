package cleancode.minesweeper.cell;

public class LandMineCell extends Cell {
    private static final String LAND_MNE_SIGN = "☼";

    private boolean isLandMine;

    @Override
    public boolean isLandMine() {
        return true;
    }

    @Override
    public boolean hasLandMineCount() {
        return false;
    }

    @Override
    public String getSign() {
        if (isOpened) {
            return LAND_MNE_SIGN;
        }
        if (isFlagged) {

            return FLAG_SIGN;
        }
        return UNCKECKED_SIGN;
    }
}
