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
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;
import javafx.scene.shape.Rectangle;
import javafx.stage.FileChooser;
import javafx.stage.Screen;

/**
 * FXML Controller class
 *
 * @author david
 */
public class PlayController implements Initializable {

    @FXML
    private StackPane keyboard;
    @FXML
    private StackPane timeline;

    private Button[] whiteKey, blackKey;

    private final int whiteCt = 52, blackCt = 36;

    private File midi;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Open a MIDI File to Play!");
        FileChooser.ExtensionFilter extension = new FileChooser.ExtensionFilter("MIDI File (*.mid)", "*.mid");
        fileChooser.getExtensionFilters().add(extension);
        this.midi = fileChooser.showOpenDialog(App.stage);
        App.stage.setMaximized(true);

        renderKeyboard();
    }

    private void renderKeyboard() {
        keyboard.setAlignment(Pos.TOP_LEFT);
        ObservableList nodeList = keyboard.getChildren();

        whiteKey = new Button[whiteCt];
        blackKey = new Button[blackCt];

        final double whiteKeyHeight = keyboard.getPrefHeight();
        final double whiteKeyWidth = Screen.getPrimary().getBounds().getWidth() / whiteCt;
        for (int i = 0; i < whiteCt; i++) {
            Button key = whiteKey[i] = new Button();
            key.setPrefWidth(whiteKeyWidth);
            key.setPrefHeight(whiteKeyHeight);
            key.setTranslateX(i * whiteKeyWidth);
            nodeList.add(key);
        }

        final double blackKeyHeight = whiteKeyHeight * 0.6;
        final double blackKeyWidth = whiteKeyWidth * 0.7;
        for (int i = 0; i < blackCt; i++) {
            Button key = blackKey[i] = new Button();
            key.setStyle("-fx-background-color: #000000;");
            key.setPrefWidth(blackKeyWidth);
            key.setPrefHeight(blackKeyHeight);
            double x = i * whiteKeyWidth + whiteKeyWidth / 2;
            x += (i + 4) / 5 * whiteKeyWidth + (i + 2) / 5 * whiteKeyWidth;
            key.setTranslateX(x);
            nodeList.add(key);
        }
    }
}
