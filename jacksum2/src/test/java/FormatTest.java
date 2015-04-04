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
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import jonelo.jacksum.JacksumAPI;
import jonelo.jacksum.algorithm.AbstractChecksum;
import jonelo.jacksum.algorithm.Algorithm;
import jonelo.jacksum.concurrent.CustomHashFormat;
import jonelo.jacksum.concurrent.Encoding;
import jonelo.jacksum.concurrent.HashFormat;
import jonelo.jacksum.util.Service;
import jonelo.sugar.util.BubbleBabble;
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

        String expected = "Hola md5 ce114e4501d2f4e2dcea3e17b546f339a54d88e06612d820bc3be72877c74f257b561b19c07a9f326b (md5) tiene valor \"ce114e4501d2f4e2dcea3e17b546f339\" chau.a54d88e06612d820bc3be72877c74f257b561b19 ce114e4501d2f4e2dcea3e17b546f339 md5 xor8\n"
                + "Hola sha1 ce114e4501d2f4e2dcea3e17b546f339a54d88e06612d820bc3be72877c74f257b561b19c07a9f326b (sha1) tiene valor \"a54d88e06612d820bc3be72877c74f257b561b19\" chau.a54d88e06612d820bc3be72877c74f257b561b19 ce114e4501d2f4e2dcea3e17b546f339 md5 xor8\n"
                + "Hola crc32 ce114e4501d2f4e2dcea3e17b546f339a54d88e06612d820bc3be72877c74f257b561b19c07a9f326b (crc32) tiene valor \"c07a9f32\" chau.a54d88e06612d820bc3be72877c74f257b561b19 ce114e4501d2f4e2dcea3e17b546f339 md5 xor8\n"
                + "Hola xor8 ce114e4501d2f4e2dcea3e17b546f339a54d88e06612d820bc3be72877c74f257b561b19c07a9f326b (xor8) tiene valor \"6b\" chau.a54d88e06612d820bc3be72877c74f257b561b19 ce114e4501d2f4e2dcea3e17b546f339 md5 xor8\n";

        HashFormat hashFormat = new CustomHashFormat(format, Encoding.HEX, 0, '.', " ", "yyyyMMddHHmmss");

        String value = hashFormat.format(
                Arrays.asList(new Algorithm[]{Algorithm.MD5, Algorithm.SHA1, Algorithm.CRC32, Algorithm.XOR8}),
                this.getByteArrays(message, "yyyyMMddHHmmss", Algorithm.MD5, Algorithm.SHA1, Algorithm.CRC32, Algorithm.XOR8),
                "",
                0,
                0);

        if (!expected.equals(value)) {

            if (expected.length() != value.length()) {
                System.out.println("LENGTHS!!! expected: " + expected.length() + " - actual: " + value.length());
            } else {

                for (int i = 0; i < expected.length(); i++) {
                    if (expected.charAt(i) != value.charAt(i)) {
                        System.out.println("char " + i);
                    }
                }
            }

            System.out.println("Expected: " + expected);
            System.out.println("Value:    " + value);
        }
        assertEquals(expected, value);
    }

    @Test
    public void complexWithFile() throws NoSuchAlgorithmException, IOException {

        final String format = "Hola #ALGONAME #FINGERPRINT #FILENAME #FILENAME{NAME} #FILENAME{PATH} $$ #FILESIZE $$ #ALGONAME{i} (#ALGONAME{i}) tiene valor #QUOTE#CHECKSUM{i}#QUOTE chau.#CHECKSUM{1} #CHECKSUM{0} #ALGONAME{0} #ALGONAME{3}";

        final String filename = JacksonJacksumTest.class.getResource("/image.jpg").getFile();

        final String justFile = new File(filename).getName();
        final String justPath = new File(filename).getParent();

        String expected = "Hola md5+sha1+crc32+xor8 994664f1ddc7745252f02ac9311c7d294a0af6fb7950d1bfc4883026cfac4bca5b6d6a91cfd36125a0 " + justPath + File.separator + justFile + " " + justFile + " " + justPath + " $$ 2921017 $$ md5 (md5) tiene valor \"994664f1ddc7745252f02ac9311c7d29\" chau.4a0af6fb7950d1bfc4883026cfac4bca5b6d6a91 994664f1ddc7745252f02ac9311c7d29 md5 xor8\n"
                + "Hola md5+sha1+crc32+xor8 994664f1ddc7745252f02ac9311c7d294a0af6fb7950d1bfc4883026cfac4bca5b6d6a91cfd36125a0 " + justPath + File.separator + justFile + " " + justFile + " " + justPath + " $$ 2921017 $$ sha1 (sha1) tiene valor \"4a0af6fb7950d1bfc4883026cfac4bca5b6d6a91\" chau.4a0af6fb7950d1bfc4883026cfac4bca5b6d6a91 994664f1ddc7745252f02ac9311c7d29 md5 xor8\n"
                + "Hola md5+sha1+crc32+xor8 994664f1ddc7745252f02ac9311c7d294a0af6fb7950d1bfc4883026cfac4bca5b6d6a91cfd36125a0 " + justPath + File.separator + justFile + " " + justFile + " " + justPath + " $$ 2921017 $$ crc32 (crc32) tiene valor \"cfd36125\" chau.4a0af6fb7950d1bfc4883026cfac4bca5b6d6a91 994664f1ddc7745252f02ac9311c7d29 md5 xor8\n"
                + "Hola md5+sha1+crc32+xor8 994664f1ddc7745252f02ac9311c7d294a0af6fb7950d1bfc4883026cfac4bca5b6d6a91cfd36125a0 " + justPath + File.separator + justFile + " " + justFile + " " + justPath + " $$ 2921017 $$ xor8 (xor8) tiene valor \"a0\" chau.4a0af6fb7950d1bfc4883026cfac4bca5b6d6a91 994664f1ddc7745252f02ac9311c7d29 md5 xor8\n";

        HashFormat hashFormat = new CustomHashFormat(format, Encoding.HEX, 0, '.', " ", "yyyyMMddHHmmss");

        String value = hashFormat.format(
                Arrays.asList(new Algorithm[]{Algorithm.MD5, Algorithm.SHA1, Algorithm.CRC32, Algorithm.XOR8}),
                this.getByteArraysWithFile(filename, "yyyyMMddHHmmss", Algorithm.MD5, Algorithm.SHA1, Algorithm.CRC32, Algorithm.XOR8),
                filename,
                new File(filename).length(),
                0);

        //  System.out.println("Expected: " + expected);
        //    System.out.println("Value:    " + value);
        assertEquals(expected, value);

    }

    @Test
    public void encodings() {

        byte[] bytes = "Hola como va muy bien.".getBytes();

        String expected = "start" + BubbleBabble.encode(bytes);
        StringBuilder sb = new StringBuilder();
        sb.append("start");
        BubbleBabble.encode(bytes, sb);
        String actual = sb.toString();

        assertEquals(expected, actual);
        
        
        expected = "start" + Service.format(bytes, true, 3, '.');
        sb = new StringBuilder();
        sb.append("start");
        Service.format(bytes, true, 3, '.', sb);
        actual = sb.toString();
        
        assertEquals(expected, actual);
        
        
        expected = "start" + Service.format(bytes, false, 0, ' ');
        sb = new StringBuilder();
        sb.append("start");
        Service.format(bytes, false, 0, ' ', sb);
        actual = sb.toString();
        
        assertEquals(expected, actual);
        
        
        expected = "start" + Service.format(bytes, false, 1, 'ñ');
        sb = new StringBuilder();
        sb.append("start");
        Service.format(bytes, false, 1, 'ñ', sb);
        actual = sb.toString();
        
        assertEquals(expected, actual);
        
        
        expected = "start" + Service.formatAsBits(bytes);
        sb = new StringBuilder();
        sb.append("start");
        Service.formatAsBits(bytes, sb);
        actual = sb.toString();
        
        assertEquals(expected, actual);
        
        
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
