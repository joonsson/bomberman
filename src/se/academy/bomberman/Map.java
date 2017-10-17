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
        drawWalls();
    }

    private void drawWalls(){
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

    public MapCell[][] getCells() {
        return cells;
    }

    public int getRows() {
        return rows;
    }

    public int getColumns() {
        return columns;
    }

    public void setCells(MapCell[][] cells) {
        this.cells = cells;
    }
}

class MapCell{
    boolean walkable;
    TextColor color;
    boolean destructible;

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