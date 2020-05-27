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
package com.david.midimug.render;

import com.david.midimug.handler.MidiDevices;
import com.david.midimug.handler.MidiInstruments;
import javafx.collections.ObservableList;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuItem;
import javafx.scene.control.RadioMenuItem;
import javax.sound.midi.MidiUnavailableException;

/**
 *
 * @author david
 */
public class MenuRenderer {

    public static void renderInstrumentsMenu(Menu target) {
        try {
            int defaultId = MidiInstruments.DEFAULT_INSTRUMENT;
            String[] instruments = MidiInstruments.getInstrumentsName();
            for (int i = 0; i < instruments.length; i++) {
                RadioMenuItem item = new RadioMenuItem(instruments[i]);
                if (i == defaultId) {
                    item.setSelected(true);
                }
                target.getItems().add(item);
            }
        } catch (MidiUnavailableException ex) {
            ex.printStackTrace();
        }
    }

    public static void renderDevicesMenu(Menu target) {
        ObservableList<MenuItem> nodeList = target.getItems();
        nodeList.remove(2, nodeList.size());
        String[] devices = MidiDevices.getDevicesName();
        for (String i : devices) {
            RadioMenuItem item = new RadioMenuItem(i);
            nodeList.add(item);
        }
    }
}
