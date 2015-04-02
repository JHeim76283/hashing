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

import java.util.regex.Pattern;

/**
 *
 * @author Federico Tello Gentile <federicotg@gmail.com>
 */
public enum QuickSequenceType {

    TXT {

                @Override
                byte[] decode(String sequence) {
                    return sequence.getBytes();
                }

            },
    DEC {

                @Override
                byte[] decode(String sequence) {
                    String[] values = DECIMAL_SEQUENCE_SEPARATOR.split(sequence);
                    byte[] answer = new byte[values.length];
                    for (int i = 0; i < values.length; i++) {
                        answer[i] = Byte.parseByte(values[i]);
                    }
                    return answer;
                }
            },
    HEX {

                @Override
                byte[] decode(String sequence) {
                    if (sequence.length() % 2 != 0) {
                        throw new NumberFormatException("Hexadecimal requires an even number of digits.");
                    }
                    byte[] answer = new byte[sequence.length() / 2];
                    int x = 0;
                    for (int i = 0; i < sequence.length();) {
                        String str = sequence.substring(i, i += 2);
                        answer[x++] = (byte) Byte.parseByte(str, 16);
                    }
                    return answer;
                }
            };

    protected static final Pattern DECIMAL_SEQUENCE_SEPARATOR = Pattern.compile(",");

    abstract byte[] decode(String sequence);

}
