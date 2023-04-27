package org.xenia;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.Random;

import org.xenia.mlp.Layer;
import org.xenia.mlp.MLP;
import org.xenia.mlp.activationfunctions.*;
import org.xenia.mlp.datasets.*;
import org.xenia.mlp.errorfunctions.*;
import org.xenia.mlp.initiliazers.*;

public class Main
{
    public static void main(String[] args) throws FileNotFoundException
    {
        Dataset dataset = new Dataset(new FileReader("csv/database-100000-count.csv"),
                100000, 174, 2);
        Dataset trainset = dataset.split(0.025);
        Dataset testset = dataset;

        Random rnd = new Random();
        long seed = rnd.nextLong();
        rnd.setSeed(seed);
        // long seed = -5934207914651010171L;

        final int MID_LAYER = 6;
        MLP mlp = new MLP(
                rnd,
                new MSE(),
                new Layer(new HeInitializer(rnd, trainset.getInputLength()),
                        new Softmax(),
                        trainset.getInputLength(), MID_LAYER),
                new Layer(new HeInitializer(rnd, MID_LAYER),
                        new Softmax(),
                        MID_LAYER, trainset.getTargetLength()));
        mlp.train(trainset, 600, 1, 0.01, true);

        double count1 = 0, err1 = 0, count2 = 0, err2 = 0;
        for (int i = 0; i < testset.getSize(); i++)
        {
            double[] output = mlp.predict(testset.inputs[i]);
            // System.out.println(i + "\t" + output[0] + " " + output[1]
            // + " | " + testset.targets[i][0] + " " + testset.targets[i][1]);
            if (testset.targets[i][0] > testset.targets[i][1])
            {
                count1 += 1;
                if (output[0] <= output[1])
                    err1 += 1;
            }
            else
            {
                count2 += 1;
                if (output[0] >= output[1])
                    err2 += 1;
            }
        }

        System.out.println("science: " + count1
                + "\terrors: " + err1 + "\terror%: " + (err1 / count1) * 100);
        System.out.println("sport:   " + count2
                + "\terrors: " + err2 + "\terror%: " + (err2 / count2) * 100);
        System.out.println("samples: " + trainset.getSize()
                + "\t\tseed: " + seed);
    }
}
