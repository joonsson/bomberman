package se.academy.bomberman;

import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

public class Music extends Thread {
    MediaPlayer mediaPlayer;
    Media media;
    boolean loop;

    public Music(String file) {
        this(file, false);
    }
    public Music(String file, boolean loop) {
        media = new Media(file);
        mediaPlayer = new MediaPlayer(media);
        this.loop = loop;
    }
    @Override
    public void run() {

    }
}
