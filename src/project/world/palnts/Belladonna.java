package project.world.palnts;

import project.Constants;
import project.world.simulation.*;

/**
 * @author Kacper Pietkun
 */
public class Belladonna extends Plant
{
    public Belladonna(World world, Position position, int strength, int initiative, int age, int spawn_attempts, int spawn_chance)
    {
        super(world, position, Constants.NAME_BELLADONNA, strength, initiative, age, spawn_attempts, spawn_chance);
    }

    @Override
    protected String getImagePath()
    {
        return Constants.ICON_PATH_BELLADONNA;
    }

    @Override
    protected Organism spawnChild(Position child_position)
    {
        return new Belladonna(world, position, Constants.STRENGTH_BELLADONNA, Constants.INITIATIVE_BELLADONNA, 1, Constants.SPAWN_ATTEMPTS_BELLADONNA, Constants.SPAWN_CHANCE_BELLADONNA);
    }

    @Override
    protected boolean collision(Organism attacker)
    {
        InfoMessenger info_messenger = world.getInfoMessenger(); // get reference to information messenger to add information to round raport
        info_messenger.addPlantKillsAnimal(attacker, this);
        return true;
    }
}
