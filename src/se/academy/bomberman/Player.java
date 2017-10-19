package se.academy.bomberman;

import com.googlecode.lanterna.TextCharacter;
import com.googlecode.lanterna.TextColor;
import com.googlecode.lanterna.screen.Screen;

import java.util.Random;
import java.util.List;

public class Player implements Constants {

    // region Variables

    private double powerLevelBomb = 1;
    private int powerLevelSpeed = 1;
    private int posX;
    private int posY;
    private int vSpeed;
    private int hSpeed;
    private Screen screen;
    private Bomb bomb;
    private TextColor bg;
    private MapCell[][] map;
    private boolean bombed;
    private boolean living;
    private Player enemy;
    private Sound bombPlant;
    private Sound bombExplode;
    private int lives;
    private Random rand = new Random();
    private List<PowerUp> powerUps;
    private boolean suicided = false;

    // Cosmetics
    private char [] playerModel;
    private char [] playerModelBomb;
    private TextColor playerBG;
    private TextColor bombBG;
    private TextColor playerColor;

// endregion

    Player(int x, int y, char[] playerModel, TextColor playerColor, TextColor playerBG, Screen screen,
           TextColor bombColor, TextColor bg, TextColor bombBG, MapCell[][] map, List<PowerUp> powerUps) {

        this.playerModel = playerModel;
        this.playerColor = playerColor;
        this.playerBG = playerBG;
        playerModelBomb = bombModel;
        this.bomb = new Bomb(bombColor, bombBG);
        this.bombBG = bombBG;

        this.bg = bg;
        this.posX = x;
        this.posY = y;
        this.vSpeed = 2;
        this.hSpeed = 3;
        this.screen = screen;
        this.map = map;
        bombed = false;
        living = true;
        init();
        bombPlant = new Sound("src/Sounds/smb_fireball.wav");
        bombPlant.start();
        bombExplode = new Sound("src/Sounds/smb_fireworks.wav");
        bombExplode.start();
        lives = 1;
        this.powerUps = powerUps;
    }

