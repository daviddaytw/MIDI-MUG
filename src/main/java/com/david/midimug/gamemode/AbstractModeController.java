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

import com.david.midimug.handler.Note;
import javafx.animation.KeyFrame;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;
import javax.sound.midi.MidiMessage;
import javax.sound.midi.Receiver;
import javax.sound.midi.ShortMessage;

/**
 *
 * @author david
 */
public abstract class AbstractModeController implements Receiver {

    private static final int NOTE_ON = 0x90;
    private static final int NOTE_OFF = 0x80;

    public abstract KeyFrame onNoteShow(Duration time, Pane pane, Rectangle bar, Note note);

    public abstract KeyFrame onNoteStart(Duration time, Pane pane, Rectangle bar, Note note);

    public abstract KeyFrame onNoteEnd(Duration time, Pane pane, Rectangle bar, Note note);

    public abstract void onUserPress(int key);

    public abstract void onUserRelease(int key);

    public abstract long getScore();

    @Override
    public void send(MidiMessage mm, long l) {
        if (mm instanceof ShortMessage) {
            ShortMessage message = (ShortMessage) mm;

            int command = message.getCommand();
            int key = message.getData1();
            int velocity = message.getData2();

            if (velocity == 0) {
                command = NOTE_OFF;
            }

            switch (command) {
                case NOTE_ON: {
                    onUserPress(key);
                    break;
                }
                case NOTE_OFF: {
                    onUserRelease(key);
                    break;
                }
                default: {
                    System.err.println("Unsupport Midi device command");
                    break;
                }
            }
        } else {
            System.err.println("Unsupport Midi device message");
        }
    }

    @Override
    public void close() {
    }
}
