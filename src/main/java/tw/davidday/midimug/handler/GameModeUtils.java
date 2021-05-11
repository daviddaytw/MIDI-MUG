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

import tw.davidday.midimug.gamemode.AbstractModeController;
import tw.davidday.midimug.gamemode.FluidModeController;
import javax.sound.midi.MidiUnavailableException;

/**
 *
 * @author david
 */
public class GameModeUtils {

    private static final AbstractModeController DEFAULT = new FluidModeController();
    private static AbstractModeController controller = DEFAULT;

    public static void setGameMode(AbstractModeController mode) throws MidiUnavailableException {
        controller = mode;
        MidiDevices.setGameController(controller);
    }

    public static AbstractModeController getGameMode() {
        return controller;
    }
}
