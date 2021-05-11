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

/**
 *
 * @author david
 */
public class Sheet {

    private final int resolution;
    private final Channel[] channels;
    private final long length;

    Sheet(Channel[] channels, int resolution, long length) {
        this.channels = channels;
        this.resolution = resolution;
        if (resolution == -1) {
            System.err.println("Bad resolution!");
        }
        this.length = length;
    }

    public int getResolution() {
        return resolution;
    }

    public Channel[] getChannels() {
        return channels;
    }

    public long getLength() {
        return length;
    }
}
