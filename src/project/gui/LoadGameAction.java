package project.gui;

import project.NoGameToLoadException;
import project.world.simulation.World;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * @author Kacper Pietkun
 */
public class LoadGameAction implements ActionListener
{
    private World world;

    public LoadGameAction(World world)
    {
        this.world = world;
    }

    @Override
    public void actionPerformed(ActionEvent e)
    {
        try
        {
            world.loadGame();
            world.getInfoMessenger().resetMessage();
            world.getInfoMessenger().addCustomMessage("Game has been loaded!");
        }
        catch (NoGameToLoadException ex)
        {
            JOptionPane.showMessageDialog(null, "You didn't save any game!");
        }
    }
}
