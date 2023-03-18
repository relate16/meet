package com.example.meet.upload;

import com.example.meet.upload.domain.UploadFile;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

@Component
public class FileStore {

    @Value("${file.dir}")
    private String fileDir;

    // 파일 전체 경로 리턴. 예: C:\User\JB\Downloads\meet_img\UUID.png
    public String getFullPath(String filename) {
        return fileDir + filename;
    }

    /**
     * 파일 저장 메서드
     * @param multipartFile
     */
    public UploadFile storeFile(MultipartFile multipartFile) throws IOException {
        // 리팩토링 필요
        if (multipartFile.isEmpty()) {
            return null;
        }
        String uploadFilename = multipartFile.getOriginalFilename(); // originalFilename : ex) img.png
        String storeFilename = createStoreFilename(uploadFilename);

        // 파일 경로에 저장
        multipartFile.transferTo(new File(getFullPath(storeFilename)));

        return new UploadFile(uploadFilename, storeFilename);

    }

    /**
     * sotreFilename UUID -> UUID.png 로 변환(확장자가 png 일 경우)
     */
    private String createStoreFilename(String uploadFilename) {
        String ext = extractExt(uploadFilename);
        String storeFilename = UUID.randomUUID() + "." + ext; // storeFilename : ex) UUID.png
        return storeFilename;
    }

    /**
     * uploadFilename myPhoto.png -> png 추출
     */
    private String extractExt(String uploadFilename) {
        int commaIndex = uploadFilename.lastIndexOf(".");
        String ext = uploadFilename.substring(commaIndex + 1); // 확장자 추출
        return ext;
    }


}
