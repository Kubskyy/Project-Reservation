package com.reservation.web.repository;

import com.reservation.web.models.Project;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface ProjectRepository extends JpaRepository<Project, Long> {
    Optional<Project> findByTopic(String url);
    @Query("SELECT p from Project p WHERE p.topic LIKE CONCAT('%', :query, '%')")
    List<Project> searchProjects(String query);

    @Query("SELECT p FROM Project p WHERE p.user.id = :userId")
    List<Project> searchProjectsByUser(Long userId);
}
