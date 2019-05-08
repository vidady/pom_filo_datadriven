package com.qa.tests;

import java.util.Hashtable;

import org.testng.annotations.Test;

import com.qa.base.TestBase;
import com.qa.pages.IDP_Dashboard;
import com.qa.pages.IDP_LoginPage;

public class R03 extends TestBase{
	
	IDP_LoginPage lp;
	IDP_Dashboard dashboard;
	
	
	@Test(dataProvider="dataProvider", priority=1)
	public void TC_374980(Hashtable<String,String> data) {
		initial_test_tasks(data);
		navigateTo("https://qa2012r2-vr3.inqa.soti.net/login");
		lp=new IDP_LoginPage();
		dashboard=lp.login(data.get("username"), data.get("password"));
		
		
	}
	
	@Test(dataProvider="dataProvider", priority=2)
	public void TC_375423_375424(Hashtable<String,String>data) {
		initial_test_tasks(data);
		dashboard.addApplication("MobiControl");
		dashboard.verifyApplicationFieldValidation(data);
		
	}
	
	
	
	
	
	

}
