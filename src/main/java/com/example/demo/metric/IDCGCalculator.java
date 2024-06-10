package com.example.demo.metric;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * The IDCG (Ideal Discounted Cumulative Gain) Calculator Class
 */
public class IDCGCalculator {

    public static double calculateIDCG(List<Double> relevanceScores) {
        List<Double> sortedScores = new ArrayList<>(relevanceScores);
        sortedScores.sort(Collections.reverseOrder());

        // Calculate IDCG using the same formula as DCG
        double idcg = 0.0;
        // calculate the number of feedbacks by taking the minimum between
        // the number of relevance scores and the number of sorted scores
        int numFeedbacks = Math.min(relevanceScores.size(), sortedScores.size());

        for (int i = 0; i < numFeedbacks; i++) {
            double relevance = sortedScores.get(i);
            int rank = i + 1;
            idcg += (relevance / (Math.log(rank + 1) / Math.log(2)));
        }

        return idcg;
    }
}