package se.academy.bomberman;

import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.util.Duration;

import java.io.File;

public class Music extends Thread {
    MediaPlayer mediaPlayer;
    Media media;
    boolean loop;

    public Music(String file) {
        this(file, false);
    }
    public Music(String file, boolean loop) {
        media = new Media(new File(file).toURI().toString());
        mediaPlayer = new MediaPlayer(media);
        this.loop = loop;
    }
    @Override
    public void run() {
        mediaPlayer.play();
        if (loop) {
            mediaPlayer.setOnEndOfMedia(new Runnable() {
                @Override
                public void run() {
                    mediaPlayer.seek(Duration.ZERO);
                    mediaPlayer.play();
                }
            });
        }
    }
}
