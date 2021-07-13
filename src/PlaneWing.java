import java.awt.*;
import java.util.ArrayList;

/**
 * A concrete class for enemy planes that the player has to shoot down in the game. Unlike most other entities, plane wings are rectangular in shape.
 * They also have multiple damage states unlike most other entities who either exist or don't
 */
public class PlaneWing extends EntityMovable {
    int xHitBox;
    int yHitBox;
    int damage;

    /**
     * A constructor that specifies planes' horizontal starting point and the speed with which the move and sets them on a straight top to bottom course
     * This is how planes are spawned in the game
     * @param xCoord
     * @param ySpeed
     */
   PlaneWing(int xCoord, double ySpeed) {
       super(xCoord, 0, 0, ySpeed);
       xHitBox = 200;
       yHitBox = 84;
       hitBoxRadius = yHitBox/2;
       xCenter = xPos+xHitBox/2;
       yCenter = yPos+yHitBox/2;
       spritePathway = "sprites/Wing.png";
   }

    /**
     *A constructor that allows to manually set many of the planes' values.
     * Not currently used in the game, but might be useful for testing or adding features/
     * @param xCoord
     * @param yCoord
     */
    PlaneWing(int xCoord, int yCoord, double xSpeed, double ySpeed) {
        super(xCoord, yCoord, xSpeed, ySpeed);
        xHitBox = 200;
        yHitBox = 84;
        hitBoxRadius = xHitBox;
        xCenter = xPos+xHitBox/2;
        yCenter = yPos+yHitBox/2;
        spritePathway = "sprites/Wing.png";
    }

    /**
     * Returns the corners of the wing's hitbox.
     * Since most entities are squares, superclass method assumes a square, which is not the case with planes, requiring an override.
     * @return
     */
    @Override
    public ArrayList<Point> getArea() {
        ArrayList<Point> area = new ArrayList<Point>();
        area.add(new Point(xPos, yPos));
        area.add(new Point(xPos+xHitBox, yPos+yHitBox));
        return area;
    }

    /**
     * Progresses the wing through states of damage. Wings loose planes when damaged - so they get a smaller hitbox but will do less damage if they do cross the screen.
     */
    void takeDamage () {
       damage++;
       if (damage == 1) {
           spritePathway = "sprites/Wing damaged 1.png";
           xHitBox = 150;
           yHitBox = 66;
           xPos = xPos+25;
           yPos = yPos+9;
       }
        if (damage == 2) {
            spritePathway = "sprites/Wing damaged 2.png";
            xHitBox = 100;
            yHitBox = 66;
            xPos = xPos+25;
        }
        if (damage == 3) {
            spritePathway = "sprites/Wing damaged 3.png";
            xHitBox = 50;
            yHitBox = 50;
            xPos = xPos+25;
            yPos = yPos+8;
        }
        //Wing lost all planes and needs to be deleted
        if (damage >= 4) {
            toBeDeleted = true;
        }
    }

    /**
     * Returns the wing's damage state
     * @return
     */
    int getDamage () {
       return damage;
    }
}
