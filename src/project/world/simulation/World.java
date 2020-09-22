package project.world.simulation;

import project.Constants;
import project.NoGameToLoadException;
import project.gui.AddNewOrganismAction;
import project.gui.Button;
import project.gui.Panel;
import project.world.animals.*;
import project.world.palnts.*;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Random;
import java.util.Scanner;

/**
 * @author Kacper Pietkun
 */

public class World
{
    private int width;
    private int height;
    private int key_input;
    private boolean key_ability_active;
    private boolean has_game_started;
    private Panel panel_board;
    private Panel panel_messenger;
    private Button[][] buttons_organisms;
    private Organism[][] organisms;
    private PriorityAction priority_action;
    private Random random;
    private InfoMessenger info_messenger;

    public World(Panel panel_board, Panel panel_messenger)
    {
        width = 0;
        height = 0;
        has_game_started = false;
        this.panel_messenger = panel_messenger;
        this.panel_board = panel_board;
        buttons_organisms = null;
        organisms = null;
        random = new Random();
        priority_action = new PriorityAction();
    }

    public void startNewGame()
    {
        panel_board.setLayout(new GridLayout(height, width, Constants.BOARD_TILE_GAP, Constants.BOARD_TILE_GAP)); // Setting new layout for board panel // This layout will make creating tiles for the board easier
        panel_messenger.setLayout(new GridLayout(1, 1, Constants.BOARD_TILE_GAP, Constants.BOARD_TILE_GAP)); // Setting new layout for board panel // This layout will make creating tiles for the board easier
        createWorldButtons();
        createMessengerBox();
        allocateOrganismsArray();
        spawnOnStart();
        drawWorld();
        has_game_started = true;
    }

    public void nextRound()
    {
        executeRound();
        drawWorld();
        key_input = Constants.NOWHERE; // rest player's input
        key_ability_active = false; // reset player's input
    }

    // Basing on images from organisms, we set new images to the buttons
    public void drawWorld()
    {
        for (int i = 0; i < height; i++)
        {
            for(int j = 0; j < width; j++)
            {
                if(organisms[i][j] == null)
                    buttons_organisms[i][j].setImage(Constants.ICON_PATH_GROUND, width, height);
                else
                    buttons_organisms[i][j].setImage(organisms[i][j].getImagePath(), width, height);
            }
        }
    }

    public void changeOrganismPosition(Organism org, Position old_position, Position new_position)
    {
        Organism organism = getOrganismAtPosition(old_position);
        if (org == organism) // validation
        {
            organism.setPosition(new_position);
            organisms[new_position.getY()][new_position.getX()] = organism;
            organisms[old_position.getY()][old_position.getX()] = null;
        }
    }

    public void setOrganismPosition(Position position, Organism org)
    {
        org.setPosition(position);
        organisms[position.getY()][position.getX()] = org;
    }

    public boolean isThisSpotFree(Position position)
    {
        return (organisms[position.getY()][position.getX()] == null);
    }

    public boolean isThisSpotInsideMap(Position position)
    {
        int x = position.getX();
        int y = position.getY();
        return (x >= 0 && x < width && y >= 0 && y < height);
    }

    public Organism getOrganismAtPosition(Position position)
    {
        return organisms[position.getY()][position.getX()];
    }

    // Increases age of every organism in the game (+1)
    public void makeEveryoneOlder()
    {
        for (int i = 0; i < height; i++)
            for(int j = 0; j < width; j++)
                if(organisms[i][j] != null)
                    organisms[i][j].setAge(organisms[i][j].getAge() + 1);
    }

    public void killOrganismAt(Position position)
    {
        if (isThisSpotInsideMap(position) == true && isThisSpotFree(position) == false) // validation of the position
        {
            priority_action.getOrganismsToKill().push(getOrganismAtPosition(position)); // we don't delete organism now (in priority action array), because we don't want to mess up priority action array during iterating it
                                                                                        // instead we add organisms to kill to stack and delete them after round it finished
            // delete organisms[position.getY()][position.getX()]; WE DONT WANT DELETE HERE ORGANISM BEACUSE THERE IS A POINTER TO THIS ORGANISM AT STACK IN PRIORITY ACTION, IT WILL BE DELETED THERE
            organisms[position.getY()][position.getX()] = null;
        }
    }

