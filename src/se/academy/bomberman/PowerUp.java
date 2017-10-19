package se.academy.bomberman;

import com.googlecode.lanterna.TextCharacter;
import com.googlecode.lanterna.TextColor;
import com.googlecode.lanterna.screen.Screen;

public class PowerUp {

    private int speed = 1;
    private int biggerBombs = 2;
    private int posX = 10;
    private int posY = 10;
    private Screen screen;
    private int death;
    private int life;
    private int dead = 0, alive = 1, speedish = 2, bomber = 3;
    private int konst;
    private TextCharacter bombs = new TextCharacter('B');
    private TextCharacter speedigonzales = new TextCharacter('S');
    private Player player;


    public PowerUp(int x, int y, int type, Screen screen) {
        this.posX = x;
        this.posY = y;
        this.konst = type;


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

    //endregion

    public void walkedOnMe(Player player){
        switch (konst) {
            case 0:
                // death
                player.setLives(player.getLives()-1);
                break;

            case 1:
                player.setLives(player.getLives()+1);
                break;

            case 2:
                player.setPowerLevelSpeed(player.getPowerLevelSpeed()+1);
                break;

            case 3:

                player.setPowerLevelBomb(player.getPowerLevelBomb()+1);
                break;
            default:
                break;

        }
    }

    public void drawPowerUp(Screen screen, int x, int y, int randomSpawn) {

        konst = randomSpawn;
        if (konst>=0 && konst<4) {
            switch (konst) {
                case 0:
                    this.posY = y;
                    this.posX = x;
                    TextCharacter deaths = new TextCharacter('D', new TextColor.RGB(0, 0, 0), TextColor.ANSI.DEFAULT);
                    break;

                case 1:
                    this.posY = y;
                    this.posX = x;
                    TextCharacter alive = new TextCharacter('L', new TextColor.RGB(0, 0, 0), TextColor.ANSI.DEFAULT);

                    break;

                case 2:
                    this.posY = y;
                    this.posX = x;
                    TextCharacter speeds = new TextCharacter('S', new TextColor.RGB(0, 0, 0), TextColor.ANSI.DEFAULT);
                    break;

                case 3:
                    this.posY = y;
                    this.posX = x;
                    TextCharacter bomber = new TextCharacter('B', new TextColor.RGB(0, 0, 0), TextColor.ANSI.DEFAULT);
                    break;
                default:
                    break;

            }
        }
    }
}
