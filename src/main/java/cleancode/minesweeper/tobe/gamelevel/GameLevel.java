package cleancode.minesweeper.tobe.gamelevel;

//추상화를 다이렉트로 표현
// 스펙은 알고 있따. 게임을 수행 가능
public interface GameLevel {

    int getRowSize();
    int getColSize();
    int getLandMineCount();

}
