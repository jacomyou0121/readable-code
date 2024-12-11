package cleancode.minesweeper.tobe;

//메서드를 추상화하여 동등화
//실무에선 점진적 리펙토링
public class GameApplication {

    public static void main(String[] args) {
        Minesweeper minesweeper = new Minesweeper();
        minesweeper.run();
    }

}
