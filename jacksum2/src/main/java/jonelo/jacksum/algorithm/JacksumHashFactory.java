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

import gnu.crypto.hash.HashFactory;
import gnu.crypto.hash.IMessageDigest;
import java.util.Set;

import gnu.crypto.hash.Haval;

import gnu.crypto.hash.Sha384;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.function.Supplier;
import jonelo.jacksum.adapt.gnu.crypto.hash.Has160;
import jonelo.jacksum.adapt.gnu.crypto.hash.Sha0;
import jonelo.jacksum.adapt.gnu.crypto.hash.Sha224;
import jonelo.jacksum.adapt.gnu.crypto.hash.Tiger128;
import jonelo.jacksum.adapt.gnu.crypto.hash.Tiger160;
import jonelo.jacksum.adapt.gnu.crypto.hash.Tiger2;
import jonelo.jacksum.adapt.gnu.crypto.hash.Whirlpool2000;
import jonelo.jacksum.adapt.gnu.crypto.hash.Whirlpool2003;

/**
 * 
 * @author Federico Tello Gentile <federicotg@gmail.com>
 */
public class JacksumHashFactory implements JacksumRegistry {

    private static final Map<String, Supplier<IMessageDigest>> FACTORY_MAP = new HashMap<>();

    static {

        FACTORY_MAP.put(WHIRLPOOL2000_HASH, () -> new Whirlpool2000());
        FACTORY_MAP.put(WHIRLPOOL2003_HASH, () -> new Whirlpool2003());
        FACTORY_MAP.put(SHA224_HASH, () -> new Sha224());
        FACTORY_MAP.put(SHA384_HASH, () -> new Sha384());
        FACTORY_MAP.put(TIGER2_HASH, () -> new Tiger2());
        FACTORY_MAP.put(TIGER160_HASH, () -> new Tiger160());
        FACTORY_MAP.put(TIGER128_HASH, () -> new Tiger128());
        FACTORY_MAP.put(HAVAL_HASH_128_3, () -> new Haval(Haval.HAVAL_128_BIT, Haval.HAVAL_3_ROUND));
        FACTORY_MAP.put(HAVAL_HASH_128_4, () -> new Haval(Haval.HAVAL_128_BIT, Haval.HAVAL_4_ROUND));
        FACTORY_MAP.put(HAVAL_HASH_128_5, () -> new Haval(Haval.HAVAL_128_BIT, Haval.HAVAL_5_ROUND));
        FACTORY_MAP.put(HAVAL_HASH_160_3, () -> new Haval(Haval.HAVAL_160_BIT, Haval.HAVAL_3_ROUND));
        FACTORY_MAP.put(HAVAL_HASH_160_4, () -> new Haval(Haval.HAVAL_160_BIT, Haval.HAVAL_4_ROUND));
        FACTORY_MAP.put(HAVAL_HASH_160_5, () -> new Haval(Haval.HAVAL_160_BIT, Haval.HAVAL_5_ROUND));
        FACTORY_MAP.put(HAVAL_HASH_192_3, () -> new Haval(Haval.HAVAL_192_BIT, Haval.HAVAL_3_ROUND));
        FACTORY_MAP.put(HAVAL_HASH_192_4, () -> new Haval(Haval.HAVAL_192_BIT, Haval.HAVAL_4_ROUND));
        FACTORY_MAP.put(HAVAL_HASH_192_5, () -> new Haval(Haval.HAVAL_192_BIT, Haval.HAVAL_5_ROUND));
        FACTORY_MAP.put(HAVAL_HASH_224_3, () -> new Haval(Haval.HAVAL_224_BIT, Haval.HAVAL_3_ROUND));
        FACTORY_MAP.put(HAVAL_HASH_224_4, () -> new Haval(Haval.HAVAL_224_BIT, Haval.HAVAL_4_ROUND));
        FACTORY_MAP.put(HAVAL_HASH_224_5, () -> new Haval(Haval.HAVAL_224_BIT, Haval.HAVAL_5_ROUND));
        FACTORY_MAP.put(HAVAL_HASH_256_3, () -> new Haval(Haval.HAVAL_256_BIT, Haval.HAVAL_3_ROUND));
        FACTORY_MAP.put(HAVAL_HASH_256_4, () -> new Haval(Haval.HAVAL_256_BIT, Haval.HAVAL_4_ROUND));
        FACTORY_MAP.put(HAVAL_HASH_256_5, () -> new Haval(Haval.HAVAL_256_BIT, Haval.HAVAL_5_ROUND));
        FACTORY_MAP.put(SHA0_HASH, () -> new Sha0());
        FACTORY_MAP.put(HAS160_HASH, () -> new Has160());
    }

    private JacksumHashFactory() {

    }

    public static IMessageDigest getInstance(String name) {
        if (name == null) {
            return null;
        }
        name = name.trim();
        IMessageDigest result = HashFactory.getInstance(name);
        if (result == null) {
            result = FACTORY_MAP.get(name.toLowerCase()).get();
        }
        if (result != null && !result.selfTest()) {
            throw new InternalError(result.name());
        }

        return result;
    }

    public static final Set getNames() {
        Set hs = new HashSet();
        hs.addAll(HashFactory.getNames());
        hs.add(SHA224_HASH);
        hs.add(SHA256_HASH);
        hs.add(SHA384_HASH);
        hs.add(SHA512_HASH);
        hs.add(TIGER_HASH);
        hs.add(HAVAL_HASH);
        hs.add(MD5_HASH);
        hs.add(MD4_HASH);
        hs.add(MD2_HASH);
        hs.add(SHA0_HASH);
        return Collections.unmodifiableSet(hs);

    }
}
