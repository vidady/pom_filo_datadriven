package com.qa.pages;

import org.openqa.selenium.By;
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
	
	@FindBy(xpath="//div[contains(text(),'oneLogin')]")
	WebElement oneLoinOption;
	
	@FindBy(xpath="//div[contains(text(),'Soti DB')]")
	WebElement sotiDbOption;
	
	
	
	
	
	
	public IDP_Dashboard login(String username,String password,String account_type) {
		btn_Login.click();
		uname.sendKeys(username);
		btn_usernameNext.click();
		/*
		 * if(driver.getCurrentUrl().contains("AccountSelector")) {
		 * if(account_type.equalsIgnoreCase("Soti DB")) sotiDbOption.click(); else
		 * if(account_type.equalsIgnoreCase("oneLogin")) oneLoinOption.click();
		 * 
		 * }
		 */
		//div[(contains(text(),'Identity Administrator'))]
		
		
		
		
		
	    pswd.sendKeys(password);
	    if(driver.getCurrentUrl().contains("CheckForUserType")) {
			driver.findElement(By.linkText("Identity Administrator")).click();
			
			
		}
	    btn_login.click();
	    return new IDP_Dashboard();
	    
		
	}
	

}
