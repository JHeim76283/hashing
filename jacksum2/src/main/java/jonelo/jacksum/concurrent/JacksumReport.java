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
import java.util.List;

/**
 *
 * @author Federico Tello Gentile <federicotg@gmail.com>
 */
public class JacksumReport {

    public static class JacksumHashLine {

        private String name;
        private String value;
        private long length;

        public JacksumHashLine() {
        }

        public JacksumHashLine(String value, long length) {
            this("", value, length);
        }

        public JacksumHashLine(String name, String value, long length) {
            this.name = name;
            this.value = value;
            this.length = length;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getValue() {
            return value;
        }

        public void setValue(String value) {
            this.value = value;
        }

        public long getLength() {
            return length;
        }

        public void setLength(long length) {
            this.length = length;
        }
        

    }

    private List<String> algorithms;
    private boolean alternative;
    private String encoding;
    private int hexaGroupSize;
    private char hexaGroupSeparatorChar;
    private char pathSeparator;
    private List<JacksumHashLine> hashes = new ArrayList<>();

    public List<String> getAlgorithms() {
        return algorithms;
    }

    public void setAlgorithms(List<String> algorithms) {
        this.algorithms = algorithms;
    }

    public boolean isAlternative() {
        return alternative;
    }

    public void setAlternative(boolean alternative) {
        this.alternative = alternative;
    }

    public String getEncoding() {
        return encoding;
    }

    public void setEncoding(String encoding) {
        this.encoding = encoding;
    }

    public int getHexaGroupSize() {
        return hexaGroupSize;
    }

    public void setHexaGroupSize(int hexaGroupSize) {
        this.hexaGroupSize = hexaGroupSize;
    }

    public char getHexaGroupSeparatorChar() {
        return hexaGroupSeparatorChar;
    }

    public void setHexaGroupSeparatorChar(char hexaGroupSeparatorChar) {
        this.hexaGroupSeparatorChar = hexaGroupSeparatorChar;
    }

    public char getPathSeparator() {
        return pathSeparator;
    }

    public void setPathSeparator(char pathSeparator) {
        this.pathSeparator = pathSeparator;
    }

    public List<JacksumHashLine> getHashes() {
        return hashes;
    }

    public void setHashes(List<JacksumHashLine> hashes) {
        this.hashes = hashes;
    }

    public void addLine(String filename, String hashValue, long length) {
        this.hashes.add(new JacksumHashLine(filename, hashValue, length));
    }

    public void addLine(String hashValue, long length) {
        this.hashes.add(new JacksumHashLine(hashValue, length));
    }
}
