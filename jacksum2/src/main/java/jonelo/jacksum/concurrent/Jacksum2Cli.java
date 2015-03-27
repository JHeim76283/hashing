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

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import jonelo.jacksum.algorithm.AbstractChecksum;
import org.kohsuke.args4j.Argument;
import org.kohsuke.args4j.CmdLineException;
import org.kohsuke.args4j.CmdLineParser;
import org.kohsuke.args4j.Option;
import org.kohsuke.args4j.OptionHandlerFilter;

/**
 *
 * @author Federico Tello Gentile <federicotg@gmail.com>
 */
public class Jacksum2Cli {

    @Option(name = "-a", metaVar = "algo")
    private String algorithm = "sha1";

    @Option(name = "-A")
    private boolean alternate = false;

    @Option(name = "-c", metaVar = "list")
    private String checkFile = null;

    @Option(name = "-d")
    private boolean ignoreSymbolicLinksToDirectories = false;

    @Option(name = "-e", metaVar = "seq")
    private String expectedHashValue = null;

    @Option(name = "-E", metaVar = "encoding")
    private String encoding = AbstractChecksum.HEX;

    @Option(name = "-f")
    private boolean processFilesOnly = false;

    @Option(name = "-F", metaVar = "format")
    private String format = null;

    @Option(name = "-g", metaVar = "count")
    private int hexaGroupSize = -1;

    @Option(name = "-G", metaVar = "separatorChar", depends = {"-g"})
    private Character hexaGroupSeparatorChar = null;

    @Option(name = "-h")
    private boolean help = false;

    @Option(name = "-p")
    private boolean fullPath = false;

    @Option(name = "-o", metaVar = "file", forbids = {"-O"})
    private String outputFile = null;

    @Option(name = "-O", metaVar = "file", forbids = {"-o"})
    private String overwriteOutputFile = null;

    @Option(name = "-I", metaVar = "string")
    private String prefixToIngonreInFilenames = null;

    @Option(name = "-l", depends = {"-c"})
    private boolean onlyListModifiedFiles = false;

    @Option(name = "-m")
    private boolean printMetainfo;

    @Option(name = "-P", metaVar = "char")
    private char pathSeparator = File.pathSeparatorChar;

    @Option(name = "-q")
    private String quickSequence;

    @Option(name = "-r")
    private boolean recursive;

    @Option(name = "-s", metaVar = "sep")
    private String customSeparator;

    @Option(name = "-S")

    private boolean summary;

    @Option(name = "-t", metaVar = "form")
    private String dateFormat;

    @Option(name = "-u", metaVar = "file")
    private String errorFileName;

    @Option(name = "-U", metaVar = "file")
    private String overwriteErrorFileName;

    @Option(name = "-v")
    private boolean showVersion;

    @Option(name = "-V", metaVar = "control")
    private String verbosity;

    @Option(name = "-w")
    private boolean fileParamIsDirAndWorkingDirectory;

    @Option(name = "-x", forbids = {"-E", "-X"})
    private boolean lowerHexaFormat;

    @Option(name = "-X", forbids = {"-E", "-x"})
    private boolean upperHexaFormat;

    @Argument
    private List<String> filenames = new ArrayList<>();

    public static void main(String[] args) {
        new Jacksum2Cli().doMain(args);
    }

    public void doMain(String[] args) {
        CmdLineParser parser = new CmdLineParser(this);

        try {
            // parse the arguments.
            parser.parseArgument(args);
            // you can parse additional arguments if you want.
            // parser.parseArgument("more","args");
            // after parsing arguments, you should check
            // if enough arguments are given.
            if (filenames.isEmpty()) {
                throw new CmdLineException(parser, "No argument is given");
            }
        } catch (CmdLineException e) {
            // if there's a problem in the command line,
            // you'll get this exception. this will report
            // an error message.
            System.err.println(e.getMessage());
            System.err.println("java SampleMain [options...] arguments...");
            // print the list of available options
            parser.printUsage(System.err);
            System.err.println();
            // print option sample. This is useful some time
            System.err.println(" Example: java SampleMain" + parser.printExample(OptionHandlerFilter.ALL));

        }

    }
}
