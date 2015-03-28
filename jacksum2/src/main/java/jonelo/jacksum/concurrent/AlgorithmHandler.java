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

import jonelo.jacksum.JacksumAPI;
import jonelo.jacksum.algorithm.Algorithm;
import org.kohsuke.args4j.CmdLineException;
import org.kohsuke.args4j.CmdLineParser;
import org.kohsuke.args4j.OptionDef;
import org.kohsuke.args4j.spi.Messages;
import org.kohsuke.args4j.spi.OneArgumentOptionHandler;
import org.kohsuke.args4j.spi.OptionHandler;
import org.kohsuke.args4j.spi.Parameters;
import org.kohsuke.args4j.spi.Setter;

/**
 *
 * @author Federico Tello Gentile <federicotg@gmail.com>
 */
public class AlgorithmHandler extends OneArgumentOptionHandler<Algorithm> {

    public AlgorithmHandler(CmdLineParser parser, OptionDef option, Setter<? super Algorithm> setter) {
        super(parser, option, setter);
    }

    @Override
    protected Algorithm parse(String argument) throws NumberFormatException, CmdLineException {
        Algorithm algo = JacksumAPI.getAlgorithm(argument);
        if (algo == null) {
            throw new CmdLineException(owner, Messages.ILLEGAL_OPERAND, "-a", argument);
        }
        return algo;
    }

}
