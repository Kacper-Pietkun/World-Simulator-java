package project.world.animals;

import project.Constants;
import project.world.palnts.Belladonna;
import project.world.palnts.Hogweed;
import project.world.simulation.Animal;
import project.world.simulation.Organism;
import project.world.simulation.Position;
import project.world.simulation.World;

/**
 * @author Kacper Pietkun
 */
public class CyberSheep extends Animal
{
    public CyberSheep(World world, Position position, int strength, int initiative, int age)
    {
        super(world, position, Constants.NAME_CYBERSHEEP, strength, initiative, age);
    }

    @Override
    public boolean canBeKilledByHogweed() // public, because it is info for plant
    {
        return false;
    }

    @Override
    protected String getImagePath()
    {
        return Constants.ICON_PATH_CYBERSHEEP;
    }

    @Override
    protected Organism spawnChild(Position child_position)
    {
        return new CyberSheep(world, position, Constants.STRENGTH_CYBERSHEEP, Constants.INITIATIVE_CYBERSHEEP, 1);
    }

    @Override
    protected Position getPositionToGo(int how_many_steps)
    {
        Position closest_hogweed_position = getClosestHogweed();
        if(closest_hogweed_position.equals(position))
            return super.getPositionToGo(how_many_steps); // If there isn't any hogweed on the map, cybersheep behaves like normal sheep

        // If there is a hogweed, then we check four directions where cybersheep can go and we return first position
        // that guarantees that cybersheep will be closer to this hogweed        Position new_position;
        Position new_position;
        int old_distance_squared = (int)(Math.pow(closest_hogweed_position.getX() - position.getX(), 2) + Math.pow(closest_hogweed_position.getY() - position.getY(), 2));
        int new_distance_squared;
        do
        {
            new_position = super.getPositionToGo(how_many_steps);
            new_distance_squared = (int)(Math.pow(closest_hogweed_position.getX() - new_position.getX(), 2) + Math.pow(closest_hogweed_position.getY() - new_position.getY(), 2));
        }
        while(new_distance_squared >= old_distance_squared);
        return new_position;
    }

    private Position getClosestHogweed()
    {
        Position closest_hogweed_position = new Position(position.getX(), position.getY()); // setting this position to cybersheep's position // if we return it we will know that there isn't any hogweed on the map
        Position organism_position = new Position(0,0);
        int closest_distance_squared = 0; // setting distance to 0, hogweed wasn't found
        int distance_squared;
        for (int i = 0; i < world.getHeight(); i++)
        {
            organism_position.setY(i);
            for (int j = 0; j < world.getWidth(); j++)
            {
                organism_position.setX(j);
                if(world.getOrganismAtPosition(organism_position) instanceof Hogweed)
                {
                    distance_squared = (int) (Math.pow(organism_position.getX() - position.getX(), 2) + Math.pow(organism_position.getY() - position.getY(), 2));
                    if(distance_squared < closest_distance_squared || closest_distance_squared == 0)
                    {
                        closest_distance_squared = distance_squared;
                        closest_hogweed_position.setX(organism_position.getX());
                        closest_hogweed_position.setY(organism_position.getY());
                    }
                }
            }
        }
        return closest_hogweed_position;
    }
}
