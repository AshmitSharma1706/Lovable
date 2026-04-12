package com.project.lovable.service.impl;

import com.project.lovable.dto.member.InviteMemberRequest;
import com.project.lovable.dto.member.MemberResponse;
import com.project.lovable.dto.member.UpdateMemberRoleRequest;
import com.project.lovable.entity.Project;
import com.project.lovable.entity.ProjectMember;
import com.project.lovable.entity.ProjectMemberId;
import com.project.lovable.entity.User;
import com.project.lovable.mapper.ProjectMemberMapper;
import com.project.lovable.repository.ProjectMemberRepository;
import com.project.lovable.repository.ProjectRepository;
import com.project.lovable.repository.UserRepository;
import com.project.lovable.security.AuthUtil;
import com.project.lovable.service.ProjectMemberService;
import jakarta.transaction.Transactional;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;

@Service
@RequiredArgsConstructor
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
@Transactional
public class ProjectMemberServiceImpl implements ProjectMemberService {

    ProjectMemberRepository projectMemberRepository;
    ProjectRepository projectRepository;
    ProjectMemberMapper projectMemberMapper;
    UserRepository userRepository;
    AuthUtil authUtil;

    @Override
    public List<MemberResponse> getProjectMembers(Long projectId) {
        Long userId= authUtil.getCurrentUserId();
        Project project=getAccessibleProjectById(projectId, userId);
        return projectMemberRepository.findByIdProjectId(projectId)
                .stream().map(projectMemberMapper::toProjectMemberResponseFromMember)
                .toList();
    }

    @Override
    public MemberResponse inviteMember(Long projectId, InviteMemberRequest request) {
        Long userId= authUtil.getCurrentUserId();
        Project project=getAccessibleProjectById(projectId, userId);
        User invitee=userRepository.findByUsername(request.username()).orElseThrow();
        if(invitee.getId().equals(userId)){
            throw  new RuntimeException("Cannot invite yourself");
        }
        ProjectMemberId projectMemberId=new ProjectMemberId(projectId, invitee.getId());
        if(projectMemberRepository.existsById(projectMemberId)){
            throw  new RuntimeException("Cannot invite again");
        }
        ProjectMember member=ProjectMember.builder()
                .id(projectMemberId)
                .project(project)
                .user(invitee)
                .projectRole(request.role())
                .invitedAt(Instant.now())
                .build();
        projectMemberRepository.save(member);
        return projectMemberMapper.toProjectMemberResponseFromMember(member);
    }

    @Override
    public MemberResponse updateMemberRole(Long projectId, Long memberId, UpdateMemberRoleRequest request) {
        Long userId= authUtil.getCurrentUserId();
        Project project=getAccessibleProjectById(projectId, userId);
        ProjectMemberId projectMemberId=new ProjectMemberId(projectId, memberId);
        ProjectMember projectMember=projectMemberRepository.findById(projectMemberId).orElseThrow();
        projectMember.setProjectRole(request.role());
        projectMemberRepository.save(projectMember);
        return projectMemberMapper.toProjectMemberResponseFromMember(projectMember);
    }

    @Override
    public void removeProjectMember(Long projectId, Long memberId) {
        Long userId= authUtil.getCurrentUserId();
        Project project=getAccessibleProjectById(projectId, userId);
        ProjectMemberId projectMemberId=new ProjectMemberId(projectId, memberId);
        if(!projectMemberRepository.existsById(projectMemberId)){
            throw  new RuntimeException("Member not found in project");
        }

        projectMemberRepository.deleteById(projectMemberId);
    }

    private Project getAccessibleProjectById(Long projectId, Long userId){
        return projectRepository.findAccessibleProjectById(projectId,userId).orElseThrow();
    }
}
