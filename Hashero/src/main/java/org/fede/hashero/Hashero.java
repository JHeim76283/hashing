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

import java.awt.Component;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.GraphicsEnvironment;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowEvent;

import java.io.File;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.BorderFactory;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingWorker;

import java.text.NumberFormat;
import java.util.Set;
import java.util.concurrent.ExecutionException;
import javax.swing.JProgressBar;
import javax.swing.border.Border;
import jonelo.jacksum.JacksumAPI;
import jonelo.jacksum.algorithm.AbstractChecksum;
import jonelo.jacksum.algorithm.CombinedChecksum;
import jonelo.jacksum.concurrent.HashAlgorithm;
/**
 * 
 * @author Federico Tello Gentile <federicotg@gmail.com>
 */
public class Hashero extends JFrame {

    // 0=Fichero ; 1=Cadena
    private static final int FILE_OPTION = 0;
    private static final int STRING_OPTION = 1;
    private static final NumberFormat NF = NumberFormat.getNumberInstance();
    private int fuenteDeDatos = FILE_OPTION;
    private Map<String, JCheckBox> checkBoxes;
    private Map<String, JProgressBar> progressBars;
    private JButton jButtonRutaFichero;
    private JButton jButtonCalcular;
    private JTextField jTextFieldEntrada;
    private JComboBox jComboSeleccion;
    private JTextField timeLabel;
    private JCheckBox selectAllCheckBox;
    private JCheckBox selectNoneCheckBox;
    private ComboBoxSelectionController comboController;
    private JTextField hashArea;

    /**
     * Constructor de la clase
     */
    public Hashero() {
        this.setTitle("Hashero v.0.2");
        initComponents();

    }

    private void initComponents() {

        this.setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        this.addWindowListener(new java.awt.event.WindowAdapter() {

            @Override
            public void windowClosed(WindowEvent evt) {
                formWindowClosed(evt);
            }
        });

        this.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.weightx = 1.0;
        gbc.weighty = 0.0;

        gbc.fill = GridBagConstraints.BOTH;

        gbc.insets = new Insets(3, 3, 3, 3);
        gbc.gridx = 0;
        gbc.gridy = 0;
        this.add(createTopPanel(), gbc);
        gbc.gridy = 1;
        this.add(createMiddlePanel(), gbc);
        gbc.gridy = 2;
        this.add(createBottomPanel(), gbc);

        pack();
        this.setMinimumSize(this.getSize());
        center(this);
    }

    private static void center(JFrame frame) {
        GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
        Point center = ge.getCenterPoint();
        int w = frame.getWidth();
        int h = frame.getHeight();
        int x = center.x - w / 2, y = center.y - h / 2;
        frame.setBounds(x, y, w, h);
    }

    private Component createStatusPanel() {
        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.weightx = 0.0;
        gbc.weighty = 1.0;


        gbc.insets = new Insets(3, 3, 3, 3);
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.LINE_END;
        panel.add(new JLabel("Tiempo:"), gbc);
        gbc.gridx = 1;
        gbc.anchor = GridBagConstraints.LINE_START;
        this.timeLabel = new JTextField(6);
        this.timeLabel.setEditable(false);
        panel.add(this.timeLabel, gbc);

        return panel;
    }

    /**
     * Creación del panel inferior
     */
    private Component createBottomPanel() {
        final JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.weightx = 1.0;

        // botón
        this.jButtonCalcular = new JButton("Calcular");
        this.jButtonCalcular.setToolTipText("Pulse aqu\u00ed para calcular los valores resumen de la entrada proporcionada");
        this.jButtonCalcular.addActionListener((ActionEvent evt) -> {
            jButtonCalcularActionPerformed(evt);
        });
        panel.add(this.jButtonCalcular, gbc);

        // Tiempo
        gbc.gridx++;
        gbc.gridx++;
        panel.add(createStatusPanel(), gbc);


        return panel;
    }

