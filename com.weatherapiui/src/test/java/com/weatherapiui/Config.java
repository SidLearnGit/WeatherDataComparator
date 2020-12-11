package com.weatherapiui;

public class Config {
	
	//Common
	public static String city="Mumbai";
	public static Integer allowedDifference=5;
	
	//Ui Related values
	public static String driverPropertyKey="webdriver.gecko.driver";
	public static String driverPropertyValue="Utilties\\geckodriver.exe";
	public static String uiUrl="https://social.ndtv.com/static/Weather/report/";
	public static String citySearchBoxXpath="//input[@id='searchBox' and @class='searchBox' and @type='text']";
	public static String listCitySearchCssSelector="div[class='message']:not([style='display: none;'])";
	public static String cityOnMapSearchXpath="//div[@class='outerContainer' and @title='"+city+"']";
	public static String cityOnMapExists="//div[@class='cityText' and contains(text(),'"+city+"')]";
	public static String cityDataPopupXpath="//div[@class='leaflet-popup-content-wrapper']";
	public static String PopupTemperatureXpath="//*[contains(text(),'Temp in Degrees')]";
	
	//Api related values
	public static String apiBaseUrl="http://api.openweathermap.org/data/2.5/weather";
	public static String apiParamCityKey="q";
	public static String apiParamApiKey="appid";
	public static String apiParamApiKeyValue="7fe67bf08c80ded756e598d6f8fedaea";
	public static String apiTempUnitsKey="units";
	public static String apiTempUnitsValue="metric";
	public static String jsonObejctName="main";
	public static String jsonTemperatureKey="temp";
	
	

}
