package se.academy.bomberman;

import com.googlecode.lanterna.TextCharacter;
import com.googlecode.lanterna.TextColor;
import javafx.stage.Screen;

public class Bomb {

    private int posX;
    private int posY;
    private boolean visible;
    public TextCharacter model;


    //   private Screen screen;

    Bomb(TextColor color, TextColor bg){
        posY=0;
        posX=0;
        visible = false;
        model = new TextCharacter('Q', color,bg );

    }

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

    public boolean isVisible() {
        return visible;
    }

    public void setVisible(boolean visible) {
        this.visible = visible;
    }
}
