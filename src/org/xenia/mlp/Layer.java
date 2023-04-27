package org.xenia.mlp;

import java.util.Locale;

import org.xenia.mlp.activationfunctions.ActivationFunction;
import org.xenia.mlp.initiliazers.Initializer;

public class Layer
{
    private int rows;
    private int cols;
    private double[][] weights;
    private double[] lastInput;
    private double[] lastOutput;
    private double[] gradient;
    private ActivationFunction function;

    public int getRows()
    {
        return rows;
    }

    public int getCols()
    {
        return cols;
    }

    public Layer(Initializer weightInitiliaser,
            ActivationFunction function,
            int rows, int cols)
    {
        if (rows <= 0)
            throw new IllegalArgumentException("Rows number must be > 0.");
        if (cols <= 0)
            throw new IllegalArgumentException("Columns number must be > 0.");

        this.rows = rows;
        this.cols = cols;
        this.function = function;

        gradient = new double[cols];
        weights = new double[rows + 1][cols];
        lastOutput = new double[cols];
        for (int i = 0; i < rows + 1; i++)
            for (int j = 0; j < cols; j++)
                weights[i][j] = weightInitiliaser.get();
    }

    public Layer(ActivationFunction function, int rows, int cols)
    {
        this(new Initializer(), function, rows, cols);
    }

    double[] propagateForwards(double[] input)
    {
        if (input.length != rows)
            throw new IllegalArgumentException(
                    "Input vector length must match rows count.");

        lastInput = input;
        lastOutput = weights[0].clone();
        for (int j = 0; j < cols; j++)
            for (int i = 0; i < rows; i++)
                lastOutput[j] += input[i] * weights[i + 1][j];

        lastOutput = function.f(lastOutput);
        return lastOutput;
    }

    private int batchCounter = 0;

    double[] propagateBackwards(double[] error, double rate, int batchSize,
            boolean update)
    {
        if (error.length != cols)
            throw new IllegalArgumentException(
                    "Error vector length must match columns number.");

        batchCounter += 1;
        double[] nextError = new double[rows];
        for (int i = 0; i < nextError.length; i++)
            for (int j = 0; j < error.length; j++)
                nextError[i] += weights[i + 1][j] * error[j];

        double[] derivOutput = function.df(lastOutput);

        for (int i = 0; i < gradient.length; i++)
            gradient[i] += rate * error[i] * derivOutput[i];

        if (update)
        {
            for (int j = 0; j < cols; j++)
            {
                gradient[j] /= batchCounter;
                weights[0][j] += gradient[j];
                for (int i = 0; i < rows; i++)
                    weights[i + 1][j] += gradient[j] * lastInput[i];
                gradient[j] = 0;
            }
            batchCounter = 0;
        }

        return nextError;
    }

    @Override
    public String toString()
    {
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < rows + 1; i++)
        {
            sb.append((i == 0 ? "biases:\t" : "\t"));
            for (int j = 0; j < cols; j++)
                sb.append(String.format(Locale.US, "%6.2f ", weights[i][j]));

            sb.append("\n");
        }

        return sb.toString();
    }
}