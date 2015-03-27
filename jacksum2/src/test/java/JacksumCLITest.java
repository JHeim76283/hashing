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

import jonelo.jacksum.concurrent.Jacksum2Cli;
import jonelo.sugar.util.ExitException;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

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

    // TODO add test methods here.
    // The methods must be annotated with annotation @Test. For example:
    //
    @Test
    public void test1() throws ExitException {
    
        Jacksum2Cli app  = new Jacksum2Cli();
        
        app.doMain(new String[]{"-a", "md5", "aa.txt"});
        
        assertEquals("md5", app.getAlgorithm());
        assertTrue(app.getFilenames().contains("aa.txt"));
    
    }
}
