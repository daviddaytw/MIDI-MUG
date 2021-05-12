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

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.Timer;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.ToolBar;
import javafx.scene.layout.Pane;
import javax.sound.midi.InvalidMidiDataException;
import javax.sound.midi.MidiUnavailableException;
import tw.davidday.midimug.gamemode.AbstractModeController;
import tw.davidday.midimug.handler.GameModeUtils;
import tw.davidday.midimug.handler.SheetUtils;
import tw.davidday.midimug.render.KeyboardRenderer;
import tw.davidday.midimug.render.LoadFileRenderer;
import tw.davidday.midimug.render.MenuRenderer;
import tw.davidday.midimug.render.ProgressRenderer;

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
    private ToolBar toolbar;
    @FXML
    private Label infoLabel;
    @FXML
    private ProgressBar progress;
    private Timer progressTimer;

    @FXML
    private Pane sheet;
    @FXML
    private Pane keyboard;

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

            progressTimer = new Timer();
            progressTimer.schedule(new ProgressRenderer(progress), 0, 50);

            toolbar.setDisable(false);
        } catch (InvalidMidiDataException | IOException | MidiUnavailableException ex) {
            final Alert alert = new Alert(AlertType.ERROR);
            alert.setContentText(ex.getLocalizedMessage());
            alert.showAndWait();
        }
    }

    @FXML
    public void play() {
        SheetUtils.play();
    }

    @FXML
    public void pause() {
        SheetUtils.pause();
    }

    @FXML
    public void replay() {
        SheetUtils.replay();
    }

    @FXML
    public void speedUp() {
        SheetUtils.setRate(SheetUtils.getRate() + 0.25);
        updateInfo();
    }

    @FXML
    public void slowDown() {
        SheetUtils.setRate(Math.max(SheetUtils.getRate() - 0.25, 0.25));
        updateInfo();
    }

    @FXML
    public void forward() {
        SheetUtils.shift(1);
    }

    @FXML
    public void backward() {
        SheetUtils.shift(-1);
    }

    private void updateInfo() {
        infoLabel.setText("Current Speed: " + SheetUtils.getRate() + "x");
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
