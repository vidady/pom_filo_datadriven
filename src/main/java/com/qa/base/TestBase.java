package com.qa.base;

import java.awt.AWTException;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.awt.event.KeyEvent;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.lang.reflect.Method;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Hashtable;
import java.util.List;
import java.util.Locale;
import java.util.Properties;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.PageLoadStrategy;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeDriverService;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.firefox.FirefoxProfile;
import org.openqa.selenium.firefox.internal.ProfilesIni;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.safari.SafariDriver;
import org.openqa.selenium.support.events.EventFiringWebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.ITestResult;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.asserts.SoftAssert;

import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.codoid.products.exception.FilloException;
import com.google.gson.JsonIOException;
import com.google.gson.JsonSyntaxException;
import com.qa.util.TestUtil;
import com.qa.util.WebEventListener;
import com.qa.util.jsonReader;
import com.qa.util.readAndWriteData;

public class TestBase {
	public static WebDriver driver;
	public static Properties prop;
	public static Properties config;
	public static EventFiringWebDriver edriver;
	public static WebEventListener eventlistener;
	public readAndWriteData readNwrite=new readAndWriteData();
	public static String TEST;
	public ITestResult result;
	public static Logger logger;
	public static String className;
	public static SoftAssert softAssert;
	public static ThreadLocal<ExtentTest> test = new ThreadLocal<>();
	public static DesiredCapabilities cap;
	public static URL url;
	public TestBase() {

		try {
			if(config==null) {
				config=new Properties();
				FileInputStream fs=new FileInputStream(System.getProperty("user.dir")+"/src/main/java/com/qa/config/config.properties");
				config.load(fs);

				prop=new Properties();
				FileInputStream ip=new FileInputStream(System.getProperty("user.dir")+"/src/main/java/com/qa/config/"+config.getProperty("Environment")+"Env.properties");
				prop.load(ip);
				System.out.println(prop.getProperty("env"));


			}}catch(FileNotFoundException e) {
				e.printStackTrace();
			}catch(IOException e) {
				e.printStackTrace();
			}

		softAssert=new SoftAssert();
		test=WebEventListener.test();

	}

	//********************************Browser invoke function*****************************************//

	public static void initialization() {
		
		String OS = System.getProperty("os.name").toString().toLowerCase();
		if(OS.contains("mac"))
			OS="MAC";
		else if(OS.contains("win"))
			OS="WINDOWS";
		System.out.println(OS);

		switch(OS) {
		case "WINDOWS":
			if(driver==null) {
				String browserName=prop.getProperty("browser");
				if(browserName.equalsIgnoreCase("chrome")) {
					System.setProperty("webdriver.chrome.driver",TestUtil.CHROMEDRIVER_EXE);
					driver=new ChromeDriver();
				}else if(browserName.equalsIgnoreCase("firefox")){
					System.setProperty("webdriver.gecko.driver",TestUtil.FIREFOXDRIVER_EXE);
					driver=new FirefoxDriver();
				}

				edriver=new EventFiringWebDriver(driver);
				eventlistener=new WebEventListener();
				edriver.register(eventlistener);
				driver=edriver;
			}
		case "MAC":
			if(driver==null) {
				String browserName=prop.getProperty("browser");
				if(browserName.equalsIgnoreCase("chrome")) {
					System.setProperty("webdriver.chrome.driver",TestUtil.CHROMEDRIVER_MAC);
					ChromeOptions options=new ChromeOptions();
					options.addArguments("--disable-notifications");
					options.addArguments("disable-infobars");
					options.addArguments("--start-maximized");
					//options.addArguments("--headless");
					options.addArguments("--proxy-server:http://100.0.0.1");
					//options.addArguments("user-data-dir:directory path of till user data folder");
					//options.setPageLoadStrategy(PageLoadStrategy.EAGER);
					System.setProperty(ChromeDriverService.CHROME_DRIVER_LOG_PROPERTY, "browserLogs");
					System.setProperty(ChromeDriverService.CHROME_DRIVER_SILENT_OUTPUT_PROPERTY, "true");

					driver=new ChromeDriver(options);
				}else if(browserName.equalsIgnoreCase("firefox")){
					System.setProperty("webdriver.gecko.driver",TestUtil.FIREFOXDRIVER_MAC);

					//FireFox profile options setting:
					System.setProperty(FirefoxDriver.SystemProperty.BROWSER_LOGFILE, "browserLogs");
					FirefoxOptions options=new FirefoxOptions();
					options.setPageLoadStrategy(PageLoadStrategy.NONE);
					ProfilesIni prof=new ProfilesIni();
					FirefoxProfile profile=prof.getProfile("default");
					profile.setPreference("dom.webnotifictions.enabled", false);

					//Untrusted certificate acceptance:
					profile.setAcceptUntrustedCertificates(true);
					profile.setAssumeUntrustedCertificateIssuer(false);

					options.setProfile(profile);
					driver=new FirefoxDriver(options);
				}else if(browserName.equalsIgnoreCase("safari")) {
					System.setProperty("webdriver.safari.noinstall", "true");
					driver = new SafariDriver();
				}else if(browserName.equalsIgnoreCase("remoteChrome")) {
					cap=DesiredCapabilities.chrome();
					try {
						url=new URL("http://localhost:4444/wd/hub");
					} catch (MalformedURLException e) {
						e.printStackTrace();
					}
					driver=new RemoteWebDriver(url,cap);
				}

				edriver=new EventFiringWebDriver(driver);
				eventlistener=new WebEventListener();
				edriver.register(eventlistener);
				driver=edriver;
			}
		}

		
		//driver.manage().window().fullscreen();
		driver.manage().window().setSize(new Dimension(TestUtil.X_COORDINATE,TestUtil.Y_COORDINATE));
		driver.manage().deleteAllCookies();
		driver.navigate().refresh();
		driver.manage().timeouts().pageLoadTimeout(TestUtil.PAGE_LOAD_TIMEOUT, TimeUnit.SECONDS);
		driver.manage().timeouts().implicitlyWait(TestUtil.IMPLICIT_WAIT, TimeUnit.SECONDS);

	}


