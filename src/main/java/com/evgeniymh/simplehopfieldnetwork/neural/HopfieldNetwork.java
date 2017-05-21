/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.evgeniymh.simplehopfieldnetwork.neural;
import org.ejml.simple.SimpleMatrix;

/**
 *
 * @author evgeniy
 */
public class HopfieldNetwork {

    private SimpleMatrix weightMatrix;

    public HopfieldNetwork(final int size) {
        this.weightMatrix = new SimpleMatrix(size, size);
    }

    public SimpleMatrix getWeightMatrix() {
        return weightMatrix;
    }

    public int getSize() {
        return weightMatrix.numRows();
    }

    public boolean[] present(final boolean[] inputPattern) {
        boolean output[] = new boolean[inputPattern.length];

        SimpleMatrix inputMatrix = new SimpleMatrix(new double[][]{bipolar2double(inputPattern)});

        for (int col = 0; col < inputPattern.length; col++) {
            SimpleMatrix columnMatrix = new SimpleMatrix(1, inputPattern.length);
            for (int j = 0; j < inputPattern.length; j++) {
                columnMatrix.set(0, j, weightMatrix.get(j, col));
            }

            double dotProduct = inputMatrix.dot(columnMatrix);
            output[col] = dotProduct > 0;
        }
        return output;
    }

    public void train(boolean[] inputPattern) throws NeuralNetworkError {
        if (inputPattern.length != weightMatrix.numRows()) {
            throw new NeuralNetworkError();
        }

        SimpleMatrix inputMatrix = new SimpleMatrix(new double[][]{bipolar2double(inputPattern)});
        SimpleMatrix contribMatrix = inputMatrix.transpose().mult(inputMatrix);

        for (int i = 0; i < inputPattern.length; i++) {
            contribMatrix.set(i, i, 0);
        }
        weightMatrix = weightMatrix.plus(contribMatrix);
    }

    public static double[] bipolar2double(final boolean b[]) {
        final double[] result = new double[b.length];

        for (int i = 0; i < b.length; i++) {
            result[i] = bipolar2double(b[i]);
        }

        return result;
    }

    public static double bipolar2double(final boolean b) {
        if (b) {
            return 1;
        } else {
            return -1;
        }
    }
}
