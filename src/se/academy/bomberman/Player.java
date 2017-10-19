package se.academy.bomberman;

import com.googlecode.lanterna.TextCharacter;
import com.googlecode.lanterna.TextColor;
import com.googlecode.lanterna.input.KeyStroke;
import com.googlecode.lanterna.screen.Screen;

public class Player {


    private int posX;
    private int posY;
    private TextCharacter playerModel;
    private int vSpeed;
    private int hSpeed;
    private Screen screen;
    private final int NORTH = 0;
    private final int SOUTH = 1;
    private final int WEST = 2;
    private final int EAST = 3;
    private Bomb bomb;
    private TextCharacter playerModelBomb;
    private TextColor bg;
    private MapCell[][] map;
    private final long FUSE = 3000;
    private boolean bombed;
    private boolean living;
    private Player enemy;
    private Sound bombPlant;
    private Sound bombExplode;
    private TextColor playerBG;
    private double powerLevelBomb = 1;
    private int powerLevelSpeed = 1;
    private int lives;


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
        lives = 1;
    }

    void init() {
        for (int i = posX; i < posX + 3; i++) {
            for (int j = posY; j < posY + 2; j++) {
                screen.setCharacter(i, j, playerModel);
            }
        }
    }

    void dropBomb() {

        if (!bombed) {
            bomb.setPosX(posX);
            bomb.setPosY(posY);
            bomb.setVisible(true);
            for (int i = bomb.getPosX(); i < bomb.getPosX() + 3; i++) {
                for (int j = bomb.getPosY(); j < bomb.getPosY() + 2; j++) {
//                    screen.setCharacter(getPosX(), getPosY(), playerModelBomb);
                    map[i][j].setWalkable(false);
                }
            }
            bomb.setStart(System.currentTimeMillis());
            bombed = true;
            bombPlant.play();

        }
    }

    void move(int direction) {
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
      /*  if( powerUp.getPosX()== getPosX() &&  powerUp.getPosY()==getPosY()){
            powerSpeed = true;
        }
        if (powerUp.getPosX()==getPosX() &&  powerUp.getPosY()==getPosY()){
            powerBombs = true;
        }
        if (powerSpeed){
            powerLevelSpeed =powerUp.getSpeed();
        }*/
        switch (direction) {
            case NORTH:
                if (map[getPosX()][getPosY() - 1].isWalkable() && map[getPosX() + 1][getPosY() - 1].isWalkable() &&
                        map[getPosX() + 2][getPosY() - 1].isWalkable()) {
                    setPosY(getPosY() - vSpeed * powerLevelSpeed);
                }
                break;
            case SOUTH:
                if (map[getPosX()][getPosY() + 2].isWalkable() && map[getPosX() + 1][getPosY() + 2].isWalkable() &&
                        map[getPosX() + 2][getPosY() + 2].isWalkable()) {
                    setPosY(getPosY() + vSpeed * powerLevelSpeed);
                }
                break;
            case WEST:
                if (map[getPosX() - 1][getPosY()].isWalkable() && map[getPosX() - 1][getPosY() + 1].isWalkable()) {
                    setPosX(getPosX() - hSpeed * powerLevelSpeed);
                }
                break;
            case EAST:
                if (map[getPosX() + 3][getPosY()].isWalkable() && map[getPosX() + 3][getPosY() + 1].isWalkable()) {
                    setPosX(getPosX() + hSpeed * powerLevelSpeed);
                }
                break;
        }
        for (int i = posX; i < posX + 3; i++) {
            for (int j = posY; j < posY + 2; j++) {
                screen.setCharacter(i, j, playerModel);
            }
        }
    }

    void explode() {
        boolean hit = false;
        boolean enemyHit = false;
        boolean hitWall = false;
        bombExplode.play();


        for (int i = bomb.getPosX(); i < bomb.getPosY() + 3; i++) {
            for (int j = bomb.getPosY(); j < bomb.getPosY() + 2; j++) {
                map[i][j].setWalkable(true);
            }

        }
        // VÄNSTER

        for (int i = bomb.getPosX(); i >= bomb.getPosX() - 3 * powerLevelBomb; i--) {
            for (int j = bomb.getPosY(); j <= bomb.getPosY() + 1 * powerLevelBomb; j++) {
                if (!map[i][j].isDestructible()) {
                    hitWall = true;
                    break;
                }
                map[i][j].setWalkable(true);
                if (i == posX && j == posY) hit = true;
                if (i == enemy.getPosX() && j == enemy.getPosY()) enemyHit = true;
                screen.setCharacter(i, j, new TextCharacter('*', new TextColor.RGB(255, 0, 0), bg));

            }
            if (hitWall) break;
        }
        hitWall = false;
        // Höger
        for (int i = bomb.getPosX(); i <= bomb.getPosX() + 5 * powerLevelBomb; i++) {
            for (int j = bomb.getPosY(); j <= bomb.getPosY() + 1 * powerLevelBomb; j++) {
                if (!map[i][j].isDestructible()) {
                    hitWall = true;
                    break;
                }
                map[i][j].setWalkable(true);
                if (i == posX && j == posY) hit = true;
                if (i == enemy.getPosX() && j == enemy.getPosY()) enemyHit = true;
                screen.setCharacter(i, j, new TextCharacter('*', new TextColor.RGB(255, 0, 0), bg));
            }
            if (hitWall) break;
        }
        hitWall = false;

        //Uppåt
        for (int j = bomb.getPosY(); j >= bomb.getPosY() - 2 * powerLevelBomb; j--) {
            for (int i = bomb.getPosX(); i <= bomb.getPosX() + 1 * powerLevelBomb; i++) {
                if (!map[i][j].isDestructible()) {
                    hitWall = true;
                    break;
                }
                map[i][j].setWalkable(true);
                if (i == posX && j == posY) hit = true;
                if (j == enemy.getPosY() && i == enemy.getPosX()) enemyHit = true;
                screen.setCharacter(i, j, new TextCharacter('*', new TextColor.RGB(255, 0, 0), bg));
            }
            if (hitWall) break;
        }
        hitWall = false;

        // NERÅT
        for (int j = bomb.getPosY(); j <= bomb.getPosY() + 3 * powerLevelBomb; j++) {
            for (int i = bomb.getPosX(); i <= bomb.getPosX() + 1 * powerLevelBomb; i++) {
                if (!map[i][j].isDestructible()) {
                    hitWall = true;
                    break;
                }
                map[i][j].setWalkable(true);
                if (i == posX && j == posY) hit = true;
                if (j == enemy.getPosY() && i == enemy.getPosX()) enemyHit = true;
                screen.setCharacter(i, j, new TextCharacter('*', new TextColor.RGB(255, 0, 0), bg));
            }
            if (hitWall) break;
        }

        if (hit) {
            lives--;
            if (lives == 0) {
                living = false;
                Bomberman.inGame = false;
            }
        } else if (enemyHit) {
            enemy.lives--;
            if (enemy.lives == 0) {
                enemy.living = false;
                Bomberman.inGame = false;
            }
        }
        bomb.setVisible(false);
        bomb.setStart(System.currentTimeMillis());

    }

    void deplode() {
        boolean hitWall = false;
        // VÄNSTER
        for (int i = bomb.getPosX(); i > bomb.getPosX() - 7; i--) {
            for (int j = bomb.getPosY(); j < bomb.getPosY() + 2; j++) {
                if (!map[i][j].isDestructible()) {
                    hitWall = true;
                    break;
                }
                screen.setCharacter(i, j, new TextCharacter(' ', TextColor.ANSI.DEFAULT, bg));
            }
            if (hitWall) break;
        }
        hitWall = false;
        // Höger
        for (int i = bomb.getPosX(); i < bomb.getPosX() + 9; i++) {
            for (int j = bomb.getPosY(); j < bomb.getPosY() + 2; j++) {
                if (!map[i][j].isDestructible()) {
                    hitWall = true;
                    break;
                }
                screen.setCharacter(i, j, new TextCharacter(' ', TextColor.ANSI.DEFAULT, bg));
            }
            if (hitWall) break;
        }
        hitWall = false;

        //Uppåt
        for (int j = bomb.getPosY(); j > bomb.getPosY() - 5; j--) {
            for (int i = bomb.getPosX(); i < bomb.getPosX() + 3; i++) {
                if (!map[i][j].isDestructible()) {
                    hitWall = true;
                    break;
                }
                screen.setCharacter(i, j, new TextCharacter(' ', TextColor.ANSI.DEFAULT, bg));
            }
            if (hitWall) break;
        }
        hitWall = false;

        // NERÅT
        for (int j = bomb.getPosY(); j < bomb.getPosY() + 6; j++) {
            for (int i = bomb.getPosX(); i < bomb.getPosX() + 3; i++) {
                if (!map[i][j].isDestructible()) {
                    hitWall = true;
                    break;
                }
                screen.setCharacter(i, j, new TextCharacter(' ', TextColor.ANSI.DEFAULT, bg));
            }
            if (hitWall) break;
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

    public boolean hasBombed() {
        return bombed;
    }

    public long getFUSE() {
        return FUSE;
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

    public int getvSpeed() {
        return vSpeed;
    }

    public void setvSpeed(int vSpeed) {
        this.vSpeed = vSpeed;
    }

    public int gethSpeed() {
        return hSpeed;
    }

    public void sethSpeed(int hSpeed) {
        this.hSpeed = hSpeed;
    }

    public Bomb getBomb() {
        return bomb;
    }

    public void setBomb(Bomb bomb) {
        this.bomb = bomb;
    }

    public int getLives() {
        return lives;
    }

    public void setLives(int lives) {
        this.lives = lives;
    }

    public double getPowerLevelBomb() {
        return powerLevelBomb;
    }

    public void setPowerLevelBomb(double powerLevelBomb) {
        this.powerLevelBomb = powerLevelBomb;
    }

    public int getPowerLevelSpeed() {
        return powerLevelSpeed;
    }

    public void setPowerLevelSpeed(int powerLevelSpeed) {
        this.powerLevelSpeed = powerLevelSpeed;
    }
    //endregion
}
