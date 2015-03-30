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

import java.util.List;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import jonelo.jacksum.JacksumAPI;
import jonelo.jacksum.algorithm.Algorithm;
import org.kohsuke.args4j.CmdLineException;
import org.kohsuke.args4j.CmdLineParser;
import org.kohsuke.args4j.OptionDef;
import org.kohsuke.args4j.spi.Messages;
import org.kohsuke.args4j.spi.OptionHandler;
import org.kohsuke.args4j.spi.Parameters;
import org.kohsuke.args4j.spi.Setter;

/**
 *
 * @author Federico Tello Gentile <federicotg@gmail.com>
 */
public class AlgorithmHandler extends OptionHandler<Algorithm> {

    private static final Pattern SPLIT_PATTERN = Pattern.compile("\\+");

    public AlgorithmHandler(CmdLineParser parser, OptionDef option, Setter<? super Algorithm> setter) {
        super(parser, option, setter);
    }

    @Override
    public int parseArguments(Parameters params) throws CmdLineException {
        final String argument = params.getParameter(0);

        List<Algorithm> algorithms;

        if ("all".equals(argument)) {
            algorithms = JacksumAPI.getAvailableAlgorithms().keySet().stream()
                    .map(algorithmName -> JacksumAPI.getAlgorithm(algorithmName))
                    .collect(Collectors.toList());

        } else {
            algorithms = SPLIT_PATTERN.splitAsStream(argument)
                    .map(algorithmName -> JacksumAPI.getAlgorithm(algorithmName))
                    .collect(Collectors.toList());
        }

        if (algorithms.contains(null)) {
            throw new CmdLineException(owner, Messages.ILLEGAL_OPERAND, "-a", argument);
        }

        for (Algorithm algo : algorithms) {
            this.setter.addValue(algo);
        }
        return 1;
    }

    @Override
    public String getDefaultMetaVariable() {
        return "algo";
    }

}
