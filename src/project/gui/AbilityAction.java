package project.gui;

import project.world.simulation.World;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * @author Kacper Pietkun
 */
public class AbilityAction implements ActionListener
{
    private World world;

    public AbilityAction(World world)
    {
        this.world = world;
    }

    @Override
    public void actionPerformed(ActionEvent e)
    {
        if(world.getHasGameStarted())
        {
            world.setKeyAbilityActive(true);
            world.getInfoMessenger().addCustomMessage("You clicked a button to activate ability!");
        }
    }
}
