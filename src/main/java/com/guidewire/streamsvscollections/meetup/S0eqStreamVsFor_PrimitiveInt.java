package com.guidewire.streamsvscollections.meetup;

import com.google.caliper.BeforeExperiment;
import com.google.caliper.Benchmark;
import com.google.caliper.Param;
import com.google.caliper.api.VmOptions;
import com.google.caliper.runner.CaliperMain;


import java.util.Arrays;
import java.util.Random;

@VmOptions("-XX:-TieredCompilation")
public class S0eqStreamVsFor_PrimitiveInt {

    private int[] randomInts;

    @Param({"1000", "10000", "50000", "200000"})
    private int arraySize;

    @BeforeExperiment
    protected void setUp() {
        randomInts = new int[arraySize];
        Random ran = new Random();
        for (int i = 0; i < randomInts.length; i++) {
            randomInts[i] = ran.nextInt();
        }
    }

    @Benchmark
    public void forLoopMaxSearchInt(int reps) {
        for (int i = 0; i < reps; i++) {
            int maxFor = Integer.MIN_VALUE;
            for (int j = 0; j < arraySize; j++) {
                if (randomInts[j] > maxFor) maxFor = randomInts[j];
            }
        }
    }

    @Benchmark
    public void sequentialStreamMaxSearchInt(int reps) {
        for (int i = 0; i < reps; i++) {
            Arrays.stream(randomInts).max();
        }
    }

    public static void main(String[] args) {
        CaliperMain.main(S0eqStreamVsFor_PrimitiveInt.class,
                new String[]{"--instrument", "runtime"});
    }
}
