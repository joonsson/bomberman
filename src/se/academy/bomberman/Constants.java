package se.academy.bomberman;

import com.googlecode.lanterna.TextColor;

public interface Constants {

    int FUSE = 3000;

    int size = 9;
    int COLUMNS = size*3+2;
    int SCREENWIDTH = 129;
    int ROWS = size*2+2;
    int SCREENHEIGHT = 49;
    int BJSTARTX = 1;
    int BHSTARTX = COLUMNS-4;
    int BJSTARTY = 1;
    int BHSTARTY = ROWS-3;
    long DELTAT = 16;
    int GROUND = 0, SOLID = 1, SPAWN = 2, BASE = 3, BRICK = 4;
    int NORMAL = 0, BLOCKS = 1, RANDOM = 2;
    int NORTH = 0;
    int SOUTH = 1;
    int WEST = 2;
    int EAST = 3;
    int blockSizeX = 3, blockSizeY = 2;
    TextColor groundColor = new TextColor.RGB(55, 55, 10);
    TextColor p1spawnColor = new TextColor.RGB(55, 55, 10); // Todo välj spelares basfärger
    TextColor p2spawnColor = new TextColor.RGB(55, 55, 10);
    TextColor p3spawnColor = new TextColor.RGB(55, 55, 10);
    TextColor p4spawnColor = new TextColor.RGB(55, 55, 10);
}
