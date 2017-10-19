package se.academy.bomberman;

public class Block implements Constants{

    Block(int type, int xPos, int yPos, Map map) {
        switch (type) {
            case GROUND:
                setGround(xPos, yPos, map);
                break;
            case SOLID:
                setSolid(xPos, yPos, map);
                break;
            case SPAWN:
                setSpawn(xPos,yPos, map);
                break;
            case BASE:
                setBase( xPos,yPos, map);
                break;
            case BRICK:
                setBrick(xPos,yPos, map);
                break;

        }
    }

    private void setBrick(int xPos, int yPos, Map map) {
        for (int y = yPos; y <= yPos + 1; y++) {
            for (int x = xPos; x >= xPos - 2; x--) {
                map.getCells()[x][y].setColor(map.getWallColor(true));
                map.getCells()[x][y].setWalkable(false);
                map.getCells()[x][y].setDestructible(true);
                map.getCells()[x][y].setSpawnable(false);
                map.getCells()[x][y].setPlaceable(false);
            }
        }
    }

    private void setBase(int xPos, int yPos, Map map) {
        for (int y = yPos; y <= yPos + 1; y++) {
            for (int x = xPos; x >= xPos - 2; x--) {
                map.getCells()[x][y].setColor(map.getGroundColor());
                map.getCells()[x][y].setWalkable(true);
                map.getCells()[x][y].setDestructible(true);
                map.getCells()[x][y].setSpawnable(true);
                map.getCells()[x][y].setPlaceable(false);
            }
        }
    }

    void setSolid(int xPos, int yPos, Map map) {
        for (int y = yPos; y <= yPos + 1; y++) {
            for (int x = xPos; x >= xPos - 2; x--) {
                map.getCells()[x][y].setColor(map.getWallColor(false));
                map.getCells()[x][y].setWalkable(false);
                map.getCells()[x][y].setDestructible(false);
                map.getCells()[x][y].setPlaceable(false);
            }
        }
    }

    void setGround(int xPos, int yPos, Map map) {
        for (int y = yPos; y <= yPos + 1; y++) {
            for (int x = xPos; x >= xPos - 2; x--) {
                map.getCells()[x][y].setColor(map.getGroundColor());
                map.getCells()[x][y].setWalkable(true);
                map.getCells()[x][y].setDestructible(false);
                map.getCells()[x][y].setPlaceable(false);
            }
        }
    }

    void setSpawn(int xPos, int yPos, Map map) {
        for (int y = yPos; y <= yPos + 1; y++) {
            for (int x = xPos; x >= xPos - 2; x--) {
                map.getCells()[x][y].setColor(map.getGroundColor());
                map.getCells()[x][y].setWalkable(true);
                map.getCells()[x][y].setDestructible(true);
                map.getCells()[x][y].setSpawnable(true);
                map.getCells()[x][y].setPlaceable(false);
            }
        }
    }

    static boolean isPlaceable(int xPos, int yPos, Map map){
        for (int y = yPos; y <= yPos + 1; y++) {
            for (int x = xPos; x >= xPos - 2; x--) {
                if(!map.getCells()[x][y].isPlaceable()) return false;
            }
        }
        return true;
    }

}


