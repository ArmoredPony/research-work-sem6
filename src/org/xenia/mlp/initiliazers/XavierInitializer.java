package org.xenia.mlp.initiliazers;

import java.util.Random;

public class XavierInitializer extends Initializer
{
    private final double COEF;

    public XavierInitializer(Random random, int inputNeurons, int outputNeurons)
    {
        super(random);

        if (inputNeurons <= 0)
            throw new IllegalArgumentException("'inputNeurons' value must be > 0.");

        if (outputNeurons <= 0)
            throw new IllegalArgumentException("'outputNeurons' value must be > 0.");

        COEF = Math.sqrt(6 / (inputNeurons + outputNeurons));
    }

    public XavierInitializer(int inputNeurons, int outputNeurons)
    {
        this(new Random(), inputNeurons, outputNeurons);
    }

    @Override
    public Double get()
    {
        return super.get() * COEF;
    }
}
