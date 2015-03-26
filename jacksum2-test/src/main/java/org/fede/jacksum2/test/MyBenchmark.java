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
package org.fede.jacksum2.test;

import java.util.concurrent.TimeUnit;
import jonelo.jacksum.algorithm.AbstractChecksum;
import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.BenchmarkMode;
import org.openjdk.jmh.annotations.Mode;
import org.openjdk.jmh.annotations.OutputTimeUnit;
/**
 * 
 * @author Federico Tello Gentile <federicotg@gmail.com>
 */
public class MyBenchmark {

    private static final String FILE = "/home/fede/NetBeansProjects/ipc-address-calc-old.tar.xz";

    @Benchmark
    @BenchmarkMode(Mode.AverageTime)
    @OutputTimeUnit(TimeUnit.MILLISECONDS)
    public String adler32File() {
        return this.getFileHashValue(this.getChecksum("adler32"), FILE);
    }

    @Benchmark
    @BenchmarkMode(Mode.AverageTime)
    @OutputTimeUnit(TimeUnit.MILLISECONDS)
    public String cksumFile() {
        return this.getFileHashValue(this.getChecksum("cksum"), FILE);
    }

    @Benchmark
    @BenchmarkMode(Mode.AverageTime)
    @OutputTimeUnit(TimeUnit.MILLISECONDS)
    public String crc16File() {
        return this.getFileHashValue(this.getChecksum("crc16"), FILE);
    }

    @Benchmark
    @BenchmarkMode(Mode.AverageTime)
    @OutputTimeUnit(TimeUnit.MILLISECONDS)
    public String crc24File() {
        return this.getFileHashValue(this.getChecksum("crc24"), FILE);
    }

    @Benchmark
    @BenchmarkMode(Mode.AverageTime)
    @OutputTimeUnit(TimeUnit.MILLISECONDS)
    public String crc32File() {
        return this.getFileHashValue(this.getChecksum("crc32"), FILE);
    }

    @Benchmark
    @BenchmarkMode(Mode.AverageTime)
    @OutputTimeUnit(TimeUnit.MILLISECONDS)
    public String crc32_bzip2File() {
        return this.getFileHashValue(this.getChecksum("crc32_bzip2"), FILE);
    }

    @Benchmark
    @BenchmarkMode(Mode.AverageTime)
    @OutputTimeUnit(TimeUnit.MILLISECONDS)
    public String crc32_mpeg2File() {
        return this.getFileHashValue(this.getChecksum("crc32_mpeg2"), FILE);
    }

    @Benchmark
    @BenchmarkMode(Mode.AverageTime)
    @OutputTimeUnit(TimeUnit.MILLISECONDS)
    public String crc64File() {
        return this.getFileHashValue(this.getChecksum("crc64"), FILE);
    }

    @Benchmark
    @BenchmarkMode(Mode.AverageTime)
    @OutputTimeUnit(TimeUnit.MILLISECONDS)
    public String crc8File() {
        return this.getFileHashValue(this.getChecksum("crc8"), FILE);
    }

    @Benchmark
    @BenchmarkMode(Mode.AverageTime)
    @OutputTimeUnit(TimeUnit.MILLISECONDS)
    public String ed2kFile() {
        return this.getFileHashValue(this.getChecksum("ed2k"), FILE);
    }

    @Benchmark
    @BenchmarkMode(Mode.AverageTime)
    @OutputTimeUnit(TimeUnit.MILLISECONDS)
    public String elfFile() {
        return this.getFileHashValue(this.getChecksum("elf"), FILE);
    }

    @Benchmark
    @BenchmarkMode(Mode.AverageTime)
    @OutputTimeUnit(TimeUnit.MILLISECONDS)
    public String fcs16File() {
        return this.getFileHashValue(this.getChecksum("fcs16"), FILE);
    }

    @Benchmark
    @BenchmarkMode(Mode.AverageTime)
    @OutputTimeUnit(TimeUnit.MILLISECONDS)
    public String gostFile() {
        return this.getFileHashValue(this.getChecksum("gost"), FILE);
    }

    @Benchmark
    @BenchmarkMode(Mode.AverageTime)
    @OutputTimeUnit(TimeUnit.MILLISECONDS)
    public String has160File() {
        return this.getFileHashValue(this.getChecksum("has160"), FILE);
    }

    @Benchmark
    @BenchmarkMode(Mode.AverageTime)
    @OutputTimeUnit(TimeUnit.MILLISECONDS)
    public String haval_128_3File() {
        return this.getFileHashValue(this.getChecksum("haval_128_3"), FILE);
    }

    @Benchmark
    @BenchmarkMode(Mode.AverageTime)
    @OutputTimeUnit(TimeUnit.MILLISECONDS)
    public String haval_128_4File() {
        return this.getFileHashValue(this.getChecksum("haval_128_4"), FILE);
    }

