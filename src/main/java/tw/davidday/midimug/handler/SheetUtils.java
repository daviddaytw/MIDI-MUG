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

import tw.davidday.midimug.render.SheetRenderer;
import java.io.File;
import java.io.IOException;
import javafx.animation.Timeline;
import javafx.scene.layout.Pane;
import javafx.util.Duration;
import javax.sound.midi.InvalidMidiDataException;

/**
 *
 * @author david
 */
public class SheetUtils {

    private static Timeline timeline = new Timeline();

    public static void setupSheet(Pane pane, File source) throws InvalidMidiDataException, IOException {
        Sheet music_sheet = MidiUtils.getSheet(source);
        timeline.stop();
        timeline.getKeyFrames().clear();
        SheetRenderer.renderBarSheet(pane, music_sheet, timeline);
    }

    public static void play() {
        timeline.play();
    }

    public static void pause() {
        if(timeline != null) timeline.pause();
    }

    public static Duration getCurrentTime() {
        return timeline.getCurrentTime();
    }
}
