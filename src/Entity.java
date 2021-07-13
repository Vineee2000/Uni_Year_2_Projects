import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;

/**
 * The abstact class inhereted by all game entities.
 * Provides location, drawing and collision information
 */
public abstract class Entity {
    int xPos;
    int yPos;
    int xCenter;
    int yCenter;
    int hitBoxRadius;
    String spritePathway;
    boolean toBeDeleted = false;

    /**
     *A baseline constructor that also provides a default size.
     * Base entity class assumes entities to be squares:
     * non-square entities can override as necessary
     * @param xCoord
     * @param yCoord
     */
    Entity (int xCoord, int yCoord) {
        xPos = xCoord;
        yPos = yCoord;
        hitBoxRadius = 25;
        xCenter = hitBoxRadius + xCoord;
        yCenter = hitBoxRadius + yCoord;
    }

    /**
     * Method that returns the corners of the entity's hitbox in an Arraylist
     * Used for collision detection
     * Fist item in the list is the top left corner, second item is bottom right corner
     * @return
     */
    public ArrayList<Point> getArea () {
        ArrayList<Point> corners = new ArrayList<Point>();
        corners.add(new Point(xPos, yPos));
        corners.add(new Point(xPos+2*hitBoxRadius, yPos+2*hitBoxRadius));

        return corners;
    }

    /**
     * Checks if this entity overlaps with the entity, whose ArrayList of corners provided as the argument
     * Assumes the array is ordered in the same way as one produced by getArea method
     * Throws IllegalArgumentException if array provided has a size other than 2
     * @param otherEntity
     * @return
     * @throws IllegalArgumentException
     */
    boolean overlaps(ArrayList<Point> otherEntity) throws IllegalArgumentException {
        if (otherEntity.size() != 2) {
            throw new IllegalArgumentException("Input must be 2 points defining a hitbox");
        }
        Point thatTopLeft = otherEntity.get(0);
        Point thatBotRight = otherEntity.get(1);
        Point thisTopLeft = new Point(xPos, yPos);
        Point thisBotRight = new Point(xPos+2*hitBoxRadius, yPos+2*hitBoxRadius);

        if (thisTopLeft.x > thatBotRight.x || thisTopLeft.y > thatBotRight.y || thisBotRight.x < thatTopLeft.x || thisBotRight.y < thatTopLeft.y) {
            return false;
        }
        else {
            return true;
        }
    }

    /**
     * Marks the entity to be deleted by the entity manager
     * While manager can delete entities directly, this allows you to separate the act of deletion from the event that qualified the entity for deletion
     * For example, you can detect a collision with another entity, mark both for deletion, draw how they collide and only then delete
     */
    void markToDelete () {
        toBeDeleted = true;
    }

    /**
     * Method that draws the entity. It tires to find the sprite at the location specified in the spritePathway parameter
     * but if that fails it resorts to drawing a grey square of the same size as entity's hitbox
     * Since exceptions are computationally expensive and draw method is called multiple times a second, I have decided against throwing an exception here.
     * @param g
     */
    void draw (Graphics g) {
        if (spritePathway != null) {
            try {
                BufferedImage sprite = ImageIO.read(new File(spritePathway));
                g.drawImage(sprite, xPos, yPos, null);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        else {
            g.setColor(Color.gray);
            g.fillRect(xPos, yPos, hitBoxRadius*2, hitBoxRadius*2);
        }
    }
}
