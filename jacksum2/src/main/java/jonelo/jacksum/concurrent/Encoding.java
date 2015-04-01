/*
 * Copyright (C) 2015 fede
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

import java.math.BigInteger;
import java.util.Base64;
import jonelo.jacksum.util.Service;
import jonelo.sugar.util.Base32;
import jonelo.sugar.util.BubbleBabble;

/**
 * 
 * @author Federico Tello Gentile <federicotg@gmail.com>
 */
public enum Encoding {

    BIN("bin") {
                @Override
                public String encode(int group, char groupChar, byte[] byteArray) {
                    return Service.formatAsBits(byteArray);
                }

            },
    DEC("dec") {
                @Override
                public String encode(int group, char groupChar, byte[] byteArray) {
                    return new BigInteger(1, byteArray).toString();
                }

            },
    OCT("oct") {
                @Override
                public String encode(int group, char groupChar, byte[] byteArray) {
                    return new BigInteger(1, byteArray).toString(8);
                }

            },
    HEX("hex") {
                @Override
                public String encode(int group, char groupChar, byte[] byteArray) {
                    return Service.format(byteArray, false, group, groupChar);
                }

            },
    HEXUP("hexup") {
                @Override
                public String encode(int group, char groupChar, byte[] byteArray) {
                    return Service.format(byteArray, true, group, groupChar);
                }

            },
    BASE16("base16") {
                @Override
                public String encode(int group, char groupChar, byte[] byteArray) {
                    return Service.format(byteArray, true, 0, groupChar);
                }

            },
    BASE32("base32") {
                @Override
                public String encode(int group, char groupChar, byte[] byteArray) {
                    return Base32.encode(byteArray);
                }

            },
    BASE64("base64") {
                @Override
                public String encode(int group, char groupChar, byte[] byteArray) {
                    return Base64.getEncoder().encodeToString(byteArray);
                }

            },
    BB("bubblebabble") {
                @Override
                public String encode(int group, char groupChar, byte[] byteArray) {
                    return BubbleBabble.encode(byteArray);
                }
            };

    private final String value;

    Encoding(String val) {
        this.value = val;
    }

    public String getValue() {
        return value;
    }

    public abstract String encode(int group, char groupChar, byte[] byteArray);

}
