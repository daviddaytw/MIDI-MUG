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

import com.david.midimug.gamemode.AbstractModeController;
import java.util.ArrayList;
import javax.sound.midi.MidiDevice;
import javax.sound.midi.MidiSystem;
import javax.sound.midi.MidiUnavailableException;
import javax.sound.midi.Sequencer;
import javax.sound.midi.Synthesizer;
import javax.sound.midi.Transmitter;

/**
 *
 * @author david
 */
public class MidiDevices {

    private static MidiDevice selected = null;

    public static ArrayList<MidiDevice> getDevices() {
        ArrayList<MidiDevice> devicesList = new ArrayList<>();
        MidiDevice.Info[] infos = MidiSystem.getMidiDeviceInfo();

        for (MidiDevice.Info i : infos) {
            try {
                MidiDevice device = MidiSystem.getMidiDevice(i);

                // specify hardware MIDI port
                if (!(device instanceof Sequencer) && !(device instanceof Synthesizer)) {
                    devicesList.add(device);
                }
            } catch (MidiUnavailableException ex) {
                ex.printStackTrace();
            }
        }

        return devicesList;
    }

    public static void selectDevice(MidiDevice device) throws MidiUnavailableException {
        selected = device;
        if (selected.isOpen()) {
            selected.close();
        }
        selected.open();
    }

    public static void setGameController(AbstractModeController controller) throws MidiUnavailableException {
        if (selected == null) {
            System.err.println("No device selected!");
            return;
        }
        Transmitter trans = selected.getTransmitter();
        trans.setReceiver(controller);
    }

    public static void closeDevice() {
        selected.close();
    }
}
