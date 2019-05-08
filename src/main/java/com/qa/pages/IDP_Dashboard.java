package com.qa.pages;

import java.util.Hashtable;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import com.qa.base.TestBase;

public class IDP_Dashboard extends TestBase{
	
	public IDP_Dashboard() {
		super();
		PageFactory.initElements(driver, this);
	}
	
	@FindBy(xpath="//*[@class='hamburger-inner']")
	WebElement hamburgerIcon;
	
	@FindBy(xpath="//a[contains(text(),'Applications')]")
	WebElement hamburgerApplicationsLink;
	
	
	@FindBy(xpath="//*[contains(text(),'ADD APPLICATION')]")
	WebElement addApplicationLink;
	
	@FindBy(xpath="//*[contains(text(),'MobiControl')]")
	WebElement addMobiControlLink;
	
	@FindBy(xpath="//input[@name='instanceName']")
	WebElement mc_instanceName;
	
	@FindBy(xpath="//input[@class='file-input']")
	WebElement mc_appLogo;
	
	@FindBy(xpath="//button[@class='btn btn-primary']")
	WebElement mc_addAndGenerateCredentials;
	
	@FindBy(xpath="//button[contains(text(),'Cancel')]")
	WebElement mc_cancelBtn;
	
	@FindBy(xpath="//div[contains(text(),' MobiControl Instance Name is required')]")
	WebElement mc_instanceNameValidationMsg;
	
	@FindBy(xpath="//div[@class='errorMessage ng-star-inserted']")
	WebElement mc_instanceNameMaxLengthValidation;
	
	
	
	
	
	
	
	
	
	public void addApplication(String app_type) {
		

		hamburgerIcon.click();
		hamburgerApplicationsLink.click();
		addApplicationLink.click();
		driver.findElement(By.xpath("//span[contains(text(),'"+app_type+"')]")).click();
		
		
		
		switch(app_type) {
		case "MC":
			
		break;
			
		case "Application":
			break;
				
		case "add_connect":
			break;
			
		case "SOTI Insight":
			break;
			
		case "Resource Share":
			break;
			
		case "SOTI Snap":
			
			
			break;
			
			default:
				System.out.println("aap type not provided");
		}
	}



	public void verifyApplicationFieldValidation(Hashtable<String,String> data) {
		mc_instanceName.sendKeys("testing");
		mc_instanceName.sendKeys(Keys.CONTROL,"a");
		mc_instanceName.sendKeys(Keys.DELETE);
		mc_instanceName.sendKeys(Keys.TAB);
		String msg=mc_instanceNameValidationMsg.getText().toString().trim();
		if(data.get("validation_message").trim().equals(msg))
			reportPass("validation message passed");
		else
			reportFail("validation message failed");
		
	
		mc_instanceName.sendKeys(data.get("instance_name"));
		String msg2=mc_instanceNameMaxLengthValidation.getText().toString().trim();
		if(data.get("validation_message2").trim().equals(msg2))
			reportPass("validation message passed");
		else
			reportFail("validation message failed");
		
	}
	
	
	

}
