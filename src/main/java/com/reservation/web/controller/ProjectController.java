package com.reservation.web.controller;

import com.reservation.web.dto.ProjectDto;
import com.reservation.web.models.Project;
import com.reservation.web.models.UserEntity;
import com.reservation.web.security.SecurityUtil;
import com.reservation.web.service.UserService;
import jakarta.validation.Valid;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.ui.Model;
import com.reservation.web.service.ProjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@SessionAttributes({"username", "id"})
public class ProjectController {

    private ProjectService projectService;
    private UserService userService;

    @Autowired
    public ProjectController(ProjectService projectService, UserService userService) {
        this.projectService = projectService;
        this.userService = userService;
    }

    private UserEntity getLoggedInUser() {
        Authentication authentication= SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();
        UserEntity user = userService.findByEmail(email);
        return user;
    }

    @GetMapping("/projects")
    public String listProjects(Model model){
        List<ProjectDto> projects = projectService.findAllProjects();
        UserEntity user = getLoggedInUser();
        model.addAttribute("user", user);
        model.addAttribute("projects", projects);
        return "projects-list";

    }

    @GetMapping("/projects/search")
    public String searchProject(@RequestParam(value = "query") String query, Model model){
        List<ProjectDto> projects = projectService.searchProjects(query);
        model.addAttribute("projects", projects);
        return "projects-list";
    }

    @GetMapping("/projects/new")
    public String createProjectForm(Model model){
        Project project = new Project();
        model.addAttribute("project", project);
        return "project-create";
    }

    @PostMapping("/projects/new")
    public String saveProject(@Valid @ModelAttribute("project") ProjectDto projectDto,
                                BindingResult result, Model model){
//        if(result.hasErrors()){
//            model.addAttribute("project", projectDto);
//            return "project-create";
//        }
        projectService.saveProject(projectDto);
        return "redirect:/projects";
    }

    @GetMapping("/projects/{projectId}/delete")
    public String deleteProject(@PathVariable("projectId") long projectId){
        projectService.delete(projectId);
        return "redirect:/projects";
    }

    @GetMapping("/projects/{projectId}/edit")
    public String editProjectForm(@PathVariable("projectId") long projectId, Model model){
        ProjectDto project = projectService.findProjectById(projectId);
        model.addAttribute("project", project);
        return "project-edit";
    }

    @PostMapping("/projects/{projectId}/edit")
    public String updateProject(@PathVariable("projectId") Long projectId,
                                @Valid @ModelAttribute("project") ProjectDto project,
                                BindingResult result){
//        if(result.hasErrors()){
//            return "project-edit";
//        }
        project.setId(projectId);
        Long user_id = getLoggedInUser().getId();
        projectService.updateProject(user_id, project);
        return "redirect:/projects";
    }


}