    @Benchmark
    @BenchmarkMode(Mode.AverageTime)
    @OutputTimeUnit(TimeUnit.MILLISECONDS)
    public String haval_128_5File() {
        return this.getFileHashValue(this.getChecksum("haval_128_5"), FILE);
    }

    @Benchmark
    @BenchmarkMode(Mode.AverageTime)
    @OutputTimeUnit(TimeUnit.MILLISECONDS)
    public String haval_160_3File() {
        return this.getFileHashValue(this.getChecksum("haval_160_3"), FILE);
    }

    @Benchmark
    @BenchmarkMode(Mode.AverageTime)
    @OutputTimeUnit(TimeUnit.MILLISECONDS)
    public String haval_160_4File() {
        return this.getFileHashValue(this.getChecksum("haval_160_4"), FILE);
    }

    @Benchmark
    @BenchmarkMode(Mode.AverageTime)
    @OutputTimeUnit(TimeUnit.MILLISECONDS)
    public String haval_160_5File() {
        return this.getFileHashValue(this.getChecksum("haval_160_5"), FILE);
    }

    @Benchmark
    @BenchmarkMode(Mode.AverageTime)
    @OutputTimeUnit(TimeUnit.MILLISECONDS)
    public String haval_192_3File() {
        return this.getFileHashValue(this.getChecksum("haval_192_3"), FILE);
    }

    @Benchmark
    @BenchmarkMode(Mode.AverageTime)
    @OutputTimeUnit(TimeUnit.MILLISECONDS)
    public String haval_192_4File() {
        return this.getFileHashValue(this.getChecksum("haval_192_4"), FILE);
    }

    @Benchmark
    @BenchmarkMode(Mode.AverageTime)
    @OutputTimeUnit(TimeUnit.MILLISECONDS)
    public String haval_192_5File() {
        return this.getFileHashValue(this.getChecksum("haval_192_5"), FILE);
    }

    @Benchmark
    @BenchmarkMode(Mode.AverageTime)
    @OutputTimeUnit(TimeUnit.MILLISECONDS)
    public String haval_224_3File() {
        return this.getFileHashValue(this.getChecksum("haval_224_3"), FILE);
    }

    @Benchmark
    @BenchmarkMode(Mode.AverageTime)
    @OutputTimeUnit(TimeUnit.MILLISECONDS)
    public String haval_224_4File() {
        return this.getFileHashValue(this.getChecksum("haval_224_4"), FILE);
    }

    @Benchmark
    @BenchmarkMode(Mode.AverageTime)
    @OutputTimeUnit(TimeUnit.MILLISECONDS)
    public String haval_224_5File() {
        return this.getFileHashValue(this.getChecksum("haval_224_5"), FILE);
    }

    @Benchmark
    @BenchmarkMode(Mode.AverageTime)
    @OutputTimeUnit(TimeUnit.MILLISECONDS)
    public String haval_256_3File() {
        return this.getFileHashValue(this.getChecksum("haval_256_3"), FILE);
    }

    @Benchmark
    @BenchmarkMode(Mode.AverageTime)
    @OutputTimeUnit(TimeUnit.MILLISECONDS)
    public String haval_256_4File() {
        return this.getFileHashValue(this.getChecksum("haval_256_4"), FILE);
    }

    @Benchmark
    @BenchmarkMode(Mode.AverageTime)
    @OutputTimeUnit(TimeUnit.MILLISECONDS)
    public String haval_256_5File() {
        return this.getFileHashValue(this.getChecksum("haval_256_5"), FILE);
    }

    @Benchmark
    @BenchmarkMode(Mode.AverageTime)
    @OutputTimeUnit(TimeUnit.MILLISECONDS)
    public String md2File() {
        return this.getFileHashValue(this.getChecksum("md2"), FILE);
    }

    @Benchmark
    @BenchmarkMode(Mode.AverageTime)
    @OutputTimeUnit(TimeUnit.MILLISECONDS)
    public String md4File() {
        return this.getFileHashValue(this.getChecksum("md4"), FILE);
    }

    @Benchmark
    @BenchmarkMode(Mode.AverageTime)
    @OutputTimeUnit(TimeUnit.MILLISECONDS)
    public String md5File() {
        return this.getFileHashValue(this.getChecksum("md5"), FILE);
    }

    @Benchmark
    @BenchmarkMode(Mode.AverageTime)
    @OutputTimeUnit(TimeUnit.MILLISECONDS)
    public String rhf32File() {
        return this.getFileHashValue(this.getChecksum("rhf32"), FILE);
    }

    @Benchmark
    @BenchmarkMode(Mode.AverageTime)
    @OutputTimeUnit(TimeUnit.MILLISECONDS)
    public String rhf64File() {
        return this.getFileHashValue(this.getChecksum("rhf64"), FILE);
    }

    @Benchmark
    @BenchmarkMode(Mode.AverageTime)
    @OutputTimeUnit(TimeUnit.MILLISECONDS)
    public String ripemd128File() {
        return this.getFileHashValue(this.getChecksum("ripemd128"), FILE);
    }

    @Benchmark
    @BenchmarkMode(Mode.AverageTime)
    @OutputTimeUnit(TimeUnit.MILLISECONDS)
    public String ripemd160File() {
        return this.getFileHashValue(this.getChecksum("ripemd160"), FILE);
    }

    @Benchmark
    @BenchmarkMode(Mode.AverageTime)
    @OutputTimeUnit(TimeUnit.MILLISECONDS)
    public String ripemd256File() {
        return this.getFileHashValue(this.getChecksum("ripemd256"), FILE);
    }

    @Benchmark
    @BenchmarkMode(Mode.AverageTime)
    @OutputTimeUnit(TimeUnit.MILLISECONDS)
    public String ripemd320File() {
        return this.getFileHashValue(this.getChecksum("ripemd320"), FILE);
    }

    @Benchmark
    @BenchmarkMode(Mode.AverageTime)
    @OutputTimeUnit(TimeUnit.MILLISECONDS)
    public String sha0File() {
        return this.getFileHashValue(this.getChecksum("sha0"), FILE);
    }

    @Benchmark
    @BenchmarkMode(Mode.AverageTime)
    @OutputTimeUnit(TimeUnit.MILLISECONDS)
    public String sha1File() {
        return this.getFileHashValue(this.getChecksum("sha1"), FILE);
    }

    @Benchmark
    @BenchmarkMode(Mode.AverageTime)
    @OutputTimeUnit(TimeUnit.MILLISECONDS)
    public String sha224File() {
        return this.getFileHashValue(this.getChecksum("sha224"), FILE);
    }

    @Benchmark
    @BenchmarkMode(Mode.AverageTime)
    @OutputTimeUnit(TimeUnit.MILLISECONDS)
    public String sha256File() {
        return this.getFileHashValue(this.getChecksum("sha256"), FILE);
    }

    @Benchmark
    @BenchmarkMode(Mode.AverageTime)
    @OutputTimeUnit(TimeUnit.MILLISECONDS)
    public String sha3224File() {
        return this.getFileHashValue(this.getChecksum("sha3-224"), FILE);
    }

    @Benchmark
    @BenchmarkMode(Mode.AverageTime)
    @OutputTimeUnit(TimeUnit.MILLISECONDS)
    public String sha3256File() {
        return this.getFileHashValue(this.getChecksum("sha3-256"), FILE);
    }

    @Benchmark
    @BenchmarkMode(Mode.AverageTime)
    @OutputTimeUnit(TimeUnit.MILLISECONDS)
    public String sha3288File() {
        return this.getFileHashValue(this.getChecksum("sha3-288"), FILE);
    }

    @Benchmark
    @BenchmarkMode(Mode.AverageTime)
    @OutputTimeUnit(TimeUnit.MILLISECONDS)
    public String sha3384File() {
        return this.getFileHashValue(this.getChecksum("sha3-384"), FILE);
    }

    @Benchmark
    @BenchmarkMode(Mode.AverageTime)
    @OutputTimeUnit(TimeUnit.MILLISECONDS)
    public String sha3512File() {
        return this.getFileHashValue(this.getChecksum("sha3-512"), FILE);
    }

    @Benchmark
    @BenchmarkMode(Mode.AverageTime)
    @OutputTimeUnit(TimeUnit.MILLISECONDS)
    public String sha384File() {
        return this.getFileHashValue(this.getChecksum("sha384"), FILE);
    }

    @Benchmark
    @BenchmarkMode(Mode.AverageTime)
    @OutputTimeUnit(TimeUnit.MILLISECONDS)
    public String sha512File() {
        return this.getFileHashValue(this.getChecksum("sha512"), FILE);
    }

    @Benchmark
    @BenchmarkMode(Mode.AverageTime)
    @OutputTimeUnit(TimeUnit.MILLISECONDS)
    public String skein1024File() {
        return this.getFileHashValue(this.getChecksum("skein-1024"), FILE);
    }

    @Benchmark
    @BenchmarkMode(Mode.AverageTime)
    @OutputTimeUnit(TimeUnit.MILLISECONDS)
    public String skein256File() {
        return this.getFileHashValue(this.getChecksum("skein-256"), FILE);
    }

    @Benchmark
    @BenchmarkMode(Mode.AverageTime)
    @OutputTimeUnit(TimeUnit.MILLISECONDS)
    public String skein512File() {
        return this.getFileHashValue(this.getChecksum("skein-512"), FILE);
    }

    @Benchmark
    @BenchmarkMode(Mode.AverageTime)
    @OutputTimeUnit(TimeUnit.MILLISECONDS)
    public String sm3File() {
        return this.getFileHashValue(this.getChecksum("sm3"), FILE);
    }

    @Benchmark
    @BenchmarkMode(Mode.AverageTime)
    @OutputTimeUnit(TimeUnit.MILLISECONDS)
    public String sum16File() {
        return this.getFileHashValue(this.getChecksum("sum16"), FILE);
    }

    @Benchmark
    @BenchmarkMode(Mode.AverageTime)
    @OutputTimeUnit(TimeUnit.MILLISECONDS)
    public String sum24File() {
        return this.getFileHashValue(this.getChecksum("sum24"), FILE);
    }

    @Benchmark
    @BenchmarkMode(Mode.AverageTime)
    @OutputTimeUnit(TimeUnit.MILLISECONDS)
    public String sum32File() {
        return this.getFileHashValue(this.getChecksum("sum32"), FILE);
    }

    @Benchmark
    @BenchmarkMode(Mode.AverageTime)
    @OutputTimeUnit(TimeUnit.MILLISECONDS)
    public String sum8File() {
        return this.getFileHashValue(this.getChecksum("sum8"), FILE);
    }

    @Benchmark
    @BenchmarkMode(Mode.AverageTime)
    @OutputTimeUnit(TimeUnit.MILLISECONDS)
    public String sumbsdFile() {
        return this.getFileHashValue(this.getChecksum("sumbsd"), FILE);
    }

    @Benchmark
    @BenchmarkMode(Mode.AverageTime)
    @OutputTimeUnit(TimeUnit.MILLISECONDS)
    public String sumsysvFile() {
        return this.getFileHashValue(this.getChecksum("sumsysv"), FILE);
    }

    @Benchmark
    @BenchmarkMode(Mode.AverageTime)
    @OutputTimeUnit(TimeUnit.MILLISECONDS)
    public String tigerFile() {
        return this.getFileHashValue(this.getChecksum("tiger"), FILE);
    }

    @Benchmark
    @BenchmarkMode(Mode.AverageTime)
    @OutputTimeUnit(TimeUnit.MILLISECONDS)
    public String tiger128File() {
        return this.getFileHashValue(this.getChecksum("tiger128"), FILE);
    }

    @Benchmark
    @BenchmarkMode(Mode.AverageTime)
    @OutputTimeUnit(TimeUnit.MILLISECONDS)
    public String tiger160File() {
        return this.getFileHashValue(this.getChecksum("tiger160"), FILE);
    }

    @Benchmark
    @BenchmarkMode(Mode.AverageTime)
    @OutputTimeUnit(TimeUnit.MILLISECONDS)
    public String tiger2File() {
        return this.getFileHashValue(this.getChecksum("tiger2"), FILE);
    }

    @Benchmark
    @BenchmarkMode(Mode.AverageTime)
    @OutputTimeUnit(TimeUnit.MILLISECONDS)
    public String treetigerFile() {
        return this.getFileHashValue(this.getChecksum("tree:tiger"), FILE);
    }

    @Benchmark
    @BenchmarkMode(Mode.AverageTime)
    @OutputTimeUnit(TimeUnit.MILLISECONDS)
    public String treetiger2File() {
        return this.getFileHashValue(this.getChecksum("tree:tiger2"), FILE);
    }

    @Benchmark
    @BenchmarkMode(Mode.AverageTime)
    @OutputTimeUnit(TimeUnit.MILLISECONDS)
    public String whirlpool0File() {
        return this.getFileHashValue(this.getChecksum("whirlpool0"), FILE);
    }

    @Benchmark
    @BenchmarkMode(Mode.AverageTime)
    @OutputTimeUnit(TimeUnit.MILLISECONDS)
    public String whirlpool1File() {
        return this.getFileHashValue(this.getChecksum("whirlpool1"), FILE);
    }

    @Benchmark
    @BenchmarkMode(Mode.AverageTime)
    @OutputTimeUnit(TimeUnit.MILLISECONDS)
    public String whirlpool2File() {
        return this.getFileHashValue(this.getChecksum("whirlpool2"), FILE);
    }

    @Benchmark
    @BenchmarkMode(Mode.AverageTime)
    @OutputTimeUnit(TimeUnit.MILLISECONDS)
    public String xor8File() {
        return this.getFileHashValue(this.getChecksum("xor8"), FILE);
    }

    private String getFileHashValue(AbstractChecksum chsum, String fileName) {
        return BenchmarkUtil.getFileHashValue(chsum, fileName);
    }

    private AbstractChecksum getChecksum(String algorithmName) {
        return BenchmarkUtil.getChecksum(algorithmName);
    }

}
