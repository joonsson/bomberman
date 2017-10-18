package se.academy.bomberman;

import com.googlecode.lanterna.TextColor;
import com.googlecode.lanterna.input.KeyStroke;
import com.googlecode.lanterna.screen.Screen;

import java.io.IOException;

public class BomberJoe extends PlayerHoes {


    BomberJoe(int x, int y, char playerModel, TextColor playerColor, TextColor playerBG, Screen screen, TextColor bombColor, TextColor bg, TextColor bombBG, MapCell[][] map) {
        super(x, y, playerModel, playerColor, playerBG, screen, bombColor, bg, bombBG, map);
    }

    @Override
    public void run() {
        bombPlant = new Sound("src/Sounds/smb_fireball.wav");
        bombPlant.start();
        bombExplode = new Sound("src/Sounds/smb_fireworks.wav");
        bombExplode.start();
        while (true) {
            long delay = System.currentTimeMillis();
            KeyStroke key = null;

            if (bombed && System.currentTimeMillis() - bomb.getStart() > BOMBD && bomb.isVisible()) {
                explode();
            }
            if (bombed && System.currentTimeMillis() - bomb.getStart() > BOMBD && !bomb.isVisible()) {
                deplode();
            }

            try {
                synchronized (screen) {
                    key = screen.pollInput();
                }
                if (key != null) {
                    if (key.isCtrlDown()) {
                        dropBomb();
                    }
                    switch (key.getKeyType()) {
                        case ArrowUp: {
                            move(NORTH);
                            break;
                        }
                        case ArrowDown: {
                            move(SOUTH);
                            break;
                        }
                        case ArrowRight: {
                            move(EAST);
                            break;
                        }
                        case ArrowLeft: {
                            move(WEST);
                            break;
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
