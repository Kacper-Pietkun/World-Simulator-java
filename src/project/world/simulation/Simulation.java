package project.world.simulation;

import project.ArrowsListener;
import project.Constants;
import project.gui.*;
import project.gui.Button;
import project.gui.Frame;
import project.gui.Panel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * @author Kacper Pietkun
 */

public class Simulation
{
    public static void main(String[] args)
    {
        // Creating main window, and setting up panels // Making sections for different components // Menu at the top // Game board left side // Commentator right side
        Frame frame_main = new Frame(new BorderLayout(), Constants.BOARD_TITLE, new Dimension(Constants.WINDOW_WIDTH, Constants.WINDOW_HEIGHT)); // main GUI
        Panel panel_up_options = new Panel(frame_main, new FlowLayout(), new Color(133, 182, 126), new Dimension(Constants.UP_OPTIONS_WIDTH, Constants.UP_OPTIONS_HEIGHT)); // Panel for options listed in the top of the window
        Panel panel_content = new Panel(frame_main, new BorderLayout(), new Color(145, 205, 181), new Dimension(Constants.CONTENT_WIDTH, Constants.CONTENT_HEIGHT)); // Panel for main content of the game
        Panel panel_board = new Panel(panel_content, null, new Color(145, 205, 155), new Dimension(Constants.BOARD_WIDTH, Constants.BOARD_HEIGHT)); // Panel for game's board
        Panel panel_messenger = new Panel(panel_content, null, new Color(145, 205, 10), new Dimension(Constants.MESSENGER_WIDTH, Constants.MESSENGER_HEIGHT)); // Panel for game's messanger

        World world = new World(panel_board, panel_messenger);
        boardDimensionsWindow(panel_board, world);
        menuOptionsWindow(panel_up_options, world);

        panel_content.add(panel_board, BorderLayout.CENTER);
        panel_content.add(panel_messenger, BorderLayout.EAST);

        frame_main.add(panel_up_options, BorderLayout.NORTH);
        frame_main.add(panel_content, BorderLayout.CENTER);

        frame_main.addKeyListener(new ArrowsListener(world));
        frame_main.pack();
        frame_main.setVisible(true);
    }

    private static void menuOptionsWindow(Panel panel, World world)
    {
        Button button_next_round = new Button(panel, Constants.ICON_PATH_NEXT_ROUND,
                new Dimension(Constants.UP_OPTIONS_BUTTONS_SIZE,Constants.UP_OPTIONS_BUTTONS_SIZE), new NextRoundAction(world));

        Button button_load = new Button(panel, Constants.ICON_PATH_LOAD,
                new Dimension(Constants.UP_OPTIONS_BUTTONS_SIZE,Constants.UP_OPTIONS_BUTTONS_SIZE), new LoadGameAction(world));

        Button button_save = new Button(panel, Constants.ICON_PATH_SAVE,
                new Dimension(Constants.UP_OPTIONS_BUTTONS_SIZE,Constants.UP_OPTIONS_BUTTONS_SIZE), new SaveGameAction(world));

        Button button_ability = new Button(panel, Constants.ICON_PATH_ABILITY,
                new Dimension(Constants.UP_OPTIONS_BUTTONS_SIZE,Constants.UP_OPTIONS_BUTTONS_SIZE), new AbilityAction(world));

        JLabel lable = new JLabel("Kacper Pietkun, 180082");
        panel.add(lable);
    }

    // adding to board panel elements to set width and height of board
    private static void boardDimensionsWindow(Panel panel, World world)
    {
        JLabel label_board_width = new JLabel("Type board width: ");
        label_board_width.setBounds(50,panel.getHeight()/5,120,20);
        panel.add(label_board_width);

        JLabel label_board_height = new JLabel("Type board height: ");
        label_board_height.setBounds(50,panel.getHeight()/5 + 40,120,20);
        panel.add(label_board_height);

        JTextField text_field_board_width = new JTextField(2);
        text_field_board_width.setBounds(170,panel.getHeight()/5,100,20);
        panel.add(text_field_board_width);

        JTextField text_field_board_height = new JTextField(2);
        text_field_board_height.setBounds(170,panel.getHeight()/5 + 40,100,20);
        panel.add(text_field_board_height);
        Button button = new Button("Start New Game", 50, panel.getHeight()/5 + 80,
                150, 20, panel, new StartGameAction(panel, world, text_field_board_width, text_field_board_height));
    }
}
