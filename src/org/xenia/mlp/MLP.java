package org.xenia.mlp;

import java.util.Random;

import org.xenia.mlp.datasets.Dataset;
import org.xenia.mlp.errorfunctions.ErrorFunction;

public class MLP
{
    Random rnd;
    ErrorFunction errorFunction;
    Layer[] layers;

    public MLP(Random random, ErrorFunction errorFunction, Layer... layers)
    {
        if (layers.length == 0)
            throw new IllegalArgumentException("MLP must have at least one layer.");

        if (layers.length > 1)
            for (int i = 1; i < layers.length; i++)
            {
                int cols = layers[i - 1].getCols();
                int rows = layers[i].getRows();
                if (cols != rows)
                    throw new IllegalArgumentException("Columns number (" + cols + ") of "
                            + (i - 1) + " layer doesn't match rows number (" + rows
                            + ") of " + i + " layer.");
            }

        rnd = random;
        this.errorFunction = errorFunction;
        this.layers = layers;
    }

    public MLP(ErrorFunction errorFunction, Layer... layers)
    {
        this(new Random(), errorFunction, layers);
    }

    public double[] predict(double[] input)
    {
        double[] output = layers[0].propagateForwards(input);
        for (int i = 1; i < layers.length; i++)
            output = layers[i].propagateForwards(output);

        return output;
    }

    public double[] train(Dataset dataset, int epoches, int batchSize, double rate,
            boolean stochastic)
    {
        if (dataset.getTargetLength() != layers[layers.length - 1].getCols())
            throw new IllegalArgumentException(
                    "Error vector length must match columns number.");

        if (batchSize > dataset.getSize())
            throw new IllegalArgumentException(
                    "Batch size can't be bigger than dataset size.");

        if (epoches <= 0)
            throw new IllegalArgumentException("Epoches value must be > 0.");

        if (batchSize <= 0)
            throw new IllegalArgumentException("Batch size must be > 0.");

        if (rate <= 0)
            throw new IllegalArgumentException("Rate must be > 0.");

        double[] errors = new double[epoches];

        for (int e = 0; e < epoches; e++)
        {
            double epochError = 0;
            for (int d = 0; d < dataset.getSize(); d++)
            {
                int v = stochastic ? rnd.nextInt(dataset.getSize()) : d;
                double[] output = predict(dataset.inputs[v]);
                double[] error = errorFunction.getLossDeriv(output, dataset.targets[v]);

                boolean update = (d + 1) % batchSize == 0 || d == dataset.getSize() - 1;
                for (int i = layers.length - 1; i >= 0; i--)
                    error = layers[i].propagateBackwards(error, rate, batchSize, update);

                epochError += errorFunction.getCost(output, dataset.targets[v]);
            }

            errors[e] = epochError / epoches;
        }

        return errors;
    }

    @Override
    public String toString()
    {
        StringBuffer sb = new StringBuffer();
        sb.append("MLP with " + layers.length + " layers: ");
        for (Layer layer : layers)
            sb.append(layer.getRows() + "x" + layer.getCols() + " ");
        sb.append("\n");

        for (int i = 0; i < layers.length; i++)
            sb.append("Weights " + i + "\n" + layers[i]);

        return sb.toString();
    }
}
