

package com.guidewire.streamsvscollections.meetup;

import com.google.caliper.BeforeExperiment;
import com.google.caliper.Benchmark;
import com.google.caliper.Param;
import com.google.caliper.api.VmOptions;
import com.google.caliper.runner.CaliperMain;

import java.util.ArrayList;
import java.util.Random;


@VmOptions("-XX:-TieredCompilation")
public class S6eqStreamVsParStream_Stateful {

    private ArrayList<Integer> randomInts;

    @Param({"100", "10000", "100000"})
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
    public void sequentialStreamDistinctCount(int reps) {
        for (int i = 0; i < reps; i++) {
            randomInts.stream()
                    .map(anInt -> anInt - 2)
                    .distinct()
                    .map(anInt -> anInt + 2)
                    .count();
        }
    }

    @Benchmark
    public void parallelStreamDistinctCount(int reps) {
        for (int i = 0; i < reps; i++) {
            randomInts.parallelStream()
                    .map(anInt -> anInt - 2)
                    .distinct()
                    .map(anInt -> anInt + 2)
                    .count();
        }
    }

    public static void main(String[] args) {
        CaliperMain.main(S6eqStreamVsParStream_Stateful.class,
                new String[]{"--instrument", "runtime", "--trials", "5"});
    }
}