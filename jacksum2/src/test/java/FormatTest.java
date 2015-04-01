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

import java.io.File;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import jonelo.jacksum.JacksumAPI;
import jonelo.jacksum.algorithm.AbstractChecksum;
import jonelo.jacksum.algorithm.Algorithm;
import jonelo.jacksum.algorithm.CombinedChecksum;
import jonelo.jacksum.concurrent.CustomHashFormat;
import jonelo.jacksum.concurrent.Encoding;
import jonelo.jacksum.concurrent.HashFormat;
import jonelo.sugar.util.GeneralString;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Federico Tello Gentile <federicotg@gmail.com>
 */
public class FormatTest {

    public FormatTest() {
    }

    @BeforeClass
    public static void setUpClass() {
    }

    @AfterClass
    public static void tearDownClass() {
    }

    @Before
    public void setUp() {
    }

    @After
    public void tearDown() {
    }

    // TODO add test methods here.
    // The methods must be annotated with annotation @Test. For example:
    //
    @Test
    public void simple() throws NoSuchAlgorithmException {

        final String format = "Hola #ALGONAME{i} tiene md5 #QUOTE#CHECKSUM{i}#QUOTE chau.";

        AbstractChecksum md5 = JacksumAPI.getChecksumInstance("md5");
        md5.setTimestampFormat("yyyyMMddHHmmss");
        md5.update("This is a test".getBytes());
        String expected = md5.format(format);

        HashFormat hashFormat = new CustomHashFormat(format, Encoding.HEX, 0, '.', md5.getSeparator(), "yyyyMMddHHmmss");

        String value = hashFormat.format(
                Algorithm.MD5,
                md5.getByteArray(),
                "",
                0,
                0);

        //System.out.println("Expected: " + expected);
        //  System.out.println("Value:    " + value);
        assertEquals(expected, value);
    }

    @Test
    public void simple2() throws NoSuchAlgorithmException {

        final String format = "Hola #ALGONAME  #FINGERPRINT tiene md5 #QUOTE#CHECKSUM#QUOTE chau.";

        AbstractChecksum chsum = JacksumAPI.getChecksumInstance("sha3-224");
        chsum.setTimestampFormat("yyyyMMddHHmmss");
        chsum.update("This is a test".getBytes());
        String expected = chsum.format(format);

        HashFormat hashFormat = new CustomHashFormat(format, Encoding.HEX, 0, '.', chsum.getSeparator(), "yyyyMMddHHmmss");

        String value = hashFormat.format(
                Algorithm.SHA3_224,
                chsum.getByteArray(),
                "",
                0,
                0);

        //System.out.println("Expected: " + expected);
        //System.out.println("Value:    " + value);
        assertEquals(expected, value);
    }

    @Test
    public void complex() throws NoSuchAlgorithmException {

        final String format = "Hola #ALGONAME{i} #FINGERPRINT (#ALGONAME{i}) tiene valor #QUOTE#CHECKSUM{i}#QUOTE chau.#CHECKSUM{1} #CHECKSUM{0} #ALGONAME{0} #ALGONAME{3}";
        final String message = "This is a test";

        AbstractChecksum chsum = JacksumAPI.getChecksumInstance("md5+sha1+crc32+xor8");
        chsum.setTimestampFormat("yyyyMMddHHmmss");
        chsum.update(message.getBytes());
        String expected = chsum.format(format);

        HashFormat hashFormat = new CustomHashFormat(format, Encoding.HEX, 0, '.', chsum.getSeparator(), "yyyyMMddHHmmss");

        String value = hashFormat.format(
                Arrays.asList(new Algorithm[]{Algorithm.MD5, Algorithm.SHA1, Algorithm.CRC32, Algorithm.XOR8}),
                this.getByteArrays(message, "yyyyMMddHHmmss", Algorithm.MD5, Algorithm.SHA1, Algorithm.CRC32, Algorithm.XOR8),
                "",
                0,
                0);

        //   System.out.println("Expected: " + expected);
        //    System.out.println("Value:    " + value);
        assertEquals(expected, value);
    }

    @Test
    public void complexWithFile() throws NoSuchAlgorithmException, IOException {

        final String format = "Hola #ALGONAME #FINGERPRINT #FILENAME #FILENAME{NAME} #FILENAME{PATH} $$ #FILESIZE $$ #ALGONAME{i} (#ALGONAME{i}) tiene valor #QUOTE#CHECKSUM{i}#QUOTE chau.#CHECKSUM{1} #CHECKSUM{0} #ALGONAME{0} #ALGONAME{3}";

        final String filename = JacksonJacksumTest.class.getResource("/image.jpg").getFile();

        AbstractChecksum chsum = JacksumAPI.getChecksumInstance("md5+sha1+crc32+xor8");
        chsum.setTimestampFormat("yyyyMMddHHmmss");
        chsum.readFile(filename);
        String expected = chsum.format(format);

        HashFormat hashFormat = new CustomHashFormat(format, Encoding.HEX, 0, '.', chsum.getSeparator(), "yyyyMMddHHmmss");

        String value = hashFormat.format(
                Arrays.asList(new Algorithm[]{Algorithm.MD5, Algorithm.SHA1, Algorithm.CRC32, Algorithm.XOR8}),
                this.getByteArraysWithFile(filename, "yyyyMMddHHmmss", Algorithm.MD5, Algorithm.SHA1, Algorithm.CRC32, Algorithm.XOR8),
                filename,
                new File(filename).length(),
                0);

        //  System.out.println("Expected: " + expected);
        //   System.out.println("Value:    " + value);
        assertEquals(expected, value);

    }

    private List<byte[]> getByteArraysWithFile(String filename, String timestampformat, Algorithm... algorithms) {

        return Arrays.stream(algorithms).map(a -> {
            try {
                AbstractChecksum chsum = JacksumAPI.getChecksumInstance(a.getCanonicalName());

                chsum.setTimestampFormat(timestampformat);
                chsum.readFile(filename);
                chsum.setEncoding("hex");

                return chsum.getByteArray();

            } catch (NoSuchAlgorithmException | IOException ex) {
                Logger.getLogger(FormatTest.class.getName()).log(Level.SEVERE, null, ex);
                return null;
            }
        }).collect(Collectors.toList());

    }

    private List<byte[]> getByteArrays(String message, String timestampformat, Algorithm... algorithms) {

        return Arrays.stream(algorithms).map(a -> {
            try {
                AbstractChecksum chsum = JacksumAPI.getChecksumInstance(a.getCanonicalName());

                chsum.setTimestampFormat(timestampformat);
                chsum.update(message.getBytes());
                chsum.setEncoding("hex");
                return chsum.getByteArray();

            } catch (NoSuchAlgorithmException ex) {
                Logger.getLogger(FormatTest.class.getName()).log(Level.SEVERE, null, ex);
                return null;
            }
        }).collect(Collectors.toList());

    }
}
