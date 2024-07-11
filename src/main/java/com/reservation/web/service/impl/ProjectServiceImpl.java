package com.reservation.web.service.impl;

import com.reservation.web.dto.ProjectDto;
import com.reservation.web.models.Project;
import com.reservation.web.models.UserEntity;
import com.reservation.web.repository.ProjectRepository;
import com.reservation.web.repository.UserRepository;
import com.reservation.web.security.SecurityUtil;
import com.reservation.web.service.ProjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


@Service
public class ProjectServiceImpl implements ProjectService {
    private ProjectRepository projectRepository;
    private UserRepository userRepository;

    @Autowired
    public ProjectServiceImpl(ProjectRepository projectRepository, UserRepository userRepository) {
        this.projectRepository = projectRepository;
        this.userRepository = userRepository;
    }

    @Override
    public List<ProjectDto> findAllProjects(){
        List<Project> projects = projectRepository.findAll();
        return projects.stream().map((project) -> mapToProjectDto(project)).collect(Collectors.toList());
    }

    @Override
    public Project saveProject(ProjectDto projectDto) {
        Project project = mapToProject(projectDto);
        return projectRepository.save(project);
    }

    @Override
    public void updateProject(Long userId, ProjectDto projectDto) {
        String username = SecurityUtil.getSessionUser();
        UserEntity user = userRepository.findById(userId).get();
        Project project = mapToProject(projectDto);
        project.setUser(user);
        if(projectDto.isOccupied() == false){
            project.setUser(null);
        }
        projectRepository.save(project);
    }

    @Override
    public ProjectDto findProjectById(long projectId) {
        Project project = projectRepository.findById(projectId).get();
        return mapToProjectDto(project);
    }

    @Override
    public void delete(long projectId) {
        projectRepository.deleteById(projectId);
    }

    @Override
    public List<ProjectDto> searchProjects(String query) {
        List<Project> projects = projectRepository.searchProjects(query);
        return projects.stream().map(project->mapToProjectDto(project)).collect(Collectors.toList());
    }

    @Override
    public List<ProjectDto> findAllProjectsByUser(Long userId) {
        List<Project> projects = projectRepository.searchProjectsByUser(userId);
        return projects.stream().map(project->mapToProjectDto(project)).collect(Collectors.toList());
    }

    private Project mapToProject(ProjectDto project){
        Project projectDto = Project.builder()
                .id(project.getId())
                .topic(project.getTopic())
                .teacher(project.getTeacher())
                .description(project.getDescription())
                .occupied(project.isOccupied())
                .build();
        return projectDto;

    }
    private ProjectDto mapToProjectDto(Project project){
        ProjectDto projectDto = ProjectDto.builder()
                .id(project.getId())
                .topic(project.getTopic())
                .teacher(project.getTeacher())
                .description(project.getDescription())
                .occupied(project.isOccupied())
                .build();
        return projectDto;
    }

}
