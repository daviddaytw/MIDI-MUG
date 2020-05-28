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

/**
 *
 * @author david
 */
public class Note {

    public static final String[] NOTE_NAMES = {"C", "C#", "D", "D#", "E", "F", "F#", "G", "G#", "A", "A#", "B"};
    public static final boolean STATUS_ON = true;
    public static final boolean STATUS_OFF = false;

    private final int key;
    private final long timestamp;
    private final boolean status;
    private final int velocity;

    Note(int key, long timestamp, boolean status, int velocity) {
        this.key = key;
        this.timestamp = timestamp;
        this.status = status;
        this.velocity = velocity;
    }

    public int getKey() {
        return key;
    }

    public String getNoteName() {
        return NOTE_NAMES[key % 12];
    }

    public int getOctave() {
        return (key / 12) - 1;
    }

    public int getVelocity() {
        return velocity;
    }

    public boolean getStatus() {
        return status;
    }

    public long getTimeStamp() {
        return timestamp;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("@").append(getTimeStamp()).append(", ");

        if (status) {
            sb.append("Note on ");
        } else {
            sb.append("Note off ");
        }
        sb.append("at ").append(getNoteName()).append(getOctave()).append(", ");
        sb.append("velocity ").append(getVelocity());

        return sb.toString();
    }
}
