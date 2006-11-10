/*
 * Part of Simbrain--a java-based neural network kit
 * Copyright (C) 2005 Jeff Yoshimi <www.jeffyoshimi.net>
 *
 * This program is free software; you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation; either version 2 of the License, or
 * (at your option) any later version.
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
package org.simbrain.network.dialog.neuron;

import java.util.ArrayList;

import javax.swing.JComboBox;
import javax.swing.JTabbedPane;
import javax.swing.JTextField;

import org.simbrain.network.NetworkUtils;
import org.simbrain.network.dialog.RandomPanel;
import org.simbrain.util.LabelledItemPanel;
import org.simbrain.util.TristateDropDown;
import org.simnet.interfaces.Network;
import org.simnet.neurons.PointNeuron;
import org.simnet.neurons.SigmoidalNeuron;


/**
 * <b>PointNeuronPanel</b>.
 */
public class PointNeuronPanel extends AbstractNeuronPanel {

    /** Excitatory Reversal field. */
    private JTextField tfER = new JTextField();

    /** Inhibitory Reversal field. */
    private JTextField tfIR = new JTextField();

    /**  Leak Reversal field. */
    private JTextField tfLR = new JTextField();

    /** Leak Conductance field. */
    private JTextField tfLC = new JTextField();
    
    private JComboBox cbOutputFunction = new JComboBox(PointNeuron.getFunctionList());
    
    private JTextField tfN = new JTextField();

    /** Time step field. */
    private JTextField tfTimeStep = new JTextField();

    /** Add noise combo box. */
    private TristateDropDown tsNoise = new TristateDropDown();

    /** Tabbed pane. */
    private JTabbedPane tabbedPane = new JTabbedPane();

    /** Main tab. */
    private LabelledItemPanel mainTab = new LabelledItemPanel();

    /**
     * Creates an instance of this panel.
     * @param net Network
     */
    public PointNeuronPanel(final Network net) {
        this.parentNet = net;
        this.add(tabbedPane);
        mainTab.addItem("Time step", tfTimeStep);
        mainTab.addItem("Excitatory Reversal", tfER);
        mainTab.addItem("Inhibitory Reversal", tfIR);
        mainTab.addItem("Leak Reversal", tfLR);
        mainTab.addItem("Leak Conductance", tfLC);
        mainTab.addItem("Output Function", cbOutputFunction);
        mainTab.addItem("Number of Connections", tfN);
        mainTab.addItem("Add noise", tsNoise);
        tabbedPane.add(mainTab, "Main");
        //tabbedPane.add(randTab, "Noise");
    }

    /**
     * Populate fields with current data.
     */
    public void fillFieldValues() {
        PointNeuron neuronRef = (PointNeuron) neuronList.get(0);

        tfTimeStep.setText(Double.toString(parentNet.getTimeStep()));

        tfER.setText(Double.toString(neuronRef.getExcitatoryReversal()));
        tfIR.setText(Double.toString(neuronRef.getInhibitoryReversal()));
        tfLR.setText(Double.toString(neuronRef.getLeakReversal()));
        tfLC.setText(Double.toString(neuronRef.getLeakConductance()));
        cbOutputFunction.setSelectedIndex(neuronRef.getOutputFunction());
        tfN.setText(Double.toString(neuronRef.getN()));
        //tsNoise.setSelected(neuronRef.getAddNoise());

        //Handle consistency of multiple selections
        if (!NetworkUtils.isConsistent(neuronList, PointNeuron.class, "getExcitatoryReversal")) {
            tfER.setText(NULL_STRING);
        }

        if (!NetworkUtils.isConsistent(neuronList, PointNeuron.class, "getInhibitoryReversal")) {
            tfIR.setText(NULL_STRING);
        }

        if (!NetworkUtils.isConsistent(neuronList, PointNeuron.class, "getLeakReversal")) {
            tfLR.setText(NULL_STRING);
        }

        if (!NetworkUtils.isConsistent(neuronList, PointNeuron.class, "getLeakConductance")) {
            tfLC.setText(NULL_STRING);
        }
        if (!cbOutputFunction.getSelectedItem().equals(NULL_STRING)) {
            neuronRef.setOutputFunction(cbOutputFunction.getSelectedIndex());
        }
        if (!NetworkUtils.isConsistent(neuronList, PointNeuron.class, "getN")) {
            tfN.setText(NULL_STRING);
        }

//        if (!NetworkUtils.isConsistent(neuronList, PointNeuron.class, "getAddNoise")) {
//            tsNoise.setNull();
//        }

        //randTab.fillFieldValues(getRandomizers());
    }

    /**
     * @return List of randomizers.
     */
    private ArrayList getRandomizers() {
        ArrayList ret = new ArrayList();

//        for (int i = 0; i < neuronList.size(); i++) {
//            ret.add(((PointNeuron) neuronList.get(i)).getNoiseGenerator());
//        }

        return ret;
    }

    /**
     * Populate fields with default data.
     */
    public void fillDefaultValues() {
        PointNeuron neuronRef = new PointNeuron();
        tfTimeStep.setText(Double.toString(parentNet.getTimeStep()));
        tfER.setText(Double.toString(neuronRef.getExcitatoryReversal()));
        tfIR.setText(Double.toString(neuronRef.getInhibitoryReversal()));
        tfLR.setText(Double.toString(neuronRef.getLeakReversal()));
        tfLC.setText(Double.toString(neuronRef.getLeakConductance()));
        cbOutputFunction.setSelectedIndex(neuronRef.getOutputFunction());
        tfN.setText(Double.toString(neuronRef.getN()));
        //tsNoise.setSelected(neuronRef.getAddNoise());
      //  randTab.fillDefaultValues();
    }

    /**
     * Called externally when the dialog is closed, to commit any changes made.
     */
    public void commitChanges() {
        parentNet.setTimeStep(Double.parseDouble(tfTimeStep.getText()));

        for (int i = 0; i < neuronList.size(); i++) {
            PointNeuron neuronRef = (PointNeuron) neuronList.get(i);

            if (!tfER.getText().equals(NULL_STRING)) {
                neuronRef.setExcitatoryReversal(Double.parseDouble(tfER.getText()));
            }

            if (!tfIR.getText().equals(NULL_STRING)) {
                neuronRef.setInhibitoryReversal(Double.parseDouble(tfIR.getText()));
            }

            if (!tfLR.getText().equals(NULL_STRING)) {
                neuronRef.setLeakReversal(Double.parseDouble(tfLR.getText()));
            }

            if (!tfLC.getText().equals(NULL_STRING)) {
                neuronRef.setLeakConductance(Double.parseDouble(tfLC.getText()));
            }
            if (!tfN.getText().equals(NULL_STRING)) {
                neuronRef.setN(Double.parseDouble(tfN.getText()));
            }
//
//            if (!tsNoise.isNull()) {
//                neuronRef.setAddNoise(tsNoise.isSelected());
//            }
//
//            randTab.commitRandom(neuronRef.getNoiseGenerator());
        }
    }
}