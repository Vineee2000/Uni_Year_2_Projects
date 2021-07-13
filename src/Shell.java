import java.awt.*;

/**
 * A concrete class for shells fired by the cannons
 * Has a static variable for its speed along the path of its flight
 */
public class Shell extends EntityMovable {
    static double speedAbsolute = 20;

    /**
     *A constructor, largely analogous to that of the superclass, but with an appropriately small hitbox
     * @param xCoord
     * @param yCoord
     * @param xSpeedInit
     * @param ySpeedInit
     */
    Shell(int xCoord, int yCoord, double xSpeedInit, double ySpeedInit) {
        super(xCoord, yCoord, xSpeedInit, ySpeedInit);
        hitBoxRadius = 5;
    }

    /**
     * Class-specific override of the superclass draw method
     * @param g
     */
    @Override
    void draw(Graphics g) {
        //Shells have no sprite, but are instead drawn as red circle shapes
        g.setColor(Color.red);
        g.fillOval(xPos, yPos, hitBoxRadius*2, hitBoxRadius*2);
    }
}
