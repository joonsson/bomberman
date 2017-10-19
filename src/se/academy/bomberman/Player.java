package se.academy.bomberman;

import com.googlecode.lanterna.TextCharacter;
import com.googlecode.lanterna.TextColor;
import com.googlecode.lanterna.input.KeyStroke;
import com.googlecode.lanterna.screen.Screen;

public class Player {

    protected int posX;
    protected int posY;
    protected TextCharacter playerModel;
    protected int vSpeed;
    protected int hSpeed;
    protected Screen screen;
    protected final int NORTH = 0;
    protected final int SOUTH = 1;
    protected final int WEST = 2;
    protected final int EAST = 3;
    protected Bomb bomb;
    protected TextCharacter playerModelBomb;
    protected TextColor bg;
    protected MapCell[][] map;
    protected final long DELTAT = 16;
    protected final long FUSE = 3000;
    protected boolean bombed;
    protected boolean living;
    protected Player enemy;
    protected Sound bombPlant;
    protected Sound bombExplode;
    protected TextColor playerBG;
    protected PowerUp powerUp = new PowerUp(20,20);
    protected double powerLevelBomb=2;
    protected int powerLevelSpeed=1;
    protected boolean powerSpeed;
    protected boolean powerBombs;




    Player(int x, int y, char playerModel, TextColor playerColor, TextColor playerBG, Screen screen,
           TextColor bombColor, TextColor bg, TextColor bombBG, MapCell[][] map) {

        this.bg = bg;
        this.playerBG = playerBG;
        this.posX = x;
        this.posY = y;
        this.playerModel = new TextCharacter(playerModel, playerColor, playerBG);
        this.vSpeed = 2;
        this.hSpeed = 3;
        this.screen = screen;
        this.bomb = new Bomb(bombColor, bombBG);
        playerModelBomb = new TextCharacter(playerModel, playerColor, bombBG);
        this.map = map;
        bombed = false;
        living = true;
        init();
        bombPlant = new Sound("src/Sounds/smb_fireball.wav");
        bombPlant.start();
        bombExplode = new Sound("src/Sounds/smb_fireworks.wav");
        bombExplode.start();
    }

    private void init() {
        for (int i = posX; i < posX + 3; i++) {
            for (int j = posY; j < posY + 2; j++) {
                screen.setCharacter(i, j, playerModel);
            }
        }
    }

    protected void dropBomb() {

        if (!bomb.isVisible()) {
            bombExplode.stopp();
            bomb.setPosX(posX);
            bomb.setPosY(posY);
            bomb.setVisible(true);
            for (int i = posX; i < posX + 3; i++) {
                for (int j = posY; j < posY + 2; j++) {
//                    screen.setCharacter(getPosX(), getPosY(), playerModelBomb);
                    map[getPosX()][getPosY()].setWalkable(false);
                }
            }
            bomb.setStart(System.currentTimeMillis());
            bombed = true;
            bombPlant.play();

        }
    }
    protected void move(int direction) {
        if (bomb.isVisible() && bomb.getPosX() == getPosX() && bomb.getPosY() == posY) {
            for (int i = posX; i < posX + 3; i++) {
                for (int j = posY; j < posY + 2; j++) {
                    screen.setCharacter(i, j, bomb.getModel());
                }
            }

        } else {
            for (int i = posX; i < posX + 3; i++) {
                for (int j = posY; j < posY + 2; j++) {
                    screen.setCharacter(i, j, new TextCharacter(' ', TextColor.ANSI.DEFAULT, bg));
                }
            }
        }
        if( powerUp.getPosX()== getPosX() &&  powerUp.getPosY()==getPosY()){
            powerSpeed = true;
            powerUp.drawBombs();
        }
//        if (powerUp.getPosX()==getPosX() &&  powerUp.getPosY()==getPosY()){
//            powerBombs = true;
//        }
        if (powerSpeed){
            powerLevelSpeed =powerUp.getSpeed();
        }
        switch (direction) {
            case NORTH:
                if (map[getPosX()][getPosY() - 1].isWalkable() && map[getPosX() + 1][getPosY() - 1].isWalkable() &&
                        map[getPosX() + 2][getPosY() - 1].isWalkable()) {
                    setPosY(getPosY() - vSpeed*powerLevelSpeed);
                }
                break;
            case SOUTH:
                if (map[getPosX()][getPosY() + 2].isWalkable() && map[getPosX() + 1][getPosY() + 2].isWalkable() &&
                        map[getPosX() + 2][getPosY() + 2].isWalkable()) {
                    setPosY(getPosY() + vSpeed*powerLevelSpeed);
                }
                break;
            case WEST:
                if (map[getPosX() - 1][getPosY()].isWalkable() && map[getPosX() - 1][getPosY() + 1].isWalkable()) {
                    setPosX(getPosX() - hSpeed*powerLevelSpeed);
                }
                break;
            case EAST:
                if (map[getPosX() + 3][getPosY()].isWalkable() && map[getPosX() + 3][getPosY() + 1].isWalkable()) {
                    setPosX(getPosX() + hSpeed*powerLevelSpeed);
                }
                break;
        }
        for (int i = posX; i < posX + 3; i++) {
            for (int j = posY; j < posY + 2; j++) {
                screen.setCharacter(i, j, playerModel);
            }
        }
    }

