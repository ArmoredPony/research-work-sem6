package org.xenia.mlp.datasets;

import java.io.FileNotFoundException;
import java.io.FileReader;

public class IrisDataset extends Dataset
{
    public IrisDataset(String path) throws FileNotFoundException
    {
        super(new FileReader(path), 150, 4, 3);
    }

    public IrisDataset() throws FileNotFoundException
    {
        this("csv/_noneed/iris.csv");
    }
}
