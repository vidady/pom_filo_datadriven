package com.qa.pages;

import java.util.Hashtable;

import org.apache.commons.lang3.RandomStringUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import com.qa.base.TestBase;

public class IDP_Tenant extends TestBase {
	
	public IDP_Tenant() {
		super();
		PageFactory.initElements(driver, this);
	}
	
	
	@FindBy(xpath="//span[contains(text(),'New Tenant')]")
	WebElement createTenantLink;
	
	@FindBy(xpath="//input[@name='companyName']")
	WebElement tenantName;
	
	@FindBy(xpath="//span/div[contains(text(),'Choose Country')]")
	WebElement country;
	
	@FindBy(xpath="//span/div[contains(text(),'Choose Industry')]")
	WebElement industry;
	
	@FindBy(xpath="//input[@name='firstName']")
	WebElement firstName;
	
	@FindBy(xpath="//input[@name='lastName']")
	WebElement lastName;
	
	@FindBy(xpath="//input[@name='email']")
	WebElement email;
	
	@FindBy(xpath="//input[@name='phoneNumber']")
	WebElement phoneNumber;
	
	@FindBy(xpath="//button[contains(text(),'Create')]")
	WebElement createBtn;
	
	@FindBy(xpath="//button[contains(text(),'Cancel')]")
	WebElement cancelBtn;
	
	
	public void createTenant(Hashtable<String,String>data) {
		String name=RandomStringUtils.randomAlphabetic(4);
		isElementPresent(createTenantLink);
		createTenantLink.click();
		isElementPresent(tenantName);
		tenantName.sendKeys(name);
		isElementPresent(country);
		country.click();
		String str1="//div[(contains(text(),'";
		String str2="'))]";
		WebElement ele=driver.findElement(By.xpath(str1+data.get("country")+str2));
		ele.click();
		isElementPresent(industry);
		industry.click();
		WebElement ele2=driver.findElement(By.xpath(str1+data.get("industry_type")+str2));
		ele2.click();
		isElementPresent(firstName);
		firstName.sendKeys(name);
		isElementPresent(lastName);
		lastName.sendKeys(name);
		isElementPresent(email);
		email.sendKeys(name+"@"+name+".com");
		isElementPresent(phoneNumber);
		phoneNumber.sendKeys("123456789");
		isElementPresent(createBtn);
		createBtn.click();
		driver.navigate().refresh();
		
	}
	
	
	
	
	


}
