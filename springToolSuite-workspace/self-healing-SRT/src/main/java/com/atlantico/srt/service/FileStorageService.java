package com.atlantico.srt.service;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
//import java.util.stream.Stream;
import java.nio.file.StandardCopyOption;

import javax.annotation.PostConstruct;
//import javax.servlet.ServletContext;

//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import com.atlantico.srt.executor.RunTests;
import com.atlantico.srt.model.FileDB;



@Service
public class FileStorageService {
	
//	@Autowired
//	ServletContext context;
//	@Value("${upload.path}")
//    public String uploadPath;
	
	
	/*
	 * Method to get the directory ('Paths' type) where the test scripts should to be saved.
	 */
	@PostConstruct
    public Path init() {
        try {
        	String userDirectory = new File("").getAbsolutePath();
        	String userD = userDirectory.replace("\\", "/");
        	String realPath = userD + "/src/main/java/com/atlantico/srt/tests";		// local directory of the test script 
        	
        	//Files.createDirectories(Paths.get(realPath));
            return Paths.get(realPath); 
            
        } catch (Exception e) {
            throw new RuntimeException("Could not create upload folder!");
        }
        
    }
	
	/*
	 * Method to get the basename of the test script file, i.e., without
	 * the extension (e.g. without ".java").
	 * @param	fileName_: filename in String type
	 */
	public String getBaseName(String fileName_) {
	    int index = fileName_.lastIndexOf('.');
	    if (index == -1) {
	        return fileName_;
	    } else {
	        return fileName_.substring(0, index);
	    }
	}
	
	/*
	 * Main method with which we store the test script and run it.
	 */
    public void store(MultipartFile file) throws IOException, ClassNotFoundException, InterruptedException {
    	
    	
        String fileName = StringUtils.cleanPath(file.getOriginalFilename());
        Path realRoot = init();
        
        RunTests obj = new RunTests();
        FileDB fileDB = new FileDB();
        fileDB.setFilename(fileName); 
        fileDB.setRoot(realRoot);
        
        Path targetLocation = realRoot.resolve(fileName);
        
        try {
        	if (Files.exists(realRoot)) {            
	            Files.copy(file.getInputStream(), targetLocation, StandardCopyOption.REPLACE_EXISTING);
	            file.getInputStream().close();
	            obj.run(targetLocation, fileName);			// where the test is run
	            
	        }
        	
        } catch (IOException e) {
        	throw new RuntimeException("Directory does not exists or overwriting process failed :/ ");
        }

     }

}