    private Component createMiddlePanel() {
        Set<Map.Entry<String, String>> algorithms = JacksumAPI.getAvailableAlgorithms().entrySet();

        this.checkBoxes = new HashMap<>();
        this.progressBars = new HashMap<>();

        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(3, 3, 3, 0);
        gbc.gridx = 0;
        gbc.gridy = 0;

        final Border emptyBorder = BorderFactory.createEmptyBorder();
        final Font font = new Font(Font.SANS_SERIF, Font.PLAIN, 11);

        final int maxWeight = HashAlgorithm.getMaxWeight();

        final int rows = Math.round((float) Math.ceil(algorithms.size() / 3));

        int col = 0;
        for (Map.Entry<String, String> ha : algorithms) {
            final JCheckBox cb = new JCheckBox();
            cb.setText(ha.getValue());

            cb.setToolTipText("Marque la casilla si desea obtener el resumen "
                    + ha.getValue());
            cb.setBorder(emptyBorder);
            cb.setFont(font);

            this.checkBoxes.put(ha.getKey(), cb);

            gbc.gridx = col + 0;
            gbc.anchor = GridBagConstraints.LINE_START;
            gbc.weightx = 0.0;
            gbc.weighty = 0.0;

            panel.add(cb, gbc);


            final JProgressBar pg = new JProgressBar();
            pg.setMaximum(maxWeight);
            pg.setMinimum(0);
            pg.setValue(HashAlgorithm.getWeight(ha.getKey()));
            pg.setStringPainted(true);
            pg.setString("");
            pg.setFont(font);
            pg.addMouseListener(new MouseAdapter() {

                @Override
                public void mouseClicked(MouseEvent me) {
                    hashArea.setText(pg.getString());
                }
            });
            pg.setToolTipText("Resumen " + ha.getValue()
                    + " de la entrada proporcionada");

            this.progressBars.put(ha.getKey(), pg);
            gbc.gridx = col + 1;
            gbc.fill = GridBagConstraints.HORIZONTAL;
            gbc.anchor = GridBagConstraints.LINE_END;
            gbc.weightx = 1.0;
            gbc.weighty = 0.0;
            panel.add(pg, gbc);

            gbc.gridy++;

            if (gbc.gridy % rows == 0) {
                col = col + 2;
                gbc.gridy = 0;
            }
        }

        this.comboController = new ComboBoxSelectionController();
        this.comboController.setCombos(selectAllCheckBox, selectNoneCheckBox, checkBoxes.values());

        // Textarea
        this.hashArea = new JTextField();
        this.hashArea.setEditable(false);

        gbc.gridx = 0;
        gbc.gridy = rows + 1;
        gbc.gridwidth = col + 3;

        panel.add(this.hashArea, gbc);


        return panel;
    }

