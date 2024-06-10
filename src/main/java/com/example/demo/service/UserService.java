package com.example.demo.service;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.List;
import java.util.Objects;

import javax.transaction.Transactional;

import com.example.demo.Role;
import com.example.demo.model.Message;
import com.example.demo.model.User;
import com.example.demo.repository.MessageRepository;
import com.example.demo.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private MessageRepository messageRepository;

    @Transactional
    public User save(User user) {
        user.setRole(Role.user);
        String password = user.getPassword();
        String encryptedpassword = null;

        //password encryption
        try {
            /* MessageDigest instance for MD5. */
            MessageDigest m = MessageDigest.getInstance("MD5");

            /* Add plain-text password bytes to digest using MD5 update() method. */
            m.update(password.getBytes());

            /* Convert the hash value into bytes */
            byte[] bytes = m.digest();

            /* The bytes array has bytes in decimal form. Converting it into hexadecimal format. */
            StringBuilder s = new StringBuilder();
            for (int i = 0; i < bytes.length; i++) {
                s.append(Integer.toString((bytes[i] & 0xff) + 0x100, 16).substring(1));
            }

            /* Complete hashed password in hexadecimal format */
            encryptedpassword = s.toString();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }

        user.setPassword(encryptedpassword);
        return userRepository.save(user);
    }

    @Transactional
    public User updateByEmail(String email, String firstName, String lastName, String mobileNumber) {
        User user = userRepository.findUserByEmail(email);
        if (!Objects.equals(firstName, "empty")) {
            user.setFirstName(firstName);
        }
        if (!Objects.equals(lastName, "empty")) {
            user.setLastName(lastName);
        }
        if (!Objects.equals(mobileNumber, "empty")) {
            user.setMobileNumber(mobileNumber);
        }
        return userRepository.save(user);
    }

    @Transactional
    public User updatePasswordByEmail(String email, String password) {
        User user = userRepository.findUserByEmail(email);
        String encryptedpassword = null;

        //password encryption
        try {
            /* MessageDigest instance for MD5. */
            MessageDigest m = MessageDigest.getInstance("MD5");

            /* Add plain-text password bytes to digest using MD5 update() method. */
            m.update(password.getBytes());

            /* Convert the hash value into bytes */
            byte[] bytes = m.digest();

            /* The bytes array has bytes in decimal form. Converting it into hexadecimal format. */
            StringBuilder s = new StringBuilder();
            for (int i = 0; i < bytes.length; i++) {
                s.append(Integer.toString((bytes[i] & 0xff) + 0x100, 16).substring(1));
            }

            /* Complete hashed password in hexadecimal format */
            encryptedpassword = s.toString();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }

        user.setPassword(encryptedpassword);
        return userRepository.save(user);
    }

    public User findByUsernameAndPassword(String email, String password) {
        return userRepository.findUserByEmailAndPassword(email, password);
    }

    @Transactional
    public User findByEmail(String email) {
        return userRepository.findUserByEmail(email);
    }

    public void deleteMessage(String email, String title) {
        User user = userRepository.findUserByEmail(email);
        Message message = messageRepository.findMessagesByTitleAndSender(title, user);
        messageRepository.delete(message);
        List<Message> responses = messageRepository.findMessagesByReceiver(user);
        for (Message rsp : responses)
            if (rsp.getTitle().contains(title)) {
                messageRepository.delete(rsp);
                break;
            }
    }
}