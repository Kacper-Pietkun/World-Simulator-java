package project.gui;

import project.world.simulation.World;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * @author Kacper Pietkun
 */
public class StartGameAction implements ActionListener
{
    private Panel panel_dimensions;
    private World world;
    private JTextField field_width;
    private JTextField field_height;

    public StartGameAction(Panel panel_dimensions, World world, JTextField field_width, JTextField field_height)
    {
        this.panel_dimensions = panel_dimensions;
        this.world = world;
        this.field_width = field_width;
        this.field_height = field_height;
    }

    @Override
    public void actionPerformed(ActionEvent e)
    {
        // updating world's board sizes
        try
        {
            world.setHeight(Integer.parseInt(field_height.getText()));
            world.setWidth(Integer.parseInt(field_width.getText()));

            if(world.getHeight() > 20 || world.getWidth() > 20 || world.getHeight() <= 0 || world.getWidth() <= 0)
            {
                JOptionPane.showMessageDialog(null, "size must be less than 21 and greater than 0");
                return;
            }
            // deleting elements from this panel (panel where user types width and height of the board for new game)
            panel_dimensions.removeAll();
            panel_dimensions.repaint();
            panel_dimensions.revalidate();
            world.startNewGame();
        }
        catch (NumberFormatException ex)
        {
            JOptionPane.showMessageDialog(null, "Incorrect Input :(");
        }
    }
}