	//********************************Before-After Methods and Initial test Methods ************************//

	public void initial_test_tasks(Hashtable<String,String> data) {
		readNwrite.runmodeCheck(data);

	}



	@BeforeMethod
	public void setUp(Method m) {
		TEST=m.getName().toString();
		className = m.getDeclaringClass().getSimpleName().toString();
		logger=Logger.getLogger(className+"----"+TEST);
		PropertyConfigurator.configure(TestUtil.LOG4J_PROPERTYFILE);

	}

	@AfterClass
	public void tearDown() {
		if(driver!=null)
			driver.quit();
		driver=null;
		logger.info("ENDING TESTS FOR CLASS --- "+className);
	}

	@BeforeClass
	public void startUp() {
		className = this.getClass().getSimpleName().toString();
		logger=Logger.getLogger(className);
		PropertyConfigurator.configure(TestUtil.LOG4J_PROPERTYFILE);
	    logger.info("STARTING TESTS FOR CLASS --- "+className);
		initialization();
	}
	
	
	//********************************* Generic Functions **********************************************//


	public String getElementAttribute(WebElement element,String attribute) {
		return element.getAttribute(attribute);
	}


	public void clickLink(String linkText) {
		List<WebElement> list=new ArrayList<WebElement>();
		list=driver.findElements(By.tagName("a"));
		for(WebElement e:list) {
			System.out.println(e.getText());
			if(e.getText().toString().equals(linkText)) {
				e.click();
				break;
			}
		}
	}

	public boolean isElementPresent(String locator, String locatorType) {
		List<WebElement> allElements=null;
		if(locatorType.equalsIgnoreCase("xpath"))
			allElements=driver.findElements(By.xpath(locator));
		else if(locatorType.equalsIgnoreCase("cssSelector"))
			allElements=driver.findElements(By.cssSelector(locator));
		else if(locatorType.equalsIgnoreCase("id"))
			allElements=driver.findElements(By.id(locator));
		if(allElements.size()==0)
			return false;
		else
			return true;

	}

	public void selectDropdownElement(WebElement element,String elementName) {
		Select s=new Select(element);
		s.selectByVisibleText(elementName);
	}

	public void navigateTo(String url) {
		driver.navigate().to(url);
	}
	
	public Set<String> getWindowsSet() {
		Set<String> winIds=driver.getWindowHandles();
		return winIds;
	}
	
	public void switchToWindowById(String id) {
		Set<String> winSet=getWindowsSet();
		for (String win:winSet) {
			if(win.equals(id)) {
				driver.switchTo().window(id);
			}
		}
	}
	
	public void switchToDefaultWindow() {
		driver.switchTo().defaultContent();
	}
	
	public void acceptBrowserPopUp() {
		Alert alert=driver.switchTo().alert();
		alert.accept();
		driver.switchTo().defaultContent();
	}
	
	public void dismissBrowserPopUp() {
		Alert alert=driver.switchTo().alert();
		alert.dismiss();
		driver.switchTo().defaultContent();
	}
	
	public String getTextBrowserPopUp() {
		Alert alert=driver.switchTo().alert();
		String text=alert.getText().toString();
		driver.switchTo().defaultContent();
		return text;
	}
	
	public void elementWait(WebElement element,int seconds) {
		WebDriverWait wait=new WebDriverWait(driver,seconds);
		wait.until(ExpectedConditions.visibilityOf(element));
	}
	
	public void clickTo(WebElement element) {
		element.click();
	}
	
	public void getAttributeValue(WebElement element,String attribute) {
		element.getAttribute(attribute);
	}
	
	public void clearCookies() {
		driver.manage().deleteAllCookies();
	}
	
	public void navigateBack() {
		driver.navigate().back();
	}
	
	public void navigateForward() {
		driver.navigate().forward();
	}

    public void moveToElement(WebElement element) {
    Actions act=new Actions(driver);
    act.moveToElement(element).build().perform();
    elementWait(element,10);
    }

	//******************************** Validity Functions ***************************************//

	public void verifyElementText(WebElement element,String expectedText) {
		softAssert.assertEquals(element.getText().toString(), expectedText);
		softAssert.assertAll();
	}
	
	//******************************** Reporting Functions ***************************************//
	
	public void reportPass(String message) {
		test.get().log(Status.PASS, message);
	}
	
	public void reportFail(String message) {
		test.get().log(Status.FAIL, message);
	}
	
	public void reportInfo(String message) {
		test.get().log(Status.INFO, message);
	}
	
	public void reportSkip(String message) {
		test.get().log(Status.SKIP, message);
	}


	//********************************Data Provider*****************************************//


	@DataProvider
	public Object[][] dataProvider(Method m) throws FilloException, JsonIOException, JsonSyntaxException, FileNotFoundException{
		Object[][] dataSet=null;
		if(config.getProperty("dataReadConfiguration").equalsIgnoreCase("excel")) {
			System.out.println("data provider is excel");
			dataSet= readAndWriteData.dataSet(m);
		}else if(config.getProperty("dataReadConfiguration").equalsIgnoreCase("json")) {
			System.out.println("data provider is json");
		dataSet= jsonReader.jsonTestDataSet(m);
		}

		return dataSet;

	}
	
	   //File upload by Robot Class
    public void uploadFileWithRobot (String imagePath) {
        StringSelection stringSelection = new StringSelection(imagePath);
        Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
        clipboard.setContents(stringSelection, null);
 
        Robot robot = null;
 
        try {
            robot = new Robot();
        } catch (AWTException e) {
            e.printStackTrace();
        }
 
        robot.delay(250);
        robot.keyPress(KeyEvent.VK_ENTER);
        robot.keyRelease(KeyEvent.VK_ENTER);
        robot.keyPress(KeyEvent.VK_CONTROL);
        robot.keyPress(KeyEvent.VK_V);
        robot.keyRelease(KeyEvent.VK_V);
        robot.keyRelease(KeyEvent.VK_CONTROL);
        robot.keyPress(KeyEvent.VK_ENTER);
        robot.delay(150);
        robot.keyRelease(KeyEvent.VK_ENTER);
    }
    
    //To change the date field to add in some days
    public void dateChange(String MMM_D_YYYY,int number_of_days_to_add) throws Exception {
		String sourceDate = MMM_D_YYYY;
		SimpleDateFormat format = new SimpleDateFormat("MMM d, yyyy", Locale.ENGLISH);
		Date myDate = format.parse(sourceDate);
		myDate = addDays(myDate, number_of_days_to_add);
		System.out.println(format.format(myDate));
	}
	 public Date addDays(Date date, int days)
	    {
	        Calendar cal = Calendar.getInstance();
	        cal.setTime(date);
	        cal.add(Calendar.DATE, days); //minus number would decrement the days
	        return cal.getTime();
	    }



}
