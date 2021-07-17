package pageObjects;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.CacheLookup;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import utilities.ConfigClass;
import utilities.HelperClass;

public class VerifyLoginPage {
	
	public WebDriver driver;
	
	public VerifyLoginPage(WebDriver rdriver) {
		driver =  rdriver;
		PageFactory.initElements(rdriver, this);
	}
	
	@FindBy(xpath="/html/body/div[2]/div[1]/div[2]/div[2]/div/div[1]/div[1]/aside/nav/ul/li[1]/a")
	@CacheLookup WebElement bookingLink;
	
	@FindBy(xpath="/html/body/div[2]/header/div[1]/div/div/div[2]/div/ul/li[3]/div/a")
	@CacheLookup WebElement userProfileClick;
	
	@FindBy(xpath="/html/body/div[2]/header/div[1]/div/div/div[2]/div/ul/li[3]/div/div/div/a[2]")
	@CacheLookup WebElement logout;
	
	@FindBy(xpath="//*[@id='bookings']/div[@class='row']")
	@CacheLookup List<WebElement> bookingRows;
	
	String bookingStars ="//*[@id='bookings']/div[XXXX]/div[1]/div[1]/div/i[@class='rating-icon fa fa-star rating-rated']";
	String bookingTourName ="//*[@id='bookings']/div[XXXX]/div[1]/div[1]/a/b";
	String bookingTourCity = "//*[@id='bookings']/div[XXXX]/div[1]/div[1]/span[1]";
	String bookingPriceInUSD = "//*[@id='bookings']/div[XXXX]/div[1]/div[1]//span[2]/strong";
	String bookingDate = "//*[@id='bookings']/div[XXXX]/div[1]/div[1]/small/span";
	String bookingID = "//*[@id='bookings']/div[XXXX]/div[2]/span";
	String bookingStatus = "//*[@id='bookings']/div[XXXX]/div[3]/h5/strong/span";
	
	public void clickOnBookingTab() {
		HelperClass.webElementClick(driver, bookingLink, ConfigClass.waitTime2);
	}
	
	public int bookingListSize() {
		return HelperClass.fetchSizeOfList(bookingRows);
	}
	
	public int getCountOfStars(int index) {
		return webElementSizeWithDynamicXpath(bookingStars,index);
	}
	
	public String getBookingTourName(int index) {
		return webElementTxtWithDynamicXpath(bookingTourName,index);
	}
	
	public String getBookingTourCity(int index) {
		return webElementTxtWithDynamicXpath(bookingTourCity,index);
	}
	
	public String getBookingPriceInUSD(int index) {
		return webElementTxtWithDynamicXpath(bookingPriceInUSD,index);	
	}
	
	public String getBookingDate(int index) {
		return webElementTxtWithDynamicXpath(bookingDate,index);	
	}
	
	public String getBookingID(int index) {
		return webElementTxtWithDynamicXpath(bookingID,index);	
	}
	
	public String getBookingStatus(int index) {
		return webElementTxtWithDynamicXpath(bookingStatus,index);	
	}
	
	public void clickOnLogoutFromDropDown() {
		HelperClass.webElementClick(driver, userProfileClick, ConfigClass.waitTime2);
		HelperClass.webElementClick(driver, logout, ConfigClass.waitTime1);
	}
	
	public String webElementTxtWithDynamicXpath(String xpathValue, int substitutionValue ) {
		boolean isDisplayed = driver.findElement(By.xpath(xpathValue.replace("XXXX", substitutionValue+""))).isDisplayed();
		if(isDisplayed == true) {
			WebElement element = driver.findElement(By.xpath(xpathValue.replace("XXXX", substitutionValue+"")));
			return HelperClass.fetchText(driver, element);
		}
		else {
			return "";
		}
	}
	
	public int webElementSizeWithDynamicXpath(String xpathValue, int substitutionValue ) {
		List<WebElement> elementList = driver.findElements(By.xpath(xpathValue.replace("XXXX", substitutionValue+"")));
		int size = 0;
		size = HelperClass.fetchSizeOfList(elementList);
		return size;
	}
}
