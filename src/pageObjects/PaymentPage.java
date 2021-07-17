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

public class PaymentPage {
	
	public WebDriver driver;
	
	public PaymentPage(WebDriver rdriver) {
		driver =  rdriver;
		PageFactory.initElements(rdriver, this);
	}
	
	@FindBy(xpath="/html/body/div[2]/div[1]/div[1]/div/div/div[5]/div[2]/div/ul/li") 
	@CacheLookup List<WebElement> bookingDetails;
	
	String detailName = "/html/body/div[2]/div[1]/div[1]/div/div/div[5]/div[2]/div/ul/li[XXXX]/span[2]";
	
	@FindBy(xpath="/html/body/div[2]/div[1]/div[1]/div/div/div[2]/center/button[2]") 
	@CacheLookup WebElement payNowButton;
	
	@FindBy(xpath="//*[@id='gateway_chosen']") 
	@CacheLookup WebElement gatewayDiv;
	
	@FindBy(xpath="//*[@id='gateway_chosen']/div/ul/li") 
	@CacheLookup List<WebElement> gatewayValuesList;
	
	String gatewayOptions = "//*[@id='gateway_chosen']/div/ul/li[XXXX]";
	
	@FindBy(xpath="//*[@id='gateway_chosen']") 
	@CacheLookup WebElement firstNameTextBox;
	
	@FindBy(xpath="//*[@id='gateway_chosen']") 
	@CacheLookup WebElement lastNameTextBox;
	
	@FindBy(xpath="//*[@id='gateway_chosen']") 
	@CacheLookup WebElement cardNumberTextBox;
	
	@FindBy(xpath="//*[@id='gateway_chosen']") 
	@CacheLookup WebElement expiryMonthDropDown;
	
	@FindBy(xpath="//*[@id='gateway_chosen']") 
	@CacheLookup WebElement expiryYearDropDown;
	
	@FindBy(xpath="/*[@id='cvv']") 
	@CacheLookup WebElement cardCVVTextBox;
	
	@FindBy(xpath="//*[@id='pay']/div/div[2]/div/div[3]/div[2]/button") 
	@CacheLookup WebElement payButton;
	
	public int sizeOfBookingDetails() {
		int size = HelperClass.fetchSizeOfList(bookingDetails);
		return size;
	}
	
	public String valesInBooking(int index,VerifyLoginPage page) {
		String text = page.webElementTxtWithDynamicXpath(detailName, index +1);
		return text;
	}
	
	public void clickOnPayNowButton() {
		HelperClass.webElementClick(driver, payNowButton, ConfigClass.waitTime3);
	}
	
	public void clickOnGatewayDropdown() {
		HelperClass.webElementClick(driver, gatewayDiv, ConfigClass.waitTime3);
	}
	
	public int sizeOfGatewayDetails() {
		int size = HelperClass.fetchSizeOfList(gatewayValuesList);
		return size;
	}
	
	public WebElement returnGatewayElement(int index) {
		return dynamicWebElemet(gatewayOptions, index+2);
	}
	
	public WebElement dynamicWebElemet(String xpathValue, int substitutionValue ) {
		WebElement element = driver.findElement(By.xpath(xpathValue.replace("XXXX", substitutionValue+"")));
		return element;
	}
	
	public void enterFirstName(String name) {
		HelperClass.enterValueInTextBox(driver, firstNameTextBox, name);
	}
	
	public void enterLastName(String lastName) {
		HelperClass.enterValueInTextBox(driver, lastNameTextBox, lastName);
	}
	
	public void enterCardNumber(String card) {
		HelperClass.enterValueInTextBox(driver, cardNumberTextBox, card);
	}
	
	
	public void enterExpiryMonth(String month) {
		HelperClass.waitHelper(driver, expiryMonthDropDown, ConfigClass.waitTime2);
		HelperClass.selectValueFromDropDown(expiryMonthDropDown, month);
	}
	
	public void enterExpiryYear(String year) {
		HelperClass.waitHelper(driver, expiryYearDropDown, ConfigClass.waitTime2);
		HelperClass.selectValueFromDropDown(expiryYearDropDown, year);
	}
	
	public void enterCVV(String cvv) {
		HelperClass.enterValueInTextBox(driver, cardCVVTextBox, cvv);
	}
	
	public void ClickOnPayButton() {
		HelperClass.webElementClick(driver, payButton, ConfigClass.waitTime3);
	}

}
