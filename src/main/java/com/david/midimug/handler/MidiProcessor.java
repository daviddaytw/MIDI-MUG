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
package com.david.midimug.handler;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import javax.sound.midi.InvalidMidiDataException;
import javax.sound.midi.MidiEvent;
import javax.sound.midi.MidiMessage;
import javax.sound.midi.MidiSystem;
import javax.sound.midi.Sequence;
import javax.sound.midi.ShortMessage;
import javax.sound.midi.Track;

/**
 *
 * @author david
 */
public class MidiProcessor {

    private static final int NOTE_ON = 0x90;
    private static final int NOTE_OFF = 0x80;

    public static Note[] getNotes(File source) throws InvalidMidiDataException, IOException {
        Sequence sequence = MidiSystem.getSequence(source);
        ArrayList<Note> noteList = new ArrayList<>();

        int trackNumber = 0;
        for (Track track : sequence.getTracks()) {
            trackNumber++;
            System.out.println("Track " + trackNumber + ": size = " + track.size());
            System.out.println();
            for (int i = 0; i < track.size(); i++) {
                MidiEvent event = track.get(i);
                MidiMessage message = event.getMessage();

                long tick = event.getTick();

                if (message instanceof ShortMessage) {
                    ShortMessage sm = (ShortMessage) message;

                    int key = sm.getData1();
                    int velocity = sm.getData2();

                    switch (sm.getCommand()) {
                        case NOTE_ON: {
                            Note note = new Note(key, tick, Note.STATUS_ON, velocity);
                            noteList.add(note);
                            break;
                        }
                        case NOTE_OFF: {
                            Note note = new Note(key, tick, Note.STATUS_OFF, velocity);
                            noteList.add(note);
                            break;
                        }
                    }
                } else {
                    System.out.print("Unhandled Midi message: ");
                    System.out.println(message);
                }
            }
        }

        Note[] notes = new Note[noteList.size()];
        notes = noteList.toArray(notes);
        return notes;
    }
}
