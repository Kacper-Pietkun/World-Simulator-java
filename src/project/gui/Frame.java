package project.gui;

import project.Constants;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyListener;

/**
 * @author Kacper Pietkun
 */

// This class is created in order to hide JFrame setup (cleaning main function)
public class Frame extends JFrame
{
    public Frame(LayoutManager layout, String title, Dimension dimension)
    {
        setLayout(layout);
        setPreferredSize(dimension);
        setTitle(title);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setResizable(false);
        setFocusable(true);
    }
}
