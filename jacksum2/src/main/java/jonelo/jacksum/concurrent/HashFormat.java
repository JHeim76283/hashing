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

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import jonelo.jacksum.algorithm.Algorithm;

/**
 *
 * @author Federico Tello Gentile <federicotg@gmail.com>
 */
public abstract class HashFormat {

    private final Encoding encoding;
    private final  int group;
    private final char groupChar;
    private final String separator;
    private final String format;
    private final String timeStampFormat;

    protected HashFormat(String format, Encoding encoding, int group, char groupChar, String separator, String timeStampFormat) {
        this.encoding = encoding;
        this.group = group;
        this.groupChar = groupChar;
        this.separator = separator;
        this.format = format;
        this.timeStampFormat = timeStampFormat;
    }

    public final String format(
            Algorithm algorithm,
            byte[] byteArray,
            String filename,
            long fileSize,
            long timestamp) {
        return this.format(Collections.singletonList(algorithm), Collections.singletonList(byteArray), filename, fileSize, timestamp);
    }
    
     public final String format(
            List<Algorithm> algorithms,
            List<byte[]> byteArrays, int length){
         return this.format(algorithms, byteArrays, null, length, 0l);
     }
     
     public final String format(
            List<Algorithm> algorithms,
            List<byte[]> byteArrays){
        StringBuilder sb = new StringBuilder(100);
        this.getEncoding().encode(this.getGroup(), this.getGroupChar(), joinByteArrays(byteArrays), sb);
        return sb.toString();
     }
     

    public abstract String format(
            List<Algorithm> algorithms,
            List<byte[]> byteArrays,
            String filename,
            long fileSize,
            long timestamp);

    protected static byte[] joinByteArrays(List<byte[]> arrays) {
        List<byte[]> v = new ArrayList<>(arrays.size());
        int size = 0;
        for (byte[] array : arrays) {
            //byte[] tmp = algorithm.getByteArray();
            v.add(array);
            size += array.length;
        }
        byte[] ret = new byte[size];
        int offset = 0;
        for (byte[] src : v) {
            System.arraycopy(src, 0, ret, offset, src.length);
            offset += src.length;
        }
        return ret;
    }

    protected Encoding getEncoding() {
        return encoding;
    }

    protected int getGroup() {
        return group;
    }

    protected char getGroupChar() {
        return groupChar;
    }

    protected String getSeparator() {
        return separator;
    }

    protected String getFormat() {
        return format;
    }

    protected String getTimeStampFormat() {
        return timeStampFormat;
    }

    
}
