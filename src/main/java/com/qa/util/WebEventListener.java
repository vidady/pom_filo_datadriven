package com.qa.util;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.events.WebDriverEventListener;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.qa.ExtentReportListener.ExtentManager;
import com.qa.base.TestBase;

public class WebEventListener extends TestBase implements WebDriverEventListener,ITestListener{

	private static ExtentReports extent = ExtentManager.createInstance();
	private static ThreadLocal<ExtentTest> test = new ThreadLocal<>();
	public static ThreadLocal<ExtentTest> classLevelReport = new ThreadLocal<ExtentTest>();
	JavascriptExecutor js=(JavascriptExecutor)driver; 
	public static ThreadLocal<ExtentTest> test() {
		return test;
	}

	public void beforeNavigateTo(String url, WebDriver driver) {
		logger.info("Before navigating to: '" + url + "'");
		test.get().log(Status.INFO, "Before navigating to: '" + url + "'");
	}

	public void afterNavigateTo(String url, WebDriver driver) {
		logger.info("Navigated to:'" + url + "'");
		test.get().log(Status.INFO, "Navigated to:'" + url + "'");

	}

	public void beforeChangeValueOf(WebElement element, WebDriver driver) {
		logger.info("Value of the:" + element.toString() + " before any changes made");
		test.get().log(Status.INFO, "Value of the:" + element.toString() + " before any changes made");
		
	}

	public void afterChangeValueOf(WebElement element, WebDriver driver) {
		logger.info("Element value changed to: " + element.toString());
		test.get().log(Status.INFO, "Element value changed to: " + element.toString());

	}

	public void beforeClickOn(WebElement element, WebDriver driver) {
		logger.info("Trying to click on: " + element.toString());
		test.get().log(Status.INFO, "Trying to click on: " + element.toString());
		js.executeScript("arguments[0].setAttribute('style', 'background: yellow; border: 2px solid red;');", element);


	}

