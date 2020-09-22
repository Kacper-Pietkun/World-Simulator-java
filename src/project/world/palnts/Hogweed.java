package project.world.palnts;

import project.Constants;
import project.world.simulation.*;

/**
 * @author Kacper Pietkun
 */
public class Hogweed extends Plant
{
    public Hogweed(World world, Position position, int strength, int initiative, int age, int spawn_attempts, int spawn_chance)
    {
        super(world, position, Constants.NAME_HOGWEED, strength, initiative, age, spawn_attempts, spawn_chance);
    }

    @Override
    protected String getImagePath()
    {
        return Constants.ICON_PATH_HOGWEED;
    }

    @Override
    protected Organism spawnChild(Position child_position)
    {
        return new Hogweed(world, position, Constants.STRENGTH_HOGWEED, Constants.INITIATIVE_HOGWEED, 1, Constants.SPAWN_ATTEMPTS_HOGWEED, Constants.SPAWN_CHANCE_HOGWEED);
    }

    @Override
    protected boolean collision(Organism attacker)
    {
        if(attacker instanceof Animal && ((Animal) attacker).canBeKilledByHogweed() == false)
            return super.collision(attacker);
        InfoMessenger info_messenger = world.getInfoMessenger(); // get reference to information messenger to add information to round raport
        info_messenger.addPlantKillsAnimal(attacker, this);
        return true;
    }

    @Override
    protected void killAllNearbyAnimals()
    {
        InfoMessenger info_messenger = world.getInfoMessenger(); // get reference to information messenger to add information to round raport
        Organism org;

        org = world.isThisSpotInsideMap(new Position(position.getX(), position.getY() - 1)) == true ? world.getOrganismAtPosition(new Position(position.getX(), position.getY() - 1)) : null; // UP
        if (org != null && org instanceof Animal && ((Animal) org).canBeKilledByHogweed() == true)
        {
            world.killOrganismAt(org.getPosition());
            info_messenger.addHogweedKillsInZone(org);
        }
        org = world.isThisSpotInsideMap(new Position(position.getX(), position.getY() + 1)) == true ? world.getOrganismAtPosition(new Position(position.getX(), position.getY() + 1)) : null; // DOWN
        if (org != null && org instanceof Animal && ((Animal) org).canBeKilledByHogweed() == true)
        {
            world.killOrganismAt(org.getPosition());
            info_messenger.addHogweedKillsInZone(org);
        }
        org = world.isThisSpotInsideMap(new Position(position.getX() + 1, position.getY())) == true ? world.getOrganismAtPosition(new Position(position.getX() + 1, position.getY())) : null; // RIGHT
        if (org != null && org instanceof Animal && ((Animal) org).canBeKilledByHogweed() == true)
        {
            world.killOrganismAt(org.getPosition());
            info_messenger.addHogweedKillsInZone(org);
        }
        org = world.isThisSpotInsideMap(new Position(position.getX() - 1, position.getY())) == true ? world.getOrganismAtPosition(new Position(position.getX() - 1, position.getY())) : null; // LEFT
        if (org != null && org instanceof Animal && ((Animal) org).canBeKilledByHogweed() == true)
        {
            world.killOrganismAt(org.getPosition());
            info_messenger.addHogweedKillsInZone(org);
        }
    }
}
