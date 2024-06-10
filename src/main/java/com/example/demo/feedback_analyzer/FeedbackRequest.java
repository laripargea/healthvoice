package com.example.demo.feedback_analyzer;

/**
 * The feedback.FeedbackRequest Class
 */
public class FeedbackRequest {

    private String feedback;

    public FeedbackRequest() {
    }

    public FeedbackRequest(String feedback) {
        this.feedback = feedback;
    }

    public String getFeedback() {
        return feedback;
    }

    public void setFeedback(String feedback) {
        this.feedback = feedback;
    }
}