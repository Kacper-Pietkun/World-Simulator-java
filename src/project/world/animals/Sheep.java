package project.world.animals;

import project.Constants;
import project.world.simulation.Animal;
import project.world.simulation.Organism;
import project.world.simulation.Position;
import project.world.simulation.World;

/**
 * @author Kacper Pietkun
 */
public class Sheep extends Animal
{

    public Sheep(World world, Position position, int strength, int initiative, int age)
    {
        super(world, position, Constants.NAME_SHEEP, strength, initiative, age);
    }

    @Override
    protected String getImagePath()
    {
        return Constants.ICON_PATH_SHEEP;
    }

    @Override
    protected Organism spawnChild(Position child_position)
    {
        return new Sheep(world, child_position, Constants.STRENGTH_SHEEP, Constants.INITIATIVE_SHEEP, 1);
    }
}
