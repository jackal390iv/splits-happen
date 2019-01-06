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
package Service;

import Business.Handlers.MainHandler;

/**
 *
 * @author JonathanButler
 */
public class MainService {

    /**
     * The Service level is where program operations begin and all set-up
     * operations take place before business logic is implemented.
     *
     * To elaborate, the service would begin here and..
     *
     * - Multi-threading would be setup, with the number of threads designated
     * by an outside resource, so a properties file in Java (app.config in c#).
     * Each threaded task will then call some generic listener code, so
     * listening to an esb queue, sqs queu, s3 bucket, ect. and then pass
     * retrieved message data to the MainHandler processing within the business
     * logic. MainHandler being a class that is tied to a known interface within
     * the generic listener code, so the listener code and send to handler logic
     * can be the same across multiple projects without much issue.
     *
     * - Any other external resources would also be loaded in on this level, so
     * everything else from properties class, such as AWS bucket addresses and
     * so forth
     *
     * - Also, logging would be setup on the service level, some kind of JSon
     * logging that can be picked up by some server tool
     *
     * Although I'm certain you have your own formating that I will adjust to;
     * this is just what I know/am use to
     *
     * @param args the command line arguments
     */
    public static void main(String[] args) {

        // assumming we are recieving the messages with spaces included..
        StringBuilder message = new StringBuilder();
        for (String item : args) {
            if (!message.toString().isEmpty()) {
                message.append(" ");
            }
            message.append(item);
        }

        IMainHandler handler = new MainHandler();

        // remove comment to use arg input
        
        handler.HandleMessage(message.toString());
        handler.HandleMessage("XXXXXXXXXXXX");
        handler.HandleMessage("9-9-9-9-9-9-9-9-9-9-");
        handler.HandleMessage("5/5/5/5/5/5/5/5/5/5/5");
        handler.HandleMessage("X7/9-X-88/-6XXX81");
        handler.HandleMessage("9-9-729-9-9-9-9-9--9");
    }
}
