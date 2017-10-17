/*
package se.academy.bomberman;


import com.googlecode.lanterna.TextColor;

import java.awt.*;
import java.util.ArrayList;

import static java.lang.System.currentTimeMillis;

*/
/**
 *
 *      Superklass mur till spelplanen, oförstörbara hinder i kartan, samt förstörbara tegelstenar
 *
 *//*


public class Obstacle {
    int size;
    int x;
    int y;
    boolean walkable;
}

class Wall extends Obstacle{

    final static int AMOUNT_WALLS = 4, NORTH = 0, SOUTH = 1, WEST = 2, EAST = 3;

    int[][] perimeter;


    int [][] border;

    void drawWalls(Map map){
        MapCell [][] cells = map.getCells();

        for(int i = 0; i < map.getColumns(); i++){
            for(int j = 0; j < map.getRows(); j++){
                if(j > 0 && j < map.getRows()-1){

                    cells[i][0].setColor(new TextColor.RGB(255,0,0)); // TODO sätt en konstant färgvariabel
                    cells[i][0].setWalkable(false);

                    cells[i][map.getRows()-1].setColor(new TextColor.RGB(255,0,0));
                    cells[i][map.getRows()-1].setWalkable(false);
                }else{
                    cells[i][j].setColor(new TextColor.RGB(255,0,0));
                    cells[i][j].setWalkable(false);
                }



            }
        }

        map.setCells(cells);
    }


    private Wall(Map map, int direction){
        switch (direction){
            case NORTH:

                break;
            case SOUTH:

                break;
            case WEST:

                break;
            case EAST:

                break;
        }
        x = map.getColumns();
        y = map.getRows();
    }

    ArrayList<Wall> getPerimeter(Map map, int direction){
        for(int i = 0; i < AMOUNT_WALLS; i++){
            perimeter.add(new Wall(map, direction));

        }
        return perimeter;
    }


}

class Block extends Obstacle{

    TextColor color;

    Block(Color color){
        this.color = new TextColor.RGB(color.getRed(), color.getGreen(), color.getBlue());
        walkable = false;
        size = 2;
    }

}

class Brick extends Obstacle implements Breakable{

}

interface Breakable{

}


*/
/*

    final Väderstreck 0 till 7
    sätt mark till ej walkable


 */