    public void saveGame()
    {
        String organism_info; // TEMPLATE: CLASS_ID POSITION(x,y) STRENGTH INITIATIVE AGE ACTIVE_ABILITY ACTIVE_ABILITY_ROUNDS (for organisms that are not human last two properties may be anything (we won't look at it))
        try
        {
            File file = new File(Constants.FILE_GAME);
            if(file.exists() == false)
                file.createNewFile();
            FileWriter writer = new FileWriter(file);
            writer.write(height + " " + width + "\n");

            for (int i = 0; i < height; i++)
            {
                for (int j = 0; j < width; j++)
                {
                    if (organisms[i][j] != null)
                    {
                        organism_info = organisms[i][j].getClassId() + " " + String.valueOf(organisms[i][j].getPosition().getX()) + " " +
                            (organisms[i][j].getPosition().getY()) + " " + String.valueOf(organisms[i][j].getStrength()) + " " +
                                String.valueOf(organisms[i][j].getInitiative()) + " " + String.valueOf(organisms[i][j].getAge()) + " " +
                                String.valueOf(organisms[i][j].getIsAbilityActive()) + " " + String.valueOf(organisms[i][j].getRoundsOfAbility());
                        writer.write(organism_info + "\n");
                    }
                }
            }
            writer.close();
        }
        catch (IOException e)
        {
            System.out.println("File Error");
            e.printStackTrace();
        }
    }

    public void loadGame() throws NoGameToLoadException
    {
        String line;
        String[] elements;
        String class_id;
        int pos_x, pos_y, strength, initiative, age, ability_rounds;
        boolean active_ability;

        try
        {
            File file = new File(Constants.FILE_GAME);
            if(file.exists() == false)
                throw new NoGameToLoadException("Game file was not found");

            deleteCurrentOrganisms();

            Scanner scanner = new Scanner(file);
            line = scanner.nextLine();
            elements = line.split(" ");
            height = Integer.parseInt(elements[0]);
            width = Integer.parseInt(elements[1]);

            allocateOrganismsArray(); // new organisms will be added to this array
            while (scanner.hasNext()) // Loop thorugh all the organisms and create them
            {
                line = scanner.nextLine();
                elements = line.split(" ");
                class_id = elements[0];
                pos_x = Integer.parseInt(elements[1]);
                pos_y = Integer.parseInt(elements[2]);
                strength = Integer.parseInt(elements[3]);
                initiative = Integer.parseInt(elements[4]);
                age = Integer.parseInt(elements[5]);
                active_ability = Boolean.parseBoolean(elements[6]);
                ability_rounds = Integer.parseInt(elements[7]);

                if (class_id.equals(Constants.NAME_WOLF))
                    organisms[pos_y][pos_x] = new Wolf(this, new Position(pos_x, pos_y), strength, initiative, age);
                else if (class_id.equals(Constants.NAME_SHEEP))
                    organisms[pos_y][pos_x] = new Sheep(this, new Position(pos_x, pos_y), strength, initiative, age);
                else if (class_id.equals(Constants.NAME_FOX))
                    organisms[pos_y][pos_x] = new Fox(this, new Position(pos_x, pos_y), strength, initiative, age);
                else if (class_id.equals(Constants.NAME_TURTLE))
                    organisms[pos_y][pos_x] = new Turtle(this, new Position(pos_x, pos_y), strength, initiative, age);
                else if (class_id.equals(Constants.NAME_ANTELOPE))
                    organisms[pos_y][pos_x] = new Antelope(this, new Position(pos_x, pos_y), strength, initiative, age);
                else if (class_id.equals(Constants.NAME_CYBERSHEEP))
                    organisms[pos_y][pos_x] = new CyberSheep(this, new Position(pos_x, pos_y), strength, initiative, age);
                else if (class_id.equals(Constants.NAME_GRASS))
                    organisms[pos_y][pos_x] = new Grass(this, new Position(pos_x, pos_y), strength, initiative, age, Constants.SPAWN_ATTEMPTS_GRASS, Constants.SPAWN_CHANCE_GRASS);
                else if (class_id.equals(Constants.NAME_SOWTHISTLE))
                    organisms[pos_y][pos_x] = new Sowthistle(this, new Position(pos_x, pos_y), strength, initiative, age, Constants.SPAWN_ATTEMPTS_SOWTHISTLE, Constants.SPAWN_CHANCE_SOWTHISTLE);
                else if (class_id.equals(Constants.NAME_GUARANA))
                    organisms[pos_y][pos_x] = new Guarana(this, new Position(pos_x, pos_y), strength, initiative, age, Constants.SPAWN_ATTEMPTS_GUARANA, Constants.SPAWN_CHANCE_GUARANA);
                else if (class_id.equals(Constants.NAME_BELLADONNA))
                    organisms[pos_y][pos_x] = new Belladonna(this, new Position(pos_x, pos_y), strength, initiative, age, Constants.SPAWN_ATTEMPTS_BELLADONNA, Constants.SPAWN_CHANCE_BELLADONNA);
                else if (class_id.equals(Constants.NAME_HOGWEED))
                    organisms[pos_y][pos_x] = new Hogweed(this, new Position(pos_x, pos_y), strength, initiative, age, Constants.SPAWN_ATTEMPTS_HOGWEED, Constants.SPAWN_CHANCE_HOGWEED);
                else if (class_id.equals(Constants.NAME_HUMAN))
                    organisms[pos_y][pos_x] = new Human(this, new Position(pos_x, pos_y), strength, initiative, age, active_ability, ability_rounds);

                priority_action.getOrganismsToSpawn().push(organisms[pos_y][pos_x]);
            }
            scanner.close();
        }
        catch (FileNotFoundException e)
        {
            e.printStackTrace();
        }

        refreshGUI();

        priority_action.spawnOrganismsFromStack();
    }

