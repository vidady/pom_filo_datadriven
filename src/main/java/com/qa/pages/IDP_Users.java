package com.qa.pages;

import org.apache.commons.lang3.RandomStringUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import com.qa.base.TestBase;

public class IDP_Users extends TestBase {
	
	public IDP_Users() {
		super();
		PageFactory.initElements(driver, this);
	}
	
	@FindBy(xpath="//span[contains(text(),'New User')]")
	WebElement newUserLink;
	
	@FindBy(xpath="//input[@name='email']")
	WebElement newUserEmail;
	
	@FindBy(xpath="//input[@name='lastName']")
	WebElement newUserLasttName;
	
	@FindBy(xpath="//input[@name='phoneNumber']")
	WebElement newUserPhoneNumber;
	
	@FindBy(xpath="//div[@title='Choose groups']")
	WebElement newUserIdentityGroup;
	
	@FindBy(xpath="//span[contains(text(),'Choose Role')]")
	WebElement newUserIdentityRole;
	
	@FindBy(xpath="//textarea[@name='comment']")
	WebElement newUserComment;
	
	@FindBy(xpath="//input[@name='firstName']")
	WebElement newUserFirstName;
	
	
	@FindBy(xpath="//button[text()='Cancel']")
	WebElement newUserCancelBtn;
	
	@FindBy(xpath="//button[@type='submit']")
	WebElement newUserSubmitBtn;
	
	@FindBy(xpath="//span[contains(text(),'My Company')]")
	WebElement myCompanyGroup;
	
	@FindBy(xpath="//button[contains(text(),'Done')]")
	WebElement DoneBtn;
	
	@FindBy(xpath="//span[contains(text(),'Account Administrator')]")
	WebElement roleAdmin;
	
	@FindBy(xpath="//span[contains(text(),'Application User')]")
	WebElement roleApplicationUser;
	
	
	

	
	
	  String name;
	  public void addUserOrGroup(String user_type,String first_name,String
	  last_name,String email,String phone_number,String group,String role) {
		  name=RandomStringUtils.randomAlphabetic(4);
		  newUserLink.click();
		  driver.findElement(By.xpath("//li/span[contains(text(),'"+user_type+"')]")).click();
	
	  
	  switch(user_type.toLowerCase()){
	  case "user": 
	  //isElementPresent(newUserFirstName);
	  newUserFirstName.sendKeys(name); 
	 // isElementPresent(newUserLasttName);
	  newUserLasttName.sendKeys(name);
	 // isElementPresent(newUserEmail);
	  newUserEmail.sendKeys(name+"@"+name+".com");
	 // isElementPresent(newUserIdentityGroup);
	  newUserIdentityGroup.click();
	  WebElement identityGrp=driver.findElement(By.xpath("//span[contains(text(),'"+group+"')]"));
	 // isElementPresent(identityGrp);
	  identityGrp.click();
	  DoneBtn.click();
	 // isElementPresent(newUserIdentityGroup);
	  newUserIdentityRole.click();
	  WebElement identityRole=driver.findElement(By.xpath("//span[contains(text(),'"+role+"')]"));
	  //isElementPresent(identityRole);
	  identityRole.click();
	  DoneBtn.click();
	  newUserSubmitBtn.click();
	  driver.navigate().refresh();
	  break;
	  
	  }
	  
	  
	  }
	 
	
	


}
