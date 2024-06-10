package com.example.demo.feedback_analyzer;

import java.util.Properties;

import edu.stanford.nlp.pipeline.CoreDocument;
import edu.stanford.nlp.pipeline.CoreSentence;
import edu.stanford.nlp.pipeline.StanfordCoreNLP;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * The feedback.Feedback Controller Class
 * creates the endpoint for the sentiment analysis of a feedback
 */
@RestController
public class FeedbackController {

    private static String analyzeSentimentFeedback(String feedback) {
        Properties props = new Properties();
        props.setProperty("annotators", "tokenize, ssplit, pos, lemma, parse, sentiment");
        StanfordCoreNLP pipeline = new StanfordCoreNLP(props);

        CoreDocument document = new CoreDocument(feedback);
        pipeline.annotate(document);

        for (CoreSentence sentence : document.sentences()) {
            String sentiment = sentence.sentiment();
            return sentiment;
        }
        return "No sentiment";
    }

    @CrossOrigin(origins = "http://localhost:3000")
    @PostMapping("/sentiment-analysis")
    public FeedbackResponse analyzeSentimentEndpoint(@RequestBody FeedbackRequest feedbackRequest) {
        return new FeedbackResponse(analyzeSentimentFeedback(feedbackRequest.getFeedback()));
    }
}