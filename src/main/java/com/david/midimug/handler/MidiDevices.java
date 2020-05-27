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
import javax.sound.midi.MidiDevice;
import javax.sound.midi.MidiSystem;
import javax.sound.midi.MidiUnavailableException;
import javax.sound.midi.Sequencer;
import javax.sound.midi.Synthesizer;

/**
 *
 * @author david
 */
public class MidiDevices {

    private static MidiDevice[] devicesList;

    public static MidiDevice[] getDevices() {
        ArrayList<MidiDevice> devices = new ArrayList<MidiDevice>();
        MidiDevice.Info[] infos = MidiSystem.getMidiDeviceInfo();

        for (MidiDevice.Info i : infos) {
            try {
                MidiDevice device = MidiSystem.getMidiDevice(i);

                // specify hardware MIDI port
                if (!(device instanceof Sequencer) && !(device instanceof Synthesizer)) {
                    devices.add(device);
                }
            } catch (MidiUnavailableException ex) {
                ex.printStackTrace();
            }
        }

        devicesList = new MidiDevice[devices.size()];
        devicesList = devices.toArray(devicesList);
        return devicesList;
    }

    public static String[] getDevicesName() {
        MidiDevice[] list = getDevices();
        String[] deviceName = new String[list.length];
        for (int i = 0; i < list.length; i++) {
            deviceName[i] = list[i].getDeviceInfo().getName();
        }
        return deviceName;
    }
}
