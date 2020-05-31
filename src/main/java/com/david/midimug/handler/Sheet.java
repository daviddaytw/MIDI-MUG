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

import java.util.ArrayList;

/**
 *
 * @author david
 */
public class Sheet {

    private final int resolution;
    private Note[] notes;
    private long length;

    Sheet(ArrayList<Note> noteList, int resolution, long length) {
        notes = new Note[noteList.size()];
        notes = noteList.toArray(notes);
        this.resolution = resolution;
        if (resolution == -1) {
            System.err.println("Bad resolution!");
        }
        this.length = length;
    }

    public int getResolution() {
        return resolution;
    }

    public Note[] getNotes() {
        return notes;
    }

    public long getLength() {
        return length;
    }
}
