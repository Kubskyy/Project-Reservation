package com.reservation.web.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "projects")
public class Project {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String topic;
    private String teacher;
    private String description;
    private boolean occupied;
    @ManyToOne
    @JoinColumn(name = "user_id")
    private UserEntity user;
}
