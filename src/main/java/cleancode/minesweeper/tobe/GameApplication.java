package cleancode.minesweeper.tobe;

import cleancode.minesweeper.tobe.gamelevel.Beginner;
import cleancode.minesweeper.tobe.gamelevel.GameLevel;

//메서드를 추상화하여 동등화
//실무에선 점진적 리펙토링
public class GameApplication {

    public static void main(String[] args) {
        GameLevel gameLevel = new Beginner(); //게임 난이도를 동적으로 선택
        Minesweeper minesweeper = new Minesweeper(gameLevel); // 게임레벨이 유동적으로 들어와도 인터페이스로 진행 
        minesweeper.run();
    }

}
