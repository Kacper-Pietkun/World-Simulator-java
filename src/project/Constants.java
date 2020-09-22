package project;

import project.gui.Frame;
import project.gui.Panel;

import java.awt.*;

/**
 * @author Kacper Pietkun
 */
public class Constants
{
    // Frame, panels, buttons dimensions
    public static final int WINDOW_HEIGHT = 500;
    public static final int WINDOW_WIDTH = 900;
    public static final int CONTENT_HEIGHT = WINDOW_WIDTH;
    public static final int CONTENT_WIDTH = WINDOW_HEIGHT*5/6;
    public static final int BOARD_HEIGHT = WINDOW_HEIGHT*5/6;
    public static final int BOARD_WIDTH = WINDOW_WIDTH/2;
    public static final int MESSENGER_HEIGHT = WINDOW_HEIGHT*5/6;
    public static final int MESSENGER_WIDTH = WINDOW_WIDTH/2;
    public static final int UP_OPTIONS_HEIGHT = WINDOW_HEIGHT/6;
    public static final int UP_OPTIONS_WIDTH = WINDOW_WIDTH;
    public static final int UP_OPTIONS_BUTTONS_SIZE = WINDOW_HEIGHT/10;

    // Frame title
    public static final String BOARD_TITLE = "World Simulation";

    // Gap between board tiles
    public static final int BOARD_TILE_GAP = 0;

    // graphics paths
    public static final String ICON_PATH_GROUND = "./graphics/icons_for_board/icon_ground.png";

    public static final String ICON_PATH_HUMAN = "./graphics/icons_for_board/icon_human.png";
    public static final String ICON_PATH_WOLF = "./graphics/icons_for_board/icon_wolf.png";
    public static final String ICON_PATH_SHEEP = "./graphics/icons_for_board/icon_sheep.png";
    public static final String ICON_PATH_FOX = "./graphics/icons_for_board/icon_fox.png";
    public static final String ICON_PATH_TURTLE = "./graphics/icons_for_board/icon_turtle.png";
    public static final String ICON_PATH_ANTELOPE = "./graphics/icons_for_board/icon_antelope.png";
    public static final String ICON_PATH_CYBERSHEEP = "./graphics/icons_for_board/icon_cybersheep.png";

    public static final String ICON_PATH_GRASS = "./graphics/icons_for_board/icon_grass.png";
    public static final String ICON_PATH_SOWTHISTLE = "./graphics/icons_for_board/icon_sowthistle.png";
    public static final String ICON_PATH_GUARANA = "./graphics/icons_for_board/icon_guarana.png";
    public static final String ICON_PATH_BELLADONNA = "./graphics/icons_for_board/icon_belladonna.png";
    public static final String ICON_PATH_HOGWEED = "./graphics/icons_for_board/icon_hogweed.png";

    public static final String ICON_PATH_NEXT_ROUND = "./graphics/icons_options/icon_next.png";
    public static final String ICON_PATH_LOAD = "./graphics/icons_options/icon_load.png";
    public static final String ICON_PATH_SAVE = "./graphics/icons_options/icon_save.png";
    public static final String ICON_PATH_ABILITY = "./graphics/icons_options/icon_ability.png";


    // Organisms' strength, initiative, class id, spawn_chance, spawn_attempts // Last two only for plants
    public static final int STRENGTH_HUMAN = 5;
    public static final int INITIATIVE_HUMAN = 4;
    public static final String NAME_HUMAN = "Human";

    public static final int STRENGTH_WOLF = 9;
    public static final int INITIATIVE_WOLF = 5;
    public static final String NAME_WOLF = "Wolf";

    public static final int STRENGTH_SHEEP = 4;
    public static final int INITIATIVE_SHEEP = 4;
    public static final String NAME_SHEEP = "Sheep";

    public static final int STRENGTH_FOX = 3;
    public static final int INITIATIVE_FOX = 7;
    public static final String NAME_FOX = "Fox";

    public static final int STRENGTH_TURTLE = 2;
    public static final int INITIATIVE_TURTLE = 1;
    public static final String NAME_TURTLE = "Turtle";

