package com.weatherapiui;

import org.testng.annotations.Test;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;

import java.util.ArrayList;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.Assert;
import org.testng.annotations.AfterClass;

public class compareApiUiData {
	String city="Mumbai";
	WebDriver driver;
	ArrayList<WebElement> citiesSearchResult;
  @BeforeClass
  public void beforeClass() {
	  
	  System.setProperty("webdriver.gecko.driver", "Utilties\\geckodriver.exe");
	  driver=new FirefoxDriver();
  } 

  @Test
  public void test1SearchCities() {
	  driver.get("https://social.ndtv.com/static/Weather/report/");
	  driver.manage().window().maximize();
	  driver.findElement(By.xpath("//input[@id='searchBox' and @class='searchBox' and @type='text']"))
	  .sendKeys(city);
	  citiesSearchResult=(ArrayList)driver.findElements(By.cssSelector("div[class='message']:not([style='display: none;'])"));
	  Assert.assertTrue(citiesSearchResult.size()>0," Atlease one city with name should exist");
	  
  }
  @Test(dependsOnMethods = "test1SearchCities")
  public void test2PinCity() {
	  WebElement cityDataPopup=null;
	  WebElement cityOnMap=driver.findElement(By.xpath("//div[@class='outerContainer' and @title='"+city+"']"));
	  try {
		  driver.findElement(By.xpath("//div[@class='cityText' and contains(text(),'"+city+"')]"));
	  }catch(Exception e) {
		  citiesSearchResult.get(0).click();
	  }
	  cityOnMap.click();
	  try {
		  
		  cityDataPopup=driver.findElement(By.xpath("//div[@class='leaflet-popup-content-wrapper']"));
		  
	  }catch(Exception e) {
		  
		  System.out.println("The POP UP did not appear");
		  
	  }
	  
	  Assert.assertNotNull(cityDataPopup," The pop up was shown");
	  
  }
  
  @Test(dependsOnMethods = "test2PinCity")
  public void getTemperature() {
	  
	  WebElement temprature=driver.findElement(By.xpath("//*[contains(text(),'Temp in Degrees')]"));
	  String[] tempStrings=temprature.getText().split(": ");
	  Integer tempDegree=Integer.parseInt(tempStrings[1]);
	  System.out.println(tempDegree);
	  
  }
  
  @AfterClass
  public void afterClass() {
  }

}
