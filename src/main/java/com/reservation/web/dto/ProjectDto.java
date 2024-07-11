package com.reservation.web.dto;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotEmpty;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ProjectDto {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @NotEmpty(message = "Topic should not be empty")
    private String topic;
    @NotEmpty(message = "Teacher field should not be empty")
    private String teacher;
    @NotEmpty(message = "Project should have some description")
    private String description;
    private boolean occupied;
}
