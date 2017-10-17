package se.academy.bomberman;

import com.googlecode.lanterna.TerminalSize;
import com.googlecode.lanterna.screen.Screen;
import com.googlecode.lanterna.terminal.DefaultTerminalFactory;
import javafx.embed.swing.JFXPanel;

import java.io.IOException;

public class Bomberman {
    public static void main(String[] args) throws IOException {
        JFXPanel jfx = new JFXPanel();
        Map map = new Map(100, 50);


        TerminalSize newSize = new TerminalSize(map.getRows(), map.getColumns());
        Screen screen = new DefaultTerminalFactory().setInitialTerminalSize(newSize).createScreen();
        screen.startScreen();
        Music brinstar = new Music("src/Sounds/Brinstar.mp3");
        brinstar.start();


        do {


        }while(true);

    }



}
