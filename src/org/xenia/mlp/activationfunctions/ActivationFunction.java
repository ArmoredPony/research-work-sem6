package org.xenia.mlp.activationfunctions;

import java.util.function.UnaryOperator;

public class ActivationFunction
{
    private UnaryOperator<double[]> f;
    private UnaryOperator<double[]> df;

    public ActivationFunction(UnaryOperator<double[]> f, UnaryOperator<double[]> df)
    {
        this.f = f;
        this.df = df;
    }

    public double[] f(double[] input)
    {
        return f.apply(input);
    }

    public double[] df(double[] input)
    {
        return df.apply(input);
    }
}
