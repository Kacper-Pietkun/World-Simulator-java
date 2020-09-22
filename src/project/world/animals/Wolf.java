package project.world.animals;

import project.Constants;
import project.world.simulation.Animal;
import project.world.simulation.Organism;
import project.world.simulation.Position;
import project.world.simulation.World;

/**
 * @author Kacper Pietkun
 */
public class Wolf extends Animal
{
    public Wolf(World world, Position position, int strength, int initiative, int age)
    {
        super(world, position, Constants.NAME_WOLF, strength, initiative, age);
    }

    @Override
    protected String getImagePath()
    {
        return Constants.ICON_PATH_WOLF;
    }

    @Override
    protected Organism spawnChild(Position child_position)
    {
        return new Wolf(world, child_position, Constants.STRENGTH_WOLF, Constants.INITIATIVE_WOLF, 1);
    }
}
