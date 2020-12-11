# WeatherDataComparator
## Overview
This project includes test cases for validating temperature data recieved from UI(NDTV website) and API(openweather api). Selenium Java library and TestNG frameworks are used for automating the UI side and REST Assured is used for the API side. The project was developed in Eclipse IDE and as a Maven project.

## Important notes
* Java 1.8 is recommeded
* It is recoimmened to install TestNG plugin in Eclipse
* **UI can be automated only in Firefox.** 
* **TestNG framework is mandatory**.
* The values such as Urls,apikey,json object keys,xpath strings etc are included in Config.java file.Its recommened that if any data has to be pre defined, then it should be done Config.java.
* **The pom.xml contains all the required dependencies.**
* **Firefox driver is located in 'Utils' folder in project path. The driver is for windows**
* As of now the project supoorts only testing for a single city. The city name should be mentioned in the Config.Java

## Test case informations
* Searching and pinning cities.
* Checking pop up if user clicks on the city on map.
* Retrieving temperature of the city(UI).
* Retrieving temperature of the city from api.
* Comparaing the Temperature data recieved from UI and API.

