/**
 * An abstract class for entities that can move accross the screen, like shells and planes
 * Provides methods to handle moving from point A to point B
 */
public abstract class EntityMovable extends Entity{
    double xSpeed;
    double ySpeed;
    double xError;
    double yError;

    /**
     *A constructor that allows to create a movable entity that is staying in place.
     * Not used, left for testing and possible expansion.
     * @param xCoord
     * @param yCoord
     */
    EntityMovable (int xCoord, int yCoord) {
        super(xCoord, yCoord);
        xSpeed = 0;
        ySpeed = 0;
        xError = 0;
        yError = 0;
    }

    /**
     *A constructor that creates an entity that is on the move from the start
     * @param xCoord
     * @param yCoord
     * @param xSpeedInit
     * @param ySpeedInit
     */
    EntityMovable (int xCoord, int yCoord, double xSpeedInit, double ySpeedInit) {
        super(xCoord, yCoord);
        xSpeed = xSpeedInit;
        ySpeed = ySpeedInit;
        xError = 0;
        yError = 0;
    }

    /**
     *A method that allows to change the speed of the entity
     * @param newXSpeed
     * @param newYSpeed
     */
    void setSpeed (double newXSpeed, double newYSpeed) {
        xSpeed = newXSpeed;
        ySpeed = newYSpeed;
        xError = 0;
        yError = 0;
    }

    /**
     * A method that actually moves the entity by its speed
     */
    void move () {
        /*
        Coordinates of an entity can be only integers, but for greater granularity in movement, speeds can be doubles.
        This creates a conversion problem. The solution is to round movement every tick to its whole part and store the factional part
        Whenever the error due to such rounding build up to more than 1, the whole part of the accumulated error is added to movement that tick
        and subtracted from the accumulated error stored
         */
        xPos = xPos + (int) xSpeed + (int) xError;
        yPos = yPos + (int) ySpeed + (int) yError;
        xError = (xError - (int) xError) + (xSpeed - (int) xSpeed);
        yError = (yError - (int) yError) + (ySpeed - (int) ySpeed);
    }
}
