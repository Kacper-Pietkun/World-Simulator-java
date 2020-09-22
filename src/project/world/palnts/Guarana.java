package project.world.palnts;

import project.Constants;
import project.world.simulation.*;

/**
 * @author Kacper Pietkun
 */
public class Guarana extends Plant
{
    private int strength_boost;

    public Guarana(World world, Position position, int strength, int initiative, int age, int spawn_attempts, int spawn_chance)
    {
        super(world, position, Constants.NAME_GUARANA, strength, initiative, age, spawn_attempts, spawn_chance);
        strength_boost = Constants.STRENGTH_BOOST_GUARANA;
    }

    @Override
    protected String getImagePath()
    {
        return Constants.ICON_PATH_GUARANA;
    }

    @Override
    protected Organism spawnChild(Position child_position)
    {
        return new Guarana(world, child_position, Constants.STRENGTH_GUARANA, Constants.INITIATIVE_GUARANA, 1, Constants.SPAWN_ATTEMPTS_GUARANA, Constants.SPAWN_CHANCE_GUARANA);
    }

    @Override
    protected boolean collision(Organism attacker)
    {
        InfoMessenger info_messenger = world.getInfoMessenger(); // get reference to information messenger to add information to round raport
        info_messenger.addStrengthBoost(attacker, this, strength_boost);
        attacker.setStrength(attacker.getStrength() + strength_boost);
        return false;
    }
}
