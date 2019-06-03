package com.qa.tests;

import java.util.Hashtable;

import org.testng.annotations.Test;

import com.qa.base.TestBase;
import com.qa.pages.IDP_Applications;
import com.qa.pages.IDP_Dashboard;
import com.qa.pages.IDP_LoginPage;
import com.qa.pages.IDP_Tenant;
import com.qa.pages.IDP_Users;

public class Tenant_Tests extends TestBase{
	
	IDP_LoginPage lp;
	IDP_Dashboard dashboard;
	IDP_Applications applications;
	IDP_Users users;
	IDP_Tenant tenant;
	
	
	
	@Test(dataProvider="dataProvider", priority=1)
	public void TC_374980(Hashtable<String,String> data) {
		
			initial_test_tasks(data);
			navigateTo(data.get("url"));
			lp=new IDP_LoginPage();
			dashboard=lp.login(data.get("username"), data.get("password"),data.get("account_type"));
				
		
	}
	
	
	
	@Test(dataProvider="dataProvider", priority=2)
	public void createTenant(Hashtable<String,String>data) {
		for(int i=0;i<60;i++) {
		initial_test_tasks(data);
		tenant=(IDP_Tenant)navigateToPage("tenant");
		tenant.createTenant(data);
		}
	}

}
