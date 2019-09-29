package com.qa.util;

import java.io.File;
import java.io.IOException;

import org.apache.commons.io.FileUtils;
import org.json.JSONArray;
import org.json.JSONObject;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;

import com.qa.base.TestBase;

public class TestUtil extends TestBase{
	
	public static long PAGE_LOAD_TIMEOUT=60;
	public static long IMPLICIT_WAIT=60;
	public static final String CHROMEDRIVER_EXE=System.getProperty("user.dir")+"/chromedriver.exe";
	public static final String FIREFOXDRIVER_EXE=System.getProperty("user.dir")+"/geckodriver.exe";
	public static final String CHROMEDRIVER_MAC=System.getProperty("user.dir")+"/chromedriver";
	public static final String FIREFOXDRIVER_MAC=System.getProperty("user.dir")+"/geckodriver";
	public static final String LOG4J_PROPERTYFILE=System.getProperty("user.dir")+"/src/main/java/com/qa/config/log4j.properties";
	public static final int X_COORDINATE = 1440;
	public static final int Y_COORDINATE = 900;

	
	
	public static void takeScreenshot(){

		//decide name - time stamp
		//Date d=new Date();
		String screenshotFile="failed_screen.png";
		String path=AppConstants.SCREENSHOT_PATH;
		File testDirectory = new File(path);
        if (!testDirectory.exists()) {
            if (testDirectory.mkdir()) {
                System.out.println("Directory: " + path + " is created!" );
            } else {
            	System.out.println("Failed to create directory: " + path);
            }
        } else {
            System.out.println("Directory already exists: " + path);
        }

		File srcFile=((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);
		try{
			FileUtils.copyFile(srcFile, new File(path+"//"+screenshotFile));
		}catch (IOException e){
			e.printStackTrace();
		}

	}
	
	public static String getValueByJPath(JSONObject responsejson, String jpath){
		Object obj = responsejson;
		for(String s : jpath.split("/")) 
			if(!s.isEmpty()) 
				if(!(s.contains("[") || s.contains("]")))
					obj = ((JSONObject) obj).get(s);
				else if(s.contains("[") || s.contains("]"))
					obj = ((JSONArray) ((JSONObject) obj).get(s.split("\\[")[0])).get(Integer.parseInt(s.split("\\[")[1].replace("]", "")));
		return obj.toString();
	}
	



}
