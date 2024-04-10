package com.taskswift.main.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Entity
@Getter
@Setter
@ToString
public class Notification {

    @Id
    private Long notificationId;

    private String message;

    private boolean notfForAll;

    @ManyToOne
    @JoinColumn(name = "user_name")
    private User user;

}
