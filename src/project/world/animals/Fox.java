package project.world.animals;

import project.Constants;
import project.world.simulation.Animal;
import project.world.simulation.Organism;
import project.world.simulation.Position;
import project.world.simulation.World;

/**
 * @author Kacper Pietkun
 */

public class Fox extends Animal
{
    public Fox(World world, Position position, int strength, int initiative, int age)
    {
        super(world, position, Constants.NAME_FOX, strength, initiative, age);
    }

    @Override
    protected String getImagePath()
    {
        return Constants.ICON_PATH_FOX;
    }

    @Override
    protected Organism spawnChild(Position child_position)
    {
        return new Fox(world, child_position, Constants.STRENGTH_FOX, Constants.INITIATIVE_FOX, 1);
    }

    protected boolean isThereStrongerOrganism(Position position)
    {
        Organism org_at_position = world.getOrganismAtPosition(position);
        if(org_at_position != null)
            return (strength < org_at_position.getStrength());
        return false;
    }
}
