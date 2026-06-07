package com.project.lovable.service.impl;

import com.project.lovable.dto.project.FileContentResponse;
import com.project.lovable.dto.project.FileNode;
import com.project.lovable.entity.Project;
import com.project.lovable.entity.ProjectFile;
import com.project.lovable.error.ResourceNotFoundException;
import com.project.lovable.mapper.ProjectFileMapper;
import com.project.lovable.repository.ProjectFileRepository;
import com.project.lovable.repository.ProjectRepository;
import com.project.lovable.service.ProjectFileService;
import io.minio.MinioClient;
import io.minio.PutObjectArgs;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.net.URLConnection;
import java.nio.charset.StandardCharsets;
import java.time.Instant;
import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class ProjectFileServiceImpl implements ProjectFileService {

    private final ProjectRepository projectRepository;
    private final ProjectFileRepository projectFileRepository;
    private final MinioClient minioClient;
    private final ProjectFileMapper projectFileMapper;

    @Value("${minio.project-bucket}")
    private String projectBucket;

    private static final String BUCKET_NAME = "lovable-ai";

    @Override
    public List<FileNode> getFileTree(Long projectId, Long userId) {
        List<ProjectFile> projectFileList = projectFileRepository.findByProjectId(projectId);
        return projectFileMapper.toListOfFileNode(projectFileList);
    }

    @Override
    public FileContentResponse getFileContent(Long projectId, String path, Long userId) {
        return null;
    }

    @Override
    public void saveFile(Long projectId, String filePath, String fileContent) {
        log.info("Saving file: {}", filePath);
        Project project=projectRepository.findById(projectId).orElseThrow(
                () -> new ResourceNotFoundException("Project", projectId.toString())
        );

        String cleanPath= filePath.startsWith("/") ? filePath.substring(1) : filePath;
        String objectKey= projectId +"/"+ cleanPath;

        try {
            byte[] contentBytes = fileContent.getBytes(StandardCharsets.UTF_8);
            InputStream inputStream = new ByteArrayInputStream(contentBytes);
            // saving the file content
            minioClient.putObject(
                    PutObjectArgs.builder()
                            .bucket(projectBucket)
                            .object(objectKey)
                            .stream(inputStream, contentBytes.length, -1)
                            .contentType(determineContentType(filePath))
                            .build());

            // Saving the metaData
            ProjectFile file = projectFileRepository.findByProjectIdAndPath(projectId, cleanPath)
                    .orElseGet(() -> ProjectFile.builder()
                            .project(project)
                            .path(cleanPath)
                            .minioObjectKey(objectKey) // Use the key we generated
                            .createdAt(Instant.now())
                            .build());

            file.setUpdatedAt(Instant.now());
            projectFileRepository.save(file);
            log.info("File saved: {}", objectKey);
        } catch (Exception e) {
            log.error("Failed to save file {}/{}", projectId, cleanPath, e);
            throw new RuntimeException("File save failed", e);
        }

    }

    private String determineContentType(String path) {
        String type = URLConnection.guessContentTypeFromName(path);
        if (type != null) return type;
        if (path.endsWith(".jsx") || path.endsWith(".ts") || path.endsWith(".tsx")) return "text/javascript";
        if (path.endsWith(".json")) return "application/json";
        if (path.endsWith(".css")) return "text/css";

        return "text/plain";
    }
}
