package se.academy.bomberman;

import com.googlecode.lanterna.TextColor;

public class Map {

    private static Map map = new Map();
    private MapCell[][] cells;
    private int rows, columns;
    private TextColor wallColor = new TextColor.RGB(4, 54, 0);

    private Map() {
    }

    Map(int columns, int rows) {
        this.rows = rows;
        this.columns = columns;
        init();
    }

    private void init() {
        cells = new MapCell[columns][rows];
        for (int i = 0; i < columns; i++) {
            for (int j = 0; j < rows; j++) {
                cells[i][j] = new MapCell();
            }
        }
        drawWalls();
    }

    private void drawWalls() {
        for (int i = 0; i < getColumns(); i++) {

            for (int j = 0; j < getRows(); j++) {

                if (i == 0 || i == getColumns() - 1) {

                    cells[0][j].setColor(wallColor); // TODO sätt en konstant färgvariabel
                    cells[0][j].setWalkable(false);
                    cells[0][j].setDestructible(false);

                    cells[getColumns() - 1][j].setColor(wallColor);
                    cells[getColumns() - 1][j].setWalkable(false);
                    cells[getColumns() - 1][j].setDestructible(false);

                } else if (j == 0 || j == getRows() - 1) {

                    cells[i][j].setColor(wallColor);
                    cells[i][j].setWalkable(false);
                    cells[i][j].setDestructible(false);

                } else if (i % 10 == 0 && j % 5 == 0) {
                    drawObstacles();
                }
            }
        }
    }

    private void drawObstacles() {
        for (int x = 6; x < getColumns(); x = x + 6) {
            for (int y = 3; y < getRows()-1; y = y+4) {
                for ( int o = y; o < getColumns()+1 && o <= y+1; o++){
                    createBlock(x, o);
                }
            }
        }
    }

    private void createBlock(int x, int o){
        cells[x][o].setColor(wallColor);
        cells[x-1][o].setColor(wallColor);
        cells[x-2][o].setColor(wallColor);
        cells[x][o].setWalkable(false);
        cells[x-1][o].setWalkable(false);
        cells[x-2][o].setWalkable(false);
        cells[x][o].setDestructible(false);
        cells[x-1][o].setDestructible(false);
        cells[x-2][o].setDestructible(false);
    }


    MapCell[][] getCells() {
        return cells;
    }

    int getRows() {
        return rows;
    }

    int getColumns() {
        return columns;
    }

    public void setCells(MapCell[][] cells) {
        this.cells = cells;
    }

}

class MapCell {
    private boolean walkable;
    private boolean destructible;
    TextColor color;

    MapCell() {
        walkable = true;
        destructible = true;
        color = new TextColor.RGB(55,55,10);
    }

    // region Getters/Setters
    void setWalkable(boolean walkable) {
        this.walkable = walkable;
    }

    void setColor(TextColor color) {
        this.color = color;
    }

    public boolean isDestructible() {
        return destructible;
    }

    public void setDestructible(boolean destructible) {
        this.destructible = destructible;
    }

    boolean isWalkable() {
        return walkable;
    }

    public TextColor getColor() {
        return color;
    }

    // endregion

}