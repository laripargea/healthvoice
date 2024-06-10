package com.example.demo.metric;

/**
 * The NDCG (Normalized Discounted Cumulative Gain) Calculator Class
 */
public class NDCGCalculator {

    public static double calculateNDCG(double dcg, double idcg) {
        if (idcg == 0.0) {
            return 0.0;
        }
        return dcg / idcg;
    }

    // Function to check if the ranking is perfect or not
    public static boolean checkRanking(double ndcg) {
        double epsilon = 1e-6; // used a small value to handle precision errors

        // Check if NDCG is close enough to 1.0
        double floor = Math.floor(Math.abs(ndcg));
        return floor < epsilon;
    }
}