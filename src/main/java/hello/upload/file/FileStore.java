package hello.upload.file;

import hello.upload.domain.UploadFile;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Component
public class FileStore {

    @Value("${file.dir}")
    private String fileDir;

    // 경로 반환
    public String getFullPath(String fileName){
        return fileDir + fileName;
    }


    public List<UploadFile> storeFiles(List<MultipartFile> multipartFiles) throws IOException {
        List<UploadFile> storeFileResult = new ArrayList<>();
        
        for (MultipartFile multipartFile : multipartFiles) {
            if(!multipartFile.isEmpty()){ //multiparFile이 비어있지 않으면 루프 돌면서 파일 저장 하고 storeFileResult에 넣기
                storeFileResult.add(storeFile(multipartFile));
            }
        }
        return storeFileResult;
    }


    // 파일을 받아서 UploadFile로 반환
    public UploadFile storeFile(MultipartFile multipartFile) throws IOException {
        if(multipartFile.isEmpty()){
            return null;
        }
        String originalFilename = multipartFile.getOriginalFilename();
        // image.png
        // 서버에 저장하는 파일명
        String storeFileName = createStoreFileName(originalFilename);
        multipartFile.transferTo(new File(getFullPath(storeFileName))); //파일 저장
        return new UploadFile(originalFilename,storeFileName);
    }

    private String createStoreFileName(String originalFilename) {
        String ext = extractExt(originalFilename);
        String uuid = UUID.randomUUID().toString();
        String storeFileName = uuid + "." + ext; // uuid에 확장자 붙여서 저장할 파일이름
        return  storeFileName;
    }


    // 파일 확장자 뽑기
    private String extractExt(String originalFilename) {
        int pos = originalFilename.lastIndexOf(".");
        String ext = originalFilename.substring(pos + 1);
        return ext;
    }


}
