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

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;
import jonelo.jacksum.algorithm.Algorithm;
import jonelo.sugar.util.GeneralString;

/**
 *
 * @author Federico Tello Gentile <federicotg@gmail.com>
 */
public class HashFormat {

    private final Encoding encoding;
    private final  int group;
    private final char groupChar;
    private final String separator;
    private final String format;
    private final String timeStampFormat;

    public HashFormat(String format, Encoding encoding, int group, char groupChar, String separator, String timeStampFormat) {
        this.encoding = encoding;
        this.group = group;
        this.groupChar = groupChar;
        this.separator = separator;
        this.format = format;
        this.timeStampFormat = timeStampFormat;
    }

    public String format(
            Algorithm algorithm,
            byte[] byteArray,
            String filename,
            long fileSize,
            long timestamp) {
        return this.format(Collections.singletonList(algorithm), Collections.singletonList(byteArray), filename, fileSize, timestamp);
    }

    public String format(
            List<Algorithm> algorithms,
            List<byte[]> byteArrays,
            String filename,
            long fileSize,
            long timestamp) {

        List<String> formattedValues = byteArrays.stream()
                .map(array -> encoding.encode(group, groupChar, array))
                .collect(Collectors.toList());

        StringBuilder temp = new StringBuilder(format.length() * 2 + algorithms.size() * 128);

        String _format = GeneralString.replaceAllStrings(format, "#FINGERPRINT", "#CHECKSUM");

        if (_format.contains("#CHECKSUM{i}") || _format.contains("#ALGONAME{i}")) {

            for (int i = 0; i < algorithms.size(); i++) {
                StringBuilder line = new StringBuilder(_format);
                GeneralString.replaceAllStrings(line, "#CHECKSUM{i}", formattedValues.get(i));
                GeneralString.replaceAllStrings(line, "#ALGONAME{i}", algorithms.get(i).getCanonicalName());
                temp.append(line);
                if (algorithms.size() > 1) {
                    temp.append("\n");
                }
            }
        } else {
            temp.append(_format);
        }

        // are there still tokens to be transformed ?
        if (temp.indexOf("#CHECKSUM{") != -1) {
            // replace CHECKSUM{1} to {CHECKSUM{n}
            for (int i = 0; i < algorithms.size(); i++) {
                GeneralString.replaceAllStrings(temp, "#CHECKSUM{" + String.valueOf(i) + "}",
                        formattedValues.get(i));
            }
        }

        if (temp.indexOf("#ALGONAME{") != -1) {
            // replace ALGONAME{1} to {ALGONAME{n}
            for (int i = 0; i < algorithms.size(); i++) {
                GeneralString.replaceAllStrings(temp, "#ALGONAME{" + String.valueOf(i) + "}",
                        algorithms.get(i).getCanonicalName());
            }
        }

        GeneralString.replaceAllStrings(temp, "#ALGONAME", algorithms.stream().map(algo -> algo.getCanonicalName()).collect(Collectors.joining("+")));
        // counter
        // temp = GeneralString.replaceAllStrings(temp, "#COUNTER", getCounter() );
        GeneralString.replaceAllStrings(temp, "#CHECKSUM", encoding.encode(group, groupChar, joinByteArrays(byteArrays)));
        // filesize
        GeneralString.replaceAllStrings(temp, "#FILESIZE", Long.toString(fileSize));
        // filename
        if (temp.toString().contains("#FILENAME{")) { // comatibility to 1.3
            File filetemp = new File(filename);
            GeneralString.replaceAllStrings(temp, "#FILENAME{NAME}", filetemp.getName());
            String parent = filetemp.getParent();
            if (parent == null) {
                parent = "";
            } else if (!parent.endsWith(File.separator)
                    && // for files on a different drive where the working dir has changed
                    (!parent.endsWith(":") && System.getProperty("os.name").toLowerCase().startsWith("windows"))) {
                parent += File.separator;
            }
            GeneralString.replaceAllStrings(temp, "#FILENAME{PATH}", parent);
        }
        GeneralString.replaceAllStrings(temp, "#FILENAME", filename);
        // timestamp
        //if (isTimestampWanted()) {
        GeneralString.replaceAllStrings(temp, "#TIMESTAMP", new SimpleDateFormat(this.timeStampFormat).format(new Date(timestamp)));
        //}
        // sepcial chars
        GeneralString.replaceAllStrings(temp, "#SEPARATOR", separator);
        GeneralString.replaceAllStrings(temp, "#QUOTE", "\"");
        return temp.toString();

    }

    private static byte[] joinByteArrays(List<byte[]> arrays) {
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

}
