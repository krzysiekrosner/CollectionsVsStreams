

package com.guidewire.streamsvscollections.meetup;

import com.google.caliper.BeforeExperiment;
import com.google.caliper.Benchmark;
import com.google.caliper.Param;
import com.google.caliper.api.VmOptions;
import com.google.caliper.runner.CaliperMain;

import java.util.ArrayList;
import java.util.Random;


@VmOptions("-XX:-TieredCompilation")
public class S3eqStreamVsParStream_Stateless {

    private ArrayList<Integer> randomInts;

    @Param({"1000", "5000", "10000"})
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
    public void sequentialStreamMaxSearchCPU(int reps) {
        for (int i = 0; i < reps; i++) {
            randomInts.stream()
                    .map(this::cpuIntensiveOperation)
                    .max(Double::compareTo);
        }
    }

    @Benchmark
    public void parallelStreamMaxSearchCPU(int reps) {
        for (int i = 0; i < reps; i++) {
            randomInts.parallelStream()
                    .map(this::cpuIntensiveOperation)
                    .max(Double::compareTo);
        }
    }

    private double cpuIntensiveOperation(int anInt) {
        return Math.sqrt((Math.tan(anInt) + Math.sin(anInt) / 97.0) * (Math.sqrt(37.3) + 2));
    }

    public static void main(String[] args) {
        CaliperMain.main(S3eqStreamVsParStream_Stateless.class,
                new String[]{"--instrument", "runtime", "--trials", "5"});
    }
}