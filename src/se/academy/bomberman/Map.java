package se.academy.bomberman;

import java.awt.*;

public class Map {

    private static Map map = new Map();
    private MapCell [][] cells;
    private int rows, columns;

    Map(){}

    Map(int rows, int columns){
        this.rows = rows;
        this.columns = columns;
        init();
    }

    private void init(){
        cells =  new MapCell[rows][columns];
        for(int i = 0; i < rows; i++){
            for(int j = 0; j < columns; j++){
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
    Color color;

    MapCell(){
        this.walkable = true;
        this.color = Color.gray;
    }

    // region Getters/Setters
    public void setWalkable(boolean walkable) {
        this.walkable = walkable;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    public boolean isWalkable() {
        return walkable;
    }

    public Color getColor() {
        return color;
    }

    // endregion

}