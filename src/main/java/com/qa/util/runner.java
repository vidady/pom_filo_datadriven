package com.qa.util;

import java.util.ArrayList;
import java.util.List;

import org.testng.TestNG;
import org.testng.annotations.Test;
import org.testng.xml.XmlClass;
import org.testng.xml.XmlSuite;
import org.testng.xml.XmlTest;


public class runner {

	public  XmlTest test;
	public  List<XmlClass> classes;
	public  List<XmlSuite> suites;
	public  TestNG tng;
	public  XmlSuite suite;
	public String Sheetname;
	
	
    @Test
	public void test_runner(){

		
		suite = new XmlSuite();
		suite.setName("Portal Automation Testing");
		suite.addListener("com.qa.ExtentReportListener.ExtentReporterNG" );
		
		
		for(String tcid:FiloExcelReader.getAllTestClasses()) {
			System.out.println(tcid);
			test = new XmlTest(suite);
			test.setName(tcid);
			classes = new ArrayList<XmlClass>();
			classes.add(new XmlClass("com.qa.testcases."+tcid));
			test.setXmlClasses(classes);
		}
		
		
		suites = new ArrayList<XmlSuite>();
		suites.add(suite);
		tng = new TestNG();
		tng.setXmlSuites(suites);
		tng.run();


	}}