    protected void explode() {
        bombPlant.stopp();
        boolean hit = false;
        boolean enemyHit = false;
        boolean hitWall = false;
        bombExplode.play();


        if(powerBombs ){ // HOW WILL I IMPLEMENT THIS POWERUP?
            powerLevelBomb = powerUp.getBiggerBombs();
        }
        // VÄNSTER
        for (int i = bomb.getPosX(); i >= bomb.getPosX()-3*powerLevelBomb; i--) {
            for (int j = bomb.getPosY(); j <= bomb.getPosY() + 1*powerLevelBomb; j++) {
                if (!map[i][j].isDestructible()) {
                    hitWall = true;
                    break;
                }
                if (i == posX && j == posY) hit = true;
                if (i == enemy.getPosX() && j == enemy.getPosY()) enemyHit = true;
                screen.setCharacter(i, j, new TextCharacter('*', new TextColor.RGB(255, 0, 0), bg));
            }
            if(hitWall)break;
        }
        hitWall = false;
        // Höger
        for (int i = bomb.getPosX(); i <= bomb.getPosX() + 4*powerLevelBomb; i++) {
            for (int j = bomb.getPosY(); j <= bomb.getPosY() + 1*powerLevelBomb; j++) {
                if (!map[i][j].isDestructible()) {
                    hitWall = true;
                    break;
                }
                if (i == enemy.getPosX() && j == enemy.getPosY()) enemyHit = true;
                screen.setCharacter(i, j, new TextCharacter('*', new TextColor.RGB(255, 0, 0), bg));
            }
            if(hitWall)break;
        }
        hitWall = false;

        //Uppåt
        for (int j = bomb.getPosY(); j >= bomb.getPosY() - 2*powerLevelBomb; j--) {
            for (int i = bomb.getPosX(); i <= bomb.getPosX() + 1*powerLevelBomb; i++) {
                if (!map[i][j].isDestructible()) {
                    hitWall = true;
                    break;
                }
                if (i == posX && j == posY) hit = true;
                if (j == enemy.getPosY() && i == enemy.getPosX()) enemyHit = true;
                screen.setCharacter(i, j, new TextCharacter('*', new TextColor.RGB(255, 0, 0), bg));
            }
            if(hitWall) break;
        }
        hitWall = false;

        // NERÅT
        for (int j = bomb.getPosY(); j <= bomb.getPosY() + 3*powerLevelBomb; j++) {
            for (int i = bomb.getPosX(); i <= bomb.getPosX() + 1*powerLevelBomb; i++) {
                if (!map[i][j].isDestructible()) {
                    hitWall = true;
                    break;
                }
                if (i == posX && j == posY) hit = true;
                if (j == enemy.getPosY() && i == enemy.getPosX()) enemyHit = true;
                screen.setCharacter(i, j, new TextCharacter('*', new TextColor.RGB(255, 0, 0), bg));
            }
            if(hitWall) break;
        }

        if (hit) {
            living = false;
            Bomberman.inGame = false;
        } else if (enemyHit) {
            enemy.living = false;
            Bomberman.inGame = false;
        }
        bomb.setVisible(false);
        bomb.setStart(System.currentTimeMillis());
    }

    protected void deplode() {
        for (int i = bomb.getPosX() - 5; i < bomb.getPosX() + 8; i++) {
            for (int j = bomb.getPosY(); j < bomb.getPosY() + 2; j++) {
                screen.setCharacter(i, j, new TextCharacter(' ', TextColor.ANSI.DEFAULT, bg));
            }
        }
        for (int j = bomb.getPosY() - 6; j < bomb.getPosY() + 6; j++) {
            for (int i = bomb.getPosX(); i < bomb.getPosX() + 3; i++) {
                screen.setCharacter(i, j, new TextCharacter(' ', TextColor.ANSI.DEFAULT, bg));
            }
        }
        bombed = false;
    }

    // region Getters/Setters
    public int getPosX() {
        return posX;
    }

    public void setPosX(int posX) {
        this.posX = posX;
    }


    public int getPosY() {
        return posY;
    }

    public void setPosY(int posY) {
        this.posY = posY;
    }

    public boolean isLiving() {
        return living;
    }

    public void setLiving(boolean alive) {
        this.living = alive;
    }

    public boolean isBombed() {
        return bombed;
    }

    public void setBombed(boolean bombed) {
        this.bombed = bombed;
    }

    public Player getEnemy() {
        return enemy;
    }

    public void setEnemy(Player enemy) {
        this.enemy = enemy;
    }
    //endregion
}
