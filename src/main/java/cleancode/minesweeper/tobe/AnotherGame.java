package cleancode.minesweeper.tobe;

// ISP 위반
public class AnotherGame implements Game{
    @Override
    public void initialize() {
        // .. 필요없는데 .. ? 

    }

    @Override
    public void run() {
        //게임 진행

    }
}
