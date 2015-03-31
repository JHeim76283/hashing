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
import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.security.NoSuchAlgorithmException;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;
import java.util.function.Function;
import java.util.stream.Collectors;
import jonelo.jacksum.JacksumAPI;
import jonelo.jacksum.algorithm.Algorithm;
import jonelo.jacksum.concurrent.ConcurrentHasher;
import jonelo.jacksum.concurrent.Encoding;
import jonelo.jacksum.concurrent.Pair;
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
public class ConcurrentTest {

    private static Map<String, HashResultHolder> IMAGE_FILE_RESULTS;

    public ConcurrentTest() {
    }

    @BeforeClass
    public static void setUpClass() throws IOException {

        List<HashResultHolder> list = new ObjectMapper().readValue(
                JacksonJacksumTest.class.getResourceAsStream("/jacksum_image.json"),
                new TypeReference<List<HashResultHolder>>() {
                });

        IMAGE_FILE_RESULTS = list.stream().collect(Collectors.toMap(HashResultHolder::getAlgorithm, Function.identity()));

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
    public void allAlgorithmsNewConcurrent() throws NoSuchAlgorithmException, InterruptedException, ExecutionException, IOException {

        // standard way
        /*AbstractChecksum chsum = JacksumAPI.getChecksumInstance("crc32_mpeg2");
        chsum.setEncoding(AbstractChecksum.HEX);
        chsum.readFile("/home/fede/NetBeansProjects/hashing/jacksum2/src/test/resources/image.jpg");
        System.out.println(chsum.getFormattedValue());
        */
        
        
        
        
        List<Algorithm> algorithms = IMAGE_FILE_RESULTS.keySet().stream()
                .map(algorithmName -> JacksumAPI.getAlgorithm(algorithmName))
                .collect(Collectors.toList());

       // Path path = FileSystems.getDefault().getPath(JacksonJacksumTest.class.getResource("/image.jpg").getFile());
        Path path = FileSystems.getDefault().getPath("/home/fede/NetBeansProjects/hashing/jacksum2/src/test/resources/image.jpg");

        for (Map.Entry<Pair<Path, Algorithm>, byte[]> e : new ConcurrentHasher().hashFiles(Collections.singletonList(path), algorithms).entrySet()) {

            String expected = IMAGE_FILE_RESULTS.get(e.getKey().getSecond().getCanonicalName()).getValue();
            String actual = Encoding.HEX.encode(-1, ' ', e.getValue());
            /*if (!expected.equals(actual)) {
                System.out.println(e.getKey().getSecond().getCanonicalName());
                System.out.println("expected: " + expected);
                System.out.println("actual:   " + actual);
            }*/

            assertEquals(expected, actual);
        }

        /*assertTrue(new ConcurrentHasher().hashFiles(
         Collections.singletonList(path), algorithms).entrySet().stream()
         .allMatch(entry -> IMAGE_FILE_RESULTS.get(entry.getKey().getSecond().getCanonicalName()).getValue().equals(Encoding.HEX.encode(-1, ' ', entry.getValue()))));
         */
    }

}
