package project.world.palnts;

import project.Constants;
import project.world.simulation.Organism;
import project.world.simulation.Plant;
import project.world.simulation.Position;
import project.world.simulation.World;

/**
 * @author Kacper Pietkun
 */
public class Sowthistle extends Plant
{
    public Sowthistle(World world, Position position, int strength, int initiative, int age, int spawn_attempts, int spawn_chance)
    {
        super(world, position, Constants.NAME_SOWTHISTLE, strength, initiative, age, spawn_attempts, spawn_chance);
    }

    @Override
    protected String getImagePath()
    {
        return Constants.ICON_PATH_SOWTHISTLE;
    }

    @Override
    protected Organism spawnChild(Position child_position)
    {
        return new Sowthistle(world, child_position, Constants.STRENGTH_SOWTHISTLE, Constants.INITIATIVE_SOWTHISTLE, 1, Constants.SPAWN_ATTEMPTS_SOWTHISTLE, Constants.SPAWN_CHANCE_SOWTHISTLE);
    }
}
