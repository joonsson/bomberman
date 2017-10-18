package se.academy.bomberman;

import com.googlecode.lanterna.TextColor;

public class Map {

    private MapCell[][] cells;
    private int rows, columns;
    static int NORMAL = 0, BLOCKS = 1, RANDOM = 2;
    private int mode;
    private static int blockSizeX = 3, blockSizeY = 2;

    Map(int columns, int rows) {
        this.rows = rows;
        this.columns = columns;
        setMode(BLOCKS);
        init();
    }

    private void init() {
        cells = new MapCell[columns][rows];
        for (int i = 0; i < columns; i++) {
            for (int j = 0; j < rows; j++) {
                cells[i][j] = new MapCell();
            }
        }
        drawMap();
    }

    private void drawMap() {
        int columns = getColumns(), rows = getRows();
        for (int i = 0; i < columns; i++) {

            for (int j = 0; j < rows; j++) {

                if (i == 0 || i == columns - 1) {

                    cells[0][j].setDestructible(false);
                    cells[0][j].setWalkable(false);
                    cells[0][j].setColor(getWallColor(cells[0][j].isDestructible())); // TODO sätt en konstant färgvariabel

                    cells[columns - 1][j].setDestructible(false);
                    cells[columns - 1][j].setWalkable(false);
                    cells[columns - 1][j]
                            .setColor(getWallColor(cells[columns - 1][j].isDestructible()));

                } else if (j == 0 || j == rows - 1) {

                    cells[i][j].setDestructible(false);
                    cells[i][j].setWalkable(false);
                    cells[i][j].setColor(getWallColor(cells[i][j].isDestructible()));

                } else if (i % 10 == 0 && j % 5 == 0) {
                    drawObstacles();
                }
            }
        }
    }

    private void drawObstacles() {
        for (int x = blockSizeX * 2; x < getColumns(); x = x + blockSizeX * 2) {
            for (int y = blockSizeX; y < getRows() - 1; y = y + blockSizeY * 2) {
                createBlockAt(x, y, false);
            }
        }
        if (getMode() == BLOCKS) {
            createBlockAt(6, 1, true);
            // TODO add code to place destructiblocks in grid
        }else if (getMode()== RANDOM){
            // TODO add code to place destructiblocks randomly
        }
    }
//            for (int x = blockSizeX; x < getColumns(); x = x + blockSizeX) {
//                for (int y = blockSizeX; y < getRows() - 1; y = y + blockSizeY) {
//                    for (int o = y; o < getColumns() + 1 && o <= y + 1; o++) {
////                        if(x - 2*blockSizeX+1 < 1 && y - blockSizeY+1 < 1||
////                            x + 2*blockSizeX+1 > getColumns() && y + blockSizeY-1 > 1){
//                        System.out.println("creat destructible blocks");
//                        createBlock(x, o, true);
////                        }
//                    }
//                }
//            }
//        }

    private void createBlockAt(int startX, int startY, boolean destructible) {

        for (int y = startY; y <= startY + 1; y++) {
            for (int x = startX; x >= startX - 2; x--) {
                cells[x][y].setColor(getWallColor(destructible));
                cells[x][y].setWalkable(false);
                cells[x][y].setDestructible(destructible);
            }
        }
    }

    private TextColor getWallColor(boolean destructible) {
        if (destructible) {
            return new TextColor.RGB(4, 100, 0);
        } else {
            return new TextColor.RGB(4, 54, 0);
        }
    }

    // region Getters/Setters MAP

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

    private int getMode() {
        return mode;
    }

    public void setMode(int mode) {
        this.mode = mode;
    }

    // endregion
}

class MapCell {
    private boolean walkable;
    private boolean destructible;
    TextColor color;

    MapCell() {
        walkable = true;
        destructible = true;
        color = new TextColor.RGB(55, 55, 10);
    }

    // region Getters/Setters MAPCELLS
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


/*
        OM cellen är 2blocks+1 från hörn, samt om cellen INTE är destructible,
        rita ett destructibleblock

        if(x - 2*blockSizeX+1 < 1 && y - blockSizeY+1 < 1||
           x + 2*blockSizeX+1 > getColumns() && y + blockSizeY-1 > 1



¤|¤
(_)

   /*
  HHH
 */