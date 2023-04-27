package org.xenia.mlp.normalizers;

import java.util.Arrays;

public class MinMaxNormalizer implements Normalizer
{
    @Override
    public void normalize(double[][] data)
    {
        double[] minValues = new double[data[0].length];
        Arrays.fill(minValues, Double.MAX_VALUE);

        double[] maxValues = new double[data[0].length];
        Arrays.fill(maxValues, Double.MIN_VALUE);

        for (double[] vector : data)
            for (int i = 0; i < vector.length; i++)
            {
                if (vector[i] < minValues[i])
                    minValues[i] = vector[i];
                if (vector[i] > maxValues[i])
                    maxValues[i] = vector[i];
            }

        for (double[] vector : data)
            for (int i = 0; i < vector.length; i++)
                vector[i] = (vector[i] - minValues[i]) / (maxValues[i] - minValues[i]);
    }
}
