package se.academy.bomberman;

import com.googlecode.lanterna.TextColor;

import java.awt.*;

public class Map {

    private static Map map = new Map();
    private MapCell [][] cells;
    private int rows, columns;

    Map(){}

    Map(int columns, int rows){
        this.rows = rows;
        this.columns = columns;
        init();
    }

    private void init(){
        cells =  new MapCell[columns][rows];
        for(int i = 0; i < columns; i++){
            for(int j = 0; j < rows; j++){
                cells[i][j] = new MapCell();
            }
        }
    }

    public MapCell[][] getCells() {
        return cells;
    }

    public int getRows() {
        return rows;
    }

    public int getColumns() {
        return columns;
    }

}

class MapCell{
    boolean walkable;
    TextColor color;

    MapCell(){
        this.walkable = true;
        this.color = new TextColor.RGB(250,250,250);
    }

    // region Getters/Setters
    public void setWalkable(boolean walkable) {
        this.walkable = walkable;
    }

    public void setColor(TextColor color) {
        this.color = color;
    }

    public boolean isWalkable() {
        return walkable;
    }

    public TextColor getColor() {
        return color;
    }

    // endregion

}