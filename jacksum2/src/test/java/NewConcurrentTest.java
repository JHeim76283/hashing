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

import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import jonelo.jacksum.algorithm.Algorithm;
import jonelo.jacksum.concurrent.DataBlock;
import jonelo.jacksum.concurrent.FileReader;
import jonelo.jacksum.concurrent.HashingTask;
import jonelo.jacksum.concurrent.Pair;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import static org.junit.Assert.*;

/**
 *
 * @author Federico Tello Gentile <federicotg@gmail.com>
 */
public class NewConcurrentTest {

    private static final String[] FILENAMES = {
        "/home/fede/Sincronizado/pagos/20264297622_15_0002_00000002.pdf",
        "/home/fede/Sincronizado/pagos/20264297622_15_0002_00000003.pdf",
        "/home/fede/Sincronizado/pagos/20264297622_15_0002_00000004.pdf",
        "/home/fede/Sincronizado/pagos/20264297622_15_0002_00000005.pdf",
        "/home/fede/Sincronizado/pagos/20264297622_15_0002_00000006.pdf",
        "/home/fede/Sincronizado/pagos/20264297622_15_0002_00000007.pdf",
        "/home/fede/Sincronizado/pagos/cablevision-201501.pdf",
        "/home/fede/Sincronizado/pagos/cablevision-201502.pdf",
        "/home/fede/Sincronizado/pagos/cablevision-201503.pdf",
        "/home/fede/Sincronizado/pagos/cablevision-201504.pdf",
        "/home/fede/Sincronizado/pagos/Comprobante Limite Extraccion 20150208.pdf",
        "/home/fede/Sincronizado/pagos/edelap-201502.pdf",
        "/home/fede/Sincronizado/pagos/edelap-201503.pdf",
        "/home/fede/Sincronizado/pagos/fiscalizacion_angeles_20150211-acuse_2136786_592.pdf",
        "/home/fede/Sincronizado/pagos/fiscalizacion_angeles_20150211-cuestionario_274_124.pdf",
        "/home/fede/Sincronizado/pagos/fiscalizacion_angeles_20150211-cuestionario_274_657.pdf",
        "/home/fede/Sincronizado/pagos/gas-201502.pdf",
        "/home/fede/Sincronizado/pagos/inmobiliario-201501.pdf",
        "/home/fede/Sincronizado/pagos/modificacion_datos_monotributo-201501.pdf",
        "/home/fede/Sincronizado/pagos/monotributo-201501.pdf",
        "/home/fede/Sincronizado/pagos/monotributo-201502.pdf",
        "/home/fede/Sincronizado/pagos/monotributo-201503.pdf",
        "/home/fede/Sincronizado/pagos/monotributo-angeles-201501.pdf",
        "/home/fede/Sincronizado/pagos/monotributo-angeles-201502.pdf",
        "/home/fede/Sincronizado/pagos/monotributo-angeles-201503.pdf",
        "/home/fede/Sincronizado/pagos/municipal-2015.pdf",
        "/home/fede/Sincronizado/pagos/pagos2007.tar.xz",
        "/home/fede/Sincronizado/pagos/pagos2008.tar.xz",
        "/home/fede/Imágenes/Documentos/poder-05.png",
        "/home/fede/NetBeansProjects/ipc-address-calc-old.tar.xz",
        "/home/fede/Sincronizado/pagos/pagos2009.tar.xz",
        "/home/fede/Sincronizado/pagos/pagos2010.tar.xz",
        "/home/fede/Sincronizado/pagos/pagos2011.tar.xz",
        "/home/fede/Sincronizado/pagos/pagos2012.tar.xz",
        "/home/fede/Sincronizado/pagos/pagos2013.tar.xz",
        "/home/fede/Sincronizado/pagos/pagos2014.tar.xz",
        "/home/fede/Sincronizado/pagos/personal-angeles-201501.pdf",
        "/home/fede/Sincronizado/pagos/personal-angeles-201502.pdf",
        "/home/fede/Sincronizado/pagos/personal-angeles-201503.pdf",
        "/home/fede/Sincronizado/pagos/personal-fede-201501.pdf",
        "/home/fede/Sincronizado/pagos/personal-fede-201502.pdf",
        "/home/fede/Sincronizado/pagos/personal-fede-201503.pdf",
        "/home/fede/Sincronizado/pagos/pf-20150211.pdf",
        "/home/fede/Sincronizado/pagos/pf-20150306.pdf",
        "/home/fede/Sincronizado/pagos/seguro-201501.pdf",
        "/home/fede/Sincronizado/pagos/seguro-201502.pdf",
        "/home/fede/Sincronizado/pagos/seguro-201503.pdf",
        "/home/fede/Sincronizado/pagos/telefonica-201502.pdf"
    };

    private static final Algorithm[] ALGORITHMS = {
        Algorithm.ADLER32,
        Algorithm.MD5,
        Algorithm.SHA256,
        Algorithm.GOST,
        Algorithm.SHA3_256,
        Algorithm.SM3,
        Algorithm.SHA3_224,
        Algorithm.CRC32,
        Algorithm.XOR8
            
    };

    private static final int BLOCK_QUEUE_SIZE = 1024;
    private static final int READERS = 2;
    private static final int THREADS = 10;

    public NewConcurrentTest() {
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
   // @Test
    public void newConcurrent() throws NoSuchAlgorithmException, InterruptedException, ExecutionException {

         System.out.println("Begins...");
        
        // set up run.
        // filenames to process go in a queue.
        final Queue<String> filenames = new ConcurrentLinkedQueue<>(Arrays.asList(FILENAMES));

        assertEquals(FILENAMES.length, filenames.size());
        
        // setup results map
        final Map<Pair<String, Algorithm>, byte[]> resultHolder = new ConcurrentHashMap<>();

        //a queue and a HashingTask for each file and algorithm
        Map<Pair<String, Algorithm>, BlockingQueue<DataBlock>> dataQueueMap = new ConcurrentHashMap<>();

        Collection<Runnable> tasks = new ArrayList<>();

        for (String filename : FILENAMES) {
            for (Algorithm algorithm : ALGORITHMS) {

                BlockingQueue<DataBlock> blockQueue = new ArrayBlockingQueue<>(BLOCK_QUEUE_SIZE);

                tasks.add(new HashingTask(filename, algorithm, blockQueue, resultHolder));

                dataQueueMap.put(new Pair<>(filename, algorithm), blockQueue);
            }
        }

        // readers
        Collection<FileReader> readers = new ArrayList<>(READERS);

        for (int i = 0; i < READERS; i++) {
            readers.add(new FileReader(filenames, Arrays.asList(ALGORITHMS), dataQueueMap));
        }

        ExecutorService executor = Executors.newCachedThreadPool();

        List<Future<?>> tasksFutures = new ArrayList<>();
        for (Runnable task : tasks) {
            tasksFutures.add(executor.submit(task));
        }

        for (Runnable reader : readers) {
            executor.submit(reader);
        }

        for (Future<?> f : tasksFutures) {
            f.get();
        }
        executor.shutdown();

        
        for(Pair<String, Algorithm> pair : resultHolder.keySet()){
            System.out.println(pair.getFirst()+" "+pair.getSecond().getCanonicalName()+" "+resultHolder.get(pair).length * 8);
        }
        
        

    }
}
