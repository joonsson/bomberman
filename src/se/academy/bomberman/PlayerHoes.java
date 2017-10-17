package se.academy.bomberman;

import com.googlecode.lanterna.TextCharacter;
import com.googlecode.lanterna.TextColor;
import com.googlecode.lanterna.input.KeyStroke;
import com.googlecode.lanterna.screen.Screen;

import java.io.IOException;

public class PlayerHoes extends Thread {

    private int posX;
    private int posY;
    private TextCharacter playerModel;
    private int speed;
    private Screen screen;
    private final int NORTH = 0;
    private final int SOUTH = 1;
    private final int WEST = 2;
    private final int EAST = 3;
    private Bomb bomb;
    private TextCharacter playerModelBomb;
    private TextColor bg;


    PlayerHoes(int x, int y, char playerModel, TextColor playerColor, Screen screen, TextColor bombColor, TextColor bg, TextColor bombBG) {

        this.bg = bg;
        this.posX = x;
        this.posY = y;
        this.playerModel = new TextCharacter(playerModel,playerColor, bg);
        this.speed = 1;
        this.screen = screen;
        this.bomb = new Bomb(bombColor, bombBG);
        playerModelBomb = new TextCharacter(playerModel,playerColor, bombBG);
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
            KeyStroke key = null;

            try {
                key = screen.pollInput();
                if (key != null) {

                    switch (key.getKeyType()) {
                        case ArrowUp: {
                            move(NORTH);
                            break;
                        }
                        case ArrowDown: {
                            move(SOUTH);
                            break;
                        }
                        case ArrowRight: {
                            move(EAST);
                            break;
                        }
                        case ArrowLeft: {
                            move(WEST);
                            break;
                        }
                    }
                    if (key.isCtrlDown()) {
                        dropBomb();
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void dropBomb() {

        if (!bomb.isVisible()) {
            bomb.setPosX(posX);
            bomb.setPosY(posY);
            bomb.setVisible(true);


        }

    }

    protected void move(int direction) {

        if(bomb.isVisible()&&bomb.getPosX()==getPosX() && bomb.getPosY()==posY){
            screen.setCharacter(getPosX(),getPosY(), bomb.model);

        }else {
            screen.setCharacter(getPosX(), getPosY(), new TextCharacter(' ',TextColor.ANSI.DEFAULT,bg));
        }
        switch (direction) {
            case NORTH:
                setPosY(getPosY() - 1 * speed);
                break;
            case SOUTH:
                setPosY(getPosY() + 1 * speed);
                break;
            case WEST:
                setPosX(getPosX() - 1 * speed);
                break;
            case EAST:
                setPosX(getPosX() + 1 * speed);
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