    public static final int STRENGTH_ANTELOPE = 4;
    public static final int INITIATIVE_ANTELOPE = 4;
    public static final String NAME_ANTELOPE = "Antelope";

    public static final int STRENGTH_CYBERSHEEP = 11;
    public static final int INITIATIVE_CYBERSHEEP = 4;
    public static final String NAME_CYBERSHEEP = "CyberSheep";

    public static final int STRENGTH_GRASS = 0;
    public static final int INITIATIVE_GRASS = 0;
    public static final String NAME_GRASS = "Grass";
    public static final int SPAWN_CHANCE_GRASS = 20;
    public static final int SPAWN_ATTEMPTS_GRASS = 1;

    public static final int STRENGTH_SOWTHISTLE = 0;
    public static final int INITIATIVE_SOWTHISTLE = 0;
    public static final String NAME_SOWTHISTLE = "SOWTHISTLE";
    public static final int SPAWN_CHANCE_SOWTHISTLE = 20;
    public static final int SPAWN_ATTEMPTS_SOWTHISTLE = 3;

    public static final int STRENGTH_GUARANA = 0;
    public static final int INITIATIVE_GUARANA = 0;
    public static final String NAME_GUARANA = "Guarana";
    public static final int SPAWN_CHANCE_GUARANA = 20;
    public static final int SPAWN_ATTEMPTS_GUARANA = 1;
    public static final int STRENGTH_BOOST_GUARANA = 3;

    public static final int STRENGTH_BELLADONNA = 99;
    public static final int INITIATIVE_BELLADONNA = 0;
    public static final String NAME_BELLADONNA = "Belladonna";
    public static final int SPAWN_CHANCE_BELLADONNA = 10;
    public static final int SPAWN_ATTEMPTS_BELLADONNA = 1;

    public static final int STRENGTH_HOGWEED = 10;
    public static final int INITIATIVE_HOGWEED = 0;
    public static final String NAME_HOGWEED = "Hogweed";
    public static final int SPAWN_CHANCE_HOGWEED = 10;
    public static final int SPAWN_ATTEMPTS_HOGWEED = 1;



    // How many organisms will be spawned at the beginning og the game (SPAWN_RATE * 100% - percent of the map size)
    public static final double SPAWN_RATE = 0.3; // possible values (0 - 1)
    public static final double SPAWN_RATE_ONE_ANIMAL = 0.03; // possible values (0 - 1)

    // Organisms id
    public static final int NUMBER_ALL_ORGANISMS = 11; // It means just how many classes there are (not how many organisms are on the map) WITHOUT HUMAN
    public static final int ID_WOLF = 0;
    public static final int ID_SHEEP = 1;
    public static final int ID_FOX = 2;
    public static final int ID_TURTLE = 3;
    public static final int ID_ANTELOPE = 4;
    public static final int ID_CYBERSHEEP = 5;
    public static final int ID_GRASS = 6;
    public static final int ID_SOWTHISTLE = 7;
    public static final int ID_GUARANA = 8;
    public static final int ID_BELLADONNA = 9;
    public static final int ID_HOGWEED = 10;
    public static final int ID_HUMAN = 11;


    // Priority action
    public static final int RESIZE_ARRAY_VALUE = 50;

    // SAVE AND LOAD FILE
    public static final String FILE_GAME = "game.txt";


    // In game constants
    public static final boolean CAN_GO_FURTHER = true;
    public static final boolean STOP = false;
    public static final int X = 1;
    public static final int PERCENT_100 = 100;
    public static final int PERCENT_50 = 50;
    public static final int PERCENT_25 = 25;
    public static final int ATTACKER = 1;
    public static final int DEFENDER = 0;
    public static final int IS_RANDOM = -1;


    // GAME DIRECTIONS
    public static final int NUMBER_OF_DIRECTIONS = 4;
    public static final int UP = 0;
    public static final int RIGHT = 1;
    public static final int DOWN = 2;
    public static final int LEFT = 3;
    public static final int NOWHERE = -1;
}
