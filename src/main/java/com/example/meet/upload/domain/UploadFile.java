package com.example.meet.upload.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Embeddable;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Embeddable
public class UploadFile {
    private String uploadFilename; //클라이언트에서 올린 파일명
    private String storeFilename; // 서버 저장용 파일명

}
