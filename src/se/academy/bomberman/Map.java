package se.academy.bomberman;

import com.googlecode.lanterna.TextColor;

import java.util.Random;

public class Map implements Constants {

    private MapCell[][] cells;
    private int rows, columns;
    private int mode;

    public TextColor getGroundColor() {
        return groundColor;
    }

    Map(int columns, int rows) {
        this.rows = rows;
        this.columns = columns;
        setMode(RANDOM); // Todo menyval för gamemode
        init();
    }

    private void init() {

        cells = new MapCell[columns][rows];
        for (int i = 0; i < columns; i++) {
            for (int j = 0; j < rows; j++) {
                cells[i][j] = new MapCell(); //
            }
        }
        addSpawn();
        addObstacles();
        drawMap();
    }

    private void addSpawn() {
        new Block(SPAWN, blockSizeX, 1, this);
        new Block(SPAWN, blockSizeX*2, 1, this);
        new Block(SPAWN, blockSizeX, blockSizeY*2, this);
        new Block(SPAWN, columns - 1, 1, this);
        new Block(SPAWN, columns - 1, blockSizeY*2, this);
        new Block(SPAWN, columns - blockSizeX - 1, 1, this);
        new Block(SPAWN, blockSizeX, (rows - 1) - blockSizeY*2, this);
        new Block(SPAWN, blockSizeX*2, (rows - 1) - blockSizeY, this);
        new Block(SPAWN, blockSizeX, (rows - 1) - blockSizeY, this);
        new Block(SPAWN, columns - 1, (rows - 1) - blockSizeY*2, this);
        new Block(SPAWN, columns - blockSizeX - 1, (rows - 1) - blockSizeY, this);
        new Block(SPAWN, columns - 1, (rows - 1) - blockSizeY, this);
    }

    private void drawMap() {
        for (int x = 0; x < columns; x++) {
            for (int y = 0; y < rows; y++) {
                if (x == 0 || x == columns - 1) {
                    // Set surrounding walls
                    cells[0][y].setWalkable(false);
                    cells[0][y].setDestructible(false);
                    cells[0][y].setPlaceable(false);

                    cells[0][y].setColor(getWallColor(cells[0][y].isDestructible()));

                    cells[0][y].setPlaceable(false);
                    cells[columns - 1][y].setWalkable(false);
                    cells[columns - 1][y].setDestructible(false);
                    cells[columns - 1][y]
                            .setColor(getWallColor(cells[columns - 1][y].isDestructible()));
                } else if (y == 0 || y == rows - 1) {

                    cells[0][y].setPlaceable(false);
                    cells[x][y].setWalkable(false);
                    cells[x][y].setDestructible(false);
                    cells[x][y].setColor(getWallColor(cells[x][y].isDestructible()));
                }
            }
        }
    }

    private void addObstacles() {
        for (int x = blockSizeX * 2; x < columns; x = x + blockSizeX * 2) {
            for (int y = blockSizeX; y < rows - 1; y = y + blockSizeY * 2) {
                if (cells[x][y].isPlaceable()) {
                    new Block(SOLID, x, y, this);
                }
            }
        }
        if (getMode() == BLOCKS) {
            for (int x = blockSizeX; x < columns; x = x + blockSizeX * 2) {
                for (int y = 1; y < rows - 1; y = y + blockSizeY * 2) {
                    if (Block.isPlaceable(x, y, this)) {
                        new Block(BRICK, x, y, this);
                    }
                }
            }
        } else if (getMode() == RANDOM) {
            for (int x = blockSizeX; x < columns; x = x + blockSizeX) {
                for (int y = 1; y < rows - 1; y = y + blockSizeY) {
                    if (Block.isPlaceable(x, y, this)) {
                        if(new Random().nextInt(3)== 1 && !cells[x][y].isSpawnable()){
                            new Block(BRICK, x, y, this);

                        }
                    }
                }
            }
            // TODO add code to place destructiblocks randomly
        }
    }

    private void createBlockAt(int startX, int startY, boolean destructible) {

        for (int y = startY; y <= startY + 1; y++) {
            for (int x = startX; x >= startX - 2; x--) {
                cells[x][y].setColor(getWallColor(destructible));
                cells[x][y].setWalkable(false);
                cells[x][y].setDestructible(destructible);
            }
        }
    }

    TextColor getWallColor(boolean destructible) { // TODO sätt denna någon annan stans
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
    private boolean destructible = true;
    private boolean spawnable;
    private boolean placeable = true;
    private boolean dropsBoost = false;
    TextColor color;

    public boolean isPlaceable() {
        return placeable;
    }

    public void setPlaceable(boolean placeable) {
        this.placeable = placeable;
    }

    MapCell() {
        walkable = true;
        destructible = true;
        color = new TextColor.RGB(55, 55, 10);
    }

    // region Getters/Setters MAPCELLS
    void setWalkable(boolean walkable) {
        if(destructible) this.walkable = walkable;
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

    public boolean isSpawnable() {
        return spawnable;
    }

    public void setSpawnable(boolean spawnable) {
        this.spawnable = spawnable;
    }

    public boolean dropsBoost() {
        return dropsBoost;
    }

    public void setDropsBoost(boolean dropsBoost) {
        this.dropsBoost = dropsBoost;
    }

    // endregion
}


/*

<<<<<<< HEAD
¤ ¤
(I)

=======
¤|¤
(_)
 /*
TNT
   /*
  HHH
>>>>>>> 51e8f109fb0c9e76a2758c33907afc477f39269a

*u*



 */