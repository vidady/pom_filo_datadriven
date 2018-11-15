package com.qa.tests;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map.Entry;

import org.testng.TestNG;
import org.testng.xml.XmlClass;
import org.testng.xml.XmlSuite;
import org.testng.xml.XmlTest;

import com.google.gson.JsonIOException;
import com.google.gson.JsonSyntaxException;
import com.qa.base.TestBase;
import com.qa.util.FiloExcelReader;
import com.qa.util.jsonReader;


public class runner extends TestBase {

	public  XmlTest test;
	public  List<XmlClass> classes;
	public  List<XmlSuite> suites;
	public  List<XmlClass> classList;
	public  List<XmlSuite> suiteList;
	public  TestNG tng;
	public  XmlSuite suite;
	public String Sheetname;
	
	
	public void test_runner() throws JsonIOException, JsonSyntaxException, FileNotFoundException{
	
		suite = new XmlSuite();
		suite.setName("Portal Automation Testing");
		suite.addListener("com.qa.ExtentReportListener.ExtentReporterNG" );
		suite.addListener("com.qa.util.WebEventListener" );
		
		switch(TestBase.config.getProperty("dataReadConfiguration")) {
		case "json":
			for(Entry<String,String> tcid:jsonReader.jsonGetTestClasses().entrySet()) {
				System.out.println(tcid.getValue().toString());
				test = new XmlTest(suite);
				test.setName(tcid.getValue().toString());
				classes = new ArrayList<XmlClass>();
				classes.add(new XmlClass("com.qa.tests."+tcid.getValue().toString()));
				test.setXmlClasses(classes);
				
			}
			break;
		case "excel":
			for(String tcid:FiloExcelReader.getAllTestClasses()) {
				System.out.println(tcid);
				test = new XmlTest(suite);
				test.setName(tcid.toString().trim());
				classes = new ArrayList<XmlClass>();
				classes.add(new XmlClass("com.qa.tests."+tcid));
				test.setXmlClasses(classes);
					
			}
			break;
			
			default:
				System.out.println("unable to read data from any of the data source");
				break;
		}
		
		
		
		suites = new ArrayList<XmlSuite>();
		suites.add(suite);
		tng = new TestNG();
		tng.setXmlSuites(suites);
		tng.run();


	}
	
	public static void main(String[] args) throws JsonIOException, JsonSyntaxException, FileNotFoundException {
		runner r=new runner();
		r.test_runner();
	}
	
}


