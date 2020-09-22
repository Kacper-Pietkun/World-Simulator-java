package project.world.animals;

import project.Constants;
import project.world.simulation.Animal;
import project.world.simulation.Organism;
import project.world.simulation.Position;
import project.world.simulation.World;

/**
 * @author Kacper Pietkun
 */
public class Turtle extends Animal
{

    public Turtle(World world, Position position, int strength, int initiative, int age)
    {
        super(world, position, Constants.NAME_TURTLE, strength, initiative, age);
    }

    @Override
    protected String getImagePath()
    {
        return Constants.ICON_PATH_TURTLE;
    }

    @Override
    protected Organism spawnChild(Position child_position)
    {
        return new Turtle(world, child_position, Constants.STRENGTH_TURTLE, Constants.INITIATIVE_TURTLE, 1);
    }

    @Override
    protected boolean isItAbleToMove()
    {
        int will_move = random.nextInt(Constants.PERCENT_100); // 0 - 99
        return (will_move < Constants.PERCENT_25); // 0 - 24
    }

    @Override
    protected boolean defendItself(Organism attacker)
    {
        return (attacker.getStrength() < 5);
    }
}
