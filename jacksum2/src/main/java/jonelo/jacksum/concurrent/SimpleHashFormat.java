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

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import jonelo.jacksum.algorithm.Algorithm;

/**
 *
 * @author Federico Tello Gentile <federicotg@gmail.com>
 */
public class SimpleHashFormat extends HashFormat {

    public SimpleHashFormat(Encoding encoding, int group, char groupChar, String timeStampFormat) {
        super(null, encoding, group, groupChar, "\t", timeStampFormat);
    }

    @Override
    public String format(List<Algorithm> algorithms, List<byte[]> byteArrays, String filename, long fileSize, long timestamp) {
        StringBuilder sb = new StringBuilder(100);
        this.getEncoding().encode(this.getGroup(), this.getGroupChar(), joinByteArrays(byteArrays), sb);

        if (filename != null) {
            sb.append(this.getSeparator())
                    .append(fileSize)
                    .append(this.getSeparator());
            if (this.getTimeStampFormat() != null) {
                sb.append(new SimpleDateFormat(this.getTimeStampFormat()).format(new Date(timestamp)))
                        .append(this.getSeparator());
            }
            sb.append(filename);
        }
        return sb.toString();
    }

}
