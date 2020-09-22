package project.world.simulation;

import project.Constants;

/**
 * @author Kacper Pietkun
 */

public abstract class Plant extends Organism
{
    protected int spawn_chance;
    protected int spawn_attempts;

    public Plant(World world, Position position, String class_id, int strength, int initiative, int age, int spawn_attempts, int spawn_chance)
    {
        super(world, position, class_id, strength, initiative, age);
        this.spawn_attempts = spawn_attempts;
        this.spawn_chance = spawn_chance;

    }

    @Override
    protected void action()
    {
        InfoMessenger info_messenger = world.getInfoMessenger(); // get reference to information messenger to add information to round raport
        boolean is_spawning = false;
        int draw;
        Position child_position;
        killAllNearbyAnimals(); // Hogweed feature
        for (int i = 0; i < spawn_attempts; i++)
        {
            draw = random.nextInt(Constants.PERCENT_100);
            if (draw < spawn_chance) // Checks if plant will spread
            {
                child_position = getNotBusyPlace();
                if (!(child_position.equals(position)))  // check if parent has free space (positions will be equal if there is not any free position)
                {
                    world.setOrganismPosition(child_position, spawnChild(child_position));
                    world.getPriorityAction().getOrganismsToSpawn().push(world.getOrganismAtPosition(child_position));  // We push it to stack, because it will add every spawned in this round organism to array
                                                                                                                        // at the end of the round, so ew don't cause any troubles while iterating array
                    world.makeEveryoneOlder();

                    info_messenger.addNewPlantSpawnedInfo(class_id);
                }
                //else info_messenger.addNewPlantNotSpawnedInfo(class_id);
            }
        }
    }

    @Override
    protected boolean collision(Organism attacker)
    {
        InfoMessenger info_messenger = world.getInfoMessenger(); // get reference to information messenger to add information to round raport
        info_messenger.addAnimalEatsPlant(attacker, this);
        return false; // standard plant does nothing to an animal
    }

    // Nothing is done here, it is hogweed's feature
    protected void killAllNearbyAnimals()
    {
    }
}
