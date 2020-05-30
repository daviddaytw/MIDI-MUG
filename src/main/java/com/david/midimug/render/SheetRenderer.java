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

import com.david.midimug.handler.Note;
import com.david.midimug.handler.Sheet;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

/**
 *
 * @author david
 */
public class SheetRenderer {

    public static void renderBarSheet(Pane target, Sheet sheet) {
        // reset sheet
        target.getChildren().clear();

        Rectangle clip = new Rectangle();
        clip.setWidth(target.getWidth());
        clip.setHeight(target.getHeight());
        target.setClip(clip);

        for (Note i : sheet.getNotes()) {
            Rectangle bar = new Rectangle();
            bar.setFill(Color.DODGERBLUE);

            bar.setWidth(KeyboardRenderer.getPianoWhiteKeyWidth());
            bar.setHeight(computeBarY(i.getLength(), sheet));
            bar.setLayoutX(KeyboardRenderer.getPianoKeyPositionX(i.getKey()));
            bar.setLayoutY(-computeBarY(i.getTimeStamp(), sheet));
            target.getChildren().add(bar);
        }
    }

    private static double computeBarY(long tick, Sheet sheet) {
        return tick / (sheet.getResolution());
    }
}
