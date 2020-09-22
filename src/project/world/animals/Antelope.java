package project.world.animals;

import project.Constants;
import project.world.simulation.Animal;
import project.world.simulation.Organism;
import project.world.simulation.Position;
import project.world.simulation.World;

/**
 * @author Kacper Pietkun
 */

public class Antelope extends Animal
{

    public Antelope(World world, Position position, int strength, int initiative, int age)
    {
        super(world, position, Constants.NAME_ANTELOPE, strength, initiative, age);
    }

    @Override
    protected String getImagePath()
    {
        return Constants.ICON_PATH_ANTELOPE;
    }

    @Override
    protected Organism spawnChild(Position child_position)
    {
        return new Antelope(world, child_position, Constants.STRENGTH_ANTELOPE, Constants.INITIATIVE_ANTELOPE, 1);
    }

    @Override
    protected int howManySteps()
    {
        return random.nextInt(2) + 1;
    }

    @Override
    protected boolean runFromBattle()
    {
        int number_of_places = howManyNotBusyPlaces();
        return (random.nextInt(2) == 0 && number_of_places > 0); // 50% chance to run
    }

}
