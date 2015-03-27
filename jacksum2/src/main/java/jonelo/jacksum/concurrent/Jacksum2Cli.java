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
import jonelo.jacksum.cli.JacksumHelp;
import jonelo.jacksum.ui.ExitStatus;
import jonelo.sugar.util.ExitException;
import org.kohsuke.args4j.Argument;
import org.kohsuke.args4j.CmdLineException;
import org.kohsuke.args4j.CmdLineParser;
import org.kohsuke.args4j.Option;

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
    private File checkFile = null;

    @Option(name = "-d")
    private boolean ignoreSymbolicLinksToDirectories = false;

    @Option(name = "-e", metaVar = "seq")
    private String expectedHashValue = null;

    @Option(name = "-E", metaVar = "encoding")
    private Encoding encoding = Encoding.HEX;

    @Option(name = "-f")
    private boolean processFilesOnly = false;

    @Option(name = "-F", metaVar = "format")
    private String format = null;

    @Option(name = "-g", metaVar = "count", depends = {"-x", "-X", "-E"})
    private int hexaGroupSize = -1;

    @Option(name = "-G", metaVar = "separatorChar", depends = {"-g"})
    private Character hexaGroupSeparatorChar = null;

    @Option(name = "-h")
    private boolean help = false;

    @Option(name = "-p")
    private boolean fullPath = false;

    @Option(name = "-o", metaVar = "file", forbids = {"-O"})
    private File outputFile = null;

    @Option(name = "-O", metaVar = "file", forbids = {"-o"})
    private File overwriteOutputFile = null;

    @Option(name = "-I", metaVar = "string")
    private String prefixToIgnoreInFilenames = null;

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
    private String dateFormat = "yyyyMMddHHmmss";

    @Option(name = "-u", metaVar = "file")
    private File errorFileName;

    @Option(name = "-U", metaVar = "file")
    private File overwriteErrorFileName;

    @Option(name = "-v")
    private boolean showVersion;

    @Option(name = "-V", metaVar = "control")
    private String verbosity;

    @Option(name = "-w")
    private boolean fileParamIsDirAndWorkingDirectory;

    @Option(name = "-x", forbids = {"-E", "-X"})
    private boolean lowerHexaFormat = true;

    @Option(name = "-X", forbids = {"-E", "-x"})
    private boolean upperHexaFormat;

    @Argument
    private List<String> filenames = new ArrayList<>();

    public static void main(String[] args) {
        try {
            new Jacksum2Cli().doMain(args);
        } catch (CmdLineException e) {
            if (e.getMessage() != null) {
                System.err.println(e.getMessage());
            }
            System.exit(ExitStatus.PARAMETER);
        }
    }

    public void doMain(String[] args) throws CmdLineException {
        CmdLineParser parser = new CmdLineParser(this);

            // parse the arguments.
            parser.parseArgument(args);
            // you can parse additional arguments if you want.
            // parser.parseArgument("more","args");
            // after parsing arguments, you should check
            // if enough arguments are given.

    }

    public String getAlgorithm() {
        return algorithm;
    }

    public boolean isAlternate() {
        return alternate;
    }

    public File getCheckFile() {
        return checkFile;
    }

    public boolean isIgnoreSymbolicLinksToDirectories() {
        return ignoreSymbolicLinksToDirectories;
    }

    public String getExpectedHashValue() {
        return expectedHashValue;
    }

    public Encoding getEncoding() {
        return encoding;
    }

    public boolean isProcessFilesOnly() {
        return processFilesOnly;
    }

    public String getFormat() {
        return format;
    }

    public int getHexaGroupSize() {
        return hexaGroupSize;
    }

    public Character getHexaGroupSeparatorChar() {
        return hexaGroupSeparatorChar;
    }

    public boolean isHelp() {
        return help;
    }

    public boolean isFullPath() {
        return fullPath;
    }

    public File getOutputFile() {
        return outputFile;
    }

    public File getOverwriteOutputFile() {
        return overwriteOutputFile;
    }

    public String getPrefixToIgnoreInFilenames() {
        return prefixToIgnoreInFilenames;
    }

    public boolean isOnlyListModifiedFiles() {
        return onlyListModifiedFiles;
    }

    public boolean isPrintMetainfo() {
        return printMetainfo;
    }

    public char getPathSeparator() {
        return pathSeparator;
    }

    public String getQuickSequence() {
        return quickSequence;
    }

    public boolean isRecursive() {
        return recursive;
    }

    public String getCustomSeparator() {
        return customSeparator;
    }

    public boolean isSummary() {
        return summary;
    }

    public String getDateFormat() {
        return dateFormat;
    }

    public File getErrorFileName() {
        return errorFileName;
    }

    public File getOverwriteErrorFileName() {
        return overwriteErrorFileName;
    }

    public boolean isShowVersion() {
        return showVersion;
    }

    public String getVerbosity() {
        return verbosity;
    }

    public boolean isFileParamIsDirAndWorkingDirectory() {
        return fileParamIsDirAndWorkingDirectory;
    }

    public boolean isLowerHexaFormat() {
        return lowerHexaFormat;
    }

    public boolean isUpperHexaFormat() {
        return upperHexaFormat;
    }

    public List<String> getFilenames() {
        return filenames;
    }
    
    
    
}
