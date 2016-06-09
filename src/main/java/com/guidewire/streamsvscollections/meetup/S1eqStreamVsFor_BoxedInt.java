package com.guidewire.streamsvscollections.meetup;

import com.google.caliper.BeforeExperiment;
import com.google.caliper.Benchmark;
import com.google.caliper.Param;
import com.google.caliper.api.VmOptions;
import com.google.caliper.runner.CaliperMain;

import java.util.ArrayList;
import java.util.Random;


@VmOptions("-XX:-TieredCompilation")
public class S1eqStreamVsFor_BoxedInt {

    private ArrayList<Integer> randomInts;

    @Param({"1000", "10000", "50000", "200000"})
    private int arraySize;

    @BeforeExperiment
    protected void setUp() {
        Random ran = new Random();
        randomInts = new ArrayList<>();
        for (int i = 0; i < arraySize; i++) {
            randomInts.add(ran.nextInt());
        }
    }

    @Benchmark
    public void forLoopMaxSearchInteger(int reps) {
        for (int i = 0; i < reps; i++) {
            int maxFor = Integer.MIN_VALUE;
            for (int anInt : randomInts) {
                if (anInt > maxFor) maxFor = anInt;
            }
        }
    }

    @Benchmark
    public void sequentialStreamMaxSearchInteger(int reps) {
        for (int i = 0; i < reps; i++) {
            randomInts.stream().max(Integer::compareTo);
        }
    }

    public static void main(String[] args) {
        CaliperMain.main(S1eqStreamVsFor_BoxedInt.class,
                new String[]{"--instrument", "runtime", "--trials", "5"});
    }
}
