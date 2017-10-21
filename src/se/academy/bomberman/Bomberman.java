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

public class Bomberman implements Constants {
    public static boolean inGame = true;
    private static boolean playing;
    private static List<Player> players;
    private static Map map;
    private static Screen screen;
    private static Music menuMusic;
    private static Music gameMusic;

    public static void main(String[] args) throws IOException, InterruptedException {
        JFXPanel jfx = new JFXPanel();

        init();
        while (playing) {
            initGame(screen);
            do {
                long delay = System.currentTimeMillis();
                screen.refresh();

                bombCheck(players); // TODO walkable again
                keyCheck(screen.pollInput(), players);
                checkGameState();

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
            endGame(screen);
            gameOver(screen, players);
        }
    }

    private static void checkGameState() {
        int n = 0;
        for (Player p: players) {
            if (p.isLiving()) {
                n++;
            }
        }
        if (n < 2) {
            inGame = false;
        }
    }

    private static void init() throws IOException {
        TerminalSize newSize = new TerminalSize(SCREENWIDTH, SCREENHEIGHT);
        screen = new DefaultTerminalFactory().setInitialTerminalSize(newSize).createScreen();
        screen.startScreen();
        menuMusic = new Music("src/Sounds/TNT.mp3");
        menuMusic.mediaPlayer.setVolume(0.5);
        menuMusic.start();
        screen.setCursorPosition(null);
        mainMenu(screen);
        playing = true;
      
    }

    private static void initGame(Screen screen) {
        map = new Map(COLUMNS, ROWS); //TODO change where we draw the map and start pos
        screen.clear();
        draw(map.getCells(), screen);
        List<PowerUp> powerUps = new ArrayList<>();
        Player player1 = new Player(P1STARTX, P1STARTY, playerModel1, new TextColor.RGB(180, 10, 140),
                new TextColor.RGB(100, 4, 80), screen, new TextColor.RGB(255, 0, 0),
                map.getCells()[P1STARTX][P1STARTY].color, new TextColor.RGB(180, 0, 0), map.getCells(), powerUps);
        Player player2 = new Player(P2STARTX, P2STARTY, playerModel2, new TextColor.RGB(0, 100, 200),
                new TextColor.RGB(0, 40, 160), screen, new TextColor.RGB(255, 0, 0),
                map.getCells()[P2STARTX][P2STARTY].color, new TextColor.RGB(180, 0, 0), map.getCells(), powerUps);
        Player player3 = new Player(P3STARTX, P3STARTY, playerModel3, new TextColor.RGB(0, 100, 200),
                new TextColor.RGB(0, 40, 160), screen, new TextColor.RGB(255, 0, 0),
                map.getCells()[P3STARTX][P3STARTY].color, new TextColor.RGB(180, 0, 0), map.getCells(), powerUps);
        Player player4 = new Player(P4STARTX, P4STARTY, playerModel4, new TextColor.RGB(0, 100, 200),
                new TextColor.RGB(0, 40, 160), screen, new TextColor.RGB(255, 0, 0),
                map.getCells()[P4STARTX][P4STARTY].color, new TextColor.RGB(180, 0, 0), map.getCells(), powerUps);


        players = new ArrayList<>();

        players.add(player1);
        players.add(player2);
        players.add(player3);
        players.add(player4);
        player1.setPlayers(players);
        player2.setPlayers(players);
        player3.setPlayers(players);
        player4.setPlayers(players);
        menuMusic.mediaPlayer.pause();
    }

    private static void gameOver(Screen screen, List<Player> players) throws IOException {
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
        } else if (players.get(1).isLiving()) {
            BufferedReader reader = new BufferedReader(new FileReader(new File("src/Text/p2win.txt")));
            String s = reader.readLine();
            int n = 10;
            while (s != null) {
                tg.putString(2, n++, s);
                s = reader.readLine();
            }
        } else if (players.get(2).isLiving()) {
            BufferedReader reader = new BufferedReader(new FileReader(new File("src/Text/p3win.txt")));
            String s = reader.readLine();
            int n = 10;
            while (s != null) {
                tg.putString(2, n++, s);
                s = reader.readLine();
            }
        } else {
            BufferedReader reader = new BufferedReader(new FileReader(new File("src/Text/p4win.txt")));
            String s = reader.readLine();
            int n = 10;
            while (s != null) {
                tg.putString(2, n++, s);
                s = reader.readLine();
            }
        }
        int n = 20;
        if (players.get(0).isSuicided()) {
            tg.putString(55, n++, "Player1 suicided.");
        }
        if (players.get(1).isSuicided()) {
            tg.putString(55, n++, "Player2 suicided.");
        }
        if (players.get(2).isSuicided()) {
            tg.putString(55, n++, "Player3 suicided.");
        }
        if (players.get(3).isSuicided()) {
            tg.putString(55, n, "Player4 suicided.");
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
            int m = 10;
            reader = new BufferedReader(new FileReader(new File("src/Text/controls1.txt")));
            s = reader.readLine();
            while (s != null) {
                tg.putString(5, m++, s);
                s = reader.readLine();
            }
            m = 10;
            reader = new BufferedReader(new FileReader(new File("src/Text/controls2.txt")));
            s = reader.readLine();
            while (s != null) {
                tg.putString(100, m++, s);
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
                    players.get(2).move(NORTH);
                    break;
                case ArrowRight:
                    players.get(2).move(EAST);
                    break;
                case ArrowLeft:
                    players.get(2).move(WEST);
                    break;
                case ArrowDown:
                    players.get(2).move(SOUTH);
                    break;
                case Enter:
                    players.get(2).dropBomb();
                    break;
            }
            if (keyStroke.getKeyType() == KeyType.Character) {
                switch (keyStroke.getCharacter()) {
                    case 'w':
                        players.get(0).move(NORTH);
                        break;
                    case 's':
                        players.get(0).move(SOUTH);
                        break;
                    case 'a':
                        players.get(0).move(WEST);
                        break;
                    case 'd':
                        players.get(0).move(EAST);
                        break;
                    case 'c':
                        players.get(0).dropBomb();
                        break;
                }
                switch (keyStroke.getCharacter()) {
                    case 'i':
                        players.get(1).move(NORTH);
                        break;
                    case 'k':
                        players.get(1).move(SOUTH);
                        break;
                    case 'j':
                        players.get(1).move(WEST);
                        break;
                    case 'l':
                        players.get(1).move(EAST);
                        break;
                    case 'n':
                        players.get(1).dropBomb();
                        break;
                }
                switch (keyStroke.getCharacter()) {
                    case '8':
                        players.get(3).move(NORTH);
                        break;
                    case '5':
                        players.get(3).move(SOUTH);
                        break;
                    case '4':
                        players.get(3).move(WEST);
                        break;
                    case '6':
                        players.get(3).move(EAST);
                        break;
                    case '1':
                        players.get(3).dropBomb();
                        break;
                }
            }
        }
    }

    static void bombCheck(List<Player> players) {
        for (Player p: players) {
            if (p.hasBombed() && System.currentTimeMillis() - p.getBomb().getStart() > p.getFUSE() && p.getBomb().isVisible()) {
                p.explode();
            }
            if (p.hasBombed() && System.currentTimeMillis() - p.getBomb().getStart() > p.getFUSE() / 3 && !p.getBomb().isVisible()) {
                p.deplode();
            }
        }
    }
}
