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
package org.fede.jacksum2.test;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.util.logging.Logger;
import jonelo.jacksum.JacksumAPI;
import jonelo.jacksum.algorithm.AbstractChecksum;

/**
 *
 * @author fede
 */
public class BenchmarkUtil {
    
    public static String getFileHashValue(AbstractChecksum chsum, String fileName) {
        try {
            chsum.reset();
            chsum.setEncoding(AbstractChecksum.HEX);
            chsum.readFile(fileName);

            return chsum.getFormattedValue();

        } catch (IOException ex) {
            Logger.getLogger(MyBenchmark.class.getName()).throwing(MyBenchmark.class.getName(), "getFileHashValue", ex);
        }
        return null;
    }

    public static AbstractChecksum getChecksum(String algorithmName) {
        return getChecksum(algorithmName, false);
    }

    public static AbstractChecksum getChecksum(String algorithmName, boolean alternate) {

        try {
            return JacksumAPI.getChecksumInstance(algorithmName, alternate);
        } catch (NoSuchAlgorithmException ex) {
            Logger.getLogger(MyBenchmark.class.getName()).throwing(MyBenchmark.class.getName(), "getChecksum", ex);

        }
        return null;
    }
}
