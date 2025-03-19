package com.tecnova.task.api.model;

import jakarta.persistence.*;
import lombok.*;

@Table(name = "STATE_TASK")
@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class StateTask {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "ID_STATE_TASK",nullable = false)
    private Integer stateTaskId;

    @Column(  nullable = false)
    private String description;
}
