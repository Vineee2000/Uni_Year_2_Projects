import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;

/**
 * This class uses a ticker to actually run the gameplay loop using all the other classes and methods
 */
public class RunTime {
    private GameFrame frame;
    private ScoreTracker scoreTracker;
    private int difficulty=2;
    private long difficultyTicker;
    private int spawnRampUpInterval;
    private int hitPoints;
    private int spawnTicker;
    private int spawnCD;
    private int cannonCDTicker;
    private static int cannonCD = 30;
    private boolean cannonsFiring;
    private boolean gameRunning;
    private boolean gameLost;

    /**
     * This constructor sets some basic variables for the gameplay loop
     */
    RunTime () {
        spawnCD = 120;
        spawnTicker = spawnCD;
        spawnRampUpInterval = 600;
        cannonCDTicker = 0;
        cannonsFiring = false;
        gameRunning = false;
        gameLost = false;
    }

    /**
     * This method brings up the game frame, puts out the start menu and awaits user input to begin the round
     * @param frameInput
     */
    void initGame(GameFrame frameInput) {
        frame = frameInput;
        hitPoints = 4;

        //This mouseListener handles firing the cannons while the player is holding down a mouse button
        frame.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent mouseEvent) { }

            @Override
            public void mousePressed(MouseEvent mouseEvent) {
                cannonsFiring = true;
            }

            @Override
            public void mouseReleased(MouseEvent mouseEvent) {
                cannonsFiring = false;
            }

            @Override
            public void mouseEntered(MouseEvent mouseEvent) { }

            @Override
            public void mouseExited(MouseEvent mouseEvent) { }
        });

        //This key listener handles awaitng keyboard input to begin the game
        frame.addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent keyEvent) {

            }

            @Override
            public void keyPressed(KeyEvent keyEvent) {
                if (gameLost == false) {
                    gameRunning = true;
                    frame.gamePanel.gameInProgress();
                }
            }

            @Override
            public void keyReleased(KeyEvent keyEvent) {

            }
        });

        for (int i = 1; i <= 4; i++) {
            frame.entityManager.addEntity(new AutoCannon(i));
        }

        frame.setVisible(true);

        frame.gamePanel.startMenu();
    }

    /**
     * This method runs the main gameplay loop at the speed of 60 ticks a second
     */
     void runGame () {
         try {
             scoreTracker = new ScoreTracker(difficulty);
         } catch (IllegalArgumentException e) {
             try {
                 scoreTracker = new ScoreTracker(2);
             } catch (IllegalArgumentException ex) {
                 ex.printStackTrace();
             }
         }

         //This code provides the repeated execution of the game loop 60 times a second on a separate thread
         ScheduledExecutorService ticker = Executors.newScheduledThreadPool(1);

        ticker.scheduleAtFixedRate(() -> {
            if (gameRunning) {
                spawnTicker = scheduledPlaneSpawn(frame, spawnTicker);
                frame.entityManager.clearOffScreen();

                //Every tick this gets the position of the mouse and points the cannons at it,
                //and also calls the firing method
                Point mousePoint = MouseInfo.getPointerInfo().getLocation();
                SwingUtilities.convertPointFromScreen(mousePoint, frame.gamePanel);
                frame.entityManager.aimCannons(mousePoint);
                cannonCDTicker = fireCannons(frame, cannonCDTicker);

                //This code moves entities every tick and checks if any of them have collided
                frame.entityManager.moveAllEntities();
                frame.entityManager.runCollisions();

                //This code updates the score and registers planes that got through every tick
                scoreTracker.scoreTick(frame.entityManager.tickScoreInfo().get(0), frame.entityManager.tickScoreInfo().get(1));
                frame.gamePanel.updatGameDisp(scoreTracker);
                hitPoints = hitPoints-frame.entityManager.getDamage();

                frame.repaint();
                frame.entityManager.deleteMarkedEntities();
                rampUpSpawns();

                if (hitPoints <= 0) {
                    endGame();
                }
            }
        }, 0, 16, TimeUnit.MILLISECONDS);
    }

    /**
     * This method marks the game as over, displays the score achieved this round and the top 5 leaderboard
     */
    void endGame () {
        gameLost = true;
        System.out.println("Game over");
        gameRunning = false;
        frame.gamePanel.gameOver();
        scoreTracker.writeScore();
    }

    /**
     * This method sets the difficulty variable of the game
     * @param difficultyInput
     * @throws IllegalArgumentException
     */
    void setDifficulty (int difficultyInput) throws IllegalArgumentException{
         if (difficultyInput > 3 || difficultyInput < 1) {
             throw new IllegalArgumentException("Difficulty must be an integer between 1-3");
         }
         else {
             difficulty = difficultyInput;
         }
    }

    int getDifficulty () {
         return difficulty;
    }

    public ScoreTracker getScoreTracker() {
        return scoreTracker;
    }

    public int getHitPoints() {
        return hitPoints;
    }

    /**
     * This method spawn planes every number of ticks at random locations along the x-axis
     * @param frame
     * @param input
     * @return
     */
    int scheduledPlaneSpawn (GameFrame frame, int input) {
        int i = input;
        if (i >= spawnCD) {
            frame.entityManager.addWing(ThreadLocalRandom.current().nextInt(0, 825), 8);
            i = 0;
        }
        else {
            i++;
        }
        return i;
    }

    /**
     * This method increases the rate of planes spawning every number of ticks, with the increase depending on difficulty
     */
    void increaseSpawnRate () {
        if (difficulty == 1) {
            spawnCD = (int) (spawnCD*0.8);
        }
        if (difficulty == 2) {
            spawnCD = (int) (spawnCD*0.6);
        }
        if (difficulty == 3) {
            spawnCD = (int) (spawnCD*0.5);
        }
        if (spawnCD<cannonCD) {
            spawnCD = cannonCD;
        }
    }

    /**
     * This method performs countdown to the spawn rate increase and calls it when the time is right
     */
    void rampUpSpawns () {
        if (difficultyTicker < spawnRampUpInterval) {
            difficultyTicker++;
        }
        else {
            increaseSpawnRate();
            difficultyTicker = 0;
        }
    }

    /**
     *This method tracks the cooldown between cannon shots and fires them if the user is holding down a mouse button
     * @param frame
     * @param input
     * @return
     */
    int fireCannons (GameFrame frame, int input) {
        int i = input;
        if (i >= cannonCD && cannonsFiring) {
            frame.entityManager.fireCannons();
            i = 0;
        }
        else {
            i++;
        }
        return i;
    }
}
