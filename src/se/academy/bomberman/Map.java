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

                    cells[getColumns() - 1][j].setColor(wallColor);
                    cells[getColumns() - 1][j].setWalkable(false);

                } else if (j == 0 || j == getRows() - 1) {

                    cells[i][j].setColor(wallColor);
                    cells[i][j].setWalkable(false);

                } else if (i % 10 == 0 && j % 5 == 0) {
                    drawObstacles();
                }
            }
        }
    }

    private void drawObstacles() {
        for (int x = 4; x < getColumns(); x = x + 6) {
            for (int y = 3; y < getRows(); y = y+4) {
                for ( int o = y; o <= y+1 && o < getColumns()+1; o++){
                    createBlock(x, o);
                }
            }
        }
    }

    private void createBlock(int n, int o){
        cells[n][o].setColor(wallColor);
        cells[n+1][o].setColor(wallColor);
        cells[n+2][o].setColor(wallColor);
        cells[n+1][o].setWalkable(false);
        cells[n+2][o].setWalkable(false);
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
        this.walkable = true;
        destructible = false;
        this.color = new TextColor.RGB(55,55,10);
    }

    // region Getters/Setters
    void setWalkable(boolean walkable) {
        this.walkable = walkable;
    }

    void setColor(TextColor color) {
        this.color = color;
    }

    boolean isWalkable() {
        return walkable;
    }

    public TextColor getColor() {
        return color;
    }

    // endregion

}