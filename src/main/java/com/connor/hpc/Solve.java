package com.connor.hpc;

import java.util.ArrayList;

import org.apache.commons.math3.primes.*;

public class Solve {

    public static long solve(ArrayList<Integer> data) {
        long result = 0;
        for (int num : data) {
            result += solveNumber(num);
        }
        return result;
    }

    public static long solveNumber(int num) {
        long result = 0;
        int prime = Primes.nextPrime(0);
        while (prime < num) {
            result += prime;
            prime = Primes.nextPrime(prime + 1);
        }
        return result;
    }
}