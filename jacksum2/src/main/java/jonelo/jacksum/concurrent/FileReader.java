/*
 * Copyright (C) 2015 Federico Tello Gentile <federicotg@gmail.com>
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
package jonelo.jacksum.concurrent;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Collection;
import java.util.Map;
import java.util.Queue;
import java.util.concurrent.BlockingQueue;
import java.util.logging.Logger;
import jonelo.jacksum.algorithm.Algorithm;

/**
 *
 * @author Federico Tello Gentile <federicotg@gmail.com>
 */
public class FileReader implements Runnable {

    private Queue<String> filenameSource;
    private Map<Pair<String, Algorithm>, BlockingQueue<DataBlock>> dataQueueMap;
    private Collection<Algorithm> algorithms;

    public FileReader(
            Queue<String> filenameSource,
            Collection<Algorithm> algorithms,
            Map<Pair<String, Algorithm>, BlockingQueue<DataBlock>> dataQueueMap) {
        this.dataQueueMap = dataQueueMap;
        this.filenameSource = filenameSource;
        this.algorithms = algorithms;
    }

    @Override
    public void run() {
        //log("FileReader starts...");
        String filename = this.filenameSource.poll();
        while (filename != null) {

            try (InputStream is = new BufferedInputStream(new FileInputStream(new File(filename)))) {

                byte[] bytes = new byte[8192];
                int read;
                while ((read = is.read(bytes)) > 0) {
                    //log("Read a block from "+filename);
                    DataBlock data = new DataBlock(bytes, read);
                    for (Algorithm algorithm : this.algorithms) {
                        this.dataQueueMap.get(new Pair<>(filename, algorithm)).put(data);
                    }

                    bytes = new byte[8192];
                }
                // last block
              //  log("Last block from "+filename);
                for (Algorithm algorithm : this.algorithms) {
                    this.dataQueueMap.get(new Pair<>(filename, algorithm)).put(new DataBlock(null, -1));
                }
            } catch (IOException | InterruptedException ex) {
                Logger.getLogger(FileReader.class.getName()).throwing("FileReader", "run", ex);
            }

            filename = this.filenameSource.poll();
        }
        
     //   log("FileReader finished.");
    }

    private void log(String msg){
        System.out.println(msg);
    }
    
}
