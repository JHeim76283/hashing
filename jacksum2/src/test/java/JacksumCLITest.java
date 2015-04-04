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

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.PrintStream;
import java.util.List;
import jonelo.jacksum.JacksumAPI;
import jonelo.jacksum.algorithm.Algorithm;
import jonelo.jacksum.concurrent.Encoding;
import jonelo.jacksum.concurrent.Jacksum2Cli;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;
import org.kohsuke.args4j.CmdLineException;

/**
 *
 * @author fede
 */
public class JacksumCLITest {

    public JacksumCLITest() {
    }

    @Before
    public void setUp() {
    }

    @After
    public void tearDown() {
    }

    @Test
    public void algorithmTest() throws Exception {
        Jacksum2Cli app = this.getApp("-a", "md5", "aa.txt");
        assertEquals(1, app.getAlgorithms().size());
        assertEquals(Algorithm.MD5, app.getAlgorithms().get(0));
        assertEquals(Encoding.HEX, app.getEncoding());
        assertTrue(app.getFilenames().contains("aa.txt"));
    }

    @Test
    public void algorithmWithAlias() throws Exception {
        Jacksum2Cli app = this.getApp("-a", "md5sum", "aa.txt");
        assertEquals(1, app.getAlgorithms().size());
        assertEquals(Algorithm.MD5, app.getAlgorithms().get(0));
    }

    @Test
    public void algorithmWithOtherAlias() throws Exception {
        Jacksum2Cli app = this.getApp("-a", "crc-32", "aa.txt");
        assertEquals(1, app.getAlgorithms().size());
        assertEquals(Algorithm.CRC32, app.getAlgorithms().get(0));
    }

    @Test
    public void algorithmCombined() throws Exception {
        Jacksum2Cli app = this.getApp("-a", "crc-32+md5+sha1", "aa.txt");
        assertEquals(3, app.getAlgorithms().size());
        assertTrue(app.getAlgorithms().contains(Algorithm.CRC32));
        assertTrue(app.getAlgorithms().contains(Algorithm.SHA1));
        assertTrue(app.getAlgorithms().contains(Algorithm.MD5));
    }

    @Test
    public void encodingTest() throws Exception {
        Jacksum2Cli app = this.getApp("-E", "bb");
        assertEquals(Encoding.BB, app.getEncoding());

        app = this.getApp("-E", "hex");
        assertEquals(Encoding.HEX, app.getEncoding());
    }

    @Test(expected = CmdLineException.class)
    public void missingEncoding() throws Exception {
        this.getApp("-E");
    }

    @Test
    public void quick() throws Exception {
         Jacksum2Cli app = this.getApp("-a", "md5", "-q", "txt:This is a test");
         String actual = app.getFormattedQuickHash();
         assertEquals("ce114e4501d2f4e2dcea3e17b546f339\t14", actual);
    }
    
    //@Test
    public void json() throws Exception {
         Jacksum2Cli app = this.getApp("-a", "md5", "-m", "-q", "txt:This is a test");
         app.printResults();
         //String actual = app.getFormattedQuickHash();
         //assertEquals("ce114e4501d2f4e2dcea3e17b546f339\t14", actual);
    }
    
    @Test(expected = CmdLineException.class)
    public void missingAlgorithm() throws Exception {
        this.getApp("-a");
    }

    @Test(expected = CmdLineException.class)
    public void wrongAlgorithm() throws Exception {
        this.getApp("-a", "algoritmoInexistente");
    }

    @Test(expected = CmdLineException.class)
    public void wrongAlgorithmCombined() throws Exception {
        this.getApp("-a", "md5+nada");
    }

    @Test
    public void genericCRC() throws Exception {
        final String filename = JacksonJacksumTest.class.getResource("/image.jpg").getFile();
        Jacksum2Cli app = this.getApp("-a", "crc:24,864CFB,B704CE,false,false,0", filename);
        List<String> hashes = app.getFormattedFileHashes();
        assertEquals(1, hashes.size());
        String actual = hashes.get(0);
        assertEquals("1f168d\t2921017\t"+filename, actual);
    }
    
    
    @Test
    public void alternate() throws Exception {
        Jacksum2Cli app = this.getApp("-A");
        assertTrue(app.isAlternate());
    }

    @Test(expected = CmdLineException.class)
    public void incompatibleParams() throws Exception {
        Jacksum2Cli app = this.getApp("-h", "-a", "md5", "-A");
    }

    @Test
    public void defaultValues() throws Exception {
        Jacksum2Cli app = this.getApp("someFile.txt");
        assertFalse(app.isAlternate());
        assertFalse(app.isFileParamIsDirAndWorkingDirectory());
        assertFalse(app.isFullPath());
        assertFalse(app.isHelp());
        assertFalse(app.isIgnoreSymbolicLinksToDirectories());
        assertFalse(app.isLowerHexaFormat());
        assertFalse(app.isOnlyListModifiedFiles());
        assertFalse(app.isPrintMetainfo());
        assertFalse(app.isProcessFilesOnly());
        assertFalse(app.isRecursive());
        assertFalse(app.isShowVersion());
        assertFalse(app.isSummary());
        assertFalse(app.isUpperHexaFormat());
        assertNull(app.getAlgorithms());
        assertNull(app.getCheckFile());
        assertEquals(" ", app.getCustomSeparator());
        assertNull(app.getDateFormat());
        assertEquals(Encoding.HEX, app.getEncoding());
        assertNull(app.getErrorFileName());
        assertNull(app.getExpectedHashValue());
        assertEquals(1, app.getFilenames().size());
        assertTrue(app.getFilenames().contains("someFile.txt"));
        assertNull(app.getFormat());
        assertTrue(' ' == app.getHexaGroupSeparatorChar());
        assertEquals(-1, app.getHexaGroupSize());
        assertNull(app.getOutputFile());
        assertNull(app.getOverwriteErrorFileName());
        assertNull(app.getOverwriteOutputFile());
        assertEquals(File.separatorChar, app.getSeparator());
        assertNull(app.getPrefixToIgnoreInFilenames());
        assertNull(app.getQuickSequence());
        assertNull(app.getVerbosity());
    }

    @Test
    public void help() throws Exception {

        Jacksum2Cli app = new Jacksum2Cli();
        ByteArrayOutputStream baos = new ByteArrayOutputStream(64 * 1024);
        app.setOut(new PrintStream(baos));
        assertEquals(baos.size(), 0);
        app.initArgs(new String[]{"-h"});
        app.printResults();
        assertTrue(baos.size() > 10000);

    }
    
    
    @Test
    public void allAlgorithms() throws Exception{
        Jacksum2Cli app = this.getApp("-a" , "all", "aa.txt");
        assertEquals(JacksumAPI.getAvailableAlgorithms().size(), app.getAlgorithms().size());
        
    }
    /* ----------------------- */
    private Jacksum2Cli getApp(String... args) throws Exception {
        Jacksum2Cli app = new Jacksum2Cli();
        app.initArgs(args);
        return app;
    }
}
