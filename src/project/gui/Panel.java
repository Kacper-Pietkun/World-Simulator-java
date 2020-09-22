package project.gui;

import javax.swing.*;
import java.awt.*;

/**
 * @author Kacper Pietkun
 */
public class Panel extends JPanel
{
    public Component parent;
    public Panel(Component parent, LayoutManager layout, Color color, Dimension dimension)
    {
        this.parent = parent;
        setLayout(layout);
        setBackground(color);
        setPreferredSize(dimension);
        setSize(dimension);
    }
}
