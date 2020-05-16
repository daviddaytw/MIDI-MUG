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
package com.david.midistudio;

import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.Initializable;
import javafx.stage.FileChooser;

/**
 * FXML Controller class
 *
 * @author david
 */
public class PlayController implements Initializable {

    private File midi;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Open a MIDI File to Play!");
        FileChooser.ExtensionFilter extension = new FileChooser.ExtensionFilter("MIDI File (*.midi)", "*.midi");
        fileChooser.getExtensionFilters().add(extension);
        this.midi = fileChooser.showOpenDialog(App.stage);
    }

}
