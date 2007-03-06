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
package org.simnet.synapses;

import org.simnet.interfaces.Neuron;
import org.simnet.interfaces.Synapse;


/**
 * <b>HebbianCPCA</b>.
 */
public class HebbianCPCA extends Synapse {

    /** Learning rate. */
    private double learningRate = 1;
    
    /** Maximum weight value (see equation 4.19 in O'Reilly and Munakata). */
    private double m = .5/.15;
    
    /** Weight offset. */
    private double theta = 1;
    
    /** Sigmoidal function. */
    private double lambda = 1;
    
    /**
     * Creates a weight of some value connecting two neurons.
     *
     * @param src source neuron
     * @param tar target neuron
     * @param val initial weight value
     * @param theId Id of the synapse
     */
    public HebbianCPCA(final Neuron src, final Neuron tar, final double val, final String theId) {
        source = src;
        target = tar;
        strength = val;
        id = theId;
    }

    /**
     * Default constructor needed for external calls which create neurons then  set their parameters.
     */
    public HebbianCPCA() {
    }

    /**
     * This constructor is used when creating a neuron of one type from another neuron of another type Only values
     * common to different types of neuron are copied.
     * @param s Synapse to make of the type
     */
    public HebbianCPCA(final Synapse s) {
        super(s);
    }

    /**
     * @return Name of synapse type.
     */
    public static String getName() {
        return "HebbianCPCA";
    }

    /**
     * @return duplicate Hebbian (used, e.g., in copy/paste).
     */
    public Synapse duplicate() {
        HebbianCPCA h = new HebbianCPCA();
        h.setLearningRate(getLearningRate());
        h.setM(getM());
        h.setTheta(getTheta());
        h.setLambda(getLambda());

        return super.duplicate(h);
    }

	/**
     * Creates a weight connecting source and target neurons.
     *
     * @param source source neuron
     * @param target target neuron
     */
    public HebbianCPCA(final Neuron source, final Neuron target) {
        this.source = source;
        this.target = target;
    }
    
    /**
     * Updates the synapse (see equation 4.18 in O'Reilly and Munakata).
     */
    public void update() {
        double input = getSource().getActivation();
        double output = getTarget().getActivation();

        double deltaW = learningRate * ((output * input) - (output * strength));
        
        deltaW = learningRate * (output * input * (m - strength) + output * (1 - input) * (-strength));
                
        strength = sigmoidal(strength);
        strength = clip(strength + deltaW);
        
    }

    /**
     * Sigmoidal Funcation (see equation 4.23 in O'Reilly and Munakata).
     */
    private double sigmoidal(final double arg) {
    	return 1/(Math.pow(1+(theta * (arg/(1-arg))), -lambda));
 
    }
    
    /**
     * @return Returns the momentum.
     */
    public double getLearningRate() {
        return learningRate;
    }
    
    /**
     * @return Returns the maximum weight.
     */
    public double getM() {
        return m;
    }
    
    /**
     * @return Returns the weight offset.
     */
    public double getTheta() {
        return theta;
    }
    
    /**
     * @return Returns sigmoidal function.
     */
    public double getLambda() {
        return lambda;
    }
    

    /**
     * @param momentum The momentum to set.
     */
    public void setLearningRate(final double momentum) {
        this.learningRate = momentum;
    }
    
    /**
     * @param maximum weight The maximum weight to set.
     */
    public void setM(double M) {
		this.m = M;
	}
    
    /**
     * @param weight offset The weight offset to set.
     */
    public void setTheta(double Theta) {
		this.theta = Theta;
	}
    
    /**
     * @param sigmoidal The sigmoidal to set.
     */
    public void setLambda(double Lambda) {
		this.lambda = Lambda;
	}

}