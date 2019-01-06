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
package Business.Utilities;

import Business.DataObjects.Frame;
import java.util.ArrayList;

/**
 *
 * @author jonathanbutler
 */
public class Converters {

    public Converters() {
    }

    /**
     * *
     * This method converts message data into a list of frames played
     *
     * @param message
     * @return
     */
    public ArrayList<Frame> ConvertData(String message) {
        ArrayList<Frame> lineFrames = new ArrayList<Frame>();
        ArrayList<Character> currentItem = new ArrayList<Character>();
        try {
            // get rid of meaningless data
            message = message.replace(" ", "");

            for (int i = 0; i < message.length(); i++) {

                if ((lineFrames.size() == 9) && ((message.charAt(i) == '/') || (message.charAt(i) == 'X'))) {
                    currentItem.add(message.charAt(i));
                    currentItem.add(message.charAt(i + 1));
                    if (message.charAt(i) == 'X') {
                        currentItem.add(message.charAt(i + 2));
                    }
                    lineFrames.add(new Frame(currentItem));
                    break;
                }

                currentItem.add(message.charAt(i));
                if ((currentItem.size() == 2) || (message.charAt(i) == 'X')) {
                    lineFrames.add(new Frame(currentItem));
                    currentItem = new ArrayList<Character>();
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
}
