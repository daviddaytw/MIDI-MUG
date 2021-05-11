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
import java.util.ArrayList;
import java.util.Arrays;
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
public class MidiUtils {

    private static final int NOTE_ON = 0x90;
    private static final int NOTE_OFF = 0x80;
    private static final int EMPTY = -1;

    public static Channel[] getChannels(Sequence sequence) {
        Channel[] channels = new Channel[sequence.getTracks().length];
        ArrayList<Note> noteList = new ArrayList<>();

        int trackNumber = 0;
        for (Track track : sequence.getTracks()) {
            System.out.println("Track " + trackNumber + ": size = " + track.size());

            noteList.clear();
            long[] buffer = new long[200];
            Arrays.fill(buffer, -1);

            for (int i = 0; i < track.size(); i++) {
                MidiEvent event = track.get(i);
                MidiMessage message = event.getMessage();

                long tick = event.getTick();

                if (message instanceof ShortMessage) {
                    ShortMessage sm = (ShortMessage) message;

                    int command = sm.getCommand();
                    int key = sm.getData1();
                    int velocity = sm.getData2();

                    if (velocity == 0) {
                        command = NOTE_OFF;
                    }

                    switch (command) {
                        case NOTE_ON: {
                            if (buffer[key] == EMPTY) {
                                buffer[key] = tick;
                            } else {
                                System.err.println("Invalid MIDI File! Note is already on.");
                            }
                            break;
                        }
                        case NOTE_OFF: {
                            if (buffer[key] != EMPTY) {
                                long timestamp = buffer[key];
                                buffer[key] = EMPTY;
                                Note note = new Note(key, timestamp, tick - timestamp);
                                noteList.add(note);
                            } else {
                                System.err.println("Invalid MIDI File! Note off when it isn't on.");
                            }
                            break;
                        }
                        default: {
                            System.err.println("Unhandled Command: " + sm.getCommand());
                            break;
                        }
                    }
                } else {
                    System.out.print("Unhandled Midi message: ");
                    System.out.println(message);
                }
            }

            channels[trackNumber] = new Channel(noteList);
            trackNumber++;
        }

        return channels;
    }

    public static Sheet getSheet(File source) throws InvalidMidiDataException, IOException {
        Sequence sequence = MidiSystem.getSequence(source);

        float divisionType = sequence.getDivisionType();
        long length = sequence.getMicrosecondLength();
        int resolution;
        if (divisionType == Sequence.PPQ) {
            resolution = sequence.getResolution();
        } else {
            System.err.print("Unhandled division type: ");
            System.err.println(divisionType);
            resolution = -1;
        }
        Channel[] channels = getChannels(sequence);

        return new Sheet(channels, resolution, length);
    }
}
