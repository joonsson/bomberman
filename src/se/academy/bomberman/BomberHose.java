package se.academy.bomberman;

import com.googlecode.lanterna.TextColor;
import com.googlecode.lanterna.input.KeyStroke;
import com.googlecode.lanterna.input.KeyType;
import com.googlecode.lanterna.screen.Screen;

import java.io.IOException;

public class BomberHose extends PlayerHoes {


    public BomberHose(int x, int y, char playerModel, TextColor playerColor,
                     Screen screen, TextColor bombColor, TextColor bg, TextColor bombBG, MapCell[][] map) {
        super(x, y, playerModel, playerColor, screen, bombColor, bg, bombBG, map);
    }
    @Override
    public void run() {

        while (true) {
            long delay = System.currentTimeMillis();
            KeyStroke key = null;

            if (System.currentTimeMillis() - bomb.getStart() > BOMBD) {
                bomb.explode();
            }

            try {
                key = screen.pollInput();
                if (key != null) {
                    if (key.isAltDown()) {
                        dropBomb();
                    }
                    if (key.getKeyType() == KeyType.Character) {
                        char keyChar = key.getCharacter();
                        switch (keyChar) {
                            case 'w': {
                                move(NORTH);
                                break;
                            }
                            case 's': {
                                move(SOUTH);
                                break;
                            }
                            case 'd': {
                                move(EAST);
                                break;
                            }
                            case 'a': {
                                move(WEST);
                                break;
                            }
                        }
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
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
        }
    }
}

