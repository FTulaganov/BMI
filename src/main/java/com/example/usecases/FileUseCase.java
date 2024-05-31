package com.example.usecases;

import com.example.dto.ApiResponse;
import com.example.dto.attach.FileResponse;
import com.example.enums.AppLanguage;

public interface FileUseCase<REQUEST,RESPONSE>{
    ApiResponse<FileResponse> uploadFile(REQUEST fileRequest, AppLanguage lang);
}
