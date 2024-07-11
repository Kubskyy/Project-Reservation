package com.reservation.web.service;

import com.reservation.web.dto.ProjectDto;
import com.reservation.web.models.Project;
import com.reservation.web.models.UserEntity;

import java.util.List;

public interface ProjectService {
    List<ProjectDto> findAllProjects();
    Project saveProject(ProjectDto projectDto);

    void updateProject(Long userId, ProjectDto project);

    ProjectDto findProjectById(long projectId);

    void delete(long projectId);

    List<ProjectDto> searchProjects(String query);

    List<ProjectDto> findAllProjectsByUser(Long userId);
}
