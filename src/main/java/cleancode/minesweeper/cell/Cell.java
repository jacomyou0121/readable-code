package cleancode.minesweeper.cell;

public abstract class Cell {
    //관심사가 분리되어 있음
    //cell의 일부이기에 옮김
    protected static final String FLAG_SIGN = "⚑";
    protected static final String UNCKECKED_SIGN = "□";

    protected boolean isFlagged;
    protected boolean isOpened;

    public void flag() {
        this.isFlagged = true;
    }

    public boolean isChecked() {
        return isFlagged || isOpened;
    }

    public abstract boolean isLandMine();

    public void open() {
        this.isOpened =true;
    }

    public boolean isOpened() {
        return isOpened;
    }

    public abstract boolean hasLandMineCount();


    //지금 셀의 상태가 뭐야?
    public abstract String getSign();
}


