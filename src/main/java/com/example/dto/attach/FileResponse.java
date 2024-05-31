package com.example.dto.attach;

import lombok.Builder;

import java.time.LocalDateTime;
@Builder
public record FileResponse(
        String id,
        String filename,
        String path,
        Long size,
        String url,
        String extension,
        LocalDateTime createdDate
) {
}
