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
import com.david.midimug.gamemode.AbstractModeController;
import com.david.midimug.gamemode.FluidModeController;
import com.david.midimug.gamemode.ShowModeController;
import java.io.File;
import java.util.NoSuchElementException;
import java.util.Optional;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ChoiceDialog;
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

    public static AbstractModeController renderModeChooser() {

        final ChoiceDialog<String> choiceDialog = new ChoiceDialog("Fluid", "Fluid", "Show");
        choiceDialog.setTitle("Game Mode");
        choiceDialog.setHeaderText("Please select game mode");
        final Optional<String> opt = choiceDialog.showAndWait();
        AbstractModeController controller = null;
        try {
            String rtn = opt.get();
            switch (rtn) {
                case "Fluid": {
                    controller = new FluidModeController();
                    break;
                }
                case "Show": {
                    controller = new ShowModeController();
                    break;
                }
            }
        } catch (final NoSuchElementException ex) {
            final Alert alert = new Alert(AlertType.ERROR);
            alert.setContentText(ex.getLocalizedMessage());
            alert.showAndWait();
        }
        return controller;
    }
}
