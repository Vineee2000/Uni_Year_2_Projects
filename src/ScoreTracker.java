import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.List;

/**
 * This class counts the round score, writes it to a file and reads it to a file
 * Since the score log is not crucial to the game functioning, its first response to failing to locate the file
 * is to create a new blank instance of the score log
 */
public class ScoreTracker {
    //A number of static variables for masking the difficulty level integers
    private static double EASY_MULTIPLIER = 1;
    private static double NORMAL_MULTIPLIER = 2;
    private static double HARD_MULTIPLIER = 4;
    public static int EASY = 1;
    public static int NORMAL = 2;
    public static int HARD = 3;

    private static String  scorePath = "scores/scores.txt";
    private int currentScore;
    private double difficultyModifier;

    /**
     * This constructor sets the difficulty level for the round
     * Throws IllegalArgumentException if provided integer is not 1-3
     * @param difficulty
     * @throws IllegalArgumentException
     */
    ScoreTracker (int difficulty) throws IllegalArgumentException {
    currentScore = 0;
    if (difficulty != EASY && difficulty != NORMAL && difficulty != HARD) {
        throw new IllegalArgumentException ("Difficulty input must correspond to one of static difficulty indexes");
    }
    if (difficulty == EASY) {
        difficultyModifier = EASY_MULTIPLIER;
    }
    if (difficulty == NORMAL) {
        difficultyModifier = NORMAL_MULTIPLIER;
    }
    if (difficulty == HARD) {
        difficultyModifier = HARD_MULTIPLIER;
    }
    }

    /**
     * This method writes this round's score to the file
     */
    void writeScore() {
        try {
            Files.writeString(Paths.get(scorePath), currentScore + System.getProperty("line.separator"), StandardOpenOption.APPEND);
        } catch (IOException e) {
            new File(scorePath).mkdirs();
        }
    }

    /**
     * This method returns all the scores in the files as an array of integers
     * @return
     */
    ArrayList <Integer> readScores() {
        try {
            List <String> stringResults = Files.readAllLines(Paths.get(scorePath), StandardCharsets.UTF_8);
            stringResults.remove(stringResults.size()-1);
            ArrayList <Integer> intResults = new ArrayList<Integer>();
            for (String score: stringResults) {
                intResults.add( Integer.parseInt(score));
            }
            return intResults;
        } catch (IOException e) {
                new File(scorePath).mkdirs();
        }
        return null;
    }



    /**
     * This method updates the score based on the integers passed
     * @param PlanesShot
     * @param WingsDestroyed
     */
    void scoreTick (int PlanesShot, int WingsDestroyed) {
        currentScore = (int) (currentScore + (PlanesShot*100 + WingsDestroyed*250+1)*difficultyModifier);
    }

    public int getCurrentScore () {
        return currentScore;
    }
}
