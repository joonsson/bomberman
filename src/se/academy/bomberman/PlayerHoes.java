package se.academy.bomberman;

import com.googlecode.lanterna.TextCharacter;
import com.googlecode.lanterna.TextColor;
import com.googlecode.lanterna.input.KeyStroke;
import com.googlecode.lanterna.screen.Screen;

import java.io.IOException;

public class PlayerHoes extends Thread {

    protected int posX;
    protected int posY;
    protected TextCharacter playerModel;
    protected int speed;
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
    protected final long BOMBD = 3000;



    PlayerHoes(int x, int y, char playerModel, TextColor playerColor, Screen screen,
               TextColor bombColor, TextColor bg, TextColor bombBG, MapCell[][] map) {

        this.bg = bg;
        this.posX = x;
        this.posY = y;
        this.playerModel = new TextCharacter(playerModel,playerColor, bg);
        this.speed = 1;
        this.screen = screen;
        this.bomb = new Bomb(bombColor, bombBG);
        playerModelBomb = new TextCharacter(playerModel,playerColor, bombBG);
        this.map = map;
        init();
    }

    private void init() {
        screen.setCharacter(posX, posY, playerModel);
    }

    @Override
    public void run() {

        while (true) {
            try {
                Thread.sleep(5);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    protected void dropBomb() {

        if (!bomb.isVisible()) {
            bomb.setPosX(posX);
            bomb.setPosY(posY);
            bomb.setVisible(true);
            screen.setCharacter(getPosX(), getPosY(), playerModelBomb);
            map[getPosX()][getPosY()].setWalkable(false);
            bomb.setStart(System.currentTimeMillis());
        }

    }

    protected void move(int direction) {

        if(bomb.isVisible()&&bomb.getPosX()==getPosX() && bomb.getPosY()==posY){
            screen.setCharacter(getPosX(),getPosY(), bomb.getModel());

        }else {
            screen.setCharacter(getPosX(), getPosY(), new TextCharacter(' ',TextColor.ANSI.DEFAULT,bg));
        }
        switch (direction) {
            case NORTH:
                if (map[getPosX()][getPosY()-1].isWalkable()) {
                    setPosY(getPosY() - speed);
                }
                break;
            case SOUTH:
                if (map[getPosX()][getPosY()+1].isWalkable()) {
                    setPosY(getPosY() + speed);
                }
                break;
            case WEST:
                if (map[getPosX()-1][getPosY()].isWalkable()) {
                    setPosX(getPosX() - speed);
                }
                break;
            case EAST:
                if (map[getPosX()+1][getPosY()].isWalkable()) {
                    setPosX(getPosX() + speed);
                }
                break;
        }
        screen.setCharacter(getPosX(), getPosY(), playerModel);
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

    //endregion
}
