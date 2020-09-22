package project.world.simulation;

import project.Constants;
import project.world.animals.Human;

import java.util.Random;

/**
 * @author Kacper Pietkun
 */

public abstract class Animal extends Organism
{
    public Animal(World world, Position position, String class_id, int strength, int initiative, int age)
    {
        super(world, position, class_id, strength, initiative, age);
    }
    public Animal(World world, Position position, String class_id, int strength, int initiative, int age, boolean is_ability_active, int rounds_of_ability)
    {
        super(world, position, class_id, strength, initiative, age, is_ability_active, rounds_of_ability);
    }

    @Override
    public void action()
    {
        boolean collision_result = Constants.CAN_GO_FURTHER;
        Position new_position;
        Position delta_position; // This position will tell to which position animal goes (helpful when animal go more than one step)

        if (world.getKeyAbilityActive() == true)
            activateSuperAbility();

        int how_many_steps = howManySteps();
        int how_many_accessible_positions = numberOfAccessiblePositions(how_many_steps);

        if (how_many_accessible_positions == 0)
            return; // animal doesn't have where to move
        if (isItAbleToMove() == false)
            return; // won't move this round

        new_position = getPositionToGo(how_many_steps);

        if (new_position == position)
            return;
        delta_position = new Position(new_position.getX() - position.getX(), new_position.getY() - position.getY());
        for (int i = 0; i < how_many_steps; i++) // Loop for number of steps that organism will do
        {
            if (world.isThisSpotFree(new_position)) // Free spot, just go there
                world.changeOrganismPosition(this, position, new_position);
            else // Spot is occupied by another organisms - colission
                collision_result = collision(world.getOrganismAtPosition(new_position));

            if (collision_result == Constants.STOP)
                break; // Don't go further because environment doesn't allow
            new_position = new Position(position.getX() + delta_position.getX(), position.getY() + delta_position.getY());
        }
    }

    public boolean canBeKilledByHogweed() // public, because it is info for plant
    {
        return true;
    }

    @Override
    protected boolean collision(Organism collision_organism)
    {
        InfoMessenger info_messenger = world.getInfoMessenger(); // get reference to information messenger to add information to round raport
        if (getClass().equals(collision_organism.getClass())) // It means breeding
        {
            Position child_position = collision_organism.getNotBusyPlace();
            if (!(child_position == collision_organism.getPosition()))  // check if first parent has free space
            {
                world.setOrganismPosition(child_position, collision_organism.spawnChild(child_position));
                world.getPriorityAction().getOrganismsToSpawn().push(world.getOrganismAtPosition(child_position));  // We push it to stack, because it will add every spawned in this round organism to array
                                                                                                                    // at the end of the round, so ew don't cause any troubles while iterating array
                world.makeEveryoneOlder();

                info_messenger.addNewAnimalBornInfo(class_id);
                return Constants.STOP;
            }
            else // check if second parent has free space
            {
                child_position = getNotBusyPlace();
                if (child_position == collision_organism.getPosition())
                {
                    world.setOrganismPosition(child_position, collision_organism.spawnChild(child_position));
                    world.getPriorityAction().getOrganismsToSpawn().push(world.getOrganismAtPosition(child_position));  // We push it to stack, because it will add every spawned in this round organism to array
                    // at the end of the round, so ew don't cause any troubles while iterating array
                    world.makeEveryoneOlder();

                    info_messenger.addNewAnimalBornInfo(class_id);
                    return Constants.STOP;
                }
            }
            // else No space for the child
            //info_messenger.addNewAnimalNotBornInfo(class_id);
        }
        else if(collision_organism instanceof Animal) // It means fighting
        {
            Animal defender = (Animal) collision_organism;
            Organism dies = null;
            if (defender.defendItself(this) == true)
            {
                info_messenger.addDefendedItselfFrom(this, defender);
                return Constants.STOP; // Defender defends itslef from being eaten
            }
            Position position_for_winner = defender.getPosition(); // winner of the fight will go to this place (or just stay there if organism defended themself)

            if (runFromBattle() == true)
            {
                world.changeOrganismPosition(this, position, getNotBusyPlace());
                info_messenger.addAnimalRanAway(this, defender, this);
                return Constants.STOP; // If this animal ran away then they don't want to attack the same organism again
            }
            else if (defender.runFromBattle())
            {
                world.changeOrganismPosition(defender, defender.getPosition(), defender.getNotBusyPlace());
                info_messenger.addAnimalRanAway(this, defender, defender);
                return Constants.CAN_GO_FURTHER; // If defender ran away then this animal can go further
            }

            if (strength >= defender.getStrength()) // if strengths are equal then attacker wins
            {
                dies = defender;
                info_messenger.addAnimalKilledByAnimal(this, defender, Constants.ATTACKER);
            }
            else
            {
                dies = this;
                info_messenger.addAnimalKilledByAnimal(this, defender, Constants.DEFENDER);
            }

            world.killOrganismAt(dies.getPosition());
            world.changeOrganismPosition(this, position, position_for_winner);
            if (dies == this)
                return Constants.STOP;
            return Constants.CAN_GO_FURTHER;
        }
        else if (collision_organism instanceof Plant)
        {
            boolean will_die = false;
            Plant plant = (Plant) collision_organism;
            Organism dies = plant;
            will_die = plant.collision(this);
            world.killOrganismAt(dies.getPosition()); // Plant always dies, because it was eaten
            if (will_die == true)
            {
                world.killOrganismAt(position); // Animal die, after eating this plant;
                return Constants.STOP;
            }
            else // else it goes to the plant's position
            {
                world.changeOrganismPosition(this, position, plant.getPosition());
                return Constants.CAN_GO_FURTHER;
            }
        }
        return Constants.CAN_GO_FURTHER;
    }