	public void afterClickOn(WebElement element, WebDriver driver) {
		logger.info("Clicked on: " + element.toString());
		test.get().log(Status.INFO, "Clicked on: " + element.toString());
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public void beforeNavigateBack(WebDriver driver) {
		logger.info("Before navigating back to previous page");
		test.get().log(Status.INFO, "Before navigating back to previous page");

	}

	public void afterNavigateBack(WebDriver driver) {
		logger.info("After navigating back to previous page");
		test.get().log(Status.INFO, "After navigating back to previous page");
	}

	public void beforeNavigateForward(WebDriver driver) {
		logger.info("Navigating forward to next page");
		test.get().log(Status.INFO, "Navigating forward to next page");

	}

	public void afterNavigateForward(WebDriver driver) {
		logger.info("Navigated forward to next page");
		test.get().log(Status.INFO, "Navigated forward to next page");

	}

	public void onException(Throwable error, WebDriver driver) {
		TestUtil.takeScreenshot();
		logger.info("Exception occured: " + error);
		test.get().log(Status.ERROR, "Exception occured: " + error);


	}

	public void beforeFindBy(By by, WebElement element, WebDriver driver) {
		logger.info("Trying to find Element By : " + by.toString());
		test.get().log(Status.INFO, "Trying to find Element By : " + by.toString());
	}

	public void afterFindBy(By by, WebElement element, WebDriver driver) {
		logger.info("Found Element By : " + by.toString());
		test.get().log(Status.INFO, "Found Element By : " + element);


	}

	/*
	 * non overridden methods of WebListener class
	 */
	public void beforeScript(String script, WebDriver driver) {
	}

	public void afterScript(String script, WebDriver driver) {
	}

	public void beforeAlertAccept(WebDriver driver) {
		// TODO Auto-generated method stub

	}

	public void afterAlertAccept(WebDriver driver) {
		// TODO Auto-generated method stub

	}

	public void afterAlertDismiss(WebDriver driver) {
		// TODO Auto-generated method stub

	}

	public void beforeAlertDismiss(WebDriver driver) {
		// TODO Auto-generated method stub

	}

	public void beforeNavigateRefresh(WebDriver driver) {
		// TODO Auto-generated method stub

	}

	public void afterNavigateRefresh(WebDriver driver) {
		// TODO Auto-generated method stub

	}

	public void beforeChangeValueOf(WebElement element, WebDriver driver, CharSequence[] keysToSend) {
		// TODO Auto-generated method stub

	}

	public void afterChangeValueOf(WebElement element, WebDriver driver, CharSequence[] keysToSend) {
		// TODO Auto-generated method stub

	}

	public void beforeSwitchToWindow(String windowName, WebDriver driver) {
		// TODO Auto-generated method stub

	}

	public void afterSwitchToWindow(String windowName, WebDriver driver) {
		// TODO Auto-generated method stub

	}

	public <X> void beforeGetScreenshotAs(OutputType<X> target) {
		// TODO Auto-generated method stub

	}

	public <X> void afterGetScreenshotAs(OutputType<X> target, X screenshot) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onTestStart(ITestResult result) {
		System.out.println((result.getMethod().getMethodName() + " started!"));
		ExtentTest extentTest = classLevelReport.get().createNode(result.getMethod().getMethodName()); 
		extentTest.assignCategory("Test_Case_Level_Run_Result");
		logger.info("STARTING TEST :"+result.getMethod().getMethodName());
		test.set(extentTest);
	}

	@Override
	public void onTestSuccess(ITestResult result) {
		logger.info(result.getMethod().getMethodName()+" --- Test Passed");
		test.get().pass("Test passed");

	}

	@Override
	public void onTestFailure(ITestResult result) {
		logger.info(result.getMethod().getMethodName()+" --- Test Failed");
		//test.get().fail(result.getThrowable());
		TestUtil.takeScreenshot();
		String exceptionMessage = result.getThrowable().getClass().toString();
		test.get()
		.fail("<details>" + "<summary>" + "<b>" + "<font color=" + "red>"
				+ "Exception Occured: Click to see. </summary>" + "</font>" + "</b >"
				+ exceptionMessage.replaceAll(",", "<br>") + "<br><a href =Screenshots/failed_screen.png"
				+ " target=\"_blank\"><img src =\"Screenshots/failed_screen.png"
				+ "\" height=\"42\" width \"42\"/></a>" + "</details>");
		test.get().log(Status.INFO, result.getMethod().getMethodName() + " Execution Ended");
		test.get().log(Status.FAIL, "FAILED");
	}

	@Override
	public void onTestSkipped(ITestResult result) {
		logger.info(result.getMethod().getMethodName()+" --- Test Skipped");
		//test.get().skip(result.getThrowable());
		String exceptionMessage = result.getThrowable().getClass().toString();
		test.get()
		.skip("<details>" + "<summary>" + "<b>" + "<font color=" + "yellow>"
				+ "Exception Occured: Click to see. </summary>" + "</font>" + "</b >"
				+ exceptionMessage.replaceAll(",", "<br>"));
		test.get().log(Status.INFO, result.getMethod().getMethodName() + " Execution Skipped");
		test.get().log(Status.SKIP, "SKIPPED");
	}

	@Override
	public void onTestFailedButWithinSuccessPercentage(ITestResult result) {
		logger.info("onTestFailedButWithinSuccessPercentage for " + result.getMethod().getMethodName());
	}

	@Override
	public void onStart(ITestContext context) {
		ExtentTest parent = extent.createTest(context.getName().toString());
		parent.assignCategory("Epic_Level_Run_Result");
		classLevelReport.set(parent);
	   

	}

	@Override
	public void onFinish(ITestContext context) {
		extent.flush();

	}

	@Override
	public void beforeGetText(WebElement element, WebDriver driver) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void afterGetText(WebElement element, WebDriver driver, String text) {
		// TODO Auto-generated method stub
		
	}

}
