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
package com.david.midimug.gamemode;

import com.david.midimug.handler.MidiInstruments;
import com.david.midimug.handler.Note;
import com.david.midimug.handler.SheetUtils;
import com.david.midimug.render.KeyboardRenderer;
import com.david.midimug.render.SheetRenderer;
import java.util.Arrays;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.beans.property.Property;
import javafx.event.ActionEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;

/**
 *
 * @author david
 */
public class FluidModeController extends AbstractModeController {

    private static final int NOTE_OFF = -1;
    private static final int NOTE_RANGE = 200;

    private final double[] note_start;
    private final double[] note_end;

    public FluidModeController() {
        super();
        note_start = new double[NOTE_RANGE];
        note_end = new double[NOTE_RANGE];
        Arrays.fill(note_start, NOTE_OFF);
    }

    @Override
    public void setupNote(Pane pane, Rectangle bar, Timeline timeline, Note note, double show_t, double start_t, double end_t) {
        Property YProperty = bar.layoutYProperty();
        double barHeight = bar.getHeight();
        double paneHeight = pane.getHeight();
        int key = note.getKey();

        KeyFrame show = new KeyFrame(
                Duration.millis(show_t),
                new KeyValue(YProperty, -barHeight)
        );

        KeyFrame start = new KeyFrame(
                Duration.millis(start_t),
                (ActionEvent t) -> {
                    MidiInstruments.noteOn(key);
                    note_start[key] = start_t;
                    note_end[key] = end_t;
                },
                new KeyValue(YProperty, paneHeight - barHeight)
        );

        KeyFrame end = new KeyFrame(
                Duration.millis(end_t),
                (ActionEvent t) -> {
                    MidiInstruments.noteOff(key);
                    note_start[key] = NOTE_OFF;
                    note_end[key] = NOTE_OFF;
                },
                new KeyValue(YProperty, paneHeight)
        );

        timeline.getKeyFrames().addAll(show, start, end);
    }

    @Override
    public void onUserPress(int key) {
        if (note_start[key] != NOTE_OFF) {
            double currentMillis = SheetUtils.getCurrentTime().toMillis();
            double length = note_end[key] - note_start[key];
            double accuracy = length - (currentMillis - note_start[key]);
            int score = (int) Math.round(1000 * accuracy / length);
            SheetRenderer.renderCombo(Integer.toString(score), Color.CORAL);

            note_start[key] = NOTE_OFF;
        }
        KeyboardRenderer.pressKey(key);
    }

    @Override
    public void onUserRelease(int key) {
        KeyboardRenderer.releaseKey(key);
    }

    @Override
    public long getScore() {
        throw new UnsupportedOperationException("Not supported yet.");
    }
}
