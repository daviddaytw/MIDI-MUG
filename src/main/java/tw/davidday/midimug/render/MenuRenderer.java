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
package tw.davidday.midimug.render;

import tw.davidday.midimug.handler.GameModeUtils;
import tw.davidday.midimug.handler.MidiDevices;
import tw.davidday.midimug.handler.MidiInstruments;
import java.util.ArrayList;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuItem;
import javafx.scene.control.RadioMenuItem;
import javax.sound.midi.Instrument;
import javax.sound.midi.MidiDevice;
import javax.sound.midi.MidiUnavailableException;

/**
 *
 * @author david
 */
public class MenuRenderer {

    public static void renderInstrumentsMenu(Menu target) {
        try {
            Instrument[] instruments = MidiInstruments.getInstruments();
            for (int i = 0; i < instruments.length; i++) {
                Instrument instr = instruments[i];
                RadioMenuItem item = new RadioMenuItem(instr.getName());
                if (i == MidiInstruments.DEFAULT_INSTRUMENT) {
                    MidiInstruments.selectInstrument(instr);
                }
                item.setOnAction((ActionEvent t) -> {
                    MidiInstruments.selectInstrument(instr);
                });
                target.getItems().add(item);
            }
        } catch (MidiUnavailableException ex) {
            final Alert alert = new Alert(AlertType.ERROR);
            alert.setContentText(ex.getLocalizedMessage());
            alert.showAndWait();
        }
    }

    public static void renderDevicesMenu(Menu target) {
        ObservableList<MenuItem> nodeList = target.getItems();
        nodeList.remove(2, nodeList.size());
        final ArrayList<MidiDevice> devices = MidiDevices.getDevices();
        devices.stream().map((MidiDevice device) -> {
            RadioMenuItem item = new RadioMenuItem(device.getDeviceInfo().getName());
            item.setOnAction((ActionEvent t) -> {
                try {
                    MidiDevices.selectDevice(device);
                    MidiDevices.setGameController(GameModeUtils.getGameMode());
                } catch (MidiUnavailableException ex) {
                    final Alert alert = new Alert(AlertType.ERROR);
                    alert.setContentText(ex.getLocalizedMessage());
                    alert.showAndWait();
                }
            });
            return item;
        }).forEachOrdered((item) -> {
            nodeList.add(item);
        });
    }
}
