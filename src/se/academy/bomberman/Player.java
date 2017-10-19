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
    private TextCharacter playerModel;
    private int vSpeed;
    private int hSpeed;
    private Screen screen;
    private Bomb bomb;
    private TextCharacter playerModelBomb;
    private TextColor bg;
    private MapCell[][] map;
    private boolean bombed;
    private boolean living;
    private Player enemy;
    private Sound bombPlant;
    private Sound bombExplode;
    private TextColor playerBG;

    private int lives;
    private Random rand = new Random();
    private List<PowerUp> powerUps;
    private boolean suicided = false;
    private List<Player> players;

// endregion

    Player(int x, int y, char playerModel, TextColor playerColor, TextColor playerBG, Screen screen,
           TextColor bombColor, TextColor bg, TextColor bombBG, MapCell[][] map, List<PowerUp> powerUps) {

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
        this.powerUps = powerUps;

    }

    private void init() {
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
            bombPlant.stopp();
            bombPlant.play();

        }
    }

    void move(int direction) {
        for (int m = 0; m < powerLevelSpeed; m++) {
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
            boolean playerCollision = false;
            switch (direction) {
                case NORTH:
                    for (Player p: players) {
                        if (getPosX() == p.getPosX() && getPosY() - 2 == p.getPosY()) {
                            playerCollision = true;
                        }
                    }
                    if (map[getPosX()][getPosY() - 1].isWalkable() && map[getPosX() + 1][getPosY() - 1].isWalkable() &&
                            map[getPosX() + 2][getPosY() - 1].isWalkable() && !playerCollision) {
                        setPosY(getPosY() - vSpeed);
                    }
                    break;
                case SOUTH:
                    playerCollision = false;
                    for (Player p: players) {
                        if (getPosX() == p.getPosX() && getPosY() + 2 == p.getPosY()) {
                            playerCollision = true;
                        }
                    }
                    if (map[getPosX()][getPosY() + 2].isWalkable() && map[getPosX() + 1][getPosY() + 2].isWalkable() &&
                            map[getPosX() + 2][getPosY() + 2].isWalkable() && !playerCollision) {
                        setPosY(getPosY() + vSpeed);
                    }
                    break;
                case WEST:
                    playerCollision = false;
                    for (Player p: players) {
                        if (getPosX() - 3 == p.getPosX() && getPosY() == p.getPosY()) {
                            playerCollision = true;
                        }
                    }
                    if (map[getPosX() - 1][getPosY()].isWalkable() && map[getPosX() - 1][getPosY() + 1].isWalkable() &&
                            !playerCollision) {
                        setPosX(getPosX() - hSpeed);
                    }
                    break;
                case EAST:
                    playerCollision = false;
                    for (Player p: players) {
                        if (getPosX() + 3 == p.getPosX() && getPosY() == p.getPosY()) {
                            playerCollision = true;
                        }
                    }
                    if (map[getPosX() + 3][getPosY()].isWalkable() && map[getPosX() + 3][getPosY() + 1].isWalkable() &&
                            !playerCollision) {
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
            for (int i = posX; i < posX + 3; i++) {
                for (int j = posY; j < posY + 2; j++) {
                    screen.setCharacter(i, j, playerModel);
                }
            }
        }
    }

    void explode() {
        boolean hit = false;
        boolean enemyHit = false;
        boolean hitWall = false;
        bombExplode.stopp();
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
        for (int i = bomb.getPosX(); i <= bomb.getPosX() + 2 + (3 * powerLevelBomb); i++) {

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
        for (int j = bomb.getPosY(); j <= bomb.getPosY() + 1 + (2 * powerLevelBomb); j++) {
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
        for (int i = bomb.getPosX(); i <= bomb.getPosX() + 2 + (3 * powerLevelBomb); i++) {
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
        for (int j = bomb.getPosY(); j <= bomb.getPosY() + 1 + (2 * powerLevelBomb); j++) {
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
            if(rand.nextInt(DROPCHANCE) == 1 ) powerUps.add(new PowerUp(i, j, screen));

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

    public List<PowerUp> getPowerUps() {
        return powerUps;
    }

    public void setPowerUps(List<PowerUp> powerUps) {
        this.powerUps = powerUps;
    }

    public List<Player> getPlayers() {
        return players;
    }

    public void setPlayers(List<Player> players) {
        this.players = players;
    }
    //endregion
}
