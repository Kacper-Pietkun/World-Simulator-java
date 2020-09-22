package project.gui;

import project.Constants;
import project.world.animals.*;
import project.world.palnts.*;
import project.world.simulation.Organism;
import project.world.simulation.Position;
import project.world.simulation.World;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * @author Kacper Pietkun
 */
public class AddNewOrganismAction implements ActionListener
{
    private World world;
    private Position position;

    public AddNewOrganismAction(World world, Position position)
    {
        this.world = world;
        this.position = position;
    }

    @Override
    public void actionPerformed(ActionEvent e)
    {
        int option = 12;
        if(world.getOrganismAtPosition(position) != null)
        {
            JOptionPane.showMessageDialog(null, "You cannot create an organism here!");
            return;
        }
        try
        {
            option = Integer.parseInt(JOptionPane.showInputDialog("What organism would you like to spawn? \n" +
                    "0. Wolf \n1. Sheep \n2. Fox \n3.Turtle \n4. Antelope \n5. CyberSheep " +
                    "\n6. Grass \n7. Sowthistle \n8.Guarana \n9.Belladonna \n10.Hogweed \n11.Nothing"));
        }
        catch (NumberFormatException ex)
        {
            JOptionPane.showMessageDialog(null, "Wrong input!");
        }


        Organism org = null;
        Organism place = null;
        switch (option)
        {
            case Constants.ID_WOLF:
                org = new Wolf(world, position, Constants.STRENGTH_WOLF, Constants.INITIATIVE_WOLF, 1);
                break;
            case Constants.ID_SHEEP:
                org = new Sheep(world, position, Constants.STRENGTH_SHEEP, Constants.INITIATIVE_SHEEP, 1);
                break;
            case Constants.ID_FOX:
                org = new Fox(world, position, Constants.STRENGTH_FOX, Constants.INITIATIVE_FOX, 1);
                break;
            case Constants.ID_TURTLE:
                org = new Turtle(world, position, Constants.STRENGTH_TURTLE, Constants.INITIATIVE_TURTLE, 1);
                break;
            case Constants.ID_ANTELOPE:
                org = new Antelope(world, position, Constants.STRENGTH_ANTELOPE, Constants.INITIATIVE_ANTELOPE, 1);
                break;
            case Constants.ID_CYBERSHEEP:
                org = new CyberSheep(world, position, Constants.STRENGTH_CYBERSHEEP, Constants.INITIATIVE_CYBERSHEEP, 1);
                break;
            case Constants.ID_GRASS:
                org = new Grass(world, position, Constants.STRENGTH_GRASS, Constants.INITIATIVE_GRASS, 1, Constants.SPAWN_ATTEMPTS_GRASS, Constants.SPAWN_CHANCE_GRASS);
                break;
            case Constants.ID_SOWTHISTLE:
                org = new Sowthistle(world, position, Constants.STRENGTH_SOWTHISTLE, Constants.INITIATIVE_SOWTHISTLE, 1, Constants.SPAWN_ATTEMPTS_SOWTHISTLE, Constants.SPAWN_CHANCE_SOWTHISTLE);
                break;
            case Constants.ID_GUARANA:
                org = new Guarana(world, position, Constants.STRENGTH_GUARANA, Constants.INITIATIVE_GUARANA, 1, Constants.SPAWN_ATTEMPTS_GUARANA, Constants.SPAWN_CHANCE_GUARANA);
                break;
            case Constants.ID_BELLADONNA:
                org = new Belladonna(world, position, Constants.STRENGTH_BELLADONNA, Constants.INITIATIVE_BELLADONNA, 1, Constants.SPAWN_ATTEMPTS_BELLADONNA, Constants.SPAWN_CHANCE_BELLADONNA);
                break;
            case Constants.ID_HOGWEED:
                org = new Hogweed(world, position, Constants.STRENGTH_HOGWEED, Constants.INITIATIVE_HOGWEED, 1, Constants.SPAWN_ATTEMPTS_HOGWEED, Constants.SPAWN_CHANCE_HOGWEED);
                break;
            default:
                return;
        }
        world.makeEveryoneOlder();
        world.setOrganismPosition(position, org);
        world.getPriorityAction().getOrganismsToSpawn().push(org);
        world.getPriorityAction().spawnOrganismsFromStack();
        world.drawWorld();
    }
}