    // Creating GUI if player clicked to load game before starting new one
    public void refreshGUI()
    {
        destroyCurrentBoard(); // Clearing menu to type dimensions of the board
        panel_board.setLayout(new GridLayout(height, width, Constants.BOARD_TILE_GAP, Constants.BOARD_TILE_GAP)); // Setting new layout for board panel // This layout will make creating tiles for the board easier
        if(has_game_started == false) // If player chose to load game from main menu
        {
            panel_messenger.setLayout(new GridLayout(1, 1, Constants.BOARD_TILE_GAP, Constants.BOARD_TILE_GAP)); // Setting new layout for board panel // This layout will make creating tiles for the board easier
            createMessengerBox();
        }
        createWorldButtons();
        drawWorld();
        has_game_started = true;
    }

    // If game has already started then clear all the buttons
    // else if game hasn't started then clear menu where player types dimensions of the board
    public void destroyCurrentBoard()
    {
        panel_board.removeAll();
        panel_board.repaint();
        panel_board.revalidate();
    }

    public PriorityAction getPriorityAction()
    {
        return priority_action;
    }

    public void setWidth(int width)
    {
        this.width = width;
    }

    public void setHeight(int height)
    {
        this.height = height;
    }

    public int getHeight()
    {
        return height;
    }

    public int getWidth()
    {
        return width;
    }

    public int getKeyInput()
    {
        return key_input;
    }

    public void setKeyInput(int key_input)
    {
        this.key_input = key_input;
    }

    public boolean getKeyAbilityActive()
    {
        return key_ability_active;
    }

    public void setKeyAbilityActive(boolean key_ability_active)
    {
        this.key_ability_active = key_ability_active;
    }

    public InfoMessenger getInfoMessenger()
    {
        return info_messenger;
    }

    public boolean getHasGameStarted()
    {
        return has_game_started;
    }

    public Organism getOrganismPlace(Position position)
    {
        return organisms[position.getX()][position.getY()];
    }

 // ********************************************************************************************************************************************

    // Triggered at the beginning of the new game
    private void spawnOnStart()
    {
        Position position;
        int number_of_organisms = (int) (Constants.SPAWN_RATE * width * height + 1);
        int number_of_one_organism = (int) (Constants.SPAWN_RATE_ONE_ANIMAL * width * height); // We know that every organism will spawn
        int which_organism = 0;

        for (int i = 0; i < number_of_organisms; i++)
        {
            position = getRandomFreePosition();
            if (i == 0)
                organisms[position.getY()][position.getX()] = new Human(this, position, Constants.STRENGTH_HUMAN, Constants.INITIATIVE_HUMAN, 1);
            else if (which_organism < 10 && number_of_one_organism > 0)
                organisms[position.getY()][position.getX()] = createRandomOrganism(position, which_organism);
            else
                organisms[position.getY()][position.getX()] = createRandomOrganism(position, Constants.IS_RANDOM);

            priority_action.getOrganismsToSpawn().push(organisms[position.getY()][position.getX()]);
            makeEveryoneOlder();
            if (i != 0 && number_of_one_organism > 0 && i % number_of_one_organism == 0)
                which_organism++;
        }
        priority_action.spawnOrganismsFromStack();
    }

    // Triggers action for every living organism
    private void executeRound()
    {
        info_messenger.resetMessage();
        int action_length = priority_action.getActualSize();
        Organism organism;
        for (int i = 0; i < action_length; i++)
        {
            organism = priority_action.getOrganismAtPos(i);
            if(priority_action.isOrganismDead(organism) == false)
                organism.action(); // If an organism will be killed in action then we don't remove it from priority action array (otherwise ACCESS VIOLATION)
                                                               // We add it to stack instead and at the end of the round all organisms from stack are deleted from priority action array
        }
        priority_action.spawnOrganismsFromStack(); // We have to spawn from stack before deleting from stack to avoid errors
        priority_action.deleteOrganismsFromStack();
    }

    // create buttons - GUI board
    private void createWorldButtons()
    {
        buttons_organisms = new Button[height][];
        for (int i = 0; i < height; i ++)
        {
            buttons_organisms[i] = new Button[width];
            for (int j = 0; j < width; j++)
            {
                buttons_organisms[i][j] = new Button(panel_board, new AddNewOrganismAction(this, new Position(j,i)));
                panel_board.add(buttons_organisms[i][j]);
            }
        }
    }

    private void createMessengerBox()
    {
        JTextArea text = new JTextArea("");
        text.setEditable(false);
        text.setBackground(new Color(153, 192, 156));
        JScrollPane scroll_pane = new JScrollPane(text);
        panel_messenger.add(scroll_pane);
        info_messenger = new InfoMessenger(text);
    }

    // Initializing whole array with nulls
    private void allocateOrganismsArray()
    {
        organisms = new  Organism[height][];
        for (int i = 0; i < height; i++)
        {
            organisms[i] = new Organism[width];
            for(int j = 0; j < width; j++)
                organisms[i][j] = null;
        }
    }

    // Deleting all organisms from priority_queue
    private void deleteCurrentOrganisms()
    {
        for (int i = 0; i < height; i++)
        {
            for (int j = 0; j < width; j++)
            {
                if (organisms[i][j] != null)
                {
                    priority_action.getOrganismsToKill().push(organisms[i][j]);
                    organisms[i][j] = null;
                }
            }
        }
        priority_action.deleteOrganismsFromStack(); // Deleting all organisms from priority_queue
    }

    private Position getRandomFreePosition()
    {
        Position new_position = new Position();
        do
        {
            new_position.setX(random.nextInt(width));
            new_position.setY(random.nextInt(height));
        }
        while(getOrganismAtPosition(new_position) != null);
        return new_position;
    }

    private Organism createRandomOrganism(Position position, int is_random)
    {
        int which_organism = (is_random == Constants.IS_RANDOM ? random.nextInt(Constants.NUMBER_ALL_ORGANISMS) : is_random);
        switch (which_organism)
        {
            case Constants.ID_WOLF:
                return new Wolf(this, position, Constants.STRENGTH_WOLF, Constants.INITIATIVE_WOLF, 1);
            case Constants.ID_SHEEP:
                return new Sheep(this, position, Constants.STRENGTH_SHEEP, Constants.INITIATIVE_SHEEP, 1);
            case Constants.ID_FOX:
                return new Fox(this, position, Constants.STRENGTH_FOX, Constants.INITIATIVE_FOX, 1);
            case Constants.ID_TURTLE:
                return new Turtle(this, position, Constants.STRENGTH_TURTLE, Constants.INITIATIVE_TURTLE, 1);
            case Constants.ID_ANTELOPE:
                return new Antelope(this, position, Constants.STRENGTH_ANTELOPE, Constants.INITIATIVE_ANTELOPE, 1);
            case Constants.ID_CYBERSHEEP:
                return new CyberSheep(this, position, Constants.STRENGTH_CYBERSHEEP, Constants.INITIATIVE_CYBERSHEEP, 1);
            case Constants.ID_GRASS:
                return new Grass(this, position, Constants.STRENGTH_GRASS, Constants.INITIATIVE_GRASS, 1, Constants.SPAWN_ATTEMPTS_GRASS, Constants.SPAWN_CHANCE_GRASS);
            case Constants.ID_SOWTHISTLE:
                return new Sowthistle(this, position, Constants.STRENGTH_SOWTHISTLE, Constants.INITIATIVE_SOWTHISTLE, 1, Constants.SPAWN_ATTEMPTS_SOWTHISTLE, Constants.SPAWN_CHANCE_SOWTHISTLE);
            case Constants.ID_GUARANA:
                return new Guarana(this, position, Constants.STRENGTH_GUARANA, Constants.INITIATIVE_GUARANA, 1, Constants.SPAWN_ATTEMPTS_GUARANA, Constants.SPAWN_CHANCE_GUARANA);
            case Constants.ID_BELLADONNA:
                return new Belladonna(this, position, Constants.STRENGTH_BELLADONNA, Constants.INITIATIVE_BELLADONNA, 1, Constants.SPAWN_ATTEMPTS_BELLADONNA, Constants.SPAWN_CHANCE_BELLADONNA);
            case Constants.ID_HOGWEED:
                return new Hogweed(this, position, Constants.STRENGTH_HOGWEED, Constants.INITIATIVE_HOGWEED, 1, Constants.SPAWN_ATTEMPTS_HOGWEED, Constants.SPAWN_CHANCE_HOGWEED);
        }
        return null;
    }
}
