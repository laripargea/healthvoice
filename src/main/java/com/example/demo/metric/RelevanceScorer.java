package com.example.demo.metric;

import java.util.List;

import com.example.demo.feedback_analyzer.Feedback;

/**
 * The Relevance Scorer Class
 * Used for assigning relevance scores to the feedbacks
 */
public class RelevanceScorer {

    // LINEAR SCORING
    public static void assignRelevanceScoresLinearly(List<Feedback> rankedFeedbacks) {
        // Assuming the highest relevance score is equal to the number of feedbacks
        // and decreases by 1 for each subsequent feedback
        int maxScore = rankedFeedbacks.size();
        for (int i = 0; i < rankedFeedbacks.size(); i++) {
            // Assigning scores in descending order starting from maxScore
            rankedFeedbacks.get(i).setRelevanceScore(Double.valueOf(maxScore - i));
        }
    }

    // LOGARITHMIC SCORING
    public static void assignLogarithmicRelevanceScores(List<Feedback> rankedFeedbacks) {
        // Assuming the highest relevance score is for the top-ranked feedback
        // and decreases logarithmically for each subsequent feedback
        final double baseLogRelevance = Math.log(rankedFeedbacks.size());
        for (int i = 0; i < rankedFeedbacks.size(); i++) {
            // Using logarithmic scale, we ensure that the top song has the highest score
            double relevanceScore = baseLogRelevance - Math.log(i + 1 + rankedFeedbacks.get(i).getSentimentScore());
            rankedFeedbacks.get(i)
                    .setRelevanceScore(Double.parseDouble(String.valueOf(String.format("%.2f", relevanceScore))));
        }
    }
}