package project.world.palnts;

import project.Constants;
import project.world.simulation.Organism;
import project.world.simulation.Plant;
import project.world.simulation.Position;
import project.world.simulation.World;

/**
 * @author Kacper Pietkun
 */
public class Grass extends Plant
{
    public Grass(World world, Position position, int strength, int initiative, int age, int spawn_attempts, int spawn_chance)
    {
        super(world, position, Constants.NAME_GRASS, strength, initiative, age, spawn_attempts, spawn_chance);
    }

    @Override
    protected String getImagePath()
    {
        return Constants.ICON_PATH_GRASS;
    }

    @Override
    protected Organism spawnChild(Position child_position)
    {
        return new Grass(world, child_position, Constants.STRENGTH_GRASS, Constants.INITIATIVE_GRASS, 1, Constants.SPAWN_ATTEMPTS_GRASS, Constants.SPAWN_CHANCE_GRASS);
    }
}
