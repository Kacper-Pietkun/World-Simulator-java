package project.gui;

import project.world.simulation.World;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * @author Kacper Pietkun
 */
public class SaveGameAction implements ActionListener
{
    private World world;

    public SaveGameAction(World world)
    {
        this.world = world;
    }

    @Override
    public void actionPerformed(ActionEvent e)
    {
        if(world.getHasGameStarted())
        {
            world.saveGame();
            world.getInfoMessenger().addCustomMessage("Game has been saved!");
        }
    }
}
