package org.xenia.mlp.datasets;

import java.io.FileNotFoundException;
import java.io.FileReader;

public class RedWineDataset extends Dataset
{
    public RedWineDataset(String path) throws FileNotFoundException
    {
        super(new FileReader(path), 1599, 11, 1);
        double min = Double.MAX_VALUE;
        double max = Double.MIN_VALUE;
        for (var target : targets)
        {
            if (target[0] > max)
                max = target[0];
            if (target[0] < min)
                min = target[0];
        }

        int classCount = (int)max - (int)min + 1;
        for (int i = 0; i < size; i++)
        {
            double[] newTarget = new double[classCount];
            newTarget[(int)targets[i][0]] = 1;
            targets[i] = newTarget;
        }

        targetLength = classCount;
    }

    public RedWineDataset() throws FileNotFoundException
    {
        this("csv/winequality-red.csv");
    }
}
