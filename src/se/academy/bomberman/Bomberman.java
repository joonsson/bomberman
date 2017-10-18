package se.academy.bomberman;

import com.googlecode.lanterna.TerminalSize;
import com.googlecode.lanterna.TextCharacter;
import com.googlecode.lanterna.TextColor;
import com.googlecode.lanterna.graphics.TextGraphics;
import com.googlecode.lanterna.input.KeyStroke;
import com.googlecode.lanterna.input.KeyType;
import com.googlecode.lanterna.screen.Screen;
import com.googlecode.lanterna.terminal.DefaultTerminalFactory;
import javafx.embed.swing.JFXPanel;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Bomberman {

    static protected final int NORTH = 0;
    static protected final int SOUTH = 1;
    static protected final int WEST = 2;
    static protected final int EAST = 3;
    private static final int COLUMNS = 29;
    private static final int ROWS = 20;
    private static final int BJSTARTX = 1;
    private static final int BHSTARTX = 25;
    private static final int BJSTARTY = 1;
    private static final int BHSTARTY = 17;
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

        Player player1 = new Player(BJSTARTX, BJSTARTY, 'J', new TextColor.RGB(180, 10, 140),
                new TextColor.RGB(100, 4, 80), screen, new TextColor.RGB(255, 0, 0),
                map.getCells()[BJSTARTX][BJSTARTY].color, new TextColor.RGB(180, 0, 0), map.getCells());

        Player player2 = new Player(BHSTARTX, BHSTARTY, 'H', new TextColor.RGB(0, 100, 200),
                new TextColor.RGB(0, 40, 160), screen, new TextColor.RGB(255, 0, 0),
                map.getCells()[BHSTARTX][BHSTARTY].color, new TextColor.RGB(180, 0, 0), map.getCells());
        player2.setEnemy(player1);
        player1.setEnemy(player2);

        List<Player> players = new ArrayList<>();

        players.add(player1);
        players.add(player2);


        do {
            long delay = System.currentTimeMillis();
            screen.refresh();

            bombCheck(players); // TODO walkable igen
            keyCheck(screen.pollInput(), players);

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

        } while (inGame);
        Music endGame = new Music("src/Sounds/smb_mariodie.wav");
        endGame.start();
        screen.refresh();
        Music gameOver = new Music("src/Sounds/smb_gameover.wav");
        Thread.sleep(3000);
        screen.clear();
        TextGraphics tg = screen.newTextGraphics();
        if (player1.isLiving()) {
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

    private static void keyCheck(KeyStroke keyStroke, List<Player> players) {

        if (keyStroke != null) {
            switch (keyStroke.getKeyType()) {
                case ArrowUp:
                    players.get(0).move(NORTH);
                    break;
                case ArrowRight:
                    players.get(0).move(EAST);
                    break;
                case ArrowLeft:
                    players.get(0).move(WEST);
                    break;
                case ArrowDown:
                    players.get(0).move(SOUTH);
                    break;
                case Enter:
                    players.get(0).dropBomb();
                    break;
            }
            if (keyStroke.getKeyType() == KeyType.Character) {
                switch (keyStroke.getCharacter()) {
                    case 'w':
                        players.get(1).move(NORTH);
                        break;
                    case 's':
                        players.get(1).move(SOUTH);
                        break;
                    case 'a':
                        players.get(1).move(WEST);
                        break;
                    case 'd':
                        players.get(1).move(EAST);
                        break;
                    case 'c':
                        players.get(1).dropBomb();
                        break;
                    // TODO Add more player controls
                }
            }
        }
    }


    static void bombCheck(List<Player> players) {
        Player player1 = players.get(0);
        Player player2 = players.get(1);

        if (player1.bombed && System.currentTimeMillis() - player1.bomb.getStart() > player1.FUSE && player1.bomb.isVisible()) {
            player1.explode();
        }
        if (player1.bombed && System.currentTimeMillis() - player1.bomb.getStart() > player1.FUSE/2 && !player1.bomb.isVisible()) {
            player1.deplode();
        }

        if (player2.bombed && System.currentTimeMillis() - player2.bomb.getStart() > player2.FUSE && player2.bomb.isVisible()) {
            player2.explode();
        }
        if (player2.bombed && System.currentTimeMillis() - player2.bomb.getStart() > player2.FUSE/2 && !player2.bomb.isVisible()) {
            player2.deplode();
        }
    }
}
