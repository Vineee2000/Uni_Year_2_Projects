import javax.swing.*;
import java.awt.event.ActionEvent;

/**
 * This is a concrete implementation of Action class that is applied to difficulty buttons to have them actually set the difficulty
 */
public class DifficultySetter extends AbstractAction {
    private int difficulty;
    private RunTime gameInstance;
    private GamePanel parentPanel;

    /**
     * This constructor needs to be passed and int 1-3 for difficulty, and instance of game RunTime class that it's seting difficulty for and the GamePanel that the buttons are on
     * Throws IllegalArgumentException when int for diffculty is other than 1-3
     * @param difficultyInput
     * @param gameInstanceRef
     * @param buttonPanelRef
     * @throws IllegalArgumentException
     */
    DifficultySetter (int difficultyInput, RunTime gameInstanceRef, GamePanel buttonPanelRef) throws IllegalArgumentException {
        super();
        gameInstance = gameInstanceRef;
        parentPanel = buttonPanelRef;
        if (difficultyInput == ScoreTracker.EASY) {
            difficulty = difficultyInput;
            super.putValue(AbstractAction.NAME, "Easy");
        }
        else {
            if (difficultyInput == ScoreTracker.NORMAL) {
                difficulty = difficultyInput;
                super.putValue(AbstractAction.NAME, "Normal");
            }
            else {
                if (difficultyInput == ScoreTracker.HARD) {
                    difficulty = difficultyInput;
                    super.putValue(AbstractAction.NAME, "Hard");
                }
                else {
                    System.out.println("Difficulty is " + difficultyInput);
                    throw new IllegalArgumentException("Difficulty must be an integer 1-3");
                }
            }
        }
    }

    /**
     * This action listener actually sets the difficulty value in the RunTime instance to match the one the button is tied to
     * @param actionEvent
     */
    @Override
    public void actionPerformed(ActionEvent actionEvent) {
        gameInstance.setDifficulty(difficulty);
        parentPanel.setSelected((JButton) actionEvent.getSource());
    }
}
