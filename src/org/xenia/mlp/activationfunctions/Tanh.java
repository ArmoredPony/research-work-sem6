package org.xenia.mlp.activationfunctions;

public class Tanh extends ActivationFunction
{
    public Tanh()
    {
        super(
                (double[] x) ->
                {
                    for (int i = 0; i < x.length; i++)
                        x[i] = Math.tanh(x[i]);
                    return x;
                },
                (double[] x) ->
                {
                    for (int i = 0; i < x.length; i++)
                        x[i] = 1. - Math.pow(Math.tanh(x[i]), 2);
                    return x;
                });
    }
}
