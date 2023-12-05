package com.fileUploading.FileUploading.services;

import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

@Component
public class FileUploadHelper {
    public final String UPLOAD_DIR = "D:\\Work\\Spring-Boot\\FileUploading\\src\\main\\resources\\static\\image";

    public boolean uploadFle(MultipartFile multipartFile) {
        boolean f = false;
        try {
            // type 1
//            InputStream is= multipartFile.getInputStream();
//            byte[] data =new byte[is.available()];
//            is.read(data);
//
//            //write
//            FileOutputStream fos= new FileOutputStream(UPLOAD_DIR+ File.separator+multipartFile.getOriginalFilename());
//            fos.write(data);
//            fos.flush();
//            fos.close();

            //type 2
            Files.copy(multipartFile.getInputStream(), Paths.get(UPLOAD_DIR + File.separator + multipartFile.getOriginalFilename()), StandardCopyOption.REPLACE_EXISTING);

            f = true;


        } catch (Exception e) {
            e.printStackTrace();
        }

        return f;
    }
}
