package com.qa.tests;

import java.util.ArrayList;
import java.util.List;

import org.testng.TestNG;
import org.testng.xml.XmlClass;
import org.testng.xml.XmlSuite;
import org.testng.xml.XmlTest;

import com.qa.util.FiloExcelReader;


public class runner {

	public  XmlTest test;
	public  List<XmlClass> classes;
	public  List<XmlSuite> suites;
	public  List<XmlClass> classList;
	public  List<XmlSuite> suiteList;
	public  TestNG tng;
	public  XmlSuite suite;
	public String Sheetname;
	
	
	public void test_runner(){
	

		
	
		suite = new XmlSuite();
		suite.setName("Portal Automation Testing");
		suite.addListener("com.qa.ExtentReportListener.ExtentReporterNG" );
		
		
		for(String tcid:FiloExcelReader.getAllTestClasses()) {
			System.out.println(tcid);
			test = new XmlTest(suite);
			test.setName(tcid.toString().trim());
			classes = new ArrayList<XmlClass>();
			classes.add(new XmlClass("com.qa.tests."+tcid));
			test.setXmlClasses(classes);
		}
		
		
		suites = new ArrayList<XmlSuite>();
		suites.add(suite);
		tng = new TestNG();
		tng.setXmlSuites(suites);
		tng.run();


	}
	
	public static void main(String[] args) {
		runner r=new runner();
		r.test_runner();
	}
	
}


