package com.project.lovable.service;

import com.project.lovable.dto.deploy.DeployResponse;

public interface DeploymentService {
    DeployResponse deploy(Long projectId);
}