    private Component createTopPanel() {
        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.weightx = 0.0;
        gbc.weighty = 0.0;

        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.gridx = 0;
        gbc.gridy = 0;

        gbc.gridwidth = 1;


        // textbox
        this.selectAllCheckBox = new JCheckBox("Todos");
        this.selectAllCheckBox.setToolTipText("Marque la casilla para seleccionar todos los algoritmos.");
        this.selectAllCheckBox.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));

        panel.add(this.selectAllCheckBox, gbc);

        //boton de limpiar
        gbc.gridx++;
        this.selectNoneCheckBox = new JCheckBox("Ninguno");
        this.selectNoneCheckBox.setToolTipText("Marque la casilla para deseleccionar todos los algoritmos.");
        this.selectNoneCheckBox.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));
        panel.add(this.selectNoneCheckBox, gbc);

        gbc.anchor = GridBagConstraints.LINE_START;
        this.jComboSeleccion = new JComboBox(new DefaultComboBoxModel(
                new String[]{"Fichero", "Texto"}));

        jComboSeleccion.setToolTipText("Seleccione aqu\u00ed el tipo de entrada");
        jComboSeleccion.addActionListener((ActionEvent evt) -> {
            jComboSeleccionActionPerformed(evt);
        });

        gbc.gridx++;
        panel.add(this.jComboSeleccion, gbc);

        this.jTextFieldEntrada = new JTextField(30);

        gbc.gridx++;
        gbc.weightx = 1.0;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        panel.add(this.jTextFieldEntrada, gbc);

        gbc.weightx = 0.0;
        jButtonRutaFichero = new JButton("...");
        jButtonRutaFichero.setToolTipText("Pulse aqu\u00ed para seleccionar un fichero del sistema");
        jButtonRutaFichero.addActionListener((ActionEvent evt) -> {
            jButtonRutaFicheroActionPerformed(evt);
        });

        gbc.fill = GridBagConstraints.NONE;
        gbc.gridx++;
        gbc.anchor = GridBagConstraints.LINE_END;
        panel.add(this.jButtonRutaFichero, gbc);

        return panel;
    }

    /**
     * Obtiene los datos de resumen que el usuario ha solicitado
     * @param evt Evento generado
     */
    private void jButtonCalcularActionPerformed(ActionEvent evt) {

        final JComponent button = ((JComponent) evt.getSource());
        // Comprueba que existan datos para calcular el resumen
        if (jTextFieldEntrada.getText().length() < 1) {
            resetText();
            // Muestra un mensaje de error
            JOptionPane.showMessageDialog(this,
                    "No se han proporcionado datos para calcular el resumen\n",
                    "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        final List<String> selection = filterSelection();

        if (selection.isEmpty()) {
            JOptionPane.showMessageDialog(this,
                    "No se ha seleccionado ningún algoritmo para calcular el resumen\n",
                    "Error", JOptionPane.ERROR_MESSAGE);
        } else {

            // Resumen de un fichero
            if (this.fuenteDeDatos == FILE_OPTION) {


                // Genera el fichero de entrada
                final String fichero = jTextFieldEntrada.getText();
                if (!new File(fichero).exists()) {

                    JOptionPane.showMessageDialog(this,
                            "El fichero seleccionado no existe\n",
                            "Error", JOptionPane.ERROR_MESSAGE);

                } else {
                    // Desactiva el botón mientras se procesan los datos
                    button.setEnabled(false);
                    // Genera un objeto SwingWorker para evitar bloquear la ventana
                    new SwingWorker<List<HashResult>, Object>() {

                        private long ms = 0L;

                        @Override
                        protected void done() {
                            List<HashResult> hashes;
                            try {
                                hashes = get();

                                setResultText(hashes);
                                // Activa el botón
                                button.setEnabled(true);
                                setElapsedTime(ms);
                            } catch (InterruptedException | ExecutionException ie) {
                                System.err.println(ie.getMessage());
                                ie.printStackTrace(System.err);
                                JOptionPane.showMessageDialog(Hashero.this,
                                        "Hubo un error al calcular los hashes\n",
                                        "Error", JOptionPane.ERROR_MESSAGE);
                                // Limpia los campos de texto
                                resetText();
                                // Activa el botón
                                button.setEnabled(true);
                            }

                        }

                        /*
                         * Este método se invoca en un thread distinto al thread de
                         * swing. Mientras se ejecuta, las ventanas de puden redibujar y
                         * de aceptan nuevos eventos.
                         */
                        @Override
                        protected List<HashResult> doInBackground() throws Exception {
                            this.ms = System.currentTimeMillis();
                            List<HashResult> answer = callFileJacksum(fichero, selection);
                            this.ms = System.currentTimeMillis() - ms;
                            return answer;
                        }
                    }.execute();

                    // Limpia los campos de texto
                    this.resetText();
                }
            } // Resumen de una cadena de texto
            else if (this.fuenteDeDatos == STRING_OPTION) {
                // Obtiene el texto de entrada
                String textoEnClaro = jTextFieldEntrada.getText();

                List<HashResult> res = callStringJacksum(textoEnClaro, selection);
                setResultText(res);


            }
        }
    }

    private List<HashResult> callStringJacksum(String value, List<String> algorithms) {
        try {
            String[] str = new String[algorithms.size()];
            AbstractChecksum ccs = new CombinedChecksum(algorithms.toArray(str), false);
            ccs.update(value.getBytes());
            String result = ccs.format("#ALGONAME{i}\n#CHECKSUM{i}");
            //System.out.println(result);
            String[] parts = result.split("\\n");
            List<HashResult> answer = new ArrayList<>(algorithms.size());

            for (int i = 0; i < parts.length; i += 2) {
                answer.add(new HashResult(parts[i], parts[i + 1]));
            }
            return answer;

        } catch (Exception ie) {
            System.err.println(ie.getMessage());
            ie.printStackTrace(System.err);
            JOptionPane.showMessageDialog(Hashero.this,
                    "Hubo un error al calcular los hashes\n",
                    "Error", JOptionPane.ERROR_MESSAGE);
            // Limpia los campos de texto
            resetText();
            // Activa el botón
            this.jButtonCalcular.setEnabled(true);
        }

        return null;
    }

    private List<HashResult> callFileJacksum(String f, List<String> algorithms) {
        try {
            String[] str = new String[algorithms.size()];
            AbstractChecksum ccs = new CombinedChecksum(algorithms.toArray(str), false);
            ccs.readFile(f);
            String result = ccs.format("#ALGONAME{i}\n#CHECKSUM{i}");
            //System.out.println(result);
            String[] parts = result.split("\\n");
            List<HashResult> answer = new ArrayList<>(algorithms.size());

            for (int i = 0; i < parts.length; i += 2) {
                answer.add(new HashResult(parts[i], parts[i + 1]));
            }
            return answer;

        } catch (NoSuchAlgorithmException | IOException ie) {
            System.err.println(ie.getMessage());
            ie.printStackTrace(System.err);
            JOptionPane.showMessageDialog(Hashero.this,
                    "Hubo un error al calcular los hashes\n",
                    "Error", JOptionPane.ERROR_MESSAGE);
            // Limpia los campos de texto
            resetText();
            // Activa el botón
            this.jButtonCalcular.setEnabled(true);
        }

        return null;
    }

    private List<String> filterSelection() {
        final List<String> algoritmos = new ArrayList<>(60);

        for (String name : this.checkBoxes.keySet()) {
            if (checkBoxes.get(name).isSelected()) {
                algoritmos.add(name);
            }

        }
        return algoritmos;
    }

    private void setElapsedTime(long ms) {
        timeLabel.setText(NF.format((double) ms / 1000) + " s");
    }

    private void setResultText(List<HashResult> hashes) {
        for (HashResult res : hashes) {
            // Establece el valor de resumen obtenido
            String result = res.getResult();
            JProgressBar tf = this.progressBars.get(res.getName());
            if (tf == null) {
                System.err.println(res.getName());
            }
            tf.setString(result);
            tf.setToolTipText(result);
        }
    }

    /**
     * Obtiene la ruta del fichero cuyo resumen queremos calcular
     * @param evt Evento generado
     */
    private void jButtonRutaFicheroActionPerformed(ActionEvent evt) {
        // Genera un nuevo selector de ficheros
        JFileChooser fc = new JFileChooser();
        // Obtiene el valor de retorno del selector
        int valorDeRetorno = fc.showOpenDialog(this);
        // Si el usuario ha aceptado la selección...
        if (valorDeRetorno == JFileChooser.APPROVE_OPTION) {
            // Obtiene el fichero seleccionado por el usuario
            File fichero = fc.getSelectedFile();
            // Establece la ruta del fichero seleccionado por el usuario
            jTextFieldEntrada.setText(fichero.getPath());
            jButtonCalcular.setEnabled(true);
        }
    }

    /**
     * Modifica la fuente de datos del cálculo
     * @param evt Evento generado
     */
    private void jComboSeleccionActionPerformed(ActionEvent evt) {
        // Obtiene la selección del usuario
        String seleccion = (String) jComboSeleccion.getItemAt(jComboSeleccion.getSelectedIndex());
        // La fuente de datos será un fichero
        if (seleccion.equals("Fichero")) {
            jButtonRutaFichero.setVisible(true);
            this.fuenteDeDatos = FILE_OPTION;
            jTextFieldEntrada.setText("");
            jTextFieldEntrada.setEditable(false);
            resetText();
        } // La fuente de datos será una cadena
        else if (seleccion.equals("Texto")) {
            jButtonRutaFichero.setVisible(false);
            this.fuenteDeDatos = STRING_OPTION;
            jTextFieldEntrada.setText("");
            jTextFieldEntrada.setEditable(true);
            resetText();
        }
    }

    /**
     * Sale de la aplicación
     * @param evt Evento generado
     */
    private void formWindowClosed(WindowEvent evt) {
        dispose();
    }

    /**
     * Reinicia los campos de texto del formulario
     */
    private void resetText() {
        // Recorre los campos de texto limpiándolos
        for (JProgressBar tf : this.progressBars.values()) {
            tf.setString("");
            tf.setToolTipText("");

        }
        this.timeLabel.setText("");
    }

    public static void main(String args[]) {
        EventQueue.invokeLater(() -> {
            new Hashero().setVisible(true);
        });
    }
}
