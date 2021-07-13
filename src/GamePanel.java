import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

/**
 * This class implements the JPanel where all of the game events are actually drawn
 */
public class GamePanel extends JPanel {
    private RunTime gameInstance;
    private ArrayList <JButton> buttons = new ArrayList<JButton>();;
    private JLabel scoreDisp;
    private JLabel hpDisp;
    private Color background;

    /**
     * This constructor creates a panel and provides it with an instance of the game's runtime class that it shall be representing
     * @param gameInstanceInput
     */
    GamePanel(RunTime gameInstanceInput) {
        this.setLayout(null);
        background = new Color(0, 9, 70);
        this.setBackground(background);
        this.setSize(Main.GAME_RESOLUTION_X, Main.GAME_RESOLUTION_Y);
        gameInstance = gameInstanceInput;

    }

    /**
     * This method displays a start menu before the game begins
     */
    protected void startMenu() {
        this.removeAll();

        //Creates the panel that will display difficulty choices
        JPanel difficultyPanel = new JPanel();
        difficultyPanel.setSize(200, Main.GAME_RESOLUTION_Y);
        difficultyPanel.setBackground(background);
        System.out.println(difficultyPanel.getParent());
        difficultyPanel.setLayout(null);

        //Creates difficulty buttons
        JButton easyBut = new JButton(new DifficultySetter(1, gameInstance, this));
        easyBut.setSize(200, 130);
        easyBut.setFocusable(false);
        JButton normBut = new JButton(new DifficultySetter(2, gameInstance, this));
        normBut.setSize(200, 130);
        normBut.setFocusable(false);
        JButton hardBut = new JButton(new DifficultySetter(3, gameInstance, this));
        hardBut.setSize(200, 130);
        hardBut.setFocusable(false);

        //Adds buttons to the panel for buttons
        difficultyPanel.add(easyBut);
        difficultyPanel.add(normBut);
        difficultyPanel.add(hardBut);

        //Adds buttons to an ArrayList where they can be referenced from in the future easily
        buttons.add(easyBut);
        buttons.add(normBut);
        buttons.add(hardBut);

        //Layout managers have an issue where they simply would not position elements so I resorted to manual positioning
        Insets insets = difficultyPanel.getInsets();
        easyBut.setBounds (0 + insets.left,0 + insets.top, easyBut.getWidth(), easyBut.getHeight());
        normBut.setBounds (0+ insets.left, easyBut.getHeight(), normBut.getWidth() + insets.top, normBut.getHeight());
        hardBut.setBounds (0+ insets.left, easyBut.getHeight() + normBut.getHeight() + insets.top, hardBut.getWidth(), hardBut.getHeight());

        //This long string of calls emulates the effect of pressing the Normal difficulty button
        normBut.getActionListeners()[0].actionPerformed(new ActionEvent(normBut, 1001, null));

        this.add(difficultyPanel);

        //This JPanel displays the user prompt at the start of the game
        JPanel introductionPanel = new JPanel();
        introductionPanel.setBackground(background);
        introductionPanel.setSize(new Dimension (Main.GAME_RESOLUTION_X-difficultyPanel.getWidth(), 100));
        introductionPanel.setBounds (difficultyPanel.getWidth() + insets.left, 0 + insets.top, introductionPanel.getWidth(), introductionPanel.getHeight());
        introductionPanel.setLayout(null);

        JLabel difficultyPrompt = new JLabel("Choose your difficulty");
        difficultyPrompt.setForeground(Color.WHITE);
        difficultyPrompt.setFont(new Font(Font.SANS_SERIF, Font.ITALIC, 24));
        difficultyPrompt.setSize(new Dimension(introductionPanel.getWidth(), introductionPanel.getHeight()/3));
        insets = introductionPanel.getInsets();

        JLabel goalPrompt = new JLabel("Shoot the enemy planes down before they cross over the screen!");
        goalPrompt.setForeground(Color.WHITE);
        goalPrompt.setSize((new Dimension(introductionPanel.getWidth(), introductionPanel.getHeight()/3)));
        goalPrompt.setBounds(0 + insets.left, difficultyPrompt.getHeight() + insets.top, goalPrompt.getWidth(), goalPrompt.getHeight());

        JLabel startPrompt = new JLabel("Press any key to start");
        startPrompt.setForeground(Color.WHITE);
        startPrompt.setSize((new Dimension(introductionPanel.getWidth(), introductionPanel.getHeight()/3)));
        startPrompt.setBounds(0 + insets.left, difficultyPrompt.getHeight() + goalPrompt.getHeight() + insets.top, startPrompt.getWidth(), startPrompt.getHeight());

        introductionPanel.add(difficultyPrompt);
        introductionPanel.add (goalPrompt);
        introductionPanel.add(startPrompt);

        this.add (introductionPanel);
    }

    /**
     * This method displays HP left and current score during the game round
     */
    public void gameInProgress () {
        this.removeAll();

        scoreDisp = new JLabel("Score: " + gameInstance.getScoreTracker().getCurrentScore());
        scoreDisp.setForeground(Color.WHITE);
        scoreDisp.setSize(200, 40);

        hpDisp = new JLabel("Hit Points: " + gameInstance.getHitPoints());
        hpDisp.setForeground(Color.WHITE);
        hpDisp.setSize(new Dimension(200, 40));
        hpDisp.setBounds(0 + this.getInsets().left, scoreDisp.getHeight(), hpDisp.getWidth(), hpDisp.getHeight());

        this.add(scoreDisp);
        this.add(hpDisp);
    }

    /**
     * This method displays the screen with scores at the end of the game
     */
    public void gameOver () {
        this.removeAll();

        JTextArea resultsScreen = new JTextArea();
        resultsScreen.setEditable(false);
        resultsScreen.setBackground(background);
        resultsScreen.setForeground(Color.WHITE);
        resultsScreen.setSize(new Dimension(300, 400));
        resultsScreen.setBounds(250, 150, resultsScreen.getWidth(), resultsScreen.getHeight());
        resultsScreen.setFont(new Font(Font.SANS_SERIF, Font.ITALIC, 22));

        resultsScreen.append("GAME OVER! \n" +
                "Your score is: " + gameInstance.getScoreTracker().getCurrentScore() + "\n" +
                "\n" +
                "Top scores:\n");

        List <Integer> resultList = gameInstance.getScoreTracker().readScores();
        Collections.sort(resultList);
        List<Integer> top5 = new ArrayList<Integer>(resultList.subList(resultList.size() -5, resultList.size()));
        for (int i = 4; i >= 0 ; i--) {
            resultsScreen.append(top5.get(i) + "\n");
        }

        this.add(resultsScreen);

        this.repaint();
    }

    /**
     * This method updates the display of scores and HP when called
     * @param scoreTracker
     */
    public void updatGameDisp(ScoreTracker scoreTracker) {
        scoreDisp.setText("Score: " + scoreTracker.getCurrentScore());
        hpDisp.setText("Hit Points: " + gameInstance.getHitPoints());
    }

    /**
     * This method draws the button with chosen difficulty different from the other 2
     * @param selectedButton
     */
    public void setSelected (JButton selectedButton) {
        for (JButton button: buttons) {
            button.setBackground(Color.darkGray);
            button.setForeground(Color.WHITE);
        }
        selectedButton.setBackground(new Color(0, 157,191));
        selectedButton.setForeground(Color.BLACK);

        this.repaint();
    }
}

