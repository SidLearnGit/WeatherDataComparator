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
	String city=Config.city;
	WebDriver driver;
	ArrayList<WebElement> citiesSearchResult;
	int uiTemperature,apiTemperature;
  @BeforeClass
  public void beforeClass() {
	  
	  System.setProperty(Config.driverPropertyKey, Config.driverPropertyValue);
	  driver=new FirefoxDriver();
  } 

  //Searching cities
  @Test
  public void test1SearchCities() {
	  driver.get(Config.uiUrl);
	  driver.manage().window().maximize();
	  driver.findElement(By.xpath(Config.citySearchBoxXpath))
	  .sendKeys(city);
	  citiesSearchResult=(ArrayList)driver.findElements(By.cssSelector(Config.listCitySearchCssSelector));
	  Assert.assertTrue(citiesSearchResult.size()>0," Atleast one city with name should exist");
	  
  }
  
  //pin city and check pop up
  
  @Test(dependsOnMethods = "test1SearchCities")
  public void test2PinCity() {
	  WebElement cityDataPopup=null;
	  WebElement cityOnMap=driver.findElement(By.xpath(Config.cityOnMapSearchXpath));
	  try {
		  driver.findElement(By.xpath(Config.cityOnMapExists));
	  }catch(Exception e) {
		  citiesSearchResult.get(0).click();
	  }
	  cityOnMap.click();
	  try {
		  
		  cityDataPopup=driver.findElement(By.xpath(Config.cityDataPopupXpath));
		  
	  }catch(Exception e) {
		  
		  System.out.println("The POP UP did not appear");
		  
	  }
	  
	  Assert.assertNotNull(cityDataPopup," The pop up was shown");
	  
  }
  
  //get temperature from UI
  @Test(dependsOnMethods = "test2PinCity")
  public void getUiTemperature() {
	  WebElement temprature=null;
	  try {
		  
		  temprature=driver.findElement(By.xpath(Config.PopupTemperatureXpath));
		  String[] tempStrings=temprature.getText().split(": ");
		  uiTemperature=Integer.parseInt(tempStrings[1]);
		  
	  }catch(Exception e) {		  
	  }
	  
	  Assert.assertNotNull(temprature, "The Temperature of the city should be retrieved from UI");
  }
  
  //get temperature from api
  @Test
  public void getApiTemperature() {
	  Float temperature=null;
	  RestAssured.baseURI=Config.apiBaseUrl;
	  RequestSpecification apiCall=RestAssured.given()
			  .param(Config.apiParamCityKey,city)
			  .param(Config.apiParamApiKey,Config.apiParamApiKeyValue)
			  .param(Config.apiTempUnitsKey,Config.apiTempUnitsValue);
	 
	  Response apiResponse=apiCall.request(Method.GET);
	  HashMap<Object,Object> weatherData=apiResponse.jsonPath().getJsonObject(Config.jsonObejctName);
	  temperature=(Float)weatherData.get(Config.jsonTemperatureKey);
	  apiTemperature=(int)((float)temperature);
	  Assert.assertNotNull(temperature,"The Temperature of the city should be retrieved from API");
	  
  }
  
  // Compare temperature from ui and api
  @Test(dependsOnMethods = {"getApiTemperature","getUiTemperature"})
  public void compareApiUiTemperature() {
	  System.out.println(uiTemperature+ " "+apiTemperature);
	  Assert.assertTrue(Math.abs(uiTemperature-apiTemperature)<Config.allowedDifference,"The Temperature difference is acceptible");
  }
  
  @AfterClass
  public void afterClass() {
	  
	  try {
		Thread.sleep(5000);
	} catch (InterruptedException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	  
	  driver.quit();
  }

}
