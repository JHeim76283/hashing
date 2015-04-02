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

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;

import java.util.List;
import java.util.Map;
import java.util.Set;

import java.util.function.Function;
import java.util.logging.Logger;
import java.util.stream.Collectors;

import jonelo.jacksum.algorithm.AbstractChecksum;
import jonelo.jacksum.algorithm.Algorithm;
import jonelo.jacksum.algorithm.CombinedChecksum;

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
public class JacksonJacksumTest {

    private static Map<String, HashResultHolder> IMAGE_FILE_RESULTS;
    private static Map<String, HashResultHolder> TEXT_FILE_RESULTS;
    private static Map<String, HashResultHolder> STRING_RESULTS;
    private static final String TEXT = "This is a test.";

    public JacksonJacksumTest() {
    }

    @BeforeClass
    public static void setUpClass() throws IOException {

        List<HashResultHolder> list = new ObjectMapper().readValue(
                JacksonJacksumTest.class.getResourceAsStream("/jacksum_image.json"),
                new TypeReference<List<HashResultHolder>>() {
                });

        IMAGE_FILE_RESULTS = list.stream().collect(Collectors.toMap(HashResultHolder::getAlgorithm, Function.identity()));

        /*Map<String, String> map = JacksumAPI.getAvailableAlgorithms();
         map.keySet().stream()
         .forEach(cannonicalName -> System.out.println(cannonicalName.toUpperCase()+"(\""+map.get(cannonicalName)+"\",\""+cannonicalName+"\", \"alias\"),"));
         */
        /*JacksumAPI.getAvailableAlgorithms().keySet().stream()
         .forEach(name -> System.out.println("@Benchmark @BenchmarkMode(Mode.AverageTime) @OutputTimeUnit(TimeUnit.MILLISECONDS)  public String "+name+"FileAlt() {return this.getFileHashValue(this.getChecksum(\""+name+"\", true), FILE);}"));
         */
//        IMAGE_FILE_RESULTS.keySet().stream().forEach(str -> System.out.println("@Test public void test_" + str + "(){this.individualTest(\"" + str + "\");}"));
        list = new ObjectMapper().readValue(
                JacksonJacksumTest.class.getResourceAsStream("/jacksum_text.json"),
                new TypeReference<List<HashResultHolder>>() {
                });

        TEXT_FILE_RESULTS = list.stream().collect(Collectors.toMap(HashResultHolder::getAlgorithm, Function.identity()));

        list = new ObjectMapper().readValue(
                JacksonJacksumTest.class.getResourceAsStream("/jacksum_string.json"),
                new TypeReference<List<HashResultHolder>>() {
                });

        STRING_RESULTS = list.stream().collect(Collectors.toMap(HashResultHolder::getAlgorithm, Function.identity()));

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
    public void test_md5() {
        this.individualTest("md5");
    }

    @Test
    public void test_crc32_mpeg2() {
        this.individualTest("crc32_mpeg2");
    }

    @Test
    public void test_sha256() {
        this.individualTest("sha-256");
    }

    @Test
    public void test_xor8() {
        this.individualTest("xor8");
    }

    @Test
    public void test_haval_160_5() {
        this.individualTest("haval_160_5");
    }

    @Test
    public void test_haval_160_3() {
        this.individualTest("haval_160_3");
    }

    @Test
    public void test_haval_160_4() {
        this.individualTest("haval_160_4");
    }

    @Test
    public void test_tree_tiger2() {
        this.individualTest("tree:tiger2");
    }

    @Test
    public void test_haval_128_4() {
        this.individualTest("haval_128_4");
    }

    @Test
    public void test_haval_128_3() {
        this.individualTest("haval_128_3");
    }

    @Test
    public void test_haval_128_5() {
        this.individualTest("haval_128_5");
    }

    @Test
    public void test_whirlpool2() {
        this.individualTest("whirlpool_2003");
    }

    @Test
    public void test_tree_tiger() {
        this.individualTest("tree:tiger");
    }

    @Test
    public void test_crc32() {
        this.individualTest("crc32");
    }

    @Test
    public void test_haval_224_5() {
        this.individualTest("haval_224_5");
    }

    @Test
    public void test_haval_224_4() {
        this.individualTest("haval_224_4");
    }

    @Test
    public void test_whirlpool0() {
        this.individualTest("whirlpool_2000");
    }

    @Test
    public void test_haval_224_3() {
        this.individualTest("haval_224_3");
    }

    @Test
    public void test_elf() {
        this.individualTest("elf");
    }

    @Test
    public void test_whirlpool1() {
        this.individualTest("whirlpool");
    }

    @Test
    public void test_tiger() {
        this.individualTest("tiger");
    }

    @Test
    public void test_crc32_bzip2() {
        this.individualTest("crc32_bzip2");
    }

    @Test
    public void test_sha1() {
        this.individualTest("sha1");
    }

    @Test
    public void test_sha0() {
        this.individualTest("sha-0");
    }

    @Test
    public void test_fcs16() {
        this.individualTest("fcs16");
    }

    @Test
    public void test_tiger2() {
        this.individualTest("tiger2");
    }

    @Test
    public void test_crc64() {
        this.individualTest("crc64");
    }

    @Test
    public void test_sum16() {
        this.individualTest("sum16");
    }

    @Test
    public void test_crc24() {
        this.individualTest("crc24");
    }

    @Test
    public void test_ripemd128() {
        this.individualTest("ripemd128");
    }

    @Test
    public void test_sum24() {
        this.individualTest("sum24");
    }

    @Test
    public void test_cksum() {
        this.individualTest("cksum");
    }

    @Test
    public void test_sha512() {
        this.individualTest("sha-512");
    }

    @Test
    public void test_crc8() {
        this.individualTest("crc8");
    }

    @Test
    public void test_crc16() {
        this.individualTest("crc16");
    }

    @Test
    public void test_has160() {
        this.individualTest("has-160");
    }

    @Test
    public void test_ripemd320() {
        this.individualTest("ripemd320");
    }

    @Test
    public void test_sumsysv() {
        this.individualTest("sumsysv");
    }

    @Test
    public void test_ripemd160() {
        this.individualTest("ripemd160");
    }

    @Test
    public void test_sha384() {
        this.individualTest("sha-384");
    }

    @Test
    public void test_haval_256_3() {
        this.individualTest("haval_256_3");
    }

    @Test
    public void test_sum32() {
        this.individualTest("sum32");
    }

    @Test
    public void test_haval_256_4() {
        this.individualTest("haval_256_4");
    }

    @Test
    public void test_haval_256_5() {
        this.individualTest("haval_256_5");
    }

    @Test
    public void test_haval_192_3() {
        this.individualTest("haval_192_3");
    }

    @Test
    public void test_tiger128() {
        this.individualTest("tiger-128");
    }

    @Test
    public void test_ripemd256() {
        this.individualTest("ripemd256");
    }

    @Test
    public void test_sumbsd() {
        this.individualTest("sumbsd");
    }

    @Test
    public void test_sha224() {
        this.individualTest("sha-224");
    }

    @Test
    public void test_haval_192_4() {
        this.individualTest("haval_192_4");
    }

    @Test
    public void test_haval_192_5() {
        this.individualTest("haval_192_5");
    }

    @Test
    public void test_tiger160() {
        this.individualTest("tiger-160");
    }

    @Test
    public void test_ed2k() {
        this.individualTest("ed2k");
    }

    @Test
    public void test_adler32() {
        this.individualTest("adler32");
    }

    @Test
    public void test_gost() {
        this.individualTest("gost");
    }

    @Test
    public void test_md2() {
        this.individualTest("md2");
    }

    @Test
    public void test_sum8() {
        this.individualTest("sum8");
    }

    @Test
    public void test_md4() {
        this.individualTest("md4");
    }

    @Test
    public void image() {
        assertFileHashes(JacksonJacksumTest.class.getResource("/image.jpg").getFile(), IMAGE_FILE_RESULTS);
    }

    @Test
    public void concurrentImage() throws NoSuchAlgorithmException, IOException {
        assertConcurrentFile(JacksonJacksumTest.class.getResource("/image.jpg").getFile(), IMAGE_FILE_RESULTS);
    }

    @Test
    public void text() {
        assertFileHashes(JacksonJacksumTest.class.getResource("/text.txt").getFile(), TEXT_FILE_RESULTS);
    }

    @Test
    public void concurrentText() throws NoSuchAlgorithmException, IOException {
        assertConcurrentFile(JacksonJacksumTest.class.getResource("/text.txt").getFile(), TEXT_FILE_RESULTS);
    }

    @Test
    public void string() {
        assertTrue(STRING_RESULTS.keySet().stream()
                .map(algorithmName -> getChecksum(algorithmName))
                .map(checksum -> getStringHashValue(checksum, TEXT))
                .allMatch(hashHolder -> hashHolder.equals(STRING_RESULTS.get(hashHolder.getAlgorithm()))));
    }


    /*
     -----------------------------------------------------------------------------------------
     */
    private void assertConcurrentFile(String filename, Map<String, HashResultHolder> expected) throws NoSuchAlgorithmException, IOException {
        Set<String> ks = IMAGE_FILE_RESULTS.keySet();

        AbstractChecksum cc = new CombinedChecksum(ks.toArray(new String[ks.size()]), false);
        cc.setEncoding(AbstractChecksum.HEX);

        cc.readFile(filename);
        String result = cc.format("#ALGONAME{i}\n#CHECKSUM{i}");

        String[] parts = result.split("\\n");
        List<HashResultHolder> answer = new ArrayList<>(ks.size());

        for (int i = 0; i < parts.length; i += 2) {
            HashResultHolder res = new HashResultHolder();
            res.setAlgorithm(parts[i]);
            res.setValue(parts[i + 1]);
            answer.add(res);
        }

        assertTrue(answer.stream().allMatch(hashHolder -> hashHolder.equals(expected.get(hashHolder.getAlgorithm()))));
    }

    private void assertFileHashes(String filename, Map<String, HashResultHolder> expected) {
        assertTrue(expected.keySet().stream()
                .map(algorithmName -> getChecksum(algorithmName))
                .map(checksum -> getFileHashValue(checksum, filename))
                .allMatch(hashHolder -> {
                    boolean ok = hashHolder.equals(expected.get(hashHolder.getAlgorithm()));
                    if(!ok){
                        
                        System.out.println("Algo: "+hashHolder.getAlgorithm()+" expected: "+expected.get(hashHolder.getAlgorithm()).getValue()+" actual: "+hashHolder.getValue());
                    }
                    return ok;
                }
                
                ));
    }

    private HashResultHolder getStringHashValue(AbstractChecksum chsum, String text) {
        chsum.reset();
        chsum.setEncoding(AbstractChecksum.HEX);
        chsum.update(text.getBytes());
        HashResultHolder answer = new HashResultHolder();
        answer.setAlgorithm(chsum.getName());
        answer.setValue(chsum.getFormattedValue());
        return answer;
    }

    private HashResultHolder getFileHashValue(AbstractChecksum chsum, String fileName) {
        try {
            chsum.reset();
            chsum.setEncoding(AbstractChecksum.HEX);
            chsum.readFile(fileName);
            HashResultHolder answer = new HashResultHolder();
            answer.setAlgorithm(chsum.getName());
            answer.setValue(chsum.getFormattedValue());
            return answer;
        } catch (IOException ex) {
            Logger.getLogger(JacksonJacksumTest.class.getName()).throwing(JacksonJacksumTest.class.getName(), "getHashValue", ex);
            fail(ex.getMessage());
        }
        return null;
    }

    private AbstractChecksum getChecksum(String algorithmName) {
        return this.getChecksum(algorithmName, false);
    }

    private AbstractChecksum getChecksum(String algorithmName, boolean alternate) {
        //assertTrue(JacksumAPI.getAvailableAlgorithms().containsKey(algorithmName));
        try {
            return Algorithm.getAlgorithm(algorithmName).getChecksumInstance(algorithmName, alternate);
        } catch (NoSuchAlgorithmException ex) {
            Logger.getLogger(JacksonJacksumTest.class.getName()).throwing(JacksonJacksumTest.class.getName(), "getChecksum", ex);
            fail(ex.getMessage());
        }
        return null;
    }

    private void individualTest(String algorithmName) {
        final String imageExpected = IMAGE_FILE_RESULTS.get(algorithmName).getValue();
        final String textExpected = TEXT_FILE_RESULTS.get(algorithmName).getValue();
        final String stringExpected = STRING_RESULTS.get(algorithmName).getValue();
        
        this.assertIndividualTest(algorithmName, this.getChecksum(algorithmName, false), imageExpected, textExpected, stringExpected);
        this.assertIndividualTest(algorithmName + " alternate", this.getChecksum(algorithmName, true), imageExpected, textExpected, stringExpected);

    }

    private void assertIndividualTest(String algorithmName, AbstractChecksum checksum, String imageExpected, String textExpected, String stringExpected) {
        final String imageActual = this.getFileHashValue(checksum, JacksonJacksumTest.class.getResource("/image.jpg").getFile()).getValue();
        assertEquals(algorithmName, imageExpected, imageActual);
        final String textActual = this.getFileHashValue(checksum, JacksonJacksumTest.class.getResource("/text.txt").getFile()).getValue();
        assertEquals(algorithmName, textExpected, textActual);
        final String stringActual = this.getStringHashValue(checksum, TEXT).getValue();
        assertEquals(algorithmName, stringExpected, stringActual);
    }

}
