package se.academy.bomberman;

import com.googlecode.lanterna.TextCharacter;
import com.googlecode.lanterna.TextColor;
import com.googlecode.lanterna.graphics.TextGraphics;
import com.googlecode.lanterna.screen.Screen;

public class PowerUp {

    private int speed=1;
    private int biggerBombs=2;
    private int posX =10;
    private int posY =10;
    private Screen screen;

    private boolean death;
    private boolean life;



    private TextCharacter bombs = new TextCharacter('B');
    private TextCharacter speedigonzales = new TextCharacter('S');
    private Bomberman bomberman;


    public PowerUp (int x , int y){
        this.posX=x;
        this.posY=y;
    }

    public boolean isDeath() {
        return death;
    }

    public void setDeath(boolean death) {
        this.death = death;
        Bomberman.gameover.start();
    }

    public boolean isLife() {
        return life;
    }

    public void setLife(boolean life) {
        this.life = life;
        Music livingIsLife = new Music("src/Sounds/smb_powerup.wav");
    }

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
    public int getSpeed (){
        this.speed = speed+1;
        return speed;
    }
    public int getBiggerBombs (){
        this.biggerBombs =biggerBombs+1;

        return biggerBombs;
    }
    public void drawBombs(Screen screen, Map map){
        MapCell [][] cells =  map.getCells();

        screen.setCharacter(cells[getPosX()][getPosY()],new TextCharacter('B'));
    }
    public void drawSpeed(){
        speedigonzales.getCharacter();
        TextGraphics graphics;
        graphics.fillRectangle(getPosX(),10,'S');

    }
    public void drawDeath(){

    }
    public void drawLife(){

    }
}
