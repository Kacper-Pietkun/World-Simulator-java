package project;

import project.world.simulation.World;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

/**
 * @author Kacper Pietkun
 */
public class ArrowsListener implements KeyListener
{
    private World world;

    public ArrowsListener(World world)
    {
        this.world = world;
    }

    @Override
    public void keyTyped(KeyEvent e)
    {
    }

    @Override
    public void keyPressed(KeyEvent e)
    {
        int key_code = e.getKeyCode();
        switch (key_code)
        {
            case KeyEvent.VK_UP:
                world.setKeyInput(Constants.UP);
                if(world.getHasGameStarted() == true)
                    world.nextRound();
                break;
            case KeyEvent.VK_RIGHT:
                world.setKeyInput(Constants.RIGHT);
                if(world.getHasGameStarted() == true)
                    world.nextRound();
                break;
            case KeyEvent.VK_DOWN:
                world.setKeyInput(Constants.DOWN);
                if(world.getHasGameStarted() == true)
                    world.nextRound();
                break;
            case KeyEvent.VK_LEFT:
                world.setKeyInput(Constants.LEFT);
                if(world.getHasGameStarted() == true)
                    world.nextRound();
                break;
        }
    }

    @Override
    public void keyReleased(KeyEvent e)
    {
    }
}
