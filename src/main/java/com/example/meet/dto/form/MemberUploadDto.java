package com.example.meet.dto.form;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

@Data
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class MemberUploadDto {

    private String username;

    private String uploadFilename;

    private String storeFilename;

    private MultipartFile profileImgFile;
}
