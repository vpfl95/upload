package hello.upload.domain;

import lombok.Data;

@Data
public class UploadFile {

    private String uploadFileName;  //업로드한 파일 이름 :원래 파일 이름
    private String storeFileName;   //디스크에 저장된 파일 이름 : uuid

    public UploadFile(String uploadFileName, String storeFileName) {
        this.uploadFileName = uploadFileName;
        this.storeFileName = storeFileName;
    }
}
