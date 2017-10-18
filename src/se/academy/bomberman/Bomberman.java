package se.academy.bomberman;

import com.googlecode.lanterna.TerminalSize;
import com.googlecode.lanterna.TextCharacter;
import com.googlecode.lanterna.TextColor;
import com.googlecode.lanterna.graphics.TextGraphics;
import com.googlecode.lanterna.screen.Screen;
import com.googlecode.lanterna.terminal.DefaultTerminalFactory;
import javafx.embed.swing.JFXPanel;

import java.io.IOException;

public class Bomberman {
    private static final int COLUMNS = 100;
    private static final int ROWS = 50;
    private static final int BJSTARTX = 30;
    private static final int BHSTARTX = 20;
    private static final int BJSTARTY = 25;
    private static final int BHSTARTY = 15;
    private static final long DELTAT = 16;
    public static boolean inGame = true;

    public static void main(String[] args) throws IOException, InterruptedException {
        JFXPanel jfx = new JFXPanel();

        Map map = new Map(COLUMNS, ROWS);


        TerminalSize newSize = new TerminalSize(map.getColumns(), map.getRows());
        Screen screen = new DefaultTerminalFactory().setInitialTerminalSize(newSize).createScreen();
        screen.startScreen();
        Music brinstar = new Music("src/Sounds/Brinstar.mp3");
        //brinstar.start();
        screen.setCursorPosition(null);

        draw(map.getCells(), screen);


        BomberJoe bJ = new BomberJoe(BJSTARTX, BJSTARTY, 'J', new TextColor.RGB(180, 10, 140),new TextColor.RGB(100, 4, 80), screen, new TextColor.RGB(255, 0, 0)
                , map.getCells()[BJSTARTX][BJSTARTY].color, new TextColor.RGB(180, 0, 0), map.getCells());
        BomberHose bH = new BomberHose(BHSTARTX, BHSTARTY, 'H', new TextColor.RGB(0, 100, 200),new TextColor.RGB(0, 40, 160), screen, new TextColor.RGB(255, 0, 0)
                , map.getCells()[BHSTARTX][BHSTARTY].color, new TextColor.RGB(180, 0, 0), map.getCells());

        bH.setEnemy(bJ);
        bJ.setEnemy(bH);


        bJ.start();
        try {
            Thread.sleep(8);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        bH.start();
        do {
            long delay = System.currentTimeMillis();
            screen.refresh();
            delay = System.currentTimeMillis() - delay;
            delay = DELTAT - delay;
            if (delay < 0) {
                delay = DELTAT;
            }
            try {
                Thread.sleep(delay);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        }while(inGame);
        Music endGame = new Music("src/Sounds/smb_mariodie.wav");
        endGame.start();
        screen.refresh();
        Music gameOver = new Music("src/Sounds/smb_gameover.wav");
        Thread.sleep(3000);

        endGame.mediaPlayer.pause();
        gameOver.start();

        screen.clear();
        TextGraphics tg = screen.newTextGraphics();
        if (bJ.isLiving()) {
            tg.putString(30, 20, "Joe wins! Hose loses! You suck!");
        } else {
            tg.putString(30, 20, "Hose wins! Joe loses! You suck!");
        }
        try {
            screen.refresh();
            Thread.sleep(5000);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }

    private static void draw(MapCell[][] mapCells, Screen screen) {

        for (int i = 0; i < COLUMNS; i++) {
            for (int j = 0; j < ROWS; j++) {
                screen.setCharacter(i, j, new TextCharacter(' ', TextColor.ANSI.DEFAULT, mapCells[i][j].color));
            }
        }

    }


}
