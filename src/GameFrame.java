import javax.swing.*;
import java.awt.*;

/**
 * The window in which the game will take place.
 */
public class GameFrame extends JFrame {
    int width = Main.GAME_RESOLUTION_X;
    int height =Main.GAME_RESOLUTION_Y;
    EntityManager entityManager;
    GamePanel gamePanel;

    /**
     * Constructor that creates the frame, sets its parameters and adds other parts of the game to it.
     * Namely the entity manager that controls all game entities and the JPanel where everything will be drawn.
     * @param gameInstace
     */
    GameFrame (RunTime gameInstace) {
        setTitle("Submission of 1804987 Bohdan Vynohradov");
        setSize(width, height);
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        this.setResizable(false);

        EntityManager entityManagerObj = new EntityManager();
        this.entityManager = entityManagerObj;

        GamePanel gridObj = new GamePanel(gameInstace) {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                entityManager.draw(g);
            }
        };
        gamePanel = gridObj;
        this.add(gridObj);
    }
}
