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
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintStream;
import java.nio.file.FileSystem;
import java.nio.file.FileSystems;
import java.nio.file.FileVisitOption;
import java.nio.file.Files;
import java.nio.file.Path;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutionException;
import java.util.stream.Collectors;
import jonelo.jacksum.algorithm.Algorithm;
import jonelo.jacksum.ui.ExitStatus;
import org.kohsuke.args4j.Argument;
import org.kohsuke.args4j.CmdLineException;
import org.kohsuke.args4j.CmdLineParser;
import org.kohsuke.args4j.Option;

/**
 *
 * @author Federico Tello Gentile <federicotg@gmail.com>
 */
public class Jacksum2Cli {

    private PrintStream out = System.out;
    private PrintStream err = System.err;

    @Option(name = "-a", handler = AlgorithmHandler.class, metaVar = "algo")
    private List<Algorithm> algorithms;

    @Option(name = "-A")
    private boolean alternate = false;

    @Option(name = "-c", metaVar = "list")
    private File checkFile = null;

    @Option(name = "-d")
    private boolean ignoreSymbolicLinksToDirectories = false;

    @Option(name = "-e", metaVar = "seq")
    private String expectedHashValue = null;

    @Option(name = "-E", metaVar = "encoding", forbids = {"-x", "-X"})
    private Encoding encoding = Encoding.HEX;

    @Option(name = "-f")
    private boolean processFilesOnly = false;

    @Option(name = "-F", metaVar = "format", forbids = {"-m"})
    private String format = null;

    @Option(name = "-g", metaVar = "count")
    private int hexaGroupSize = -1;

    @Option(name = "-G", metaVar = "separatorChar", depends = {"-g"})
    private char hexaGroupSeparatorChar = ' ';

    @Option(name = "-h", forbids = {
        "-a", "-A", "-c", "-d",
        "-e", "-E", "-f", "-g", "-G", "-p", "-o", "-O",
        "-I", "-l", "-m", "-P", "-q", "-r", "-s", "-S",
        "-t", "-u", "-U", "-v", "-V", "-w", "-x", "-X"})
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

    @Option(name = "-m", forbids = {"-F"})
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
    private String dateFormat = null;

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
    private boolean lowerHexaFormat = false;

    @Option(name = "-X", forbids = {"-E", "-x"})
    private boolean upperHexaFormat = false;

    @Argument
    private List<String> filenames = new ArrayList<>();

    public static void main(String[] args) {
        try {
            Jacksum2Cli app = new Jacksum2Cli();
            app.initArgs(args);

            app.printResults();
        } catch (CmdLineException | IOException | NoSuchAlgorithmException | InterruptedException | ExecutionException e) {
            System.err.println(e.getMessage());
            System.exit(ExitStatus.PARAMETER);
        }
    }

    public void initArgs(String[] args) throws CmdLineException {

        CmdLineParser parser = new CmdLineParser(this);

        // parse the arguments.
        parser.parseArgument(args);
        // you can parse additional arguments if you want.
        // parser.parseArgument("more","args");
        // after parsing arguments, you should check
        // if enough arguments are given.

    }

    public void printResults() throws IOException, NoSuchAlgorithmException, InterruptedException, ExecutionException {
        if (this.isHelp()) {
            this.printHelp();
        } else {
            for (String resultString : this.getFomattedFileHashes()) {
                this.out.println(resultString);
            }
        }
    }

    public List<String> getFomattedFileHashes() throws IOException, NoSuchAlgorithmException, InterruptedException, ExecutionException {

        List<Path> allFiles = new ArrayList<>();

        final int maxDepth = this.recursive ? Integer.MAX_VALUE : 1;

        FileSystem fs = FileSystems.getDefault();

        FileVisitOption[] options = this.ignoreSymbolicLinksToDirectories
                ? new FileVisitOption[0]
                : new FileVisitOption[]{FileVisitOption.FOLLOW_LINKS};

        final Map<Path, Long> fileSizes = new ConcurrentHashMap<>();
        final Map<Path, Long> fileLastModified = new ConcurrentHashMap<>();

        for (String filename : this.filenames) {
            Files.walk(fs.getPath(filename), maxDepth, options)
                    .filter(path -> Files.isRegularFile(path))
                    .forEach(path -> {
                        allFiles.add(path);
                        try {
                            fileSizes.put(path, Files.size(path));
                            fileLastModified.put(path, Files.getLastModifiedTime(path).toMillis());
                        } catch (IOException ioEx) {
                            fileSizes.put(path, -1l);
                            fileLastModified.put(path, 0l);
                        }
                    });
        }

        final Map<Pair<Path, Algorithm>, byte[]> results = new ConcurrentHasher().hashFiles(
                allFiles,
                this.algorithms);

        
        final HashFormat hashFormat = format != null 
                ? new CustomHashFormat(format, encoding, hexaGroupSize, hexaGroupSeparatorChar, customSeparator, dateFormat)
                : new SimpleHashFormat(encoding, hexaGroupSize, hexaGroupSeparatorChar, dateFormat);

        return allFiles.parallelStream()
                .map(filename -> hashFormat.format(
                                this.algorithms,
                                this.algorithms.stream()
                                    .map(algo -> results.get(new Pair<>(filename, algo)))
                                    .collect(Collectors.toList()),
                                filename.toFile().getAbsolutePath(),
                                fileSizes.get(filename),
                                fileLastModified.get(filename)))
                .collect(Collectors.toList());

    }

    public List<Algorithm> getAlgorithms() {
        return algorithms;
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
        if (this.lowerHexaFormat) {
            return Encoding.HEX;
        }
        if (this.upperHexaFormat) {
            return Encoding.HEXUP;
        }
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

    public char getHexaGroupSeparatorChar() {
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

    private void printHelp() throws IOException {
        try (InputStream helpTextStream = Jacksum2Cli.class.getResourceAsStream(ResourceBundle.getBundle("resources").getString("help_file"))) {
            byte[] buffer = new byte[8192];
            int read;
            while ((read = helpTextStream.read(buffer)) > 0) {
                this.out.write(buffer, 0, read);
            }
        }
    }

    public void setOut(PrintStream out) {
        this.out = out;
    }

    public void setErr(PrintStream err) {
        this.err = err;
    }

}
