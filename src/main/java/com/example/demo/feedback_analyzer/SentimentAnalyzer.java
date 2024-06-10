package com.example.demo.feedback_analyzer;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import edu.stanford.nlp.pipeline.CoreDocument;
import edu.stanford.nlp.pipeline.CoreSentence;
import edu.stanford.nlp.pipeline.StanfordCoreNLP;

/**
 * The Sentiment Analyzer Class
 * analyzes the sentiment of each feedback by using the Stanford CoreNLP library
 */
public class SentimentAnalyzer {

    public List<Feedback> rankedFeedbacks = new ArrayList<>();

    // Analyzes the sentiment of each feedback in the list
    public void analyzeSentiment(List<String> feedbacks) {
        // Create a StanfordCoreNLP object with the properties for sentiment analysis
        Properties props = new Properties();
        props.setProperty("annotators", "tokenize, ssplit, pos, lemma, parse, sentiment");
        StanfordCoreNLP pipeline = new StanfordCoreNLP(props);

        // ranking the feedbacks based on their sentiment from -1.0 to 3.0
        double rank = 0.0;

        for (String feedback : feedbacks) {
            // Annotate the feedback
            CoreDocument document = new CoreDocument(feedback);
            pipeline.annotate(document);

            // Iterate over the sentences in the document
            for (CoreSentence sentence : document.sentences()) {
                String sentiment = sentence.sentiment();
                if (sentiment.equals("Very negative")) {
                    rank = -1.0;
                } else if (sentiment.equals("Negative")) {
                    rank = 0.0;
                } else if (sentiment.equals("Neutral")) {
                    rank = 1.0;
                } else if (sentiment.equals("Positive")) {
                    rank = 2.0;
                } else if (sentiment.equals("Very positive")) {
                    rank = 3.0;
                } else {
                    rank = 0.0;
                }
                rankedFeedbacks.add(new Feedback(sentence.toString(), rank));

                // writing everything to a file without overwriting its previous content
                try {
                    FileWriter myWriter = new FileWriter("SentimentAnalysis.txt", true);
                    myWriter.write(
                            sentence + " \n~~~~~~~~~~~~~~~~~~~ SENTIMENT ~~~~~~~~~~~~~~~~~~~ " + sentiment + " " + rank
                                    + "\n");
                    myWriter.write("\n");
                    myWriter.close();
                } catch (IOException e) {
                    System.out.println("An error occurred.");
                    e.printStackTrace();
                }
            }
        }
    }

    public static void main(String[] args) {
        Properties props = new Properties();
        props.setProperty("annotators", "tokenize, ssplit, pos, lemma, parse, sentiment");
        StanfordCoreNLP pipeline = new StanfordCoreNLP(props);

        String textNegative = "Subject: Unacceptable Service\n" + "\n" + "Dear [Recipient],\n" + "\n"
                + "I am thoroughly displeased with the service provided by your company "
                + "The level of incompetence and disregard for customer satisfaction is unacceptable "
                + "I demand a full refund and prompt action to rectify this situation "
                + "Failure to do so will result in further escalation of this matter";

        CoreDocument document = new CoreDocument(textNegative);
        pipeline.annotate(document);

        for (CoreSentence sentence : document.sentences()) {
            String sentiment = sentence.sentiment();
            System.out.println(sentence + " \n~~~~~~~~~~~~~~~~~~~ SENTIMENT ~~~~~~~~~~~~~~~~~~~ " + sentiment);
        }
    }
}