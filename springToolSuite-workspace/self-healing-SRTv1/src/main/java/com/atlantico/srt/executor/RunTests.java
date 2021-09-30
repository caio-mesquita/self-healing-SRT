package com.atlantico.srt.executor;

import org.junit.runner.JUnitCore;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
//import java.nio.file.Paths;
import org.junit.internal.TextListener;



public class RunTests {
	
	
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
	 * Method to run the test scripts.
	 * @param	classPath: directory where the test script is stored in 'Path' type
	 * @param	file_name: file name in String type
	 * 
	 * Note: the method tries to run the test script with a retry limit
	 * (in this case, 10 attempts). This is to wait for the test script 
	 * to be saved safely before running.
	 * Thus, if the max retry limit is achieved, the microservice does not
	 * run the test script and returns the message that is in the FileController.java script.
	 * 
	 */
	public void run(Path classPath, String file_name) throws ClassNotFoundException, InterruptedException, IOException
	{
		
		boolean control = true;
		
		JUnitCore junitCore = new JUnitCore();
        junitCore.addListener(new TextListener(System.out));
        String file_name_noExt = getBaseName(file_name);
        
        if(Files.exists(classPath)) {
        	
        	int maxRetries = 10;
    		int retries = 0;
        	System.out.println("Running your test ...");
        	
        	while(control) {
        		
        		retries += 1;
        		if(retries == maxRetries) {
        			System.out.println("Maximum retries of test execution reached.");
        			break;        			
        		}
        		Thread.sleep(1000);
        		//System.out.println(retries);
        		try {
        			
        			Class<?> testClass = Class.forName("com.atlantico.srt.tests." + file_name_noExt);	//local directory 
        			        			
        			junitCore.run(testClass);			// any exception here on this line, gonna make 'control = false' anyway
        			control = false;
        			
        		} catch (ClassNotFoundException e){
        			continue;
        		}
        		
        	}
        	
        }
        
	}
	
}