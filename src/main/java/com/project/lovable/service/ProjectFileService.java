package com.project.lovable.service;

import com.project.lovable.dto.project.FileContentResponse;
import com.project.lovable.dto.project.FileNode;
import com.project.lovable.dto.project.FileTreeResponse;

import java.util.List;

public interface ProjectFileService {
    FileTreeResponse getFileTree(Long projectId);

    FileContentResponse getFileContent(Long projectId, String path);

    void saveFile(Long projectId, String filePath, String fileContent);
}