    protected void activateSuperAbility()
    {
        // Only human has an ability
    }

    // Implementation in this class will always return 1, Antelope will generate 1 or 2
    protected int howManySteps()
    {
        return 1;
    }

    // return false, because this is feature for fox
    protected boolean isThereStrongerOrganism(Position position)
    {
        return false;
    }

    // Returns true, this is feature for turtle
    protected boolean isItAbleToMove()
    {
        return true;
    }

    // returns false, this is feature for turtle
    protected boolean defendItself(Organism attacker)
    {
        return false;
    }

    // returns false, this is feature for antelope
    protected boolean runFromBattle()
    {
        return false;
    }

    // If it is possible, then we return position where animal will go, if animal will make two steps, we check if it will be possible,
    // however if it is possible we return position as if he made only one step. We will move him gradually;
    // If animal cannot go there we return its current position
    protected Position getOneOfPossiblePositionToMove(int direction, int how_many_steps)
    {
        Position new_position = position; // We check if our destination position is free, but we return new position which is only one step away from an animal, because it will go to its destination gradually
        if (direction == Constants.UP && world.isThisSpotInsideMap(new Position(position.getX(), position.getY() - how_many_steps)) == true)
            return new Position(position.getX(), position.getY() - 1);
        else if (direction == Constants.RIGHT && world.isThisSpotInsideMap(new Position(position.getX() + how_many_steps, position.getY())) == true)
            return new Position(position.getX() + 1, position.getY());
        else if (direction == Constants.DOWN && world.isThisSpotInsideMap(new Position(position.getX(), position.getY() + how_many_steps)) == true)
            return new Position(position.getX(), position.getY() + 1);
        else if (direction == Constants.LEFT && world.isThisSpotInsideMap(new Position(position.getX() - how_many_steps, position.getY())) == true)
            return new Position(position.getX() - 1, position.getY());
        return position;
    }

    protected Position getPositionToGo(int how_many_steps)
    {
        Position new_position;
        int direction;
        do
        {
            direction = random.nextInt(4);
            new_position = getOneOfPossiblePositionToMove(direction, how_many_steps);
        } while (new_position == position || isThereStrongerOrganism(new_position) == true); // it means it generated position outside of the map, so repeat rand()
        return new_position;
    }

    private int numberOfAccessiblePositions(int how_many_steps)
    {
        int number = 0;
        if (world.isThisSpotInsideMap(new Position(position.getX(), position.getY() - how_many_steps)) &&
                isThereStrongerOrganism(new Position(position.getX(), position.getY() - how_many_steps)) == false) // UP
            number++;
        if (world.isThisSpotInsideMap(new Position(position.getX() + how_many_steps, position.getY())) &&
                isThereStrongerOrganism(new Position(position.getX() + how_many_steps, position.getY())) == false) // RIGHT
            number++;
        if (world.isThisSpotInsideMap(new Position(position.getX(), position.getY() + how_many_steps)) &&
                isThereStrongerOrganism(new Position(position.getX(), position.getY() + how_many_steps)) == false) // DOWN
            number++;
        if (world.isThisSpotInsideMap(new Position(position.getX() - how_many_steps, position.getY()))  &&
                isThereStrongerOrganism(new Position(position.getX() - how_many_steps, position.getY())) == false) // LEFT
            number++;
        return number;
    }

}
