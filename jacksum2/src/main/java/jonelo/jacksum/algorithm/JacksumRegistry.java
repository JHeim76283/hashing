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

import gnu.crypto.Registry;

/**
 * 
 * @author Federico Tello Gentile <federicotg@gmail.com>
 */
public interface JacksumRegistry extends Registry{
       // added by jonelo
   String SHA224_HASH        = "sha-224";

   String WHIRLPOOL2000_HASH = "whirlpool_2000";
   String WHIRLPOOL2003_HASH = "whirlpool_2003";
   
      // added by jonelo
   String SHA0_HASH        = "sha-0";
   
   String TIGER2_HASH        = "tiger2";
   String TIGER160_HASH      = "tiger-160";
   String TIGER128_HASH      = "tiger-128";
   
   String HAS160_HASH        = "has-160";
   
   String HAVAL_HASH_128_3 = "haval_128_3";
   String HAVAL_HASH_128_4 = "haval_128_4";
   String HAVAL_HASH_128_5 = "haval_128_5";
   String HAVAL_HASH_160_3 = "haval_160_3";
   String HAVAL_HASH_160_4 = "haval_160_4";
   String HAVAL_HASH_160_5 = "haval_160_5";
   String HAVAL_HASH_192_3 = "haval_192_3";
   String HAVAL_HASH_192_4 = "haval_192_4";
   String HAVAL_HASH_192_5 = "haval_192_5";
   String HAVAL_HASH_224_3 = "haval_224_3";
   String HAVAL_HASH_224_4 = "haval_224_4";
   String HAVAL_HASH_224_5 = "haval_224_5";
   String HAVAL_HASH_256_3 = "haval_256_3";
   String HAVAL_HASH_256_4 = "haval_256_4";
   String HAVAL_HASH_256_5 = "haval_256_5";
}
