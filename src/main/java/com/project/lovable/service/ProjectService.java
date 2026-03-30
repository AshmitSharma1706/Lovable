package com.project.lovable.service;

import com.project.lovable.dto.project.ProjectRequest;
import com.project.lovable.dto.project.ProjectResponse;
import com.project.lovable.dto.project.ProjectSummaryResponse;
import org.jspecify.annotations.Nullable;

import java.util.List;

public interface ProjectService {
    List<ProjectSummaryResponse> getUserProjects(Long userId);

    ProjectResponse getUserProjectById(Long projectId, Long userId);

    ProjectResponse createProject(ProjectRequest request, Long userId);

    ProjectResponse updateProject(Long projectId, ProjectRequest request, Long userId);

    void softDelete(Long projectId, Long userId);
}
