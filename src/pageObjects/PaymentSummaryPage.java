package pageObjects;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.CacheLookup;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import utilities.ConfigClass;
import utilities.HelperClass;

public class PaymentSummaryPage {
	
public WebDriver driver;
	
	public PaymentSummaryPage(WebDriver rdriver) {
		driver =  rdriver;
		PageFactory.initElements(rdriver, this);
	}
	
	@FindBy(xpath="/html/body/div[2]/div[1]/div[1]/div/div/div[1]") 
	@CacheLookup WebElement messageAfterCCPayment;
	
	@FindBy(xpath="/html/body/div[2]/div[1]/div[1]/div/div/div[3]/center/button[1]") 
	@CacheLookup WebElement payOnArrivalButtonAfterFailure;
	
	@FindBy(xpath="/html/body/div[2]/div[1]/div[1]/div/div/div[2]/center/button[1]") 
	@CacheLookup WebElement payOnArrivalButton;
	
	@FindBy(xpath="/html/body/div[2]/div[1]/div[1]/div/div/div[2]/div[2]/h4") 
	@CacheLookup WebElement reservedMessage;
	
	public String messageAfterPayment() {
		return HelperClass.fetchText(driver,messageAfterCCPayment);
	}
	
	public void clickOnPayArrivalAfterFaiure(){
		HelperClass.webElementClick(driver, payOnArrivalButtonAfterFailure, ConfigClass.waitTime3);
	}
	
	public void clickOnPayArrival(){
		HelperClass.webElementClick(driver, payOnArrivalButton, ConfigClass.waitTime3);
	}
	
	public String reservedMessageAfterPayment() {
		return HelperClass.fetchText(driver,reservedMessage);
	}
}
