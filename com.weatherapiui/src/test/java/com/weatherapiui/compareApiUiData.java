package com.weatherapiui;

import org.testng.annotations.Test;

import io.restassured.RestAssured;
import static io.restassured.RestAssured.*;
import io.restassured.http.Method;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;

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
	int uiTemperature,apiTemperature;
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
	  Assert.assertTrue(citiesSearchResult.size()>0," Atleast one city with name should exist");
	  
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
  public void getUiTemperature() {
	  
	  WebElement temprature=driver.findElement(By.xpath("//*[contains(text(),'Temp in Degrees')]"));
	  String[] tempStrings=temprature.getText().split(": ");
	  uiTemperature=Integer.parseInt(tempStrings[1]);
	  
  }
  @Test
  public void getApiTemperature() {
	  
	  RestAssured.baseURI="http://api.openweathermap.org/data/2.5/weather";
	  RequestSpecification apiCall=RestAssured.given()
			  .param("q",city)
			  .param("appid","7fe67bf08c80ded756e598d6f8fedaea")
			  .param("units","metric");
	 
	  Response apiResponse=apiCall.request(Method.GET);
	   HashMap<Object,Object> weatherData=apiResponse.jsonPath().getJsonObject("main");
	  apiTemperature=(Integer)weatherData.get("temp");
	  System.out.println(apiTemperature);
  }
  
  @Test(dependsOnMethods = {"getApiTemperature","getUiTemperature"})
  public void compareApiUiTemperature() {
	  System.out.println(uiTemperature+ " "+apiTemperature);
	  Assert.assertTrue(Math.abs(uiTemperature-apiTemperature)<5,"The Temperature difference is acceptible");
  }
  
  @AfterClass
  public void afterClass() {
  }

}
