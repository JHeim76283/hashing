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
package org.fede.hashero;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Collection;
import javax.swing.JCheckBox;

/**
 *
 * @author Federico Tello Gentile <federicotg@gmail.com>
 */
public class ComboBoxSelectionController {

    private int selectionCount;
    private JCheckBox all;
    private JCheckBox none;
    private Collection<JCheckBox> combos;

    public void setCombos(final JCheckBox all, final JCheckBox none, Collection<JCheckBox> combos) {
        this.all = all;
        this.none = none;
        this.combos = combos;
        this.selectionCount = 0;

        this.all.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent ae) {
                if (ComboBoxSelectionController.this.all.isSelected()) {
                    ComboBoxSelectionController.this.selectionCount = ComboBoxSelectionController.this.combos.size();
                    none.setSelected(false);
                    for (JCheckBox cb : ComboBoxSelectionController.this.combos) {
                        cb.setSelected(true);
                    }
                }
            }
        });


        this.none.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent ae) {
                if (ComboBoxSelectionController.this.none.isSelected()) {
                    ComboBoxSelectionController.this.selectionCount = 0;
                    all.setSelected(false);
                    for (JCheckBox cb : ComboBoxSelectionController.this.combos) {
                        cb.setSelected(false);
                    }
                }
            }
        });


        ActionListener cl = new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent ae) {

                if (((JCheckBox) ae.getSource()).isSelected()) {
                    ComboBoxSelectionController.this.selectionCount++;
                } else {
                    ComboBoxSelectionController.this.selectionCount--;
                }

                ComboBoxSelectionController.this.all.setSelected(ComboBoxSelectionController.this.selectionCount == ComboBoxSelectionController.this.combos.size());
                ComboBoxSelectionController.this.none.setSelected(ComboBoxSelectionController.this.selectionCount == 0);
            }
        };

        for (JCheckBox cb : this.combos) {
            cb.addActionListener(cl);
        }
    }

    public void selectNone() {
        for (JCheckBox cb : ComboBoxSelectionController.this.combos) {
            cb.setSelected(false);
        }
        this.selectionCount = 0;
        this.all.setSelected(false);
    }
}
