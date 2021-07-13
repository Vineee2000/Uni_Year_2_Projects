import java.awt.*;

/**
 * A concrete class for cannons the player will be firing at the enemies
 */
public class AutoCannon extends EntityRotating {

    /**
     * A constructor that creates a cannon anywhere on the screen.
     * Not used, but left for testing and possible expansion uses.
     * @param xCoord
     * @param yCoord
     */
    AutoCannon(int xCoord, int yCoord) {
        super(xCoord, yCoord);
        spritePathway = "sprites/Autocannon.png";
        hitBoxRadius = 18;
    }

    /**
     * A constructor that creates a cannon in 1 of the 4 locations where they are supposed to be positioned in the game
     * Throws an IllegalArgumentException if int provided is other than 1 through 4
     * @param cannonNo
     * @throws IllegalArgumentException
     */
    AutoCannon(int cannonNo) throws IllegalArgumentException {
        super(cannonNo*204, 700);
        spritePathway = "sprites/Autocannon.png";
        hitBoxRadius = 18;
        checkIndex(cannonNo);
    }

    /**
     * The method that provides a check to throw the exception in the constructor
     * @param input
     * @throws IllegalArgumentException
     */
    void checkIndex (int input) throws IllegalArgumentException {
        if (input < 1 || input > 4) {
            throw new IllegalArgumentException("cannon index out of range");
        }
    }

    /**
     * This method turns the cannon towards the point provided as an argument
     * @param target
     */
    void aim (Point target) {
        double xDistance = target.x-xCenter;
        double yDistance = target.y-yCenter;
        targetAngle = Math.atan2(yDistance, xDistance);
    }

    /**
     * Fires a shell in the direction cannon is facing right now
     * @return
     */
    Shell fire () {
        double tan = Math.tan(targetAngle);
        double xShellSpeed = Math.sqrt((Shell.speedAbsolute*Shell.speedAbsolute)/(1+tan*tan));
        double yShellSpeed = xShellSpeed * tan;

        /*
        This code checks that the signs of x and y speed of the shell matches the angle is actually facing
        Since tangent is used to get a set of x and y values from an angle and tangent has the same value for angle in opposite quadrants of a circle,
        shells occasionaly fly in the direction opposite the cannon barrel without this check
         */

        if (targetAngle > 0 && targetAngle < 0.5*Math.PI) {
            if (xShellSpeed < 0) {
                xShellSpeed = -xShellSpeed;
            }
            if (yShellSpeed < 0) {
                yShellSpeed = -yShellSpeed;
            }
        }

        if (targetAngle > 0.5*Math.PI && targetAngle <= Math.PI) {
            if (xShellSpeed > 0) {
                xShellSpeed = -xShellSpeed;
            }
            if (yShellSpeed < 0) {
                yShellSpeed = -yShellSpeed;
            }
        }

        if (targetAngle < 0 && targetAngle > -0.5*Math.PI) {
            if (xShellSpeed < 0) {
                xShellSpeed = -xShellSpeed;
            }
            if (yShellSpeed > 0) {
                yShellSpeed = -yShellSpeed;
            }
        }

        if (targetAngle < -0.5*Math.PI && targetAngle >= -Math.PI) {
            if (xShellSpeed > 0) {
                xShellSpeed = -xShellSpeed;
            }
            if (yShellSpeed > 0) {
                yShellSpeed = -yShellSpeed;
            }
        }
        return new Shell(xCenter, yCenter, xShellSpeed, yShellSpeed);
    }
}