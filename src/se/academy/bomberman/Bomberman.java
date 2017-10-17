package se.academy.bomberman;

import com.googlecode.lanterna.TerminalSize;
import com.googlecode.lanterna.TextCharacter;
import com.googlecode.lanterna.TextColor;
import com.googlecode.lanterna.screen.Screen;
import com.googlecode.lanterna.terminal.DefaultTerminalFactory;
import javafx.embed.swing.JFXPanel;

import java.io.IOException;

public class Bomberman {
    private static final int COLUMNS = 100;
    private static final int ROWS =50;
    private static final int BJSTARTX=50;
    private static final int BJSTARTY=25;

    public static void main(String[] args) throws IOException {
        JFXPanel jfx = new JFXPanel();

        Map map = new Map(COLUMNS, ROWS);


        TerminalSize newSize = new TerminalSize(map.getColumns(),map.getRows());
        Screen screen = new DefaultTerminalFactory().setInitialTerminalSize(newSize).createScreen();
        screen.startScreen();
        Music brinstar = new Music("src/Sounds/Brinstar.mp3");
        brinstar.start();

        draw(map.getCells(), screen);
        BomberJoe bJ = new BomberJoe(BJSTARTX,BJSTARTY,'J',new TextColor.RGB(25,254,21),screen,new TextColor.RGB(123,234,0)
                ,map.getCells()[BJSTARTX][BJSTARTY].color, new TextColor.RGB(0,0,250));

        bJ.start();
        do {

<<<<<<< HEAD
=======
            screen.refresh();
>>>>>>> d7cd3e326abc783ff5db1c706d7601b5f03875f8

        }while(true);

    }

<<<<<<< HEAD
=======
    private static void draw(MapCell[][] mapCells, Screen screen){

       for (int i = 0; i< COLUMNS; i++){
           for (int j = 0; j< ROWS; j++){
            screen.setCharacter(i,j, new TextCharacter(' ', TextColor.ANSI.DEFAULT, mapCells[i][j].color) );
           }
       }

    }
>>>>>>> d7cd3e326abc783ff5db1c706d7601b5f03875f8


}
