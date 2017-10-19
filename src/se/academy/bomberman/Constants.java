package se.academy.bomberman;

import com.googlecode.lanterna.TextColor;
import com.googlecode.lanterna.graphics.TextImage;

public interface Constants {

    // BOMB
    int FUSE = 1500;
    int DROPCHANCE = 4;

    // MAP
    int wallThickness = 1;
    int blockSizeX = 3;
    int blockSizeY = 2;
    int size = 13;
    int COLUMNS = size * blockSizeX + (wallThickness * 2);
    int ROWS = size * blockSizeY + (wallThickness * 2);
    int SCREENWIDTH = 129;
    int SCREENHEIGHT = 49;

    //TERRAIN
    TextColor groundColor = new TextColor.RGB(55, 55, 10);
    int GROUND = 0;
    int SOLID = 1;
    int SPAWN = 2;
    int BASE = 3;
    int BRICK = 4;
    int NORMAL = 0;
    int BLOCKS = 1;
    int RANDOM = 2;

    // PLAYERS
    char[] bombModel    = {' ', 'T', '/', 'N', '*', 'T'};
    char[] playerModel1 = {'¤', '(', ' ', 'I', '¤', ')'};
    char[] playerModel2 = {'o', '(', '|', '_', '.', ')'};
    char[] playerModel3 = {'*',' ','u',' ','*',' '};
    char[] playerModel4 = {'P','R','A','I','T','K'};
    char[] death ={'D','A','E','T','H','S'};
    char[] LIFE ={'!','L','I','F','E','!'};
    char[] SPEEDS ={'S','P','E','E','D',' ',};
    char[] BIOMBS ={'B','O','O','O','O','M'};

    int BJSTARTX = 1;
    int BHSTARTX = COLUMNS - 4;
    int BJSTARTY = 1;
    int BHSTARTY = ROWS - 3;
    long DELTAT = 16;
    int NORTH = 0;
    int SOUTH = 1;
    int WEST = 2;
    int EAST = 3;
    TextColor p1spawnColor = new TextColor.RGB(55, 55, 10); // Todo välj spelares basfärger
    TextColor p2spawnColor = new TextColor.RGB(55, 55, 10);
    TextColor p3spawnColor = new TextColor.RGB(55, 55, 10);
    TextColor p4spawnColor = new TextColor.RGB(55, 55, 10);

   
}
