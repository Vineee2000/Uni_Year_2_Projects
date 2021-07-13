import java.awt.*;

/**
 * An abstract class for entities that can't move from their place but can rotate about their centre, like cannons
 * Provides methods for dealing with said rotation
 */
public abstract class EntityRotating extends Entity {
    double currentAngle;
    double targetAngle;

    /**
     * A constructor that creates a rotatable entity but without any inital rotation
     * @param xCoord
     * @param yCoord
     */
    EntityRotating(int xCoord, int yCoord) {
        super(xCoord, yCoord);
        currentAngle = 0;
        targetAngle = 0;
    }

    /**
     *A constructor that creates a rotatable entity that is going to be turned towards a desired angle in radians
     * @param xCoord
     * @param yCoord
     * @param rotationAngle
     */
    EntityRotating(int xCoord, int yCoord, double rotationAngle) {
        super(xCoord, yCoord);
        currentAngle = 0;
        targetAngle = rotationAngle;
    }

    /**
     *Rotates the entity to a desired angle in radians
     * @param newAngle
     */
    void rotateTo (double newAngle) {
        targetAngle = newAngle;
    }

    /**
     * A draw method override that provide rotating the entity's image
     * @param g
     */
    @Override
    void draw(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;
        double rotAngle = targetAngle-currentAngle;
        g2d.rotate(rotAngle, xCenter, yCenter);
        super.draw(g);
        g2d.rotate(-rotAngle, xCenter, yCenter);
    }
}
