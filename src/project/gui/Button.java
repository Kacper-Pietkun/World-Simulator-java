package project.gui;

import project.Constants;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

/**
 * @author Kacper Pietkun
 */

// This class is created in order to hide JButton setup (cleaning other functions)
public class Button extends JButton
{
    private JPanel panel; // This panel is parent of the button

    public Button(String text, int pos_x, int pos_y, int width, int height, JPanel panel, ActionListener actionListener)
    {
        setText(text);
        setBounds(pos_x, pos_y, width, height);
        this.panel = panel;
        panel.add(this);
        addActionListener(actionListener);
        setFocusable(false);
    }

    // Constructor for buttons on the board, don't need position, scale and text, buttons are added to grid layout, if we set our own scale it won't be applied
    public Button(JPanel panel, ActionListener actionListener)
    {
        this.panel = panel;
        panel.add(this);
        addActionListener(actionListener);
        setFocusable(false);
    }

    public Button(JPanel panel, String icon_path, Dimension dimension, ActionListener actionListener)
    {
        this.panel = panel;
        panel.add(this);
        addActionListener(actionListener);
        setFocusable(false);
        setPreferredSize(dimension);
        try
        {
            setIcon(new ImageIcon(icon_path));
        }
        catch (Exception e)
        {
            System.out.println("GRAPICS NOT FOUND");
        }
    }

    public void setImage(String icon_path, int board_width, int board_height)
    {
        try
        {
            // Basing on how many buttons there are in one row and column, we resize image_icon to fit the whole button
            int new_image_width = Constants.BOARD_WIDTH / board_width;
            int new_image_height = Constants.BOARD_HEIGHT / board_height;

            ImageIcon icon = new ImageIcon(icon_path);
            Image image = icon.getImage();
            Image scaled_image = image.getScaledInstance(new_image_width, new_image_height,  java.awt.Image.SCALE_SMOOTH);
            icon = new ImageIcon(scaled_image);

            setIcon(icon);
        }
        catch (Exception e)
        {
            System.out.println("GRAPICS NOT FOUND");
        }
    }
}