    private void init() {
        int x = 0;
        for (int i = posX; i < posX + 3; i++) {
            for (int j = posY; j < posY + 2; j++) {
                screen.setCharacter(i, j, new TextCharacter(playerModel[x++], playerColor, playerBG));
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
            int x = 0;
            for (int i = posX; i < posX + 3; i++) {
                for (int j = posY; j < posY + 2; j++) {
                    screen.setCharacter(i, j, new TextCharacter(bombModel[x++], playerColor, bombBG));
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
        for (int m = 0; m < powerLevelSpeed; m++) {
            switch (direction) {
                case NORTH:
                    if (map[getPosX()][getPosY() - 1].isWalkable() && map[getPosX() + 1][getPosY() - 1].isWalkable() &&
                            map[getPosX() + 2][getPosY() - 1].isWalkable() && !(getPosX() == enemy.getPosX() && getPosY() - 2 == enemy.getPosY())) {
                        setPosY(getPosY() - vSpeed);
                    }
                    break;
                case SOUTH:
                    if (map[getPosX()][getPosY() + 2].isWalkable() && map[getPosX() + 1][getPosY() + 2].isWalkable() &&
                            map[getPosX() + 2][getPosY() + 2].isWalkable() && !(getPosX() == enemy.getPosX() && getPosY() + 2 == enemy.getPosY())) {
                        setPosY(getPosY() + vSpeed);
                    }
                    break;
                case WEST:
                    if (map[getPosX() - 1][getPosY()].isWalkable() && map[getPosX() - 1][getPosY() + 1].isWalkable() &&
                            !(getPosX() - 3 == enemy.getPosX() && getPosY() == enemy.getPosY())) {
                        setPosX(getPosX() - hSpeed);
                    }
                    break;
                case EAST:
                    if (map[getPosX() + 3][getPosY()].isWalkable() && map[getPosX() + 3][getPosY() + 1].isWalkable() &&
                            !(getPosX() + 3 == enemy.getPosX() && getPosY() == enemy.getPosY())) {
                        setPosX(getPosX() + hSpeed);
                    }
                    break;
            }
            if (!powerUps.isEmpty()) {
                for (int n = powerUps.size() - 1; n >= 0; n--) {
                    PowerUp p = powerUps.get(n);
                    if (getPosX() == p.getPosX() && getPosY() == p.getPosY()) {
                        p.walkedOnMe(this);
                        powerUps.remove(n);
                    }
                }
            }
            int x = 0;
            for (int i = posX; i < posX + 3; i++) {
                for (int j = posY; j < posY + 2; j++) {
                    screen.setCharacter(i, j, new TextCharacter(playerModel[x++], playerColor, playerBG));
                }
            }
        }
    }

    void explode() {
        boolean hit = false;
        boolean enemyHit = false;
        boolean hitWall = false;
        bombExplode.play();


        for (int i = bomb.getPosX(); i < bomb.getPosY() + 2; i++) {
            for (int j = bomb.getPosY(); j < bomb.getPosY() + 2; j++) {
                map[i][j].setWalkable(true);
            }
        }
        // VÄNSTER

        for (int i = bomb.getPosX(); i >= bomb.getPosX() - 3 * powerLevelBomb; i--) {
            for (int j = bomb.getPosY(); j <= bomb.getPosY() + 1; j++) {
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
            for (int j = bomb.getPosY(); j <= bomb.getPosY() + 1; j++) {
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
            for (int i = bomb.getPosX(); i <= bomb.getPosX() + 2; i++) {
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
            for (int i = bomb.getPosX(); i <= bomb.getPosX() + 2; i++) {
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
                suicided = true;
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

    void deplode() { // Todo refakturera för enkelheten
        boolean hitWall = false;

        // VÄNSTER
        for (int i = bomb.getPosX(); i >= bomb.getPosX() - 3 * powerLevelBomb; i--) {
            for (int j = bomb.getPosY(); j <= bomb.getPosY() + 1; j++) {
                if (!map[i][j].isDestructible()) {
                    hitWall = true;
                    break;
                }
                screen.setCharacter(i, j, new TextCharacter(' ', TextColor.ANSI.DEFAULT, bg));
                giveDrop(i, j);
            }
            if (hitWall) break;
        }
        hitWall = false;
        // Höger
        for (int i = bomb.getPosX(); i <= bomb.getPosX() + 5 * powerLevelBomb; i++) {
            for (int j = bomb.getPosY(); j <= bomb.getPosY() + 1; j++) {
                if (!map[i][j].isDestructible()) {
                    hitWall = true;
                    break;
                }
                screen.setCharacter(i, j, new TextCharacter(' ', TextColor.ANSI.DEFAULT, bg));
                giveDrop(i, j);
            }
            if (hitWall) break;
        }
        hitWall = false;

        //Uppåt
        for (int j = bomb.getPosY(); j >= bomb.getPosY() - 2 * powerLevelBomb; j--) {
            for (int i = bomb.getPosX(); i <= bomb.getPosX() + 2; i++) {
                if (!map[i][j].isDestructible()) {
                    hitWall = true;
                    break;
                }
                screen.setCharacter(i, j, new TextCharacter(' ', TextColor.ANSI.DEFAULT, bg));
                giveDrop(i, j);
            }
            if (hitWall) break;
        }
        hitWall = false;

        // NERÅT
        for (int j = bomb.getPosY(); j <= bomb.getPosY() + 3 * powerLevelBomb; j++) {
            for (int i = bomb.getPosX(); i <= bomb.getPosX() + 2; i++) {
                if (!map[i][j].isDestructible()) {
                    hitWall = true;
                    break;
                }
                screen.setCharacter(i, j, new TextCharacter(' ', TextColor.ANSI.DEFAULT, bg));

                giveDrop(i, j);

            }
            if (hitWall) break;
        }
        bombed = false;

    }

    private void giveDrop(int i, int j){
        if(map[i][j].dropsBoost()){
            map[i][j].setDropsBoost(false);
            if(rand.nextInt(DROPCHANCE) == 1 ) new PowerUp(i, j, screen);

        }
    }

    // region Getters/Setters
    private int getPosX() {
        return posX;
    }

    private void setPosX(int posX) {
        this.posX = posX;
    }

    private int getPosY() {
        return posY;
    }

    private void setPosY(int posY) {
        this.posY = posY;
    }

    boolean isLiving() {
        return living;
    }

    public void setLiving(boolean alive) {
        this.living = alive;
    }

    boolean hasBombed() {
        return bombed;
    }

    long getFUSE() {
        return FUSE;
    }

    public void setBombed(boolean bombed) {
        this.bombed = bombed;
    }

    public Player getEnemy() {
        return enemy;
    }

    void setEnemy(Player enemy) {
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

    Bomb getBomb() {
        return bomb;
    }

    public void setBomb(Bomb bomb) {
        this.bomb = bomb;
    }

    int getLives() {
        return lives;
    }

    void setLives(int lives) {
        this.lives = lives;
    }

    double getPowerLevelBomb() {
        return powerLevelBomb;
    }

    void setPowerLevelBomb(double powerLevelBomb) {
        this.powerLevelBomb = powerLevelBomb;
    }

    int getPowerLevelSpeed() {
        return powerLevelSpeed;
    }

    void setPowerLevelSpeed(int powerLevelSpeed) {
        this.powerLevelSpeed = powerLevelSpeed;
    }

    public boolean isSuicided() {
        return suicided;
    }

    public void setSuicided(boolean suicided) {
        this.suicided = suicided;
    }
    //endregion
}
