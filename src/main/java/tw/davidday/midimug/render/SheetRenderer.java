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
package tw.davidday.midimug.render;

import javafx.animation.Timeline;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import tw.davidday.midimug.gamemode.AbstractModeController;
import tw.davidday.midimug.handler.Channel;
import tw.davidday.midimug.handler.GameModeUtils;
import tw.davidday.midimug.handler.Note;
import tw.davidday.midimug.handler.Sheet;

/**
 *
 * @author david
 */
public class SheetRenderer {

    private static final double HUE_SHIFT = -85;
    private static final long TEMPO = 120;

    public static void renderBarSheet(Pane target, Sheet sheet, Timeline timeline) {
        // reset sheet
        target.getChildren().clear();

        Rectangle clip = new Rectangle();
        clip.setWidth(target.getWidth());
        clip.setHeight(target.getHeight());
        target.setClip(clip);
        Color white_key_color = Color.DODGERBLUE;

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

                controller.setupNote(
                        target,
                        bar,
                        timeline,
                        i,
                        show_time,
                        start_time,
                        end_time
                );

                target.getChildren().add(bar);
            }

            white_key_color = white_key_color.deriveColor(HUE_SHIFT, 1, 1, 1);
        }
    }

    private static double computeTick(long tick, Sheet sheet) {
        return tick * 60000.0 / sheet.getResolution() / TEMPO;
    }
}
