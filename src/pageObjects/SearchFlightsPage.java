package pageObjects;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.CacheLookup;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import utilities.ConfigClass;
import utilities.HelperClass;

public class SearchFlightsPage {

	WebDriver driver;
	
	public SearchFlightsPage(WebDriver rdriver) {
		driver =  rdriver;
		PageFactory.initElements(rdriver, this);
	}
	
	@FindBy(xpath="//*[@id='mobileMenuMain']/nav/ul[1]/li/a")@CacheLookup WebElement homeTabLink;
	
	@FindBy(xpath="//*[@id='search']/div/div/div/div/div/nav/ul/li/a[@href='#flights']")@CacheLookup WebElement flightTabLink;
	
	@FindBy(id="flightSearchRadio-2")
	@CacheLookup WebElement oneWayFlights;
	
	@FindBy(xpath="//*[@id='flights']/div/div/form/div/div/div[3]/div[1]/div/div[1]/div/div[2]")
	@CacheLookup WebElement fromCityNameDiv;
	
	@FindBy(xpath="//*[@id='select2-drop']/div/input")
	@CacheLookup WebElement fromCityName;
	
	@FindBy(xpath="//*[@id='select2-drop']/ul/li[1]")
	@CacheLookup WebElement selectFromCityName;
	
	@FindBy(xpath="//*[@id='flights']/div/div/form/div/div/div[3]/div[1]/div/div[2]/div/div[2]")
	@CacheLookup WebElement toCityNameDiv;
	
	@FindBy(xpath="//*[@id='select2-drop']/div/input")
	@CacheLookup WebElement toCityName;
	
	@FindBy(xpath="//*[@id='select2-drop']/ul/li[1]")
	@CacheLookup WebElement selectToCityName;
	
	@FindBy(xpath="//*[@id='FlightsDateStart']")
	@CacheLookup WebElement dateTextBox;
	
	@FindBy(xpath="//*[@id='datepickers-container']/div[9]/nav/div[2]")
	@CacheLookup WebElement datePickerNav;
	
	@FindBy(xpath="//*[@id='datepickers-container']/div[9]/nav/div[2]")
	@CacheLookup WebElement datePickerNav1;
	
	@FindBy(xpath="//*[@id='datepickers-container']/div[9]/div/div[3]/div/div[not(contains(@class, 'disabled'))]")
	@CacheLookup List<WebElement> datePickerYearList;
	
	String datePickerWeb = "//*[@id='datepickers-container']/div[9]/div";
	
	String datePickerYearElement = datePickerWeb+"/div[3]/div/div[not(contains(@class, 'disabled'))][XXXX]";
	String datePickerOtherElement = datePickerWeb+"/div[3]/div/div[not(contains(@class, 'other-decade'))][XXXX]";
	
	@FindBy(xpath="//*[@id='datepickers-container']/div[9]/nav/div[3]")
	@CacheLookup WebElement datePickerYearContainer;
	
	@FindBy(xpath="//*[@id='datepickers-container']/div[9]/div/div[2]/div/div[not(contains(@class, 'disabled'))]")
	@CacheLookup List<WebElement> datePickerMonthList;
	
	String datePickerMonthElement = datePickerWeb+"/div[2]/div/div[not(contains(@class, 'disabled'))][XXXX]";
	
	@FindBy(xpath="//*[@id='datepickers-container']/div[9]/div/div[1]/div[2]/div[not(contains(@class, 'disabled'))]")
	@CacheLookup List<WebElement> datePickerDayList;
	
	String datePickerDayElement = datePickerWeb+"/div[1]/div[2]/div[not(contains(@class, 'disabled'))][XXXX]";
	
	@FindBy(xpath="//*[@id='flights']/div/div/form/div/div/div[3]/div[4]/button")
	@CacheLookup WebElement searchButton;
	
	public void clickOnHomeTab() {
		HelperClass.webElementClick(driver, homeTabLink, ConfigClass.waitTime3);
	}
	
	public void clickOnFlightTab() {
		HelperClass.webElementClick(driver, flightTabLink, ConfigClass.waitTime3);
	}
	
	public void clickOnOneWay() {
		if (!(oneWayFlights.isSelected())) {
			HelperClass.webElementClick(driver, oneWayFlights, ConfigClass.waitTime3);
		}
	}
	
	public void selectFromCityName(String fromCity) {
		HelperClass.webElementClick(driver, fromCityNameDiv, ConfigClass.waitTime3);
		HelperClass.enterValueInTextBox(driver, fromCityName, fromCity);
		try {
			Thread.sleep(5000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		HelperClass.webElementClick(driver, selectFromCityName, ConfigClass.waitTime3);
	}
	
	public void selectToCityName(String toCity) {
		HelperClass.webElementClick(driver, toCityNameDiv, ConfigClass.waitTime3);
		HelperClass.enterValueInTextBox(driver, toCityName, toCity);
		try {
			Thread.sleep(5000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		HelperClass.webElementClick(driver, selectToCityName, ConfigClass.waitTime3);
	}
	
	public void selectDate(String inputDate) throws InterruptedException{
		HelperClass.webElementClick(driver, dateTextBox, ConfigClass.waitTime2);
		Thread.sleep(5000);
		JavascriptExecutor js = (JavascriptExecutor) driver;
		js.executeScript("window.scrollBy(0,200)");
		HelperClass.webElementClick(driver, datePickerNav, ConfigClass.waitTime2);
		Thread.sleep(5000);
		HelperClass.webElementClick(driver, datePickerNav1, ConfigClass.waitTime2);
		Thread.sleep(5000);
		String[] dateArray = inputDate.split("\\-");
		VerifyLoginPage page = new VerifyLoginPage(driver);
		int size = HelperClass.fetchSizeOfList(datePickerYearList);
		int flag = 0;
		for (int i = 0; i < size; i++) {
			int index = i+1;
			String text = page.webElementTxtWithDynamicXpath(datePickerYearElement, index);
			String decadeText = page.webElementTxtWithDynamicXpath(datePickerOtherElement, index);
			if (text.equals(dateArray[2])) {
				if (decadeText.equals(dateArray[2]) && i == size) {
					HelperClass.webElementClick(driver, datePickerYearContainer, ConfigClass.waitTime3);
				}
				webElementClickWithDynamicXpath(datePickerYearElement,index);
				flag = 1;
			}
			if (flag == 1) {
				break;
			}
		}
		
		if (flag == 0) {
			System.out.println("Enter a valid year");
		} else {
			flag = 0;
     	}
		
		calenderOperations(page,dateArray[1],datePickerMonthList,datePickerMonthElement);
		calenderOperations(page,dateArray[0],datePickerDayList,datePickerDayElement);
	}
	
	public void calenderOperations(VerifyLoginPage page,String dateArray,List<WebElement> datePickerDayList2,String datePickerElement) {
		int size = HelperClass.fetchSizeOfList(datePickerDayList2);
		int flag = 0;
		for (int i = 0; i < size; i++) {
			int index = i+1;
			String text = page.webElementTxtWithDynamicXpath(datePickerElement, index);
			if (text.equals(dateArray)) {
				webElementClickWithDynamicXpath(datePickerElement,index);
				flag = 1;
			}
			if (flag == 1) {
				break;
			}
		}
		
		if (flag == 0) {
			System.out.println("Enter a valid value");
		} else {
			flag = 0;
     	}
	}
	
	public void webElementClickWithDynamicXpath(String xpathValue, int substitutionValue ) {
		HelperClass.webElementClick(driver, driver.findElement(By.xpath(xpathValue.replace("XXXX", substitutionValue+""))),ConfigClass.waitTime3);
	}
	
	public void clickOnSearch() {
		HelperClass.webElementClick(driver,searchButton ,ConfigClass.waitTime3);
	}
}
