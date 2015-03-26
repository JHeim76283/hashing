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

import java.util.HashMap;
import java.util.Map;
import jonelo.jacksum.algorithm.AbstractChecksum;

/**
 *
 * @author Federico Tello Gentile <federicotg@gmail.com>
 */
public class HashAlgorithm implements Comparable<HashAlgorithm> {

    private static final Map<String, Integer> WEIGHTS = new HashMap<>();

    /* 
     * This weight information is used to balance the load among
     * different worker threads.
     * To regenerate this information use the main method
     * in this class.

Benchmark                              Mode  Samples      Score  Score error  Units
o.f.j.t.MyBenchmark.adler32File        avgt       20    101,994        2,882  ms/op
o.f.j.t.MyBenchmark.cksumFile          avgt       20    245,298        1,127  ms/op
o.f.j.t.MyBenchmark.crc16File          avgt       20    181,123        0,157  ms/op
o.f.j.t.MyBenchmark.crc24File          avgt       20    245,989        0,543  ms/op
o.f.j.t.MyBenchmark.crc32File          avgt       20    105,493        0,349  ms/op
o.f.j.t.MyBenchmark.crc32_bzip2File    avgt       20    245,936        0,602  ms/op
o.f.j.t.MyBenchmark.crc32_mpeg2File    avgt       20    246,198        1,069  ms/op
o.f.j.t.MyBenchmark.crc64File          avgt       20    205,852        3,415  ms/op
o.f.j.t.MyBenchmark.crc8File           avgt       20    210,619        0,542  ms/op
o.f.j.t.MyBenchmark.ed2kFile           avgt       20    224,806        0,497  ms/op
o.f.j.t.MyBenchmark.elfFile            avgt       20    241,329        1,190  ms/op
o.f.j.t.MyBenchmark.fcs16File          avgt       20    244,554        0,314  ms/op
o.f.j.t.MyBenchmark.gostFile           avgt       20  16362,103      133,577  ms/op
o.f.j.t.MyBenchmark.has160File         avgt       20    426,172        1,084  ms/op
o.f.j.t.MyBenchmark.haval_128_3File    avgt       20    435,391        2,432  ms/op
o.f.j.t.MyBenchmark.haval_128_4File    avgt       20    544,450        0,869  ms/op
o.f.j.t.MyBenchmark.haval_128_5File    avgt       20    679,956        6,720  ms/op
o.f.j.t.MyBenchmark.haval_160_3File    avgt       20    435,459        1,898  ms/op
o.f.j.t.MyBenchmark.haval_160_4File    avgt       20    544,699        0,828  ms/op
o.f.j.t.MyBenchmark.haval_160_5File    avgt       20    679,638        6,719  ms/op
o.f.j.t.MyBenchmark.haval_192_3File    avgt       20    435,654        2,904  ms/op
o.f.j.t.MyBenchmark.haval_192_4File    avgt       20    544,799        0,778  ms/op
o.f.j.t.MyBenchmark.haval_192_5File    avgt       20    678,541        6,358  ms/op
o.f.j.t.MyBenchmark.haval_224_3File    avgt       20    436,464        2,698  ms/op
o.f.j.t.MyBenchmark.haval_224_4File    avgt       20    545,871        2,818  ms/op
o.f.j.t.MyBenchmark.haval_224_5File    avgt       20    679,071        6,258  ms/op
o.f.j.t.MyBenchmark.haval_256_3File    avgt       20    436,651        1,924  ms/op
o.f.j.t.MyBenchmark.haval_256_4File    avgt       20    544,264        1,081  ms/op
o.f.j.t.MyBenchmark.haval_256_5File    avgt       20    678,517        6,426  ms/op
o.f.j.t.MyBenchmark.md2File            avgt       20   9214,714        6,688  ms/op
o.f.j.t.MyBenchmark.md4File            avgt       20    224,631        0,502  ms/op
o.f.j.t.MyBenchmark.md5File            avgt       20    266,585        0,804  ms/op
o.f.j.t.MyBenchmark.rhf32File          avgt       20    159,488        1,457  ms/op
o.f.j.t.MyBenchmark.rhf64File          avgt       20    173,144        1,654  ms/op
o.f.j.t.MyBenchmark.ripemd128File      avgt       20    423,250        2,816  ms/op
o.f.j.t.MyBenchmark.ripemd160File      avgt       20    828,331        3,189  ms/op
o.f.j.t.MyBenchmark.ripemd256File      avgt       20    447,119        2,778  ms/op
o.f.j.t.MyBenchmark.ripemd320File      avgt       20   2076,641       21,722  ms/op
o.f.j.t.MyBenchmark.sha0File           avgt       20    438,427        0,897  ms/op
o.f.j.t.MyBenchmark.sha1File           avgt       20    363,860        1,084  ms/op
o.f.j.t.MyBenchmark.sha224File         avgt       20    682,369        8,998  ms/op
o.f.j.t.MyBenchmark.sha256File         avgt       20    584,969        6,629  ms/op
o.f.j.t.MyBenchmark.sha3224File        avgt       20   4823,599       30,223  ms/op
o.f.j.t.MyBenchmark.sha3256File        avgt       20   4924,646       32,551  ms/op
o.f.j.t.MyBenchmark.sha3288File        avgt       20   5427,045       30,353  ms/op
o.f.j.t.MyBenchmark.sha3384File        avgt       20   6403,095       31,862  ms/op
o.f.j.t.MyBenchmark.sha3512File        avgt       20   9273,542       53,351  ms/op
o.f.j.t.MyBenchmark.sha384File         avgt       20    410,861        2,919  ms/op
o.f.j.t.MyBenchmark.sha512File         avgt       20    410,585        3,047  ms/op
o.f.j.t.MyBenchmark.skein1024File      avgt       20    493,805        3,813  ms/op
o.f.j.t.MyBenchmark.skein256File       avgt       20    560,816        4,096  ms/op
o.f.j.t.MyBenchmark.skein512File       avgt       20    455,122        3,005  ms/op
o.f.j.t.MyBenchmark.sm3File            avgt       20    679,023        6,513  ms/op
o.f.j.t.MyBenchmark.sum16File          avgt       20     67,517        1,423  ms/op
o.f.j.t.MyBenchmark.sum24File          avgt       20     66,534        0,572  ms/op
o.f.j.t.MyBenchmark.sum32File          avgt       20     66,290        0,636  ms/op
o.f.j.t.MyBenchmark.sum8File           avgt       20     66,238        0,647  ms/op
o.f.j.t.MyBenchmark.sumbsdFile         avgt       20    147,537        1,448  ms/op
o.f.j.t.MyBenchmark.sumsysvFile        avgt       20     66,603        0,778  ms/op
o.f.j.t.MyBenchmark.tiger128File       avgt       20    330,288        1,509  ms/op
o.f.j.t.MyBenchmark.tiger160File       avgt       20    330,097        1,359  ms/op
o.f.j.t.MyBenchmark.tiger2File         avgt       20    331,827        1,640  ms/op
o.f.j.t.MyBenchmark.tigerFile          avgt       20    331,660        1,216  ms/op
o.f.j.t.MyBenchmark.treetiger2File     avgt       20    407,938        3,470  ms/op
o.f.j.t.MyBenchmark.treetigerFile      avgt       20    406,500        2,132  ms/op
o.f.j.t.MyBenchmark.whirlpool0File     avgt       20   1648,710        6,130  ms/op
o.f.j.t.MyBenchmark.whirlpool1File     avgt       20   1649,499        7,440  ms/op
o.f.j.t.MyBenchmark.whirlpool2File     avgt       20   1660,393       30,004  ms/op
o.f.j.t.MyBenchmark.xor8File           avgt       20     66,255        0,770  ms/op
    
     */
    static {
        WEIGHTS.put("adler32", 102);
        WEIGHTS.put("cksum", 245);
        WEIGHTS.put("crc16", 181);
        WEIGHTS.put("crc24", 246);
        WEIGHTS.put("crc32", 105);
        WEIGHTS.put("crc32_bzip2", 246);
        WEIGHTS.put("crc32_mpeg2", 246);
        WEIGHTS.put("crc64", 206);
        WEIGHTS.put("crc8", 211);
        WEIGHTS.put("ed2k", 225);
        WEIGHTS.put("elf", 241);
        WEIGHTS.put("fcs16", 245);
        WEIGHTS.put("gost", 16362);
        WEIGHTS.put("has160", 426);
        WEIGHTS.put("haval_128_3", 435);
        WEIGHTS.put("haval_128_4", 544);
        WEIGHTS.put("haval_128_5", 680);
        WEIGHTS.put("haval_160_3", 435);
        WEIGHTS.put("haval_160_4", 545);
        WEIGHTS.put("haval_160_5", 680);
        WEIGHTS.put("haval_192_3", 436);
        WEIGHTS.put("haval_192_4", 545);
        WEIGHTS.put("haval_192_5", 679);
        WEIGHTS.put("haval_224_3", 436);
        WEIGHTS.put("haval_224_4", 546);
        WEIGHTS.put("haval_224_5", 679);
        WEIGHTS.put("haval_256_3", 437);
        WEIGHTS.put("haval_256_4", 544);
        WEIGHTS.put("haval_256_5", 679);
        WEIGHTS.put("md2", 9215);
        WEIGHTS.put("md4", 225);
        WEIGHTS.put("md5", 267);
        WEIGHTS.put("rhf32", 159);
        WEIGHTS.put("rhf64", 173);
        WEIGHTS.put("ripemd128", 423);
        WEIGHTS.put("ripemd160", 828);
        WEIGHTS.put("ripemd256", 447);
        WEIGHTS.put("ripemd320", 2077);
        WEIGHTS.put("sha0", 438);
        WEIGHTS.put("sha1", 364);
        WEIGHTS.put("sha224", 682);
        WEIGHTS.put("sha256", 585);
        WEIGHTS.put("sha3-224", 4824);
        WEIGHTS.put("sha3-256", 4925);
        WEIGHTS.put("sha3-288", 5427);
        WEIGHTS.put("sha3-384", 6403);
        WEIGHTS.put("sha3-512", 9274);
        WEIGHTS.put("sha384", 411);
        WEIGHTS.put("sha512", 411);
        WEIGHTS.put("skein-1024", 494);
        WEIGHTS.put("skein-256", 561);
        WEIGHTS.put("skein-512", 455);
        WEIGHTS.put("sm3", 679);
        WEIGHTS.put("sum16", 68);
        WEIGHTS.put("sum24", 67);
        WEIGHTS.put("sum32", 66);
        WEIGHTS.put("sum8", 66);
        WEIGHTS.put("sumbsd", 148);
        WEIGHTS.put("sumsysv", 67);
        WEIGHTS.put("tiger128", 330);
        WEIGHTS.put("tiger160", 330);
        WEIGHTS.put("tiger2", 332);
        WEIGHTS.put("tiger", 332);
        WEIGHTS.put("tree:tiger2", 408);
        WEIGHTS.put("tree:tiger", 407);
        WEIGHTS.put("whirlpool0", 1649);
        WEIGHTS.put("whirlpool1", 1649);
        WEIGHTS.put("whirlpool2", 1660);
        WEIGHTS.put("xor8", 66);
    }

    public static int getWeight(String name) {
        return WEIGHTS.getOrDefault(name, 1);
    }

    public static int getMaxWeight() {
        int max = 0;
        for (int w : WEIGHTS.values()) {
            if (w > max) {
                max = w;
            }
        }
        return max;
    }

    public static HashAlgorithm getAlgorithm(AbstractChecksum cs) {
        String name = cs.getName();
        return new HashAlgorithm(name, cs);
    }
    private final String name;
    private final int weight;
    private final AbstractChecksum cs;

    private HashAlgorithm(String name, AbstractChecksum cs) {
        this.name = name;
        /* if there's no weigth for a given algorithm, then
         * it is possible that one processor gets much more work to do
         * than the rest and therefore the total time will no be optimal.
         * So use the main method in this class to regenerate the algorithm's
         * weights every time a new hash function in added or when a new
         * implementation is used.
         */
        this.weight = WEIGHTS.getOrDefault(name, 1);
        this.cs = cs;
    }

    public String getName() {
        return name;
    }

    public int getWeight() {
        return weight;
    }

    public AbstractChecksum getChecksum() {
        return this.cs;
    }

    @Override
    public int compareTo(HashAlgorithm t) {
        return t.getWeight() - this.getWeight();
    }

}
