package project.world.simulation;

import project.Constants;

import java.util.Stack;

/**
 * @author Kacper Pietkun
 */
public class PriorityAction
{
    private int max_size;
    private int actual_size;
    private Organism[] priority_actions;
    private Stack<Organism> organisms_to_kill; // Contains all organisms that will be killed at the end of the round // WE NEED THIS TWO STACKS to do things after iterating over array triggering actions
    private Stack<Organism> organisms_to_spawn; // Contains all organisms that will born at the end of the round

    public PriorityAction()
    {
        actual_size = 0;
        max_size = Constants.RESIZE_ARRAY_VALUE;
        priority_actions = new Organism[max_size];
        for (int i = 0; i < max_size; i++)
            priority_actions[i] = null;

        organisms_to_kill = new Stack<Organism>();
        organisms_to_spawn = new Stack<Organism>();
    }

    // Kills organisms that are at the stack
    public void deleteOrganismsFromStack()
    {
        Organism org_to_kill = null;
        while (organisms_to_kill.size() > 0)
        {
            org_to_kill = organisms_to_kill.peek();
            deleteFromActions(org_to_kill); // It removes organism from priority_actions array
            organisms_to_kill.pop();
        }
    }

    // Spawn organisms that are at the stack
    public void spawnOrganismsFromStack()
    {
        Organism org_to_spawn = null;
        while (organisms_to_spawn.size()> 0)
        {
            org_to_spawn = organisms_to_spawn.peek();
            addToPriorityActions(org_to_spawn);
            organisms_to_spawn.pop();
        }
    }

    // Check if another organism has not already killed this organism in this round
    public boolean isOrganismDead(Organism org)
    {
        return (organisms_to_kill.search(org) != -1);
    }

    // we know it will return valid organism
    public Organism getOrganismAtPos(int pos)
    {
        return priority_actions[pos];
    }

    public Stack getOrganismsToKill()
    {
        return organisms_to_kill;
    }

    public Stack getOrganismsToSpawn()
    {
        return organisms_to_spawn;
    }

    public int getActualSize()
    {
        return actual_size;
    }

    private Organism[] reallocateMemory(int multiplier)
    {
        max_size = Constants.RESIZE_ARRAY_VALUE * (multiplier + 1);
        Organism[] temp_arr = new Organism[max_size];
        for (int i = 0; i < max_size; i++)
        {
            if (i < actual_size)
                temp_arr[i] = priority_actions[i];
            else
                temp_arr[i] = null;
        }
        return temp_arr;
    }

    // Index in the table, if it doesn't find this organism it returns -1
    private int indexOfThatOrganism(Organism org)
    {
        for (int i = 0; i < actual_size; i++)
        {
            if (priority_actions[i] == org)
                return i;
        }
        return -1;
    }

    // Delete single organism from array
    private void deleteFromActions(Organism org)
    {
        int index = indexOfThatOrganism(org);
        if (index == -1)
            return;

        actual_size--;
        for (int i = index; i < actual_size; i++)
            priority_actions[i] = priority_actions[i + 1];
    }

    // Add single organism to array
    private void addToPriorityActions(Organism org)
    {
        if (actual_size == max_size)
            priority_actions = reallocateMemory(max_size / Constants.RESIZE_ARRAY_VALUE);
        priority_actions[actual_size] = org;
        actual_size++;

        // insert organism on adequate position according to its initiative or age
        int i = actual_size - 2;
        while (i >= 0 && (priority_actions[i].getInitiative() < org.getInitiative() ||
            (priority_actions[i].getInitiative() == org.getInitiative() && priority_actions[i].getAge() < org.getAge())))
        {
            priority_actions[i + 1] = priority_actions[i];
            i--;
        }
        priority_actions[i + 1] = org;
    }
}
