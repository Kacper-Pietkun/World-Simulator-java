package project.world.animals;

import project.Constants;
import project.world.simulation.*;

/**
 * @author Kacper Pietkun
 */
public class Human extends Animal
{
    public Human(World world, Position position, int strength, int initiative, int age)
    {
        super(world, position, Constants.NAME_HUMAN, strength, initiative, age);
    }

    public Human(World world, Position position, int strength, int initiative, int age, boolean is_ability_active, int rounds_of_ability)
    {
        super(world, position, Constants.NAME_HUMAN, strength, initiative, age, is_ability_active, rounds_of_ability);
    }

    @Override
    protected String getImagePath()
    {
        return Constants.ICON_PATH_HUMAN;
    }

    // Added this function here in case we ever develop human class
    @Override
    protected Organism spawnChild(Position child_position)
    {
        return new Human(world, child_position, Constants.STRENGTH_HUMAN, Constants.INITIATIVE_HUMAN, 1);
    }

    @Override
    protected Position getPositionToGo(int how_many_steps)
    {
        int key = world.getKeyInput();
        switch (key)
        {
            case Constants.UP:
                return getOneOfPossiblePositionToMove(Constants.UP, how_many_steps);
            case Constants.DOWN:
                return getOneOfPossiblePositionToMove(Constants.DOWN, how_many_steps);
            case Constants.LEFT:
                return getOneOfPossiblePositionToMove(Constants.LEFT, how_many_steps);
            case Constants.RIGHT:
                return getOneOfPossiblePositionToMove(Constants.RIGHT, how_many_steps);
        }
        return position;
    }

    @Override
    protected int howManySteps()
    {
        rounds_of_ability++;
        if (is_ability_active == true)
        {
            if (rounds_of_ability <= 3)
                return 2;
            else if (rounds_of_ability <= 5)
                return random.nextInt(2) + 1;
            else if (rounds_of_ability == 10)
                is_ability_active = false; // Changing active to false after 10 round because, if it is inactive then we know that player can use it again
        }
        return 1;
}

    @Override
    protected void activateSuperAbility()
    {
        InfoMessenger info_messenger = world.getInfoMessenger();
        if (is_ability_active == false)
        {
            is_ability_active = true;
            rounds_of_ability = 0;
            info_messenger.addAbilityActivated(true);
        }
        else
            info_messenger.addAbilityActivated(false);
}

}
