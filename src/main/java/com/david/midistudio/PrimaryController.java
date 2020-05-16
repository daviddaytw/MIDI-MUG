package com.david.midistudio;

import java.io.IOException;
import javafx.fxml.FXML;

public class PrimaryController {

    @FXML
    private void switchToPlay() throws IOException {
        App.setRoot("MIDIChooser");
    }

    @FXML
    private void switchToRecord() throws IOException {
        App.setRoot("record");
    }

    @FXML
    private void switchToSetting() throws IOException {
        App.setRoot("setting");
    }

}
