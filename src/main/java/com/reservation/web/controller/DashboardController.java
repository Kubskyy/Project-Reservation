package com.reservation.web.controller;

import org.springframework.ui.Model;
import com.reservation.web.dto.ProjectDto;
import com.reservation.web.models.UserEntity;
import com.reservation.web.service.ProjectService;
import com.reservation.web.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class DashboardController {
    private ProjectService projectService;
    private UserService userService;

    @Autowired
    public DashboardController(ProjectService projectService, UserService userService) {
        this.projectService = projectService;
        this.userService = userService;
    }

    private UserEntity getLoggedInUser() {
        Authentication authentication= SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();
        UserEntity user = userService.findByEmail(email);
        return user;
    }

    @GetMapping("/dashboard")
    public String dashboard(Model model){
        UserEntity user = getLoggedInUser();
        Long userId = user.getId();
        List<ProjectDto> projects = projectService.findAllProjectsByUser(userId);
        model.addAttribute("projects", projects);
        model.addAttribute("user", user);
        return "dashboard";
    }
}
