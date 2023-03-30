package com.bsib.demo.fileUploader.service;

import com.bsib.demo.fileUploader.exception.FileException;
import com.bsib.demo.fileUploader.model.FileInfo;
import com.bsib.demo.fileUploader.repository.FileRepository;
import lombok.extern.log4j.Log4j2;
import org.apache.tomcat.util.http.fileupload.impl.FileSizeLimitExceededException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.transaction.Transactional;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;

@Service
@Transactional
@Log4j2
public class FileService {

    @Value("${file.upload-dir}")
    private Path fileStorageLocation;
    @Autowired
    private FileRepository fileRepository;
    @Autowired
    private EmailService emailService;

    public FileInfo saveFile(MultipartFile multipartFile, FileInfo fileInfo) throws FileException, FileSizeLimitExceededException {
        if (multipartFile.getContentType().equalsIgnoreCase("application/pdf") ||
            multipartFile.getContentType().equalsIgnoreCase("image/png")) {
            String fileName = storeFile(multipartFile);
            if (fileName != "") {
                String fileDownloadUri = ServletUriComponentsBuilder.fromCurrentContextPath()
                        .path("/" + this.fileStorageLocation.toString() + "/")
                        .path(fileName)
                        .toUriString();

                fileInfo.setFileName(fileName);
                fileInfo.setFilePath(fileDownloadUri);
                emailService.sendEmail(fileInfo.getEmail());
                log.info("File Save Success : " + fileInfo);
            }
            return fileRepository.save(fileInfo);
        } else {
            throw new FileException("Allowed pdf & png files only");
        }
    }

    private String storeFile(MultipartFile file) {
        String fileName = StringUtils.cleanPath(file.getOriginalFilename());

        try {
            if(fileName.contains("..")) {
                throw new FileException("Sorry! Filename contains invalid path sequence " + fileName);
            }
            Path targetLocation = this.fileStorageLocation.resolve(fileName);
            if (!targetLocation.toFile().exists()) {
                Files.createDirectories(targetLocation.toAbsolutePath().normalize());
            }
            Files.copy(file.getInputStream(), targetLocation, StandardCopyOption.REPLACE_EXISTING);

            return fileName;
        } catch (IOException ex) {
            throw new FileException("Could not store file " + fileName + ". Please try again!", ex);
        }
    }

}
