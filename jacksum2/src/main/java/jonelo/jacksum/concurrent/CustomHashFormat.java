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
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;
import jonelo.jacksum.algorithm.Algorithm;
import jonelo.sugar.util.GeneralString;

/**
 *
 * @author Federico Tello Gentile <federicotg@gmail.com>
 */
public class CustomHashFormat extends HashFormat {

    public CustomHashFormat(String format, Encoding encoding, int group, char groupChar, String separator, String timeStampFormat) {
        super(format, encoding, group, groupChar, separator, timeStampFormat);
    }

    @Override
    public String format(
            List<Algorithm> algorithms,
            List<byte[]> byteArrays,
            String filename,
            long fileSize,
            long timestamp) {

        List<String> formattedValues = byteArrays.stream()
                .map(array -> this.getEncoding().encode(this.getGroup(), this.getGroupChar(), array))
                .collect(Collectors.toList());

        StringBuilder temp = new StringBuilder(this.getFormat().length() * 2 + algorithms.size() * 128);

        String _format = GeneralString.replaceAllStrings(this.getFormat(), "#FINGERPRINT", "#CHECKSUM");

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
        GeneralString.replaceAllStrings(temp, "#CHECKSUM", this.getEncoding().encode(this.getGroup(), this.getGroupChar(), joinByteArrays(byteArrays)));
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
        if (this.getTimeStampFormat() != null) {
            GeneralString.replaceAllStrings(temp, "#TIMESTAMP", new SimpleDateFormat(this.getTimeStampFormat()).format(new Date(timestamp)));
        }
        // sepcial chars
        GeneralString.replaceAllStrings(temp, "#SEPARATOR", this.getSeparator());
        GeneralString.replaceAllStrings(temp, "#QUOTE", "\"");
        return temp.toString();

    }

}
