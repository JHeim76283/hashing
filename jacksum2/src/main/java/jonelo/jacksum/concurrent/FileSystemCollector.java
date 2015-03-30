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
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import jonelo.jacksum.util.Service;

/**
 *
 * @author Federico Tello Gentile <federicotg@gmail.com>
 */
public class FileSystemCollector {

    private final boolean ignoreSymbolicLinksToDirectories;
    private boolean recursive;

    public FileSystemCollector(boolean ignoreSymbolicLinksToDirectories, boolean recursive) {
        this.ignoreSymbolicLinksToDirectories = ignoreSymbolicLinksToDirectories;
        this.recursive = recursive;

    }

    public List<String> collectFiles(List<String> roots) {
        List<String> answer = new ArrayList<>();
        if (recursive) {
            this.collect(roots, answer);
        } else {
            for (String root : roots) {
             
                File f = new File(root);
                if (f.isDirectory()) {
                    if (!(this.ignoreSymbolicLinksToDirectories && Service.isSymbolicLink(f))) {

                        String[] dirContents = f.list();
                        if (dirContents != null) {
                            answer.addAll(Arrays.stream(dirContents)
                                    .map(relativeName -> f.getAbsolutePath() + File.separator + relativeName)
                                    .filter(fullName -> new File(fullName).isFile())
                                    .collect(Collectors.toList()));
                        }
                    }
                } else {
                    answer.add(root);
                }

            }
        }
        return answer;
    }

    private void collect(List<String> roots, List<String> filenames) {
        for (String root : roots) {
            File f = new File(root);
            if (f.isDirectory()) {
                this.collectDir(f, filenames);
            } else {
                filenames.add(f.getAbsolutePath());
            }
        }
    }

    private void collectDir(File dir, List<String> filenames) {
        if (!(this.ignoreSymbolicLinksToDirectories && Service.isSymbolicLink(dir))) {
            String[] dirContents = dir.list();
            if (dirContents != null) {
                collect(Arrays.stream(dirContents)
                        .map(relativeName -> dir.getAbsolutePath() + File.separator + relativeName)
                        .collect(Collectors.toList()), filenames);
            }
        }
    }

}
