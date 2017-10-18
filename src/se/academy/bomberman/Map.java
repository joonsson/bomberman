package se.academy.bomberman;

import com.googlecode.lanterna.TextColor;

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
        for(int i = 0; i < getColumns(); i++){

            for(int j = 0; j < getRows(); j++){

                if(i == 0 || i == getColumns()-1){

                    cells[0][j].setColor(new TextColor.RGB(255,0,0)); // TODO sätt en konstant färgvariabel
                    cells[0][j].setWalkable(false);

                    cells[getColumns()-1][j].setColor(new TextColor.RGB(255,0,0));
                    cells[getColumns()-1][j].setWalkable(false);

                }else if (j == 0 || j == getRows() - 1){

                    cells[i][j].setColor(new TextColor.RGB(255,0,0));
                    cells[i][j].setWalkable(false);

                }else if (i % 10 == 0 && j % 5 == 0) {
                    for (int n = i - 3; n < i; n++) {
                        for (int m = j - 3; m < j; m++) {
                            cells[n][m].setColor(new TextColor.RGB(255,0,0));
                            cells[n][m].setWalkable(false);
                        }
                    }
                }
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
        destructible = false;
        this.color = new TextColor.RGB(55,55,10);
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