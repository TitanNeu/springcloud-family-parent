package com.daddy.controller;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Objects;

/**
 * @ClassName UploadController
 * @Description TODO
 * @Author niuyp
 * @Date 2021/7/19 11:18
 * @Version 1.0
 **/
@Controller
@Slf4j
@CrossOrigin(origins = {"*"}, allowCredentials = "true")
public class UploadController {

    @Value("${file.upload.path}")
    private String localDirectoryPath;

    @GetMapping("/email/upload")
    public String uploadPage() {
        return "upload";
    }

    @PostMapping("/upload")
    @ResponseBody
    public String create(@RequestPart MultipartFile file) throws IOException {
        FileUtils.cleanDirectory(new File(localDirectoryPath));
        String fileName = null;
        if(Objects.requireNonNull(file.getOriginalFilename()).contains("html")) {
            fileName = "temp.html";
        }else {
            fileName =file.getOriginalFilename();
        }
        String filePath = localDirectoryPath + fileName;

        File dest = new File(filePath);
        Files.copy(file.getInputStream(), dest.toPath());
        return "Upload file success : " + dest.getAbsolutePath();
    }

}
