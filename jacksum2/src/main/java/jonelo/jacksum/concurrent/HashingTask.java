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

import java.nio.file.Path;
import java.security.NoSuchAlgorithmException;
import java.util.Map;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.TimeUnit;
import java.util.logging.Logger;
import jonelo.jacksum.algorithm.AbstractChecksum;
import jonelo.jacksum.algorithm.Algorithm;

/**
 *
 * @author Federico Tello Gentile <federicotg@gmail.com>
 */
public class HashingTask implements Runnable {

    private final Path filename;
    private final Algorithm algorithm;
    private final Map<Pair<Path, Algorithm>, byte[]> resultHolder;
    private final BlockingQueue<DataBlock> dataBlockSource;
    private AbstractChecksum checksum;
    private String crcSpec;
    private boolean alternate;

    public HashingTask(
            Path filename,
            Algorithm algorithm,
            boolean alternate,
            String crcSpec,
            BlockingQueue<DataBlock> dataBlockSource,
            Map<Pair<Path, Algorithm>, byte[]> resultHolder) {
        this.filename = filename;
        this.algorithm = algorithm;
        this.resultHolder = resultHolder;
        this.dataBlockSource = dataBlockSource;
        this.crcSpec = crcSpec;
        this.alternate = alternate;
    }

    public HashingTask(
            Path filename,
            Algorithm algorithm,
            BlockingQueue<DataBlock> dataBlockSource,
            Map<Pair<Path, Algorithm>, byte[]> resultHolder) {
        this.filename = filename;
        this.algorithm = algorithm;
        this.resultHolder = resultHolder;
        this.dataBlockSource = dataBlockSource;
    }

    @Override
    public void run() {

        //log("HashingTask starts. "+this.filename+" "+this.algorithm.getCanonicalName());
        try {
            this.checksum = crcSpec == null
                    ? this.algorithm.getChecksumInstance(this.alternate)
                    : this.algorithm.getChecksumInstance(this.crcSpec, this.alternate);
            //JacksumAPI.getChecksumInstance(this.algorithm.getCanonicalName());
            //DataBlock data = this.dataBlockSource.take();
            DataBlock data = this.dataBlockSource.poll(1, TimeUnit.MINUTES);
            while (data != null && data.isNotLast()) {
                //   log("processing one block "+this.filename+" "+this.algorithm.getCanonicalName());
                data.updateChecksum(this.checksum);
                //data = this.dataBlockSource.take();
                data = this.dataBlockSource.poll(1, TimeUnit.MINUTES);
            }

            // log("Saving one result " +this.filename+" "+this.algorithm.getCanonicalName());
            this.resultHolder.put(new Pair<>(this.filename, this.algorithm), this.checksum.getByteArray());

        } catch (InterruptedException | NoSuchAlgorithmException iEx) {
            Logger.getLogger(HashingTask.class.getName()).throwing("HashingTask", "run", iEx);
        }
        // log("Hashing finished " +this.filename+" "+this.algorithm.getCanonicalName()+" "+I.incrementAndGet());
    }

    private void log(String msg) {
        System.out.println(msg);
    }

}
