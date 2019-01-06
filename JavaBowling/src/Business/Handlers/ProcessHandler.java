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
import java.util.ArrayList;

/**
 *
 * @author jonathanbutler
 */
public class ProcessHandler {

    public ProcessHandler() {
    }

    /**
     * this method will handle frame score calculations for the entire line
     *
     * @param lineFrames
     * @return
     */
    public ArrayList<Frame> ProcessData(ArrayList<Frame> lineFrames) {
        try {
            outterLoop:
            for (int i = 0; i < lineFrames.size(); i++) {
                Frame currentFrame = lineFrames.get(i);

                if ((currentFrame.Plays.get(0) != 'X') && (currentFrame.Plays.get(1) != '/')) {
                    currentFrame = CalculateNormal(currentFrame);
                    continue;
                }

                if ((currentFrame.Plays.size() > 1) && (currentFrame.Plays.get(1) == '/')) {
                    currentFrame = CalculateSpare(lineFrames, i);
                    continue;
                }

                if (currentFrame.Plays.get(0) == 'X') {
                    currentFrame = CalculateStrike(lineFrames, i);
                    continue;
                }
            }
        } catch (Exception ex) {
            // would normally log and throw message, not system.exit
            System.out.print("\n\nError: ");
            ex.printStackTrace(System.out);
            //throw ex;
            System.exit(0);
        }
        return lineFrames;
    }

    /**
     * this method will calculate non-bonus frames
     *
     * @param currentFrame
     * @return
     */
    private Frame CalculateNormal(Frame currentFrame) {
        for (int i = 0; i < currentFrame.Plays.size(); i++) {
            if (Character.isDigit(currentFrame.Plays.get(i))) {
                currentFrame.FrameScore += Character.getNumericValue(currentFrame.Plays.get(i));
            }
        }
        return currentFrame;
    }

    /**
     * this method will calculate spare scores and bonus's
     *
     * @param lineFrames
     * @param index
     * @return
     */
    private Frame CalculateSpare(ArrayList<Frame> lineFrames, int index) {
        Frame currentFrame = lineFrames.get(index);
        currentFrame.FrameScore += 10;
        if (currentFrame.Plays.size() > 2) {
            if (Character.isDigit(currentFrame.Plays.get(2))) {
                currentFrame.FrameScore += Character.getNumericValue(currentFrame.Plays.get(2));
            } else if (currentFrame.Plays.get(2) == 'X') {
                currentFrame.FrameScore += 10;
            }
        } else {
            Frame nextFrame = lineFrames.get(index + 1);
            if (Character.isDigit(nextFrame.Plays.get(0))) {
                currentFrame.FrameScore += Character.getNumericValue(nextFrame.Plays.get(0));
            } else if (nextFrame.Plays.get(0) == 'X') {
                currentFrame.FrameScore += 10;
            }
        }
        return currentFrame;
    }

    /**
     * this method will calculate strike scores and bonus's
     *
     * @param lineFrames
     * @param index
     * @return
     */
    private Frame CalculateStrike(ArrayList<Frame> lineFrames, int index) {
        Frame currentFrame = lineFrames.get(index);
        currentFrame.FrameScore += 10;
        if (currentFrame.Plays.size() > 1) {
            if (currentFrame.Plays.get(2) == '/') {
                currentFrame.FrameScore += 10;
            } else {
                // calculate first bonus play
                if (Character.isDigit(currentFrame.Plays.get(1))) {
                    currentFrame.FrameScore += Character.getNumericValue(currentFrame.Plays.get(1));
                } else if (currentFrame.Plays.get(1) == 'X') {
                    currentFrame.FrameScore += 10;
                }
                // calculate second bonus play
                if (Character.isDigit(currentFrame.Plays.get(2))) {
                    currentFrame.FrameScore += Character.getNumericValue(currentFrame.Plays.get(2));
                } else if (currentFrame.Plays.get(2) == 'X') {
                    currentFrame.FrameScore += 10;
                }
            }
        } else {
            Frame nextFrame = lineFrames.get(index + 1);
            if (nextFrame.Plays.get(0) == 'X') {
                currentFrame.FrameScore += 10;
                if (nextFrame.Plays.size() == 1) {
                    Frame followingFrame = lineFrames.get(index + 2);
                    if (Character.isDigit(followingFrame.Plays.get(0))) {
                        currentFrame.FrameScore += Character.getNumericValue(followingFrame.Plays.get(0));
                    } else if (followingFrame.Plays.get(0) == 'X') {
                        currentFrame.FrameScore += 10;
                    }
                } else {
                    if (Character.isDigit(nextFrame.Plays.get(1))) {
                        currentFrame.FrameScore += Character.getNumericValue(nextFrame.Plays.get(1));
                    } else if (nextFrame.Plays.get(1) == 'X') {
                        currentFrame.FrameScore += 10;
                    }
                }

            } else if (nextFrame.Plays.get(1) == '/') {
                currentFrame.FrameScore += 10;
            } else {
                if (Character.isDigit(nextFrame.Plays.get(0))) {
                    currentFrame.FrameScore += Character.getNumericValue(nextFrame.Plays.get(0));
                }
                if (Character.isDigit(nextFrame.Plays.get(1))) {
                    currentFrame.FrameScore += Character.getNumericValue(nextFrame.Plays.get(1));
                }
            }
        }
        return currentFrame;
    }
}
