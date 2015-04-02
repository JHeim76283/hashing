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
 * MDgnu is a wrapper class for accessing MessageDigests from the GNU crypto
 * project http://www.gnu.org/software/classpathx/crypto
 *
 ****************************************************************************
 */
package jonelo.jacksum.algorithm;

import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Supplier;
import org.bouncycastle.crypto.Digest;
import org.bouncycastle.crypto.digests.GOST3411Digest;
import org.bouncycastle.crypto.digests.MD2Digest;
import org.bouncycastle.crypto.digests.MD5Digest;
import org.bouncycastle.crypto.digests.RIPEMD128Digest;
import org.bouncycastle.crypto.digests.RIPEMD160Digest;
import org.bouncycastle.crypto.digests.RIPEMD256Digest;
import org.bouncycastle.crypto.digests.RIPEMD320Digest;
import org.bouncycastle.crypto.digests.SHA1Digest;
import org.bouncycastle.crypto.digests.SHA224Digest;
import org.bouncycastle.crypto.digests.SHA256Digest;
import org.bouncycastle.crypto.digests.SHA384Digest;
import org.bouncycastle.crypto.digests.SHA3Digest;
import org.bouncycastle.crypto.digests.SHA512Digest;
import org.bouncycastle.crypto.digests.SM3Digest;
import org.bouncycastle.crypto.digests.SkeinDigest;
import org.bouncycastle.crypto.digests.TigerDigest;
import org.bouncycastle.crypto.digests.WhirlpoolDigest;

/**
 * A wrapper class that can be used to compute GOST, RIPEMD256 and RIPEMD320
 * (provided by bouncycastle.org).
 */
public class MDbouncycastle extends AbstractChecksum {

    private static final Map<String, Supplier<Digest>> FACTORY_MAP = new HashMap<>();

    static {
        FACTORY_MAP.put(JacksumRegistry.SHA1_HASH, () -> new SHA1Digest());
        FACTORY_MAP.put(JacksumRegistry.SHA224_HASH, () -> new SHA224Digest());
        FACTORY_MAP.put(JacksumRegistry.MD2_HASH, () -> new MD2Digest());
        FACTORY_MAP.put(JacksumRegistry.MD5_HASH, () -> new MD5Digest());
        FACTORY_MAP.put(JacksumRegistry.WHIRLPOOL_HASH, () -> new WhirlpoolDigest());
        FACTORY_MAP.put(JacksumRegistry.TIGER_HASH, () -> new TigerDigest());
        FACTORY_MAP.put(JacksumRegistry.SHA256_HASH, () -> new SHA256Digest());
        FACTORY_MAP.put(JacksumRegistry.SHA384_HASH, () -> new SHA384Digest());
        FACTORY_MAP.put(JacksumRegistry.SHA512_HASH, () -> new SHA512Digest());
        
        FACTORY_MAP.put("gost", () -> new GOST3411Digest());
        FACTORY_MAP.put(JacksumRegistry.RIPEMD128_HASH, () -> new RIPEMD128Digest());
        FACTORY_MAP.put(JacksumRegistry.RIPEMD160_HASH, () -> new RIPEMD160Digest());
        FACTORY_MAP.put("ripemd256", () -> new RIPEMD256Digest());
        FACTORY_MAP.put("ripemd320", () -> new RIPEMD320Digest());

        FACTORY_MAP.put("sha3-224", () -> new SHA3Digest(224));
        FACTORY_MAP.put("sha3-256", () -> new SHA3Digest(256));
        FACTORY_MAP.put("sha3-288", () -> new SHA3Digest(288));
        FACTORY_MAP.put("sha3-384", () -> new SHA3Digest(384));
        FACTORY_MAP.put("sha3-512", () -> new SHA3Digest(512));

        FACTORY_MAP.put("sm3", () -> new SM3Digest());
        FACTORY_MAP.put("skein-256", () -> new SkeinDigest(SkeinDigest.SKEIN_256, 256));
        FACTORY_MAP.put("skein-512", () -> new SkeinDigest(SkeinDigest.SKEIN_512, 512));
        FACTORY_MAP.put("skein-1024", () -> new SkeinDigest(SkeinDigest.SKEIN_1024, 1024));
        
    }

    private Digest md = null;
    private boolean virgin = true;
    private byte[] digest = null;

    /**
     * Creates new MDbouncycastle
     */
    public MDbouncycastle(String arg) throws NoSuchAlgorithmException {
        // value0; we don't use value, we use md
        length = 0;
        filename = null;
        separator = " ";
        encoding = HEX;
        virgin = true;
        this.setName(arg);
        Digest answer = FACTORY_MAP.get(arg.toLowerCase()).get();

        if (answer == null) {
            throw new NoSuchAlgorithmException(arg + " is an unknown algorithm.");
        }
        md = answer;

    }

    @Override
    public void reset() {
        md.reset();
        length = 0;
        virgin = true;
    }

    @Override
    public void update(byte[] buffer, int offset, int len) {
        md.update(buffer, offset, len);
        length += len;
    }

    @Override
    public void update(byte b) {
        md.update(b);
        length++;
    }

    @Override
    public String toString() {
        return getFormattedValue() + separator
                + (isTimestampWanted() ? getTimestampFormatted() + separator : "")
                + getFilename();
    }

    @Override
    public byte[] getByteArray() {
        if (virgin) {
            digest = new byte[md.getDigestSize()];
            md.doFinal(digest, 0);
            //digest=md.digest();
            virgin = false;
        }
        // we don't expose internal representations
        byte[] save = new byte[digest.length];
        System.arraycopy(digest, 0, save, 0, digest.length);
        return save;
    }

}
