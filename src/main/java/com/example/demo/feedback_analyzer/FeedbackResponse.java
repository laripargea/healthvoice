package com.example.demo.feedback_analyzer;

/**
 * The feedback.FeedbackResponse Class
 */
public class FeedbackResponse {

    private String sentiment;

    public FeedbackResponse() {
    }

    public FeedbackResponse(String sentiment) {
        this.sentiment = sentiment;
    }

    public String getSentiment() {
        return sentiment;
    }
}