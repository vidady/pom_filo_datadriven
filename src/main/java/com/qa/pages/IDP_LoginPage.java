package com.qa.pages;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import com.qa.base.TestBase;

public class IDP_LoginPage extends TestBase {
	
	public IDP_LoginPage() {
		super();
		PageFactory.initElements(driver, this);
	}
	
	@FindBy(xpath="//*[@id=\"mainContent\"]/app-login/div/div/div/div/section/div/div[1]/div[2]/div/div[2]/button")
	WebElement btn_Login;
	
	@FindBy(xpath="//*[@id=\"Email\"]")
	WebElement uname;
	
	@FindBy(xpath="//*[@id=\"btnClickEmail\"]")
	WebElement btn_usernameNext;
	
	@FindBy(xpath="//*[@id=\"form\"]/button/div/div[2]/div")
	WebElement loginAccountSelection;
	
	@FindBy(xpath="//*[@id=\"btnBack\"]")
	WebElement btn_loginBack;
	
	@FindBy(xpath="//input[@id='Password']")
	WebElement pswd;
	
	@FindBy(xpath="//*[@id=\"submitPassword\"]")
	WebElement btn_login;
	
	
	
	
	
	
	
	
	
	public IDP_Dashboard login(String username,String password) {
		btn_Login.click();
		uname.sendKeys(username);
		btn_usernameNext.click();
	    pswd.sendKeys(password);
	    btn_login.click();
	    return new IDP_Dashboard();
	    
		
	}
	

}
