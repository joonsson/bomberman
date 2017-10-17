package se.academy.bomberman;

import com.googlecode.lanterna.TextColor;
import com.googlecode.lanterna.screen.Screen;

public class BomberJoe extends PlayerHoes {


    public BomberJoe(int x, int y, char playerModel, TextColor playerColor, Screen screen, TextColor bombColor, TextColor bg, TextColor bombBG) {
        super(x, y, playerModel, playerColor, screen, bombColor, bg, bombBG);
    }
}
