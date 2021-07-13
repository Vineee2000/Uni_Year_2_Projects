import java.awt.*;
import java.util.*;

/**
 * A class that manages all the game entities and interacions between them
 * Holds a LinkedList of all the entities in the game
 */
public class EntityManager {
    private LinkedList<Entity> entities = new LinkedList<Entity>();
    private int planesHitThisTick;
    private int wingsKilledThisTick;
    private int damageTakenThisTick;

    EntityManager () {
    }

    /**
     * Manager's draw method draws all the entities in the game
     * @param g
     */
    protected void draw (Graphics g) {
        try {
            for (Entity entity : entities) {
                entity.draw(g);
            }
        }
        /*Rarely drawing produces ConcurrentModificationException when multiple offscreen entities are deleted, but this has no impact on the game
        and can be simply retried next tick without adverse effects, so this error is basically ignored
         */
        catch (ConcurrentModificationException e) {
            for (Entity entity : entities) {
                entity.draw(g);
            }
        }
    }

    /**
     *Adds an entity to the list and thus, to the game
     * @param entity
     */
    void addEntity (Entity entity) {
        entities.add(entity);
    }

    /**
     * Method for moving a specific entity.
     * Not in use. Left for testing and potential expansion purposes.
     * @param target
     */
    void moveEntity (EntityMovable target) {
        if (offScreenCheck(target) == false) {
            target.move();
        }
    }

    /**
     * Method for setting the speed of a specific movable entity.
     * Not in use. Left for testing and potential expansion purposes.
     * @param target
     * @param xSpeed
     * @param ySpeed
     */
    void setEntitySpeed (EntityMovable target,  int xSpeed, int ySpeed) {
        target.xSpeed = xSpeed;
        target.ySpeed = ySpeed;
    }

    /**
     * Method for deleting a specific entity.
     * Not in use. Left for testing and potential expansion purposes.
     * @param entity
     */
    void deleteEntity (Entity entity) {
        entities.remove(entity);
    }

    /**
     * Method that moves all the movable entities in the game by their speed
     */
    protected void moveAllEntities () {
        for (Entity entity: entities) {
            if (entity instanceof EntityMovable) {
                ((EntityMovable) entity).move();
            }
        }
    }

    /**
     * Method that deletes all entities that are marked for deletion
     */
    protected void deleteMarkedEntities () {
        Iterator <Entity> iterator = entities.iterator();
        while (iterator.hasNext()) {
            Entity entity = iterator.next();
            if (entity.toBeDeleted) {
                if (entity instanceof PlaneWing) {
                    wingsKilledThisTick++;
                }
                iterator.remove();
            }
        }
    }

    /**
     * Method specifically for spawning a plane wing moving in a straight line from top to bottom
     * @param xCoord
     * @param ySpeed
     */
    void addWing(int xCoord, double ySpeed) {
        entities.add(new PlaneWing(xCoord, ySpeed));
    }

    /**
     * Method that checks if provided entity is outside the bounds of the game screen
     * @param entity
     * @return
     */
    private boolean offScreenCheck (EntityMovable entity) {
        if ((entity.xPos+2*entity.hitBoxRadius)<0 || (entity.yPos+2*entity.hitBoxRadius)<0 || (entity.xPos > Main.GAME_RESOLUTION_X)
        || (entity.yPos > Main.GAME_RESOLUTION_Y)) {
            return true;
        }
        return false;
    }

    /**
     * This method deletes all entities that are outside of the game screen
     */
    void clearOffScreen () {
        Iterator<Entity> iterator =entities.iterator();
        while (iterator.hasNext()) {
            Entity entity = iterator.next();
            if (entity instanceof EntityMovable) {
                if (entity instanceof PlaneWing && entity.yPos>Main.GAME_RESOLUTION_Y) {
                    damageTakenThisTick = 4-((PlaneWing) entity).getDamage();
                }
                if (offScreenCheck((EntityMovable) entity)) {
                    iterator.remove();
                }
            }
        }
    }

    /**
     * This method points all the cannons in normal gameplay towards the given point
     * @param target
     */
    void aimCannons (Point target) {
        int i = 0;
        //Since this method is called every tick and amount of cannons in normal gameplay is known,
        //the method stops after 4 cannons
        for (int cannonCount = 0; cannonCount < 4;) {
            Entity entity =entities.get(i);
            if (entity instanceof AutoCannon) {
                ((AutoCannon) entity).aim(target);
                cannonCount++;
            }
            i++;
        }
    }

    /**
     * This method fires all the cannons during normal gameplay
     */
    void fireCannons () {
        int i = 0;
        //Since amount of cannons in normal gameplay is known, this method stops after 4 cannons
        for (int cannonCount = 0; cannonCount < 4;) {
            Entity entity =entities.get(i);
            if (entity instanceof AutoCannon) {
                entities.add(((AutoCannon) entity).fire());
                cannonCount++;
            }
            i++;
        }
    }

    /**
     * This method checks if any shells and planes overlap. If so, the planes take 1 damage for every shell they are overlapping with
     * and the shells that are overlapping with planes are deleted
     */
    void runCollisions () {
        Iterator <Entity> iterator = entities.iterator();
        while (iterator.hasNext()) {
            Entity entity = iterator.next();
            if (entity instanceof Shell) {
                boolean hitNotScored = true;
                Iterator <Entity> hitCheckIterator = entities.iterator();
                while (hitNotScored && hitCheckIterator.hasNext()) {
                    Entity checkTarget = hitCheckIterator.next();
                    if (checkTarget instanceof PlaneWing && entity.overlaps(checkTarget.getArea())) {
                        ((PlaneWing) checkTarget).takeDamage();
                        planesHitThisTick++;
                        hitNotScored = false;
                    }
                }
                if (hitNotScored == false) {
                    entity.markToDelete();
                }
            }
        }
    }

    /**
     * This method provides an array of integers where first entry is
     * the amount of individual planes shot down since last time this method was called
     * and the second is the amount of wings reduced to 0 planes since last times this method was called
     *
     * Used for scoring
     * @return
     */
    ArrayList <Integer> tickScoreInfo () {
        ArrayList <Integer> scoreInfoArray = new ArrayList<Integer>();
        scoreInfoArray.add(planesHitThisTick);
        scoreInfoArray.add(wingsKilledThisTick);
        planesHitThisTick = 0;
        wingsKilledThisTick = 0;
        return scoreInfoArray;
    }

    /**
     * This method returns how many planes have gotten past the player's defences since last time it was called
     * @return
     */
    int getDamage() {
        int damageTaken = damageTakenThisTick;
        damageTakenThisTick = 0;
        return damageTaken;
    }
}
