package se.academy.bomberman;

import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.util.Duration;

import java.io.File;

public class Sound extends Thread {
    MediaPlayer mediaPlayer;
    Media media;
    boolean loop;

    public Sound(String file) {
        this(file, false);
    }
    public Sound(String file, boolean loop) {
        media = new Media(new File(file).toURI().toString());
        mediaPlayer = new MediaPlayer(media);
        this.loop = loop;
    }
    @Override
    public void run() {
        mediaPlayer.setVolume(0.5);
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
    public void play() {
        mediaPlayer.play();
    }
    public void pause() {
        mediaPlayer.pause();
    }
    public void stopp() {
        mediaPlayer.pause();
        mediaPlayer.seek(Duration.ZERO);
    }
}
