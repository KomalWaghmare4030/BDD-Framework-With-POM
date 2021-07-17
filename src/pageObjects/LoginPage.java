package pageObjects;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.CacheLookup;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import utilities.ConfigClass;
import utilities.HelperClass;

public class LoginPage {

	public WebDriver driver;
	
	public LoginPage(WebDriver rdriver) {
		driver =  rdriver;
		PageFactory.initElements(rdriver, this);
	}
	
	@FindBy(xpath="//*[@id='dropdownLangauge']")@CacheLookup WebElement languageDropDown;
	
	@FindBy(xpath="//*[@id='en']")@CacheLookup WebElement language;
	
	@FindBy(xpath="/html/body/div[2]/header/div[1]/div/div/div[2]/div/ul/li[3]/div/a")
	@CacheLookup WebElement myAccountDropDown;
	
	@FindBy(xpath="/html/body/div[2]/header/div[1]/div/div/div[2]/div/ul/li[3]/div/div/div/a[1]")
	@CacheLookup WebElement loginValueOfDropDown;
	
	@FindBy(xpath="//*[@id='loginfrm']/div[3]/div[1]/label/input") @CacheLookup WebElement userNameTextBox;
	
	@FindBy(xpath="//*[@id='loginfrm']/div[3]/div[2]/label/input") @CacheLookup WebElement passwordTextBox;
	
	@FindBy(xpath="//*[@id='loginfrm']/button") @CacheLookup WebElement loginButton;
	
	@FindBy(xpath="/html/body/div[2]/div[1]/div[1]/div/div/div[1]/div/div[2]/h3")
	@CacheLookup WebElement headingText;
	
	public void selectLanguage() {
		HelperClass.webElementClick(driver, languageDropDown, ConfigClass.waitTime2);
		HelperClass.webElementClick(driver, language, ConfigClass.waitTime1);
	}
	
	public void clickOnLoginFromDropDown() {
		HelperClass.webElementClick(driver, myAccountDropDown, ConfigClass.waitTime2);
		HelperClass.webElementClick(driver, loginValueOfDropDown, ConfigClass.waitTime2);
	}
	
	public void enterUserName(String userName) {
		HelperClass.enterValueInTextBox(driver, userNameTextBox, userName);
	}
	
	public void enterPassword(String password) {
		HelperClass.enterValueInTextBox(driver, passwordTextBox, password);
	}
	
	public void clickOnLogin() {
		HelperClass.webElementClick(driver, loginButton, ConfigClass.waitTime1);
	}
	
	public String getMessageAfterLogin() {
		return HelperClass.fetchText(driver, headingText);
	}
}
