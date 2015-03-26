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
import java.util.concurrent.BlockingQueue;
import java.util.logging.Logger;
import jonelo.jacksum.algorithm.AbstractChecksum;

/**
 * Reads the file and puts its data in several queues for processing by Hashers.
 * There's one queue per processor.
 * 
 * @author Federico Tello Gentile <federicotg@gmail.com>
 */
public class DataReader implements Runnable {

    private final Collection<BlockingQueue<DataUnit>> queues;
    private final File file;

    public DataReader(File file, Collection<BlockingQueue<DataUnit>> queues) {
        this.queues = queues;
        this.file = file;

    }

    private void enqueue(DataUnit du) throws InterruptedException {
        for (BlockingQueue<DataUnit> queue : this.queues) {
            queue.put(du);
        }
    }

    @Override
    public void run() {
        InputStream is = null;
        try {
            is = new BufferedInputStream(new FileInputStream(this.file));

            DataUnit du = new DataUnit(AbstractChecksum.BUFFERSIZE);
            int read = du.readData(is);
            while(read > 0){
                enqueue(du);
                du = new DataUnit(AbstractChecksum.BUFFERSIZE);
                read = du.readData(is);
            }
            if(read == -1){
                // enqueue last one in case the last one is zero bytes
                du = new DataUnit(1);
                du.setLength(0);
                enqueue(du);
            }

        } catch (IOException | InterruptedException ex) {
            Logger.getLogger(ConcurrentHasher.class.getName()).throwing("DataReader", "run", ex);
        } finally {
            if (is != null) {
                try {
                    is.close();
                } catch (IOException ex) {
                    Logger.getLogger(ConcurrentHasher.class.getName()).throwing("DataReader", "run", ex);
                }
            }
        }
    }
}
