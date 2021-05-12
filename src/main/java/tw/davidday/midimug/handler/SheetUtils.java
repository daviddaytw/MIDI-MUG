/*
 * Copyright (C) 2020 david
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package tw.davidday.midimug.handler;

import java.io.File;
import java.io.IOException;
import javafx.animation.Timeline;
import javafx.scene.layout.Pane;
import javafx.util.Duration;
import javax.sound.midi.InvalidMidiDataException;
import tw.davidday.midimug.render.SheetRenderer;

/**
 *
 * @author david
 */
public class SheetUtils {

    private static final Timeline timeline = new Timeline();
    private static Sheet musicSheet;
    private static Pane pane;

    public static void setupSheet(Pane pane, File source) throws InvalidMidiDataException, IOException {
        SheetUtils.musicSheet = MidiUtils.getSheet(source);
        SheetUtils.pane = pane;
        SheetUtils.replay();
    }

    public static void play() {
        timeline.play();
    }

    public static void replay() {
        timeline.stop();
        timeline.getKeyFrames().clear();
        SheetRenderer.renderBarSheet(pane, musicSheet, timeline);
        timeline.playFromStart();
    }

    public static void pause() {
        if (timeline != null) {
            timeline.pause();
        }
    }

    public static double getRate() {
        return timeline.getRate();
    }

    public static void setRate(double d) {
        timeline.setRate(d);
    }

    public static void shift(double d) {
        timeline.playFrom(timeline.getCurrentTime().add(Duration.seconds(d)));
    }

    public static Duration getCurrentTime() {
        return timeline.getCurrentTime();
    }

    public static Duration getTotalTime() {
        return timeline.getCycleDuration();
    }
}
