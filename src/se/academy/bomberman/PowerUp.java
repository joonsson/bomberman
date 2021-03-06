package se.academy.bomberman;

import com.googlecode.lanterna.TextCharacter;
import com.googlecode.lanterna.TextColor;
import com.googlecode.lanterna.screen.Screen;

import java.util.Random;

public class PowerUp implements Constants {

    private int posX = 10;
    private int posY = 10;
    private Screen screen;
    private final int DEAD = 0, LIVE = 1, SPEED = 2, BOMB = 3;
    private int konst;
    private Player player;
    private boolean used;

    public PowerUp(int x, int y, Screen screen) {
        Random rand = new Random();
        this.posX = x;
        this.posY = y;
        int chancePower = rand.nextInt(100);
        if(chancePower<2){
            this.konst = DEAD;
        }else if(chancePower <20){
            this.konst=LIVE;
        }else if(chancePower<60){
            this.konst=SPEED;
        }else
            this.konst=BOMB;
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
                Sound oneUp = new Sound("src/Sounds/smb_1-up.wav");
                oneUp.start();
                player.setLives(player.getLives()+1);
                oneUp.mediaPlayer.play();
                break;

            case SPEED:
                Sound speed = new Sound("src/Sounds/gotta go fast.wav");
                speed.start();
                if (player.getMoveDelay() < 0) {
                    player.setMoveDelay(player.getMoveDelay() - 100);
                }
                speed.mediaPlayer.setVolume(1);
                speed.mediaPlayer.play();
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
//                    TextCharacter deaths = new TextCharacter('D', new TextColor.RGB(0, 250, 0), TextColor.ANSI.DEFAULT);
//                    screen.setCharacter(posX, posY, deaths);
                    int n = 0;
                    for (int i = posX; i < posX + 3; i++) {
                        for (int j = posY; j < posY + 2; j++) {
                            screen.setCharacter(i, j, new TextCharacter(death[n++], new TextColor.RGB(0,250,0), TextColor.ANSI.DEFAULT));
                        }
                    }
                    break;

                case 1:
//                    TextCharacter life = new TextCharacter('L', new TextColor.RGB(250, 250, 250), TextColor.ANSI.DEFAULT);
//                    screen.setCharacter(posX, posY, life);
                   n = 0;
                    for (int i = posX; i < posX + 3; i++) {
                        for (int j = posY; j < posY + 2; j++) {
                            screen.setCharacter(i, j, new TextCharacter(LIFE[n++], new TextColor.RGB(0,250,250), TextColor.ANSI.DEFAULT));
                        }
                    }
                    break;

                case 2:
//                    TextCharacter speeds = new TextCharacter('S', new TextColor.RGB(250, 0, 0), TextColor.ANSI.DEFAULT);
//                    screen.setCharacter(posX, posY, speeds);
                     n = 0;
                    for (int i = posX; i < posX + 3; i++) {
                        for (int j = posY; j < posY + 2; j++) {
                            screen.setCharacter(i, j, new TextCharacter(SPEEDS[n++], new TextColor.RGB(250,0,0), TextColor.ANSI.DEFAULT));
                        }
                    }
                    break;

                case 3:
//                    TextCharacter bomber = new TextCharacter('B', new TextColor.RGB(0,0,250), TextColor.ANSI.DEFAULT);
//                    screen.setCharacter(posX, posY, bomber);
                    n = 0;
                    for (int i = posX; i < posX + 3; i++) {
                        for (int j = posY; j < posY + 2; j++) {
                            screen.setCharacter(i, j, new TextCharacter(BIOMBS[n++], new TextColor.RGB(0,0,250), TextColor.ANSI.DEFAULT));
                        }
                    }
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
