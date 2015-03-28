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
package jonelo.jacksum.algorithm;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 *
 * @author Federico Tello Gentile <federicotg@gmail.com>
 */
public enum Algorithm {

    ADLER32("Adler 32", "adler32", "adler-32"),
    CKSUM("cksum (Unix)", "cksum"),
    CRC16("CRC-16 (LHA/ARC)", "crc16", "crc-16"),
    CRC24("CRC-24 (Open PGP)", "crc24", "crc-24"),
    CRC32("CRC-32 (FCS-32)", "crc32", "crc-32", "fcs32", "fcs-32"),
    CRC32_BZIP2("CRC-32 (BZIP2)", "crc32_bzip2", "crc-32_bzip-2"),
    CRC32_MPEG2("CRC-32 (MPEG-2)", "crc32_mpeg2", "crc-32_mpeg-2"),
    CRC64("CRC-64 (ISO 3309)", "crc64", "crc-64"),
    CRC8("CRC-8 (FLAC)", "crc8", "crc-8"),
    ED2K("ed2k", "ed2k", "emule", "edonkey"),
    ELF("Elf", "elf", "elf-32", "elf32"),
    FCS16("FCS-16", "fcs16", "fcs-16", "crc16_x25", "crc-16_x-25"),
    GOST("GOST (R 34.11-94)", "gost"),
    HAS160("HAS-160", "has160", "has-160"),
    HAVAL_128_3("HAVAL 128 (3 rounds)", "haval_128_3", "haval"),
    HAVAL_128_4("HAVAL 128 (4 rounds)", "haval_128_4"),
    HAVAL_128_5("HAVAL 128 (5 rounds)", "haval_128_5"),
    HAVAL_160_3("HAVAL 160 (3 rounds)", "haval_160_3"),
    HAVAL_160_4("HAVAL 160 (4 rounds)", "haval_160_4"),
    HAVAL_160_5("HAVAL 160 (5 rounds)", "haval_160_5"),
    HAVAL_192_3("HAVAL 192 (3 rounds)", "haval_192_3"),
    HAVAL_192_4("HAVAL 192 (4 rounds)", "haval_192_4"),
    HAVAL_192_5("HAVAL 192 (5 rounds)", "haval_192_5"),
    HAVAL_224_3("HAVAL 224 (3 rounds)", "haval_224_3"),
    HAVAL_224_4("HAVAL 224 (4 rounds)", "haval_224_4"),
    HAVAL_224_5("HAVAL 224 (5 rounds)", "haval_224_5"),
    HAVAL_256_3("HAVAL 256 (3 rounds)", "haval_256_3"),
    HAVAL_256_4("HAVAL 256 (4 rounds)", "haval_256_4"),
    HAVAL_256_5("HAVAL 256 (5 rounds)", "haval_256_5"),
    MD2("MD2", "md2", "md2sum"),
    MD4("MD4", "md4", "md4sum"),
    MD5("MD5", "md5", "md5sum"),
    RHF32("Rabin Hash 32", "rhf32"),
    RHF64("Rabin Hash 64", "rhf64"),
    RIPEMD128("RIPEMD-128", "ripemd128", "ripemd-128", "ripe-md128", "rmd-128", "rmd128"),
    RIPEMD160("RIPEMD-160", "ripemd160", "ripemd-160", "ripe-md160", "rmd-160", "rmd160"),
    RIPEMD256("RIPEMD-256", "ripemd256", "ripemd-256", "ripe-md256", "rmd-256", "rmd256"),
    RIPEMD320("RIPEMD-320", "ripemd320", "ripemd-320", "ripe-md320", "rmd-320", "rmd320"),
    SHA0("SHA-0", "sha0", "sha-0"),
    SHA1("SHA-1 (SHA-160)", "sha1", "sha", "sha-1", "sha-160", "sha160"),
    SHA224("SHA-2 (SHA-224)", "sha224", "sha-224"),
    SHA256("SHA-2 (SHA-256)", "sha256", "sha-256"),
    SHA3_224("SHA3-224", "sha3-224"),
    SHA3_256("SHA3-256", "sha3-256"),
    SHA3_288("SHA3-288", "sha3-288"),
    SHA3_384("SHA3-384", "sha3-384"),
    SHA3_512("SHA3-512", "sha3-512"),
    SHA384("SHA-2 (SHA-384)", "sha384", "sha-384"),
    SHA512("SHA-2 (SHA-512)", "sha512", "sha-512"),
    SKEIN_1024("Skein-1024", "skein-1024"),
    SKEIN_256("Skein-256", "skein-256"),
    SKEIN_512("Skein-512", "skein-512"),
    SM3("SM3", "sm3", "alias"),
    SUM16("sum 16", "sum16", "sum-16"),
    SUM24("sum 24", "sum24", "sum-24"),
    SUM32("sum 32", "sum32", "sum-32"),
    SUM8("sum 8", "sum8", "sum-8"),
    SUMBSD("sum (BSD Unix)", "sumbsd", "bsdsum", "bsd"),
    SUMSYSV("sum (System V Unix)", "sumsysv", "sysvsum", "sysv"),
    TIGER("Tiger (Tiger/192)", "tiger", "tiger-192", "tiger192"),
    TIGER128("Tiger/128", "tiger128", "tiger-128"),
    TIGER160("Tiger/160", "tiger160", "tiger-160"),
    TIGER2("Tiger2", "tiger2"),
    TREE_TIGER("Tiger Tree Hash", "tree:tiger"),
    TREE_TIGER2("Tiger2 Tree Hash", "tree:tiger2"),
    WHIRLPOOL0("Whirlpool-0", "whirlpool0", "whirlpool-0"),
    WHIRLPOOL1("Whirlpool-1", "whirlpool1", "whirlpool-1"),
    WHIRLPOOL2("Whirlpool", "whirlpool2", "whirlpool-2", "whirlpool"),
    XOR8("XOR 8", "xor8", "xor-8");

    private String canonicalName;
    private Set<String> aliases;
    private String displayName;

    Algorithm(String displayName, String canonicalName, String... aliases) {
        this.canonicalName = canonicalName;
        this.aliases = new HashSet<>(Arrays.asList(aliases));
        this.displayName = displayName;
    }

    public String getCanonicalName() {
        return canonicalName;
    }

    public String getDisplayName() {
        return displayName;
    }

    public void mapAliases(Map<String, Algorithm> map){
        map.put(this.canonicalName, this);
        this.aliases.stream().forEach(alias -> map.put(alias, this));
    }
    
    
}
