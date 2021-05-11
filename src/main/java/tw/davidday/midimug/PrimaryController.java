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
package tw.davidday.midimug;

import tw.davidday.midimug.gamemode.AbstractModeController;
import tw.davidday.midimug.handler.GameModeUtils;
import tw.davidday.midimug.handler.SheetUtils;
import tw.davidday.midimug.render.KeyboardRenderer;
import tw.davidday.midimug.render.LoadFileRenderer;
import tw.davidday.midimug.render.MenuRenderer;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Menu;
import javafx.scene.layout.Pane;
import javax.sound.midi.InvalidMidiDataException;
import javax.sound.midi.MidiUnavailableException;

/**
 * FXML Controller class
 *
 * @author david
 */
public class PrimaryController implements Initializable {

    @FXML
    private Menu instrumentsMenu;
    @FXML
    private Menu devicesMenu;
    @FXML
    private Pane keyboard;
    @FXML
    private Pane sheet;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        KeyboardRenderer.renderPianoKeys(keyboard);
        MenuRenderer.renderInstrumentsMenu(instrumentsMenu);
        MenuRenderer.renderDevicesMenu(devicesMenu);
    }

    @FXML
    public void openFile() {
        try {
            SheetUtils.pause();
            File source = LoadFileRenderer.renderMidiChooser();
            AbstractModeController controller = LoadFileRenderer.renderModeChooser();
            GameModeUtils.setGameMode(controller);
            SheetUtils.setupSheet(sheet, source);
            SheetUtils.play();
        } catch (InvalidMidiDataException | IOException | MidiUnavailableException ex) {
            final Alert alert = new Alert(AlertType.ERROR);
            alert.setContentText(ex.getLocalizedMessage());
            alert.showAndWait();
        }
    }

    @FXML
    public void refreshDevices() {
        MenuRenderer.renderDevicesMenu(devicesMenu);
    }

    @FXML
    public void exit() {
        Platform.exit();
    }
}
