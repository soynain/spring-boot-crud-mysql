package com.crudspringdbconn.main.Formulario;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;

import javax.annotation.Resource;

import org.apache.tomcat.util.http.parser.MediaType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.crudspringdbconn.main.services.FileStorageServiceImpl;

@Controller
@RequestMapping("/")
public class FormularioController {
    private static final Logger logger = LoggerFactory.getLogger(FormularioController.class);
    @Autowired
    private FileStorageServiceImpl fileStorage;

    /*
     * This controller just works to handle the form
     * interaction and get the params and file
     * info
     */
    @GetMapping("/")
    public ModelAndView getFormulario() {
        ModelAndView formularioGetter = new ModelAndView("formulario");
        return formularioGetter;
    }

    @PostMapping(path = "/file/upload", consumes = { org.springframework.http.MediaType.MULTIPART_FORM_DATA_VALUE })
    public ResponseEntity<Object> saveFile(@RequestBody MultipartFile[] documents) throws IOException {
        fileStorage.save(documents);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/upload/{filename:.+}")
    public ResponseEntity<org.springframework.core.io.Resource> getDocument(@PathVariable String filename) throws IOException {
        org.springframework.core.io.Resource file = fileStorage.load(filename);
        if(Files.probeContentType(file.getFile().toPath()).toString().equals("application/pdf")){
            return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "inline; filename=\"" + file.getFilename() + "\";")
                .contentType(org.springframework.http.MediaType.APPLICATION_PDF)
                .body(file);
        }else{
            return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + file.getFilename() + "\";")
                .contentType(org.springframework.http.MediaType.APPLICATION_PDF)
                .body(file);
        } 
    }
}
