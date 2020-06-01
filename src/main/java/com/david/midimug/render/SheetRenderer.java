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

import com.david.midimug.gamemode.AbstractModeController;
import com.david.midimug.handler.Channel;
import com.david.midimug.handler.GameModeUtils;
import com.david.midimug.handler.Note;
import com.david.midimug.handler.Sheet;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;

/**
 *
 * @author david
 */
public class SheetRenderer {

    private static final long TIME_PAD = 16;
    private static final double HUE_SHIFT = -85;
    private static long tempo = 120;

    public static Timeline renderBarSheet(Pane target, Sheet sheet) {
        // reset sheet
        target.getChildren().clear();

        Rectangle clip = new Rectangle();
        clip.setWidth(target.getWidth());
        clip.setHeight(target.getHeight());
        target.setClip(clip);
        Color color = Color.DODGERBLUE;

        final double real_pad = TIME_PAD * target.getHeight();

        Timeline timeline = new Timeline();
        AbstractModeController controller = GameModeUtils.getGameMode();

        for (Channel channel : sheet.getChannels()) {
            for (Note i : channel.getNotes()) {
                Rectangle bar = new Rectangle();
                bar.setFill(color);

                bar.setWidth(KeyboardRenderer.getPianoWhiteKeyWidth());
                bar.setHeight(computeTick(i.getLength(), sheet) / 10);
                bar.setArcHeight(10);
                bar.setArcWidth(bar.getWidth() / 3);
                bar.setLayoutX(KeyboardRenderer.getPianoKeyPositionX(i.getKey()));
                bar.setLayoutY(-bar.getHeight());

                long start_time = computeTick(i.getTimeStamp(), sheet);
                long show_time = start_time - Math.round(target.getHeight() * computeTick(i.getLength(), sheet) / bar.getHeight());
                long end_time = computeTick(i.getTimeStamp() + i.getLength(), sheet);

                KeyFrame show = controller.onNoteShow(Duration.millis(show_time + real_pad), target, bar, i);
                KeyFrame start = controller.onNoteStart(Duration.millis(start_time + real_pad), target, bar, i);
                KeyFrame end = controller.onNoteEnd(Duration.millis(end_time + real_pad), target, bar, i);

                timeline.getKeyFrames().addAll(show, start, end);

                target.getChildren().add(bar);
            }

            color = color.deriveColor(HUE_SHIFT, 1, 1, 1);
        }

        return timeline;
    }

    public static long computeTick(long tick, Sheet sheet) {
        return tick * 60000 / sheet.getResolution() / tempo;
    }
}
