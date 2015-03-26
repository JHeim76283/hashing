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

import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.logging.Logger;
import jonelo.jacksum.algorithm.AbstractChecksum;

/**
 * Updates a set of AbstractChecksums.
 * Reads the data from a queue.
 * @author Federico Tello Gentile <federicotg@gmail.com>
 */
public class Hasher implements Runnable {

    private final List<AbstractChecksum> digests;
    private final BlockingQueue<DataUnit> queue;
    private int weight = 0;

    public Hasher(BlockingQueue<DataUnit> queue) {
        this.queue = queue;
        this.digests = new ArrayList<>();
    }

    public int getWeight() {
        return weight;
    }

    public void addMessageDigest(HashAlgorithm hash) throws NoSuchAlgorithmException {
        this.weight += hash.getWeight();
        this.digests.add(hash.getChecksum());
    }

    @Override
    public void run() {
        try {
            DataUnit du;
            do {
                du = this.queue.take();
                for (AbstractChecksum md : this.digests) {
                    du.updateMessageDigest(md);
                }
            } while (du.isNotLast());
        } catch (InterruptedException iEx) {
           Logger.getLogger(ConcurrentHasher.class.getName()).throwing("Hasher", "run", iEx);
        }
    }
}
