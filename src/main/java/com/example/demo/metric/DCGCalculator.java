package com.example.demo.metric;

import java.util.List;

/**
 * The DCG (Discounted Cumulative Gain) Calculator Class
 */
public class DCGCalculator {

    public static double calculateDCG(List<Double> relevanceScores) {
        double dcg = 0.0;
        int numFeedbacks = relevanceScores.size();

        for (int i = 0; i < numFeedbacks; i++) {
            double relevance = relevanceScores.get(i);
            int rank = i + 1;
            dcg += (relevance / (Math.log(rank + 1) / Math.log(2)));
        }

        return dcg;
    }
}