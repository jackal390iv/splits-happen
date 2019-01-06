/*
 * Copyright (C) 2019 jonathanbutler
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public License
 * as published by the Free Software Foundation; either version 2
 * of the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place - Suite 330, Boston, MA  02111-1307, USA.
 */
package Business.Handlers;

import Business.DataObjects.Frame;
import Business.Utilities.Converters;
import Service.IMainHandler;
import java.util.ArrayList;

/**
 * This class handles message processing and returning data, if needed..
 * The processing could have been done when tearing apart the string
 *
 * @author JonathanButler
 */
public class MainHandler implements IMainHandler {

    public void HandleMessage(String message) {
        try {
            System.out.print(String.format("%nOriginal Message: %s", message));

            // dependency injection could be used here instead of object creation..
            Converters converters = new Converters();
            ProcessHandler processHandler = new ProcessHandler();

            ArrayList<Frame> lineFrames = converters.ConvertData(message);

            System.out.print(String.format("%nLine Frames [%d]: ", lineFrames.size(), lineFrames.toString()));
            for (Frame frame : lineFrames) {
                System.out.print(frame.Plays.toString());
            }

            lineFrames = processHandler.ProcessData(lineFrames);
            System.out.print("\nFrame Scores: ");
            int totalScore = 0;
            for (int i = 0; i < lineFrames.size(); i++) {
                System.out.print(String.format("[%d:%d]", (i + 1), lineFrames.get(i).FrameScore));
                totalScore += lineFrames.get(i).FrameScore;
            }

            System.out.print(String.format("%nTotal Score: %d %n", totalScore));
        } catch (Exception ex) {
             //here error checking would be in place to deside if the message needs to be rerun, taken off the queue, or otherwise 
        }
    }
}
