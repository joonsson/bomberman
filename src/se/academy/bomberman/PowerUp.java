package se.academy.bomberman;

import com.googlecode.lanterna.TextCharacter;
import com.googlecode.lanterna.TextColor;
import com.googlecode.lanterna.screen.Screen;

import java.util.Random;

public class PowerUp {

    private int speed = 1;
    private int biggerBombs = 2;
    private int posX = 10;
    private int posY = 10;
    private Screen screen;
    private int death;
    private int life;
    private final int DEAD = 0, LIVE = 1, SPEED = 2, BOMB = 3;
    private int konst;
    private TextCharacter bombs = new TextCharacter('B');
    private TextCharacter speedigonzales = new TextCharacter('S');
    private Player player;
    private boolean used;


    public PowerUp(int x, int y, Screen screen) {
        Random rand = new Random();
        this.posX = x;
        this.posY = y;
        this.konst = rand.nextInt(4);
        this.screen = screen;
        used = false;
        drawPowerUp(screen);
    }


    public void walkedOnMe(Player player){
        used = true;
        switch (konst) {
            case DEAD:
                // death
                player.setLives(player.getLives()-1);
                break;

            case LIVE:
                player.setLives(player.getLives()+1);
                break;

            case SPEED:
                player.setPowerLevelSpeed(player.getPowerLevelSpeed()+1);
                break;

            case BOMB:

                player.setPowerLevelBomb(player.getPowerLevelBomb()+1);
                break;
            default:
                break;

        }
    }

    public void drawPowerUp(Screen screen) {


        if (konst>=0 && konst<4) {
            switch (konst) {
                case 0:
                    TextCharacter deaths = new TextCharacter('D', new TextColor.RGB(0, 0, 0), TextColor.ANSI.DEFAULT);
                    screen.setCharacter(posX, posY, deaths);
                    break;

                case 1:
                    TextCharacter life = new TextCharacter('L', new TextColor.RGB(0, 0, 0), TextColor.ANSI.DEFAULT);
                    screen.setCharacter(posX, posY, life);

                    break;

                case 2:
                    TextCharacter speeds = new TextCharacter('S', new TextColor.RGB(0, 0, 0), TextColor.ANSI.DEFAULT);
                    screen.setCharacter(posX, posY, speeds);
                    break;

                case 3:
                    TextCharacter bomber = new TextCharacter('B', new TextColor.RGB(0, 0, 0), TextColor.ANSI.DEFAULT);
                    screen.setCharacter(posX, posY, bomber);
                    break;
                default:
                    break;

            }
        }
    }
    // region Getters/setters

    public int getPosY() {
        return posY;
    }

    public void setPosY(int posY) {
        this.posY = posY;
    }

    public int getPosX() {

        return posX;
    }

    public void setPosX(int posX) {
        this.posX = posX;

    }

    public int getKonst() {
        return konst;
    }

    public void setKonst(int konst) {
        this.konst = konst;
    }

    public boolean isUsed() {
        return used;
    }

    public void setUsed(boolean used) {
        this.used = used;
    }
    //endregion
}
