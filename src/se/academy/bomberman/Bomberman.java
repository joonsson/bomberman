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

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Bomberman {

    static protected final int NORTH = 0;
    static protected final int SOUTH = 1;
    static protected final int WEST = 2;
    static protected final int EAST = 3;
    private static final int COLUMNS = 29;
    private static final int SCREENWIDTH = 129;
    private static final int ROWS = 20;
    private static final int SCREENHEIGHT = 49;
    private static final int BJSTARTX = 1;
    private static final int BHSTARTX = COLUMNS - 4;
    private static final int BJSTARTY = 1;
    private static final int BHSTARTY = ROWS - 3;
    private static final long DELTAT = 16;
    public static boolean inGame = true;
    private static boolean playing;
    private static List<Player> players;
    private static Map map;
    private static Screen screen;
    private static Music brinstar;

    public static void main(String[] args) throws IOException, InterruptedException {
        JFXPanel jfx = new JFXPanel();

        init();
        while (playing) {
            initGame(screen);
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

            }while (inGame) ;
            endGame(screen);
            gameOver(screen, players);
        }
    }

    private static void init() throws IOException {
        TerminalSize newSize = new TerminalSize(SCREENWIDTH, SCREENHEIGHT);
        screen = new DefaultTerminalFactory().setInitialTerminalSize(newSize).createScreen();
        screen.startScreen();
        brinstar = new Music("src/Sounds/Brinstar.mp3");
        //brinstar.start();
        screen.setCursorPosition(null);
        mainMenu(screen);
        playing = true;
    }

    private static void initGame(Screen screen) {
        map = new Map(COLUMNS, ROWS); //TODO change where we draw the map and start pos
        screen.clear();
        draw(map.getCells(), screen);
        Player player1 = new Player(BJSTARTX, BJSTARTY, 'J', new TextColor.RGB(180, 10, 140),
                new TextColor.RGB(100, 4, 80), screen, new TextColor.RGB(255, 0, 0),
                map.getCells()[BJSTARTX][BJSTARTY].color, new TextColor.RGB(180, 0, 0), map.getCells());

        Player player2 = new Player(BHSTARTX, BHSTARTY, 'H', new TextColor.RGB(0, 100, 200),
                new TextColor.RGB(0, 40, 160), screen, new TextColor.RGB(255, 0, 0),
                map.getCells()[BHSTARTX][BHSTARTY].color, new TextColor.RGB(180, 0, 0), map.getCells());
        player2.setEnemy(player1);
        player1.setEnemy(player2);


        players = new ArrayList<>();

        players.add(player1);
        players.add(player2);
    }

    private static void gameOver(Screen screen, List<Player> players) throws IOException {
        brinstar.mediaPlayer.pause();
        Music gameOver = new Music("src/Sounds/smb_gameover.wav");
        screen.clear();
        screen.clear();
        TextGraphics tg = screen.newTextGraphics();
        gameOver.start();
        if (players.get(0).isLiving()) {
            BufferedReader reader = new BufferedReader(new FileReader(new File("src/Text/p1win.txt")));
            String s = reader.readLine();
            int n = 10;
            while (s != null) {
                tg.putString(2, n++, s);
                s = reader.readLine();
            }
        } else {
            BufferedReader reader = new BufferedReader(new FileReader(new File("src/Text/p2win.txt")));
            String s = reader.readLine();
            int n = 10;
            while (s != null) {
                tg.putString(2, n++, s);
                s = reader.readLine();
            }
        }
        tg.putString(50, 28, "Press enter to play again.");
        tg.putString(50, 30, "Press escape to exit.");

        screen.refresh();
        KeyStroke key;
        boolean menu = true;
        while (menu) {
            key = screen.pollInput();
            if (key != null) {
                switch (key.getKeyType()) {
                    case Enter:
                        gameOver.mediaPlayer.pause();
                        menu = false;
                        inGame = true;
                        break;
                    case Escape:
                        screen.close();
                        playing = false;
                        System.exit(0);
                        break;
                }
            }
        }
    }

    private static void endGame(Screen screen) throws IOException {
        Music endGame = new Music("src/Sounds/smb_mariodie.wav");
        endGame.start();
        screen.refresh();
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        endGame.mediaPlayer.pause();
    }

    private static void mainMenu(Screen screen) throws IOException {
        boolean menu = true;
        boolean start = true;
        TextGraphics tg = screen.newTextGraphics();
        while (menu) {
            screen.clear();
            BufferedReader reader = new BufferedReader(new FileReader(new File("src/Text/title.txt")));
            String s = reader.readLine();
            int n = 0;
            while (s != null) {
                tg.putString(15, n++, s);
                s = reader.readLine();
            }
            reader = new BufferedReader(new FileReader(new File("src/Text/bomberman.txt")));
            s = reader.readLine();
            n--;
            while (s != null) {
                tg.putString(45, n++, s);
                s = reader.readLine();
            }
            reader = new BufferedReader(new FileReader(new File("src/Text/start.txt")));
            s = reader.readLine();
            int startX = 20;
            int startY = n;
            while (s != null) {
                tg.putString(30, n++, s);
                s = reader.readLine();
            }
            reader = new BufferedReader(new FileReader(new File("src/Text/exit.txt")));
            s = reader.readLine();
            int exitX = 35;
            int exitY = n;
            while (s != null) {
                tg.putString(45, n++, s);
                s = reader.readLine();
            }
            reader = new BufferedReader(new FileReader(new File("src/Text/star.txt")));
            if (start) {
                s = reader.readLine();
                n = startY;
                while (s != null) {
                    tg.putString(startX, n++, s);
                    s = reader.readLine();
                }
            } else {
                s = reader.readLine();
                n = exitY;
                while (s != null) {
                    tg.putString(exitX, n++, s);
                    s = reader.readLine();
                }
            }
            screen.refresh();
            KeyStroke key = null;
            while (key == null) {
                key = screen.pollInput();
                if (key != null) {
                    switch (key.getKeyType()) {
                        case Enter:
                            if (start) {
                                menu = false;
                                inGame = true;
                                break;
                            } else {
                                screen.close();
                                System.exit(0);
                            }
                        case ArrowUp:
                            if (!start) start = true;
                            break;
                        case ArrowDown:
                            if (start) start = false;
                            break;
                        default:
                            key = null;
                            break;
                    }
                }
            }
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

        if (player1.hasBombed() && System.currentTimeMillis() - player1.getBomb().getStart() > player1.getFUSE() && player1.getBomb().isVisible()) {
            player1.explode();
        }

            if (player1.hasBombed() && System.currentTimeMillis() - player1.getBomb().getStart() > player1.getFUSE() / 4 && !player1.getBomb().isVisible()) {
                player1.deplode();
            }

            if (player2.hasBombed() && System.currentTimeMillis() - player2.getBomb().getStart() > player2.getFUSE() && player2.getBomb().isVisible()) {
                player2.explode();
            }
                if (player2.hasBombed() && System.currentTimeMillis() - player2.getBomb().getStart() > player2.getFUSE() / 4 && !player2.getBomb().isVisible()) {
                    player2.deplode();
                }
            }
        }
