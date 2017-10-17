package se.academy.bomberman;

import com.googlecode.lanterna.TerminalSize;
import com.googlecode.lanterna.screen.Screen;
import com.googlecode.lanterna.terminal.DefaultTerminalFactory;

import java.io.IOException;

public class Bomberman {

    public static void main(String[] args) throws IOException {

        Map map = new Map(100, 50);


        TerminalSize newSize = new TerminalSize(map.getRows(), map.getColumns());
        Screen screen = new DefaultTerminalFactory().setInitialTerminalSize(newSize).createScreen();
        screen.startScreen();


        do {

            draw(map.getCells(), screen);

        }while(true);

    }

    private static void draw(MapCell[][] mapCells, Screen screen){
        screen.setCharacter();
        screen.setBackgroundColor();
    }


}
