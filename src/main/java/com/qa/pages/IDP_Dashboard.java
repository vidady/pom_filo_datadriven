package com.qa.pages;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;

import org.apache.commons.lang3.RandomStringUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import com.qa.base.TestBase;

public class IDP_Dashboard extends TestBase{
	public static String name;
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

	@FindBy(xpath="//button[contains(text(),'Done')]")
	WebElement mc_doneBtn;

	@FindBy(xpath="//div[@class='copy-credential-btn']")
	WebElement mc_copyAppCredentials;

	@FindBy(xpath="//ul/li[1]/div[@class='list-title']")
	WebElement clientId;

	@FindBy(xpath="//ul/li[1]/div[@class='list-description']")
	WebElement clientIdValue;

	@FindBy(xpath="//ul/li[3]/div[@class='list-title']")
	WebElement clientSecretId;

	@FindBy(xpath="//ul/li[3]/div[@class='list-description']")
	WebElement clientSecretIdValue;

	@FindBy(xpath="//i[@class='icon-delete round large']")
	WebElement deleteAction;

	@FindBy(xpath="//div[@class='checkbox-icon']")
	WebElement deleteAppCheckBox;

	@FindBy(xpath="//button[contains(text(),'Delete')]")
	WebElement deleteApplicationBtn;





	public IDP_Applications addApplication(String app_type) {


		hamburgerIcon.click();
		hamburgerApplicationsLink.click();
		addApplicationLink.click();
		driver.findElement(By.xpath("//span[contains(text(),'"+app_type+"')]")).click();
		name=RandomStringUtils.randomAlphabetic(4);


		switch(app_type) {
		case "MobiControl":
			mc_instanceName.sendKeys(name);
			mc_instanceName.sendKeys(Keys.TAB);
			mc_addAndGenerateCredentials.click();

			String mc_provided_name=getElementAttribute(mc_instanceName, "placeholder");
			softAssert.assertEquals(name, mc_provided_name);
			softAssert.assertTrue(mc_copyAppCredentials.isDisplayed());
			softAssert.assertTrue(clientId.isDisplayed());
			softAssert.assertTrue(clientIdValue.isDisplayed());
			softAssert.assertTrue(clientSecretId.isDisplayed());
			softAssert.assertTrue(clientSecretIdValue.isDisplayed());
			softAssert.assertAll();
			mc_doneBtn.click();







			softAssert.assertAll();
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

		return new IDP_Applications();
	}





	public void verifyAddedApplicationOnListingPage(String verify_data) {
		driver.navigate().refresh();
		switch(verify_data) {
		case "name":

			List<WebElement> e=driver.findElements(By.xpath("//soti-cell[@class='soti-cell cdk-column-Name soti-column-Name ng-star-inserted']"));
			List<String> list=new ArrayList<String>();
			for(WebElement ele:e) {
				list.add(ele.getText().toString());
			}
			System.out.println(list);
			if(list.contains(name)) softAssert.assertTrue(true); else softAssert.fail();
			break;


		case "state":
			List<WebElement> e2=driver.findElements(By.xpath("//soti-cell[@class='soti-cell cdk-column-Status soti-column-Status ng-star-inserted']"));
			List<String> list2=new ArrayList<String>();
			for(WebElement ele:e2) {
				list2.add(ele.getText().toString());
			}
			System.out.println(list2);
			if(list2.contains("Draft")) softAssert.assertTrue(true);
			else softAssert.fail();
			break;

		default:
			reportFail("Unable to find data type to verify");
		}


	}



	public void verifyApplicationFieldValidation(Hashtable<String,String> data) {

		hamburgerIcon.click();
		hamburgerApplicationsLink.click();
		addApplicationLink.click();
		driver.findElement(By.xpath("//span[contains(text(),'"+data.get("app_type")+"')]")).click();
		switch(data.get("app_type")) {

		case "MobiControl":
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

			break;

		default:
			System.out.println("aap type not provided");
			break;

		}

	}

	public void deleteApplication(String application) {
		int count=driver.findElements(By.xpath("//soti-row[@class='soti-row ng-star-inserted']/soti-cell[3]")).size();
		if(application==null) {
			application=name;
		}
		for(int i=0;i<count;i++) {
			if(driver.findElements(By.xpath("//soti-row[@class='soti-row ng-star-inserted']/soti-cell[3]")).get(i).getText().toString().trim().equals(application)) {
				driver.findElements(By.xpath("//soti-row[@class='soti-row ng-star-inserted']/soti-cell[1]")).get(i).click();
				elementWait(deleteAction,4);
				deleteAction.click();
				deleteAppCheckBox.click();
				deleteApplicationBtn.click();
				driver.navigate().refresh();
			}
		}

		if(!driver.getPageSource().contains(application)) {
			reportPass("Application deleted successfully");
		}else
		{
			reportFail("Application name still exists in source code");
		}
	}
	
	
	public void navigateToUserListingPage() {
		
	}




}
