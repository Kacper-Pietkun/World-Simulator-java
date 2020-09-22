package project.gui;

import project.world.simulation.World;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * @author Kacper Pietkun
 */
public class NextRoundAction implements ActionListener
{
    private World world;

    public NextRoundAction(World world)
    {
        this.world = world;
    }

    @Override
    public void actionPerformed(ActionEvent e)
    {
        if(world.getHasGameStarted() == true)
            world.nextRound();
    }
}
