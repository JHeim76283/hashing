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
import jonelo.jacksum.algorithm.Algorithm;
import jonelo.jacksum.concurrent.Encoding;
import jonelo.jacksum.concurrent.Jacksum2Cli;
import jonelo.sugar.util.ExitException;
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
    public void algorithmTest() throws CmdLineException, ExitException {
        Jacksum2Cli app  = this.getApp("-a", "md5", "aa.txt");
        assertEquals(Algorithm.MD5, app.getAlgorithm());
        assertTrue(app.getFilenames().contains("aa.txt"));
    }
    
    
    @Test
    public void algorithmWithAlias() throws CmdLineException , ExitException{
        Jacksum2Cli app  = this.getApp("-a", "md5sum", "aa.txt");
        assertEquals(Algorithm.MD5, app.getAlgorithm());
    }

    @Test
    public void algorithmWithOtherAlias() throws CmdLineException, ExitException {
        Jacksum2Cli app  = this.getApp("-a", "crc-32", "aa.txt");
        assertEquals(Algorithm.CRC32, app.getAlgorithm());
    }

    
    @Test
    public void encodingTest() throws CmdLineException, ExitException {
        Jacksum2Cli app  = this.getApp("-E", "bb");
        assertEquals(Encoding.BB, app.getEncoding());
        
        app  = this.getApp("-E", "hex");
        assertEquals(Encoding.HEX, app.getEncoding());
    }
    
    @Test(expected=CmdLineException.class)
    public void missingEncoding() throws CmdLineException, ExitException{
        this.getApp("-E");
    }
    
    @Test(expected=CmdLineException.class)
    public void missingAlgorithm() throws CmdLineException, ExitException{
        this.getApp("-a");
    }

    @Test(expected=CmdLineException.class)
    public void wrongAlgorithm() throws CmdLineException, ExitException{
        this.getApp("-a" , "algoritmoInexistente");
    }
    
    @Test
    public void alternate() throws CmdLineException, ExitException{
        Jacksum2Cli app = this.getApp("-A");
        assertTrue(app.isAlternate());
    }

    @Test(expected=CmdLineException.class)
    public void incompatibleParams() throws CmdLineException, ExitException{
        Jacksum2Cli app = this.getApp("-h", "-a", "md5", "-A");
    }
    
    
    @Test
    public void defaultValues() throws CmdLineException, ExitException{
        Jacksum2Cli app = this.getApp("someFile.txt");
        assertFalse(app.isAlternate());
        assertFalse(app.isFileParamIsDirAndWorkingDirectory());
        assertFalse(app.isFullPath());
        assertFalse(app.isHelp());
        assertFalse(app.isIgnoreSymbolicLinksToDirectories());
        assertTrue(app.isLowerHexaFormat());
        assertFalse(app.isOnlyListModifiedFiles());
        assertFalse(app.isPrintMetainfo());
        assertFalse(app.isProcessFilesOnly());
        assertFalse(app.isRecursive());
        assertFalse(app.isShowVersion());
        assertFalse(app.isSummary());
        assertFalse(app.isUpperHexaFormat());
        assertEquals(Algorithm.SHA1, app.getAlgorithm());
        assertNull(app.getCheckFile());
        assertNull(app.getCustomSeparator());
        assertEquals("yyyyMMddHHmmss",app.getDateFormat());
        assertEquals(Encoding.HEX, app.getEncoding());
        assertNull(app.getErrorFileName());
        assertNull(app.getExpectedHashValue());
        assertEquals(1, app.getFilenames().size());
        assertTrue(app.getFilenames().contains("someFile.txt"));
        assertNull(app.getFormat());
        assertNull(app.getHexaGroupSeparatorChar());
        assertEquals(-1, app.getHexaGroupSize());
        assertNull(app.getOutputFile());
        assertNull(app.getOverwriteErrorFileName());
        assertNull(app.getOverwriteOutputFile());
        assertEquals(File.pathSeparatorChar, app.getPathSeparator());
        assertNull(app.getPrefixToIgnoreInFilenames());
        assertNull(app.getQuickSequence());
        assertNull(app.getVerbosity());
    }
    
    @Test
    public void help() throws CmdLineException, ExitException{
        
        Jacksum2Cli app  = new Jacksum2Cli();
        ByteArrayOutputStream baos = new ByteArrayOutputStream(64*1024);
        app.setOut(new PrintStream(baos));
        assertEquals(baos.size(), 0);
        app.doMain(new String[]{"-h"});
        assertTrue(baos.size() > 10000);
        
    }
    
    /* ----------------------- */
    private Jacksum2Cli getApp(String... args) throws CmdLineException, ExitException{
        Jacksum2Cli app  = new Jacksum2Cli();
        app.doMain(args);
        return app;
    }
}
