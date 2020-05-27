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

import com.david.midimug.App;
import java.io.File;
import javafx.stage.FileChooser;

/**
 *
 * @author david
 */
public class LoadFileRenderer {

    public static File renderMidiChooser() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Open a MIDI File to Play!");
        FileChooser.ExtensionFilter extension = new FileChooser.ExtensionFilter("MIDI File (*.mid)", "*.mid");
        fileChooser.getExtensionFilters().add(extension);
        return fileChooser.showOpenDialog(App.getAppStage());
    }
}
