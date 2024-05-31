package com.example.service;

import com.example.dto.ApiResponse;
import com.example.dto.attach.FileResponse;
import com.example.entity.AttachEntity;
import com.example.enums.AppLanguage;
import com.example.exception.APIException;
import com.example.repository.AttachRepository;
import com.example.usecases.FileUseCase;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Objects;
import java.util.UUID;
import java.util.function.Function;

import static java.time.LocalDateTime.now;

@Service
public class AttachService implements FileUseCase<MultipartFile, FileResponse> {
    @Value("${attach.open.url}")
    private String fileUrl;
    private final AttachRepository attachRepository;

    public AttachService(AttachRepository attachRepository) {
        this.attachRepository = attachRepository;
    }

    @Override
    public ApiResponse<FileResponse> uploadFile(MultipartFile fileRequest, AppLanguage lang) {
        String fileUploadDir = "attach/" + getYearMonthDay();
        String extension = getExtension(Objects.requireNonNull(fileRequest.getOriginalFilename()));
        String uuid = UUID.randomUUID().toString();
        String fileId = uuid + "." + extension;
        var directory = new File(fileUploadDir);
        if (!directory.exists()) directory.mkdirs();
        try {
            Path path = Paths.get(fileUploadDir + fileId);
            Files.write(path, fileRequest.getBytes());
            var fileEntity = AttachEntity.builder()
                    .id(fileId)
                    .size(fileRequest.getSize())
                    .filename(fileRequest.getOriginalFilename())
                    .extension(extension)
                    .path(fileUploadDir)
                    .build();

            attachRepository.save(fileEntity);
            var responseMapper = mapToFileResponse();

            return new ApiResponse<>(200, false, responseMapper.apply(fileEntity));

        } catch (IOException e) {
            throw new APIException(lang.name(), 404);
        }
    }
    private String getYearMonthDay(){
    return String.format("%s/%s/%s/",now().getYear(),now().getMonthValue(),now().getDayOfMonth());
    }
    public String getExtension(String fileName) { // mp3/jpg/npg/mp4.....
        int lastIndex = fileName.lastIndexOf(".");
        return fileName.substring(lastIndex + 1);
    }
    private Function<AttachEntity, FileResponse> mapToFileResponse() {
        return file -> FileResponse.builder()
                .id(file.getId())
                .size(file.getSize())
                .createdDate(file.getCreatedDate())
                .url(fileUrl + file.getId())
                .extension(file.getExtension())
                .filename(file.getFilename())
                .build();
    }
}

