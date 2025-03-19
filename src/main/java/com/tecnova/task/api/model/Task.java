package com.tecnova.task.api.model;

import jakarta.persistence.*;
import lombok.*;

@Table(name = "TASK")
@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Task {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(nullable = false)
    private Integer id;
    @Column(nullable = false)
    private String description;

    @ManyToOne
    @JoinColumn(name = "USER_ID",nullable = false)
    private User user;

    @ManyToOne
    @JoinColumn(name = "STATE_TASK_ID",nullable = false)
    private StateTask state;
}
