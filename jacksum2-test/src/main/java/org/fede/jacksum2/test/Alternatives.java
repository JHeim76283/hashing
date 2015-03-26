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

import java.security.NoSuchAlgorithmException;
import java.util.concurrent.TimeUnit;
import jonelo.jacksum.algorithm.Crc32;
import jonelo.jacksum.algorithm.FCS32;
import jonelo.jacksum.algorithm.JacksumRegistry;
import jonelo.jacksum.algorithm.MD;
import jonelo.jacksum.algorithm.MDbouncycastle;
import jonelo.jacksum.algorithm.MDgnu;
import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.BenchmarkMode;
import org.openjdk.jmh.annotations.Mode;
import org.openjdk.jmh.annotations.OutputTimeUnit;

/**
 *
 * @author fede
 */
@BenchmarkMode(Mode.AverageTime)
@OutputTimeUnit(TimeUnit.MILLISECONDS)
public class Alternatives {

    private static final String FILE = "/home/fede/NetBeansProjects/ipc-address-calc-old.tar.xz";

    @Benchmark
    public String md2BouncyCastle() throws NoSuchAlgorithmException {
        return bouncyCastle(JacksumRegistry.MD2_HASH);
    }

    @Benchmark
    public String md2GnuCrypto() throws NoSuchAlgorithmException {
        return gnuCrypto(JacksumRegistry.MD2_HASH);
    }

    @Benchmark
    public String md5BouncyCastle() throws NoSuchAlgorithmException {
        return bouncyCastle(JacksumRegistry.MD5_HASH);
    }

    @Benchmark
    public String md5GnuCrypto() throws NoSuchAlgorithmException {
        return gnuCrypto(JacksumRegistry.MD5_HASH);
    }

    @Benchmark
    public String md5JDK() throws NoSuchAlgorithmException {
        return jdk(JacksumRegistry.MD5_HASH);
    }

    @Benchmark
    public String ripemd128GnuCrypto() throws NoSuchAlgorithmException {
        return gnuCrypto(JacksumRegistry.RIPEMD128_HASH);
    }

    @Benchmark
    public String ripemd128BouncyCastle() throws NoSuchAlgorithmException {
        return bouncyCastle(JacksumRegistry.RIPEMD128_HASH);
    }

    @Benchmark
    public String ripemd160GnuCrypto() throws NoSuchAlgorithmException {
        return gnuCrypto(JacksumRegistry.RIPEMD160_HASH);
    }

    @Benchmark
    public String ripemd160BouncyCastle() throws NoSuchAlgorithmException {
        return bouncyCastle(JacksumRegistry.RIPEMD160_HASH);
    }

    @Benchmark
    public String sha1BouncyCastle() throws NoSuchAlgorithmException {
        return bouncyCastle(JacksumRegistry.SHA1_HASH);
    }

    @Benchmark
    public String sha1GnuCrypto() throws NoSuchAlgorithmException {
        return gnuCrypto(JacksumRegistry.SHA1_HASH);
    }

    @Benchmark
    public String sha1JDK() throws NoSuchAlgorithmException {
        return jdk(JacksumRegistry.SHA1_HASH);
    }

    @Benchmark
    public String sha224BouncyCastle() throws NoSuchAlgorithmException {
        return bouncyCastle(JacksumRegistry.SHA224_HASH);
    }

    @Benchmark
    public String sha224Jonelo() throws NoSuchAlgorithmException {
        return BenchmarkUtil.getFileHashValue(new MDgnu(JacksumRegistry.SHA224_HASH), FILE);
    }

    @Benchmark
    public String sha256JDK() throws NoSuchAlgorithmException {
        return jdk(JacksumRegistry.SHA256_HASH);
    }

    @Benchmark
    public String sha256Gnu() throws NoSuchAlgorithmException {
        return gnuCrypto(JacksumRegistry.SHA256_HASH);
    }

    @Benchmark
    public String sha256BouncyCastle() throws NoSuchAlgorithmException {
        return bouncyCastle(JacksumRegistry.SHA256_HASH);
    }

    @Benchmark
    public String sha384JDK() throws NoSuchAlgorithmException {
        return jdk(JacksumRegistry.SHA384_HASH);
    }

    @Benchmark
    public String sha384Gnu() throws NoSuchAlgorithmException {
        return gnuCrypto(JacksumRegistry.SHA384_HASH);
    }

    @Benchmark
    public String sha384BouncyCastle() throws NoSuchAlgorithmException {
        return bouncyCastle(JacksumRegistry.SHA384_HASH);
    }

    @Benchmark
    public String sha512JDK() throws NoSuchAlgorithmException {
        return jdk(JacksumRegistry.SHA512_HASH);
    }

    @Benchmark
    public String sha512Gnu() throws NoSuchAlgorithmException {
        return gnuCrypto(JacksumRegistry.SHA512_HASH);
    }

    @Benchmark
    public String sha512BouncyCastle() throws NoSuchAlgorithmException {
        return bouncyCastle(JacksumRegistry.SHA512_HASH);
    }

    @Benchmark
    public String tigerGnu() throws NoSuchAlgorithmException {
        return gnuCrypto(JacksumRegistry.TIGER_HASH);
    }

    @Benchmark
    public String tigerBouncyCastle() throws NoSuchAlgorithmException {
        return bouncyCastle(JacksumRegistry.TIGER_HASH);
    }

    @Benchmark
    public String whirlpoolGnu() throws NoSuchAlgorithmException {
        return gnuCrypto(JacksumRegistry.WHIRLPOOL_HASH);
    }

    @Benchmark
    public String whirlpoolBouncyCastle() throws NoSuchAlgorithmException {
        return bouncyCastle(JacksumRegistry.WHIRLPOOL_HASH);
    }
    
    @Benchmark
    public String crc32JDK() throws NoSuchAlgorithmException {
        return  BenchmarkUtil.getFileHashValue(new Crc32(), FILE);
    }
    
    @Benchmark
    public String crc32Jonelo() throws NoSuchAlgorithmException {
        return  BenchmarkUtil.getFileHashValue(new FCS32(), FILE);
    }

    /* -------------------------- */
    private String jdk(String name) throws NoSuchAlgorithmException {
        return BenchmarkUtil.getFileHashValue(new MD(name), FILE);
    }

    private String bouncyCastle(String name) throws NoSuchAlgorithmException {
        return BenchmarkUtil.getFileHashValue(new MDbouncycastle(name), FILE);
    }

    private String gnuCrypto(String name) throws NoSuchAlgorithmException {
        return BenchmarkUtil.getFileHashValue(new MDgnu(name), FILE);
    }

}
