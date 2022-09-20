package com.crudspringdbconn.main.services;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.MalformedURLException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Random;
import java.util.stream.Stream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.util.FileSystemUtils;
import org.springframework.web.multipart.MultipartFile;

@Service
public class FileStorageServiceImpl implements FilesStorageService {
    /*I found a great resource to implement FileStorageService
     * suggested interface by spring
     * https://www.bezkoder.com/spring-boot-file-upload/
    */
    private final Path root = Paths.get("uploads");
    private static final Logger logger = LoggerFactory.getLogger(FileStorageServiceImpl.class);
    private final int leftLimit = 97; // letter 'a'
    private final int rightLimit = 122; // letter 'z'
    private final int targetStringLength = 10;

    @Override
    public void init() {
        try {
            if(!Files.exists(root)){
                Files.createDirectory(root);
            }   
        } catch (IOException e) {
            throw new RuntimeException("Could not initialize folder for upload!");
        }
    }

    @Override
    public void save(MultipartFile[] documents) {
        Arrays.asList(documents).stream().forEach(document -> {
          //  logger.info(document.getContentType() + " " + document.getOriginalFilename());
            try {
                Random random = new Random();
                String randomFileName = random.ints(leftLimit, rightLimit + 1)
                        .limit(targetStringLength)
                        .collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append)
                        .toString();
                if (document.getContentType().equals("application/pdf")) {
                    Files.copy(document.getInputStream(), this.root.resolve(randomFileName.concat(".pdf")));
                }else{
                    Files.copy(document.getInputStream(), this.root.resolve(randomFileName.concat(".docx")));
                }
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        });

    }

    @Override
    public Resource load(String filename) {
        try {
            Path file = root.resolve(filename);
            Resource resource = new UrlResource(file.toUri());

            if (resource.exists() || resource.isReadable()) {
                return resource;
            } else {
                throw new RuntimeException("Could not read the file!");
            }
        } catch (MalformedURLException e) {
            throw new RuntimeException("Error: " + e.getMessage());
        }
    }

    @Override
    public void deleteAll() {
        FileSystemUtils.deleteRecursively(root.toFile());
    }

    @Override
    public Stream<Path> loadAll() {
        try {
            return Files.walk(this.root, 1).filter(path -> !path.equals(this.root)).map(this.root::relativize);
        } catch (IOException e) {
            throw new RuntimeException("Could not load the files!");
        }
    }

}
