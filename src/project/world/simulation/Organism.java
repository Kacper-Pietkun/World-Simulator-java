package project.world.simulation;

import project.Constants;

import java.util.Random;

/**
 * @author Kacper Pietkun
 */
public abstract class Organism
{
    protected String class_id;
    protected int strength;
    protected int initiative;
    protected int age;
    protected World world;
    protected Position position;
    protected Random random;
    protected boolean is_ability_active; // for human // but we need it here to be able to save it to txt file
    protected int rounds_of_ability; // Turned on or off, it depends

    public Organism(World world, Position position, String class_id, int strength, int initiative, int age)
    {
        this.world = world;
        this.position = position;
        this.class_id = class_id;
        this.strength = strength;
        this.initiative = initiative;
        this.age = age;
        is_ability_active = false;
        rounds_of_ability = 0;
        random = new Random();
    }

    public Organism(World world, Position position, String class_id, int strength, int initiative, int age, boolean is_ability_active, int rounds_of_ability)
    {
        this.world = world;
        this.position = position;
        this.class_id = class_id;
        this.strength = strength;
        this.initiative = initiative;
        this.age = age;
        this.is_ability_active = is_ability_active;
        this.rounds_of_ability = rounds_of_ability;
        random = new Random();
    }

    // For instance to get random position for a new organism (breeding)
    // returns parent's position if there isn't any place for a new organism
    public Position getNotBusyPlace()
    {
        if (howManyNotBusyPlaces() == 0) // There is no space for breeding
            return position;

        Position new_position;
        int rand_delta_x, rand_delta_y; // values -1, 0, 1
        int x_or_y;
        do
        {
            rand_delta_x = random.nextInt(3) - 1; // to get -1 or 0 or 1
            rand_delta_y = random.nextInt(3) - 1; // to get -1 or 0 or 1
            x_or_y =  random.nextInt(2);
            if(x_or_y == Constants.X)
                new_position = new Position(position.getX() + rand_delta_x, position.getY());
            else
                new_position = new Position(position.getX(), position.getY() + rand_delta_y);
        } while (world.isThisSpotInsideMap(new_position) == false || world.isThisSpotFree(new_position) == false);

        return new_position;
    }

    public int getAge()
    {
        return age;
    }

    public void setAge(int age)
    {
        this.age = age;
    }

    public int getInitiative()
    {
        return initiative;
    }

    public int getStrength()
    {
        return strength;
    }

    public void setStrength(int strength)
    {
        this.strength = strength;
    }

    public int getRoundsOfAbility()
    {
        return rounds_of_ability;
    }

    public void setRoundsOfAbility(int rounds_of_ability)
    {
        this.rounds_of_ability = rounds_of_ability;
    }

    public boolean getIsAbilityActive()
    {
        return is_ability_active;
    }

    public void setIsAbilityActive(boolean is_ability_active)
    {
        this.is_ability_active = is_ability_active;
    }

    public void setPosition(Position position)
    {
        this.position = position;
    }

    public Position getPosition()
    {
        return position;
    }

    public String getClassId()
    {
        return class_id;
    }

    // Used for instance for breeding, how many possibilities there is for a new organism
    protected int howManyNotBusyPlaces()
    {
        int count = 0;
        if (world.isThisSpotInsideMap(new Position(position.getX(), position.getY() - 1)) && world.isThisSpotFree(new Position(position.getX(), position.getY() - 1))) // UP
            count++;
        if (world.isThisSpotInsideMap(new Position(position.getX() + 1, position.getY())) && world.isThisSpotFree(new Position(position.getX() + 1, position.getY()))) // RIGHT
            count++;
        if (world.isThisSpotInsideMap(new Position(position.getX(), position.getY() + 1)) && world.isThisSpotFree(new Position(position.getX(), position.getY() + 1))) // DOWN
            count++;
        if (world.isThisSpotInsideMap(new Position(position.getX() - 1, position.getY())) && world.isThisSpotFree(new Position(position.getX() - 1, position.getY()))) // LEFT
            count++;
        return count;
    }

    protected abstract boolean collision(Organism collision_organism);
    protected abstract void action();
    protected abstract String getImagePath();
    protected abstract Organism spawnChild(Position child_position);
}