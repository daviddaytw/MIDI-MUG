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
import java.util.Timer;
import java.util.TimerTask;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.util.Duration;

/**
 *
 * @author david
 */
public class SheetRenderer {

    private static final double HUE_SHIFT = -85;
    private static long tempo = 120;

    private static final Text combo = new Text();
    private static final Font COMBO_FONT = new Font(64);
    private static Timer timer = new Timer();

    private static Pane target;

    public static Timeline renderBarSheet(Pane target, Sheet sheet) {
        SheetRenderer.target = target;

        // reset sheet
        target.getChildren().clear();
        target.getChildren().add(combo);

        Rectangle clip = new Rectangle();
        clip.setWidth(target.getWidth());
        clip.setHeight(target.getHeight());
        target.setClip(clip);
        Color white_key_color = Color.DODGERBLUE;

        Timeline timeline = new Timeline();
        AbstractModeController controller = GameModeUtils.getGameMode();

        for (Channel channel : sheet.getChannels()) {
            Color black_key_color = white_key_color.deriveColor(0, 1, 0.5, 1);
            for (Note i : channel.getNotes()) {
                Rectangle bar = new Rectangle();
                if (i.isBlackKey()) {
                    bar.setFill(black_key_color);
                    bar.setWidth(KeyboardRenderer.getPianoBlackKeyWidth());
                } else {
                    bar.setFill(white_key_color);
                    bar.setWidth(KeyboardRenderer.getPianoWhiteKeyWidth());
                }

                bar.setHeight(computeTick(i.getLength(), sheet) / 10);
                bar.setArcHeight(10);
                bar.setArcWidth(bar.getWidth() / 3);
                bar.setLayoutX(KeyboardRenderer.getPianoKeyPositionX(i.getKey()));
                bar.setLayoutY(-bar.getHeight());

                double shift = target.getHeight() / bar.getHeight() * computeTick(i.getLength(), sheet);
                double show_time = computeTick(i.getTimeStamp(), sheet);
                double start_time = show_time + shift;
                double end_time = computeTick(i.getTimeStamp() + i.getLength(), sheet) + shift;

                KeyFrame show = controller.onNoteShow(Duration.millis(show_time), target, bar, i);
                KeyFrame start = controller.onNoteStart(Duration.millis(start_time), target, bar, i);
                KeyFrame end = controller.onNoteEnd(Duration.millis(end_time), target, bar, i);

                timeline.getKeyFrames().addAll(show, start, end);

                target.getChildren().add(bar);
            }

            white_key_color = white_key_color.deriveColor(HUE_SHIFT, 1, 1, 1);
        }

        return timeline;
    }

    private static double computeTick(long tick, Sheet sheet) {
        return tick * 60000.0 / sheet.getResolution() / tempo;
    }

    public static void renderCombo(String text, Color color) {
        combo.setText(text);
        combo.setFont(COMBO_FONT);
        combo.setFill(color);

        double x = (target.getWidth() - combo.getLayoutBounds().getWidth()) / 2;
        double y = (target.getHeight() - combo.getLayoutBounds().getHeight()) / 2;
        combo.setLayoutX(x);
        combo.setLayoutY(y);
        combo.setVisible(true);

        TimerTask end = new TimerTask() {
            @Override
            public void run() {
                combo.setVisible(false);
            }

        };
        timer.cancel();
        timer = new Timer();
        timer.schedule(end, 500);
    }
}
