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

import java.util.List;
import jonelo.jacksum.algorithm.Algorithm;
import static jonelo.jacksum.concurrent.HashFormat.joinByteArrays;

/**
 *
 * @author Federico Tello Gentile <federicotg@gmail.com>
 */
public class QuickHashFormat extends HashFormat {

    public QuickHashFormat(Encoding encoding, int group, char groupChar, String separator) {
        super(null, encoding, group, groupChar, separator, null);
    }
   
    @Override
    public String format(List<Algorithm> algorithms, List<byte[]> byteArrays, String filename, long fileSize, long timestamp) {
        return this.getEncoding().encode(this.getGroup(), this.getGroupChar(), joinByteArrays(byteArrays)) + this.getSeparator();
    }
    
}
