package com.example.demo.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.transaction.annotation.Transactional;

@Entity
@Table(name = "messages")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Transactional
public class Message {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_message", nullable = false)
    private Long idMessage;

    @Column(name = "sent_date")
    private Date sentDate;

    @Column
    private String title;

    @Column(length = 1000)
    private String text;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "sender")
    private User sender;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "receiver")
    private User receiver;

    @Column
    private boolean isNegative;
}