package com.qa.pages;

import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import com.qa.base.TestBase;

public class IDP_Applications extends TestBase{

	public IDP_Applications() {
		super();
		PageFactory.initElements(driver, this);
	}

	@FindBy(xpath="//*[@class='hamburger-inner']")
	WebElement hamburgerIcon;

	@FindBy(xpath="//a[contains(text(),'Applications')]")
	WebElement hamburgerApplicationsLink;


	@FindBy(xpath="//*[contains(text(),'ADD APPLICATION')]")
	WebElement addApplicationLink;


	
	@FindBy(xpath="//i[@class='icon-delete round large']")
	WebElement deleteAction;
	
	@FindBy(xpath="//div[@class='checkbox-icon']")
	WebElement deleteAppCheckBox;
	
	@FindBy(xpath="//button[contains(text(),'Delete')]")
	WebElement deleteApplicationBtn;





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
			if(list.contains(IDP_Dashboard.name)) softAssert.assertTrue(true); else softAssert.fail();
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



	
	
	public void deleteApplication(String application) {
		int count=driver.findElements(By.xpath("//soti-row[@class='soti-row ng-star-inserted']/soti-cell[3]")).size();
		if(application==null) {
			application=IDP_Dashboard.name;
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




}
