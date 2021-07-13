/**
 * The main class that puts all the bits together and actually runs the game.
 * Also holds static variables for the size of the game window, as changing resolution will affect how the game works.
 */
public class Main {
    public static int GAME_RESOLUTION_X = 1024;
    public static int GAME_RESOLUTION_Y = 768;

    public static void main(String[] args) {
        RunTime runTime = new RunTime();
        GameFrame gameWindow = new GameFrame(runTime);
        runTime.initGame(gameWindow);
        runTime.runGame();
    }
}
