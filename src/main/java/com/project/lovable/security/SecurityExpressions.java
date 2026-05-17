package com.project.lovable.security;

import com.project.lovable.enums.ProjectRole;
import com.project.lovable.repository.ProjectMemberRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Component;

@Component("security")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class SecurityExpressions {
    ProjectMemberRepository projectMemberRepository;
    AuthUtil authUtil;

    public boolean canViewProject(Long projectId){
        Long userId= authUtil.getCurrentUserId();
        return projectMemberRepository.findRoleByProjectIdAndUserId(projectId,userId)
                .map(role -> role.equals(ProjectRole.OWNER)
                                || role.equals(ProjectRole.EDITOR)
                                || role.equals(ProjectRole.VIEWER))
                .orElse(false);
    }

    public boolean canEditProject(Long projectId){
        Long userId= authUtil.getCurrentUserId();
        return projectMemberRepository.findRoleByProjectIdAndUserId(projectId,userId)
                .map(role -> role.equals(ProjectRole.OWNER)
                        || role.equals(ProjectRole.EDITOR))
                .orElse(false);
    }
}
