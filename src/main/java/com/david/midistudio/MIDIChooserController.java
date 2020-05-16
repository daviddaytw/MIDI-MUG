/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.david.midistudio;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextField;
import javafx.stage.FileChooser;

/**
 * FXML Controller class
 *
 * @author david
 */
public class MIDIChooserController implements Initializable {

    private File midi;

    @FXML
    private TextField filePath;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }

    @FXML
    private void browseFiles() throws IOException {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Open MIDI File");
        FileChooser.ExtensionFilter extension = new FileChooser.ExtensionFilter("MIDI File (*.midi)", "*.midi");
        fileChooser.getExtensionFilters().add(extension);
        this.midi = fileChooser.showOpenDialog(App.stage);
        if (this.midi != null) {
            this.filePath.setText(this.midi.getAbsolutePath());
        }
    }
}
