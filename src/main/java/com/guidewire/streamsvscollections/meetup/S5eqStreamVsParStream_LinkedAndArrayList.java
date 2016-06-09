

package com.guidewire.streamsvscollections.meetup;

import com.google.caliper.BeforeExperiment;
import com.google.caliper.Benchmark;
import com.google.caliper.Param;
import com.google.caliper.api.VmOptions;
import com.google.caliper.runner.CaliperMain;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Random;


@VmOptions("-XX:-TieredCompilation")
public class S5eqStreamVsParStream_LinkedAndArrayList {

    private LinkedList<Integer> randomInts1;
    private ArrayList<Integer> randomInts2;

    @Param({"10000", "50000", "100000"})
    private int arraySize;

    @BeforeExperiment
    protected void setUp() {
        Random ran = new Random();

        randomInts1 = new LinkedList<>();
        randomInts2 = new ArrayList<>();
        for (int i = 0; i < arraySize; i++) {
            randomInts1.add(ran.nextInt());
            randomInts2.add(ran.nextInt());
        }
    }

    @Benchmark
    public void parallelStreamMaxLinkedList(int reps) {
        for (int i = 0; i < reps; i++) {
            randomInts1.parallelStream().max(Integer::compareTo);
        }
    }

    @Benchmark
    public void parallelStreamMaxArrayList(int reps) {
        for (int i = 0; i < reps; i++) {
            randomInts2.parallelStream().max(Integer::compareTo);
        }
    }

    public static void main(String[] args) {
        CaliperMain.main(S5eqStreamVsParStream_LinkedAndArrayList.class,
                new String[]{"--instrument", "runtime", "--trials", "5"});
    }
}