package com.example.demo.metric;

import java.io.FileWriter;
import java.io.IOException;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import com.example.demo.feedback_analyzer.Feedback;
import com.example.demo.feedback_analyzer.SentimentAnalyzer;

public class Result {

    public static void main(String[] args) {
        String filePath = "../datatset_large.txt";
        List<String> feedbacks = MessageReader.readMessage(filePath);

        // use the SentimentAnalyzer class to analyze the sentiment of each feedback
        SentimentAnalyzer sentimentAnalyzer = new SentimentAnalyzer();
        sentimentAnalyzer.analyzeSentiment(feedbacks);

        // RANKING based on their sentiment from -1 to 3 (sentiment score)
        // Very Negative = -1.0
        // Negative = 0.0
        // Neutral = 1.0
        // Positive = 2.0
        // Very Positive = 3.0

        // get the ranked list
        List<Feedback> rankedFeedbacksList = sentimentAnalyzer.rankedFeedbacks;

        System.out.println("Standford NLP - Performance:");
        measurePerformance(rankedFeedbacksList);
    }

    private static void measurePerformance(List<Feedback> rankedFeebacksList) {
        Collections.sort(rankedFeebacksList);

        // assign relevance scores-- LINEAR RELEVANCE
        //        RelevanceScorer.assignRelevanceScoresLinearly(rankedFeedbacksList);

        // assign relevance scores -- LOGARITHMIC RELEVANCE
        RelevanceScorer.assignLogarithmicRelevanceScores(rankedFeebacksList);

        for (Feedback rankedFeedback : rankedFeebacksList) {
            try {
                //                String fileName = "RankingAndLinearRelevance.txt"; // -- LINEAR RELEVANCE SCORE
                String fileName = "RankingAndLogarithmicRelevance.txt"; // -- LOGARITHMIC RELEVANCE SCORE
                FileWriter myWriter = new FileWriter(fileName, true);
                myWriter.write(rankedFeedback + "\n");
                myWriter.write("\n");
                myWriter.close();
            } catch (IOException e) {
                System.out.println("An error occurred.");
                e.printStackTrace();
            }
        }

        List<Double> relevanceScores = getRelevanceScores(rankedFeebacksList);
        System.out.println(relevanceScores);

        // Calculate DCG (Discounted Cumulative Gain)
        double dcg = DCGCalculator.calculateDCG(relevanceScores);
        System.out.println("The DCG value is: " + dcg);

        // Calculate IDCG (Ideal Discounted Cumulative Gain)
        double idcg = IDCGCalculator.calculateIDCG(relevanceScores);
        System.out.println("The IDCG value is: " + idcg);

        // Calculate NDCG (Normalized Discounted Cumulative Gain)
        double ndcg = NDCGCalculator.calculateNDCG(dcg, idcg);
        System.out.println("The NDCG value is: " + ndcg);

        // Check if the ranking is perfect or not
        if (NDCGCalculator.checkRanking(ndcg)) {
            System.out.println("The ranking is almost perfect...");
        } else {
            System.out.println("The ranking is perfect!");
        }
    }

    private static List<Double> getRelevanceScores(List<Feedback> rankedFeedbacksList) {
        return rankedFeedbacksList.stream().map(Feedback::getRelevanceScore).collect(Collectors.toList());
    }
}