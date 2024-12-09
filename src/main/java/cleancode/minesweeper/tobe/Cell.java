package cleancode.minesweeper.tobe;

public class Cell {
    //관심사가 분리되어 있음
    //cell의 일부이기에 옮김
    private static final String FLAG_SIGN = "⚑";
    private static final String LAND_MNE_SIGN = "☼";
    private static final String UNCKECKED_SIGN = "□";
    private static final String ENPTY_SIGN = "■";

    private int nearbyLandMineCount;
    private boolean isLandMine;
    private boolean isFlagged;
    private boolean isOpened;

    //cell이 가진 속성 : 근처 지뢰 숫자, 지뢰 여부
    //cell의 상태 : 깃발 유무, 열렸다 / 닫혔다. 사용자가 확인함

    private Cell(int nearbyLandMineCount,boolean isLandMine,boolean isFlagged,boolean isOpend) {
        this.nearbyLandMineCount = nearbyLandMineCount;
        this.isLandMine = isLandMine;
        this.isFlagged = isFlagged;
        this.isOpened = isOpend;
    }
    //Cell이 가진 속성 : 근처 지뢰 숫자, 지뢰 여부
    //Cell의 상태 : 깃발 유무 , 열렸다 / 닫혔다 , 사용자가 확인함

    // 정적 팩토리 메서드 이름을 줄 수 있다
    //검증도 다르게 해줄 수 있다.
    //취향의 차이
    public static Cell of(int nearbyLandMineCount,boolean isLandMine,boolean ifFlagged,boolean isOpened){
        return new Cell(nearbyLandMineCount,isLandMine,ifFlagged,isOpened);
    }

    public static Cell create() {
        return of( 0, false,false,false);
    }

    public void turnOnLandMine() {
        this.isLandMine = true;
    }

    public void updateNearByAndMineCount(int count) {
        this.nearbyLandMineCount = count;
    }

    public void flag() {
        this.isFlagged = true;
    }

    public boolean isChecked() {
        return isFlagged || isOpened;
    }

    public boolean isLandMine() {
        return isLandMine;
    }

    public void open() {
        this.isOpened =true;
    }

    public boolean isOpened() {
        return isOpened;
    }

    public boolean hasLandMineCount() {
        return this.nearbyLandMineCount !=0;
    }


    //지금 셀의 상태가 뭐야?
    public String getSign() {
        if (isOpened) {
            if (isLandMine) {
                return LAND_MNE_SIGN;
            }
            if (hasLandMineCount()) {
                return String.valueOf(nearbyLandMineCount);
            }
            return ENPTY_SIGN;
        }


        if (isFlagged) {
            return FLAG_SIGN;
        }
        return UNCKECKED_SIGN;
    }
}


