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

import java.io.File;
import jonelo.jacksum.concurrent.Encoding;
import jonelo.jacksum.concurrent.Jacksum2Cli;
import jonelo.sugar.util.ExitException;
import org.bouncycastle.util.Encodable;
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
    public void algorithmTest() throws CmdLineException {
        Jacksum2Cli app  = this.getApp("-a", "md5", "aa.txt");
        assertEquals("md5", app.getAlgorithm());
        assertTrue(app.getFilenames().contains("aa.txt"));
    }

    @Test
    public void encodingTest() throws CmdLineException {
        Jacksum2Cli app  = this.getApp("-E", "bb");
        assertEquals(Encoding.BB, app.getEncoding());
        
        app  = this.getApp("-E", "hex");
        assertEquals(Encoding.HEX, app.getEncoding());
    }
    
    @Test(expected=CmdLineException.class)
    public void missingEncoding() throws CmdLineException{
        this.getApp("-E");
    }
    
    @Test(expected=CmdLineException.class)
    public void missingAlgorithm() throws CmdLineException{
        this.getApp("-a");
    }
    
    @Test
    public void alternate() throws CmdLineException{
        Jacksum2Cli app = this.getApp("-A");
        assertTrue(app.isAlternate());
    }
    
    
    @Test
    public void defaultValues() throws CmdLineException{
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
        assertEquals("sha1", app.getAlgorithm());
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
    
    
    
    
    /* ----------------------- */
    private Jacksum2Cli getApp(String... args) throws CmdLineException{
        Jacksum2Cli app  = new Jacksum2Cli();
        app.doMain(args);
        return app;
    }
}
