package pageObjects;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.CacheLookup;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import utilities.ConfigClass;
import utilities.HelperClass;

public class PassengerDetailsPage {

	public WebDriver driver;
	
	public PassengerDetailsPage(WebDriver rdriver) {
		driver =  rdriver;
		PageFactory.initElements(rdriver, this);
	}
	
	@FindBy(xpath="//*[@id='mobileMenuMain']/nav/ul[1]/li/a") @CacheLookup WebElement bookingButton;
	@FindBy(xpath="//*[@id='passenger_name_0']") @CacheLookup WebElement passengerNameTextbox;
	@FindBy(xpath="//*[@id='passenger_age_0']") @CacheLookup WebElement passengerAgeTextbox;
	@FindBy(xpath="//*[@id='passenger_passport_0']") @CacheLookup WebElement passengerPassNoTextbox;
	@FindBy(xpath="/html/body/div[2]/div[1]/div/div/div/div[1]/div/div[1]/div[2]/div[4]/button") @CacheLookup WebElement passengerDetailsOk;
	
	public void clickOnBookingButton() {
		HelperClass.webElementClick(driver, bookingButton, ConfigClass.waitTime3);
	}
	
	public void enterPassengerName(String name) {
		HelperClass.enterValueInTextBox(driver, passengerNameTextbox, name);
	}
	
	public void enterPassengerAge(String age) {
		HelperClass.enterValueInTextBox(driver, passengerAgeTextbox, age);
	}
	
	public void enterPassengerPassportNo(String passNo) {
		HelperClass.enterValueInTextBox(driver, passengerPassNoTextbox, passNo);
	}
	
	public void clickOnOkButton() {
		HelperClass.webElementClick(driver, passengerDetailsOk, ConfigClass.waitTime3);
	}
	
}
