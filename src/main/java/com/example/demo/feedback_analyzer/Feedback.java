package com.example.demo.feedback_analyzer;

/**
 * The feedback.Feedback Class
 * Used for storing the feedbacks, sentiment score of a feedback amd its relevance score
 */
public class Feedback implements Comparable<Feedback> {

    private String feedback;
    private Double sentimentScore;
    private Double relevanceScore;

    public Feedback(String feedback, Double sentimentScore) {
        this.feedback = feedback;
        this.sentimentScore = sentimentScore;
        this.relevanceScore = 0.0;
    }

    public Feedback() {
    }

    public String getFeedback() {
        return feedback;
    }

    public void setFeedback(String feedback) {
        this.feedback = feedback;
    }

    public Double getSentimentScore() {
        return sentimentScore;
    }

    public void setSentimentScore(Double sentimentScore) {
        this.sentimentScore = sentimentScore;
    }

    public Double getRelevanceScore() {
        return relevanceScore;
    }

    public void setRelevanceScore(Double relevanceScore) {
        this.relevanceScore = relevanceScore;
    }

    // Compare feedbacks based on their sentiment score
    @Override
    public int compareTo(Feedback other) {
        return Double.compare(other.sentimentScore, this.sentimentScore); // Descending order
    }

    // Override toString() method
    @Override
    public String toString() {
        return "feedback.Feedback: " + feedback + "\nScore: " + sentimentScore + "\nRelevanceScore: " + relevanceScore + "\n";
    }
}