package com.qa.creative_tests;



import java.io.IOException;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.testng.annotations.Test;

import com.galenframework.api.Galen;
import com.qa.base.GalenBase;

public class Login extends GalenBase {

   @Test(dataProvider = "devices")
    public void welcomePage_shouldLookGood(TestDevice device) throws IOException {
        load("/");

        getDriver().findElement(By.xpath("//button[.='Login']")).click();
        getDriver().manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);

        StackTraceElement[] stacktrace = Thread.currentThread().getStackTrace();
        StackTraceElement e = stacktrace[1];//coz 0th will be getStackTrace so 1st
        String methodName = e.getMethodName();
        System.out.println(methodName);
        reporting(Galen.checkLayout(getDriver(), System.getProperty("user.dir")+"/src/main/resources/specs/loginPage.gspec",device.getTags()),methodName,device.toString());

        }

}

