package com.example.demo.service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.Properties;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import javax.transaction.Transactional;

import com.example.demo.Role;
import com.example.demo.model.Message;
import com.example.demo.model.User;
import com.example.demo.repository.MessageRepository;
import com.example.demo.repository.UserRepository;
import edu.stanford.nlp.pipeline.CoreDocument;
import edu.stanford.nlp.pipeline.CoreSentence;
import edu.stanford.nlp.pipeline.StanfordCoreNLP;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

@Service
public class MessageService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private MessageRepository messageRepository;

    @Autowired
    private JavaMailSender emailSender;

    @Transactional
    public Message save(Message message, String email) {
        User user = userRepository.findUserByEmail(email);
        message.setSender(user);
        boolean isNegative = saveResponse(message);
        if (isNegative) {
            message.setNegative(true);
        }
        return messageRepository.save(message);
    }

    @Transactional
    public Message saveAnonymous(Message message, String email) throws MessagingException {
        boolean isNegative = saveResponse(message);
        if (isNegative) {
            message.setNegative(true);
        }
        messageRepository.save(message);
        Message response = messageRepository.findMessagesByTitleContaining("Re: " + message.getTitle() + ")");
        sendMessage(email, response.getText());
        return message;
    }

    private void sendMessage(String to, String text) throws MessagingException {
        MimeMessage message = emailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true);
        helper.setTo(to);
        helper.setSubject("HealthVoice Feedback Response");
        String emailContent = text + "<br><br><i>HealthVoice Team<i>";
        helper.setText(emailContent, true);
        emailSender.send(message);
    }

    private boolean saveResponse(Message message) {
        boolean isNegative = false;
        Properties props = new Properties();
        props.setProperty("annotators", "tokenize, ssplit, pos, lemma, parse, sentiment");
        StanfordCoreNLP pipeline = new StanfordCoreNLP(props);
        String feedback = (message.getTitle() + " " + message.getText()).replace(".", " ");
        CoreDocument document = new CoreDocument(feedback);
        pipeline.annotate(document);
        String sentiment = "";
        Message response = new Message();
        response.setSentDate(message.getSentDate());
        response.setReceiver(message.getSender());
        for (CoreSentence sentence : document.sentences()) {
            sentiment = sentence.sentiment();
            System.out.println(sentiment);
        }
        if (Objects.equals(sentiment, "Positive")) {
            response.setTitle("Glad We Could Help (Re: " + message.getTitle() + ")");
            response.setText(
                    "It's wonderful to know that we met your expectations. We strive to provide the best possible care to all our patients, and your positive feedback reinforces our efforts. Thank you for sharing your experience with us.");
        } else if (Objects.equals(sentiment, "Negative")) {
            isNegative = true;
            response.setTitle("Addressing Your Concerns (Re: " + message.getTitle() + ")");
            response.setText(
                    "Thank you for sharing your feedback. We take your concerns seriously and will investigate the issue further. Rest assured, we are committed to resolving any issues you've encountered.");
        } else if (Objects.equals(sentiment, "Neutral")) {
            response.setTitle("Thank You for Your Feedback (Re: " + message.getTitle() + ")");
            response.setText(
                    "We appreciate you taking the time to share your thoughts with us. Your feedback helps us understand how we can better serve our patients. If you have any further comments or suggestions, please feel free to let us know.");
        } else if (Objects.equals(sentiment, "Very negative")) {
            isNegative = true;
            response.setTitle("Aiming for Improvement (Re: " + message.getTitle() + ")");
            response.setText(
                    "We sincerely apologize for the experience you had at our clinic, and we're deeply sorry to hear about your dissatisfaction. Please feel free to contact us directly at 1-800-555-1234 to discuss your concerns further. Your satisfaction is our top priority, and we are here to listen and make things right.");
        } else if (Objects.equals(sentiment, "Very positive")) {
            response.setTitle("Thrilled to Exceed Your Expectations (Re: " + message.getTitle() + ")");
            response.setText(
                    "We strive to provide the best possible care to all our patients, and it is incredibly rewarding to know that our efforts have made a positive impact on your health and well-being. Your feedback encourages us to continue working hard and maintaining the high standards of care that we pride ourselves on. Thank you for choosing us for your healthcare needs.");
        }
        response.setNegative(false);
        messageRepository.save(response);
        return isNegative;
    }

    public List<Message> getMessagesBySender(String email) {
        User user = userRepository.findUserByEmail(email);
        List<Message> messages;
        if (user.getRole() == Role.user) {
            messages = messageRepository.findMessagesBySender(user);
        } else {
            messages = getMessagesAdmin();
        }
        return messages;
    }

    public List<Message> getMessagesByReceiver(String email) {
        User user = userRepository.findUserByEmail(email);
        List<Message> messages = messageRepository.findMessagesByReceiver(user);
        messages.sort(Comparator.comparing(Message::getSentDate).reversed());
        return messages;
    }

    public List<Message> getMessagesAdmin() {
        List<Message> messages = messageRepository.findAll();
        List<Message> adminMessages = new ArrayList<>();
        for (Message message : messages) {
            if (message.isNegative()) {
                adminMessages.add(message);
            }
        }
        return adminMessages;
    }
}