package com.project.lovable.dto.project;

import java.util.List;

public record FileTreeResponse(
        List<FileNode> files
) {
}
