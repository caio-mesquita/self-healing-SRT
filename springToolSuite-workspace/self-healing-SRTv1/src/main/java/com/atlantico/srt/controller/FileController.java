package com.atlantico.srt.controller;

//import java.io.File;
//import java.nio.file.Files;
//import java.nio.file.Path;
//import java.util.List;
//import java.util.stream.Collectors;

//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
//import org.springframework.web.bind.annotation.CrossOrigin;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
//import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.atlantico.srt.service.FileStorageService;
//import com.atlantico.srt.message.ResponseFile;
import com.atlantico.srt.message.ResponseMessage;
//import com.atlantico.srt.model.FileDB;


@Controller
public class FileController {
	
	@Autowired
	private final FileStorageService fileService;
	
	
    public FileController(FileStorageService fileService) {
        this.fileService = fileService;
    }
    
    
    /*
     * POST method request for Self-Healing.
     * @param	file: test script with ".java" extension.
     * Returns a message of success or unsuccess whether the test script was run or not. 
     */
    @PostMapping("/autohealing")
    public ResponseEntity<ResponseMessage> uploadFile(@RequestParam("file") MultipartFile file) {
        String message = "";
        try {       	        	
        	
        	fileService.store(file);             

            message = "Test "  + file.getOriginalFilename() + " ran successfully!";
            
            return ResponseEntity.status(HttpStatus.OK).body(new ResponseMessage(message));       
        } catch (Exception e) {
            message = "Sorry, we could not run your test: " + file.getOriginalFilename() + " :'(" ;
            return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(new ResponseMessage(message));
        }
    }

}
