/**
 * ****************************************************************************
 *
 * Jacksum version 1.7.0 - checksum utility in Java Copyright (C) 2001-2006
 * Dipl.-Inf. (FH) Johann Nepomuk Loefflmann, All Rights Reserved,
 * http://www.jonelo.de
 *
 * This program is free software; you can redistribute it and/or modify it under
 * the terms of the GNU General Public License as published by the Free Software
 * Foundation; either version 2 of the License, or (at your option) any later
 * version.
 *
 * This program is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU General Public License for more
 * details.
 *
 * You should have received a copy of the GNU General Public License along with
 * this program; if not, write to the Free Software Foundation, Inc., 59 Temple
 * Place - Suite 330, Boston, MA 02111-1307, USA.
 *
 * E-mail: jonelo@jonelo.de
 *
 ****************************************************************************
 */
package jonelo.jacksum.algorithm;

import java.io.File;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import jonelo.jacksum.JacksumAPI;
import jonelo.jacksum.concurrent.ConcurrentHasher;
import jonelo.jacksum.concurrent.HashAlgorithm;
import jonelo.sugar.util.EncodingException;
import jonelo.sugar.util.GeneralString;

/**
 * @author jonelo
 */
public final class CombinedChecksum extends AbstractChecksum {

    private List<AbstractChecksum> algorithms;

    /**
     * Creates a new instance of CombinedChecksum
     */
    public CombinedChecksum() {
        init();
    }

    public CombinedChecksum(String[] algos, boolean alternate) throws NoSuchAlgorithmException {
        init();
        setAlgorithms(algos, alternate);
    }

    private void init() {
        algorithms = new ArrayList<>();
        length = 0;
        filename = null;
        separator = " ";
        encoding = HEX;
    }

    public void addAlgorithm(String algorithm, boolean alternate) throws NoSuchAlgorithmException {
        AbstractChecksum checksum = JacksumAPI.getChecksumInstance(algorithm, alternate);
        checksum.setName(algorithm);
        algorithms.add(checksum);
    }

    public void setAlgorithms(String[] algos, boolean alternate) throws NoSuchAlgorithmException {
        for (int i = 0; i < algos.length; i++) {
            addAlgorithm(algos[i], alternate);
        }
    }

    @Override
    public void reset() {
        // for all algorithms
        for (AbstractChecksum algorithm : algorithms) {
            algorithm.reset();
        }
        length = 0;
    }

    /**
     * Updates all checksums with the specified byte.
     */
    @Override
    public void update(byte b) {
        for (AbstractChecksum algorithm : algorithms) {
            algorithm.update(b);
        }
        length++;
    }

    /**
     * Updates all checksums with the specified array of bytes.
     */
    @Override
    public void update(byte[] bytes, int offset, int length) {
        for (AbstractChecksum algorithm : algorithms) {
            algorithm.update(bytes, offset, length);
        }
        this.length += length;
    }

    /**
     * Returns the result of the computation as byte array.
     */
    @Override
    public byte[] getByteArray() {
        List<byte[]> v = new ArrayList<>(this.algorithms.size());
        int size = 0;
        for (AbstractChecksum algorithm : algorithms) {
            byte[] tmp = algorithm.getByteArray();
            v.add(tmp);
            size += tmp.length;
        }
        byte[] ret = new byte[size];
        int offset = 0;
        for (byte[] src : v) {
            System.arraycopy(src, 0, ret, offset, src.length);
            offset += src.length;
        }
        return ret;
    }

    /**
     * with this method the format() method can be customized, it will be
     * launched at the beginning of format()
     */
    @Override
    protected void firstFormat(StringBuilder formatBuf) {

        // normalize the checksum code token
        GeneralString.replaceAllStrings(formatBuf, "#FINGERPRINT", "#CHECKSUM");

        // normalize the output of every algorithm
        setEncoding(encoding);

        StringBuilder buf = new StringBuilder();
        String format = formatBuf.toString();

        if (format.contains("#CHECKSUM{i}") || format.contains("#ALGONAME{i}")) {

            for (AbstractChecksum algorithm : algorithms) {
                StringBuilder line = new StringBuilder(format);
                GeneralString.replaceAllStrings(line, "#CHECKSUM{i}", algorithm.getFormattedValue());
                GeneralString.replaceAllStrings(line, "#ALGONAME{i}", algorithm.getName());
                buf.append(line);
                if (algorithms.size() > 1) {
                    buf.append("\n");
                }
            }
        } else {
            buf.append(format);
        }

        // are there still tokens to be transformed ?
        if (buf.toString().contains("#CHECKSUM{")) {
            // replace CHECKSUM{1} to {CHECKSUM{n}
            for (int i = 0; i < algorithms.size(); i++) {
                GeneralString.replaceAllStrings(buf, "#CHECKSUM{" + i + "}",
                        algorithms.get(i).getFormattedValue());
            }
        }

        if (buf.toString().contains("#ALGONAME{")) {
            // replace ALGONAME{1} to {ALGONAME{n}
            for (int i = 0; i < algorithms.size(); i++) {
                GeneralString.replaceAllStrings(buf, "#ALGONAME{" + i + "}",
                        algorithms.get(i).getName());
            }
        }
        formatBuf.setLength(0);
        formatBuf.append(buf.toString());
    }

    /**
     * Sets the encoding of the checksum.
     *
     * @param encoding the encoding of the checksum.
     */
    @Override
    public void setEncoding(String encoding) throws EncodingException {
        for (AbstractChecksum algorithm : algorithms) {
            algorithm.setEncoding(encoding);
        }
        this.encoding = ((AbstractChecksum) algorithms.get(0)).getEncoding();
    }

   
    /**
     * Soon to be replaced by a ConcurrentHasher
     * @param filename
     * @param reset
     * @return
     * @throws IOException 
     */
    public long readFileConcurrent(String filename, boolean reset) throws IOException {
        this.filename = filename;
        if (isTimestampWanted()) {
            setTimestamp(filename);
        }

        long lengthBackup = 0;
        if (reset) {
            reset();
        }
        List<HashAlgorithm> ha = new ArrayList<>(this.algorithms.size());
        for (AbstractChecksum algorithm : this.algorithms) {
            ha.add(HashAlgorithm.getAlgorithm(algorithm));
        }
        lengthBackup = length;
        File f = new File(filename);
        this.length = f.length();

        new ConcurrentHasher().updateHashes(f, ha);

        return length - lengthBackup;
    }

}
