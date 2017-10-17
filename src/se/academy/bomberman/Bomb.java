package se.academy.bomberman;

import com.googlecode.lanterna.TextCharacter;
import com.googlecode.lanterna.TextColor;
import javafx.stage.Screen;

public class Bomb {

    private int posX;
    private int posY;
    private boolean visible;
    private TextCharacter model;
    private long start;



    //   private Screen screen;
    Bomb(TextColor color, TextColor bg){
        posY=0;
        posX=0;
        visible = false;
        model = new TextCharacter('Q', color,bg );

    }

    public void explode() {
        
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

    public TextCharacter getModel() {
        return model;
    }

    public void setModel(TextCharacter model) {
        this.model = model;
    }

    public long getStart() {
        return start;
    }

    public void setStart(long start) {
        this.start = start;
    }
}
