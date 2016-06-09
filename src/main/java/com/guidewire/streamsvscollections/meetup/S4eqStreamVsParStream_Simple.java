

package com.guidewire.streamsvscollections.meetup;

import com.google.caliper.BeforeExperiment;
import com.google.caliper.Benchmark;
import com.google.caliper.Param;
import com.google.caliper.api.VmOptions;
import com.google.caliper.runner.CaliperMain;

import java.util.ArrayList;
import java.util.Random;


@VmOptions("-XX:-TieredCompilation")
public class S4eqStreamVsParStream_Simple {

    private ArrayList<Integer> randomInts;

    @Param({"100", "1000", "10000", "100000", "500000"})
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
    public void sequentialStreamMaxSearch(int reps) {
        for (int i = 0; i < reps; i++) {
            randomInts.stream()
                    .max(Integer::compareTo);
        }
    }

    @Benchmark
    public void parallelStreamMaxSearch(int reps) {
        for (int i = 0; i < reps; i++) {
            randomInts.parallelStream()
                    .max(Integer::compareTo);
        }
    }

    public static void main(String[] args) {
        CaliperMain.main(S4eqStreamVsParStream_Simple.class,
                new String[]{"--instrument", "runtime", "--trials", "5"});
    }
}