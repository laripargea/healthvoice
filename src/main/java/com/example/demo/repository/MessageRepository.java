package com.example.demo.repository;

import java.util.List;

import com.example.demo.model.Message;
import com.example.demo.model.User;
import com.sun.istack.NotNull;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MessageRepository extends JpaRepository<Message, Long> {

    List<Message> findMessagesBySender(@NotNull User user);

    List<Message> findMessagesByReceiver(@NotNull User user);

    Message findMessagesByTitleAndSender(@NotNull String title, @NotNull User user);

    Message findMessagesByTitleContaining(@NotNull String title);
}