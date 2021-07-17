package utilities;

import java.time.Duration;
import java.util.List;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.Select;

import io.netty.handler.timeout.TimeoutException;

public class HelperClass {
	

	public static void webElementClick(WebDriver driver,WebElement element,long waitTime) {
		waitHelper(driver,element,waitTime);
		element.click();
	}
	
	public static void waitHelper(WebDriver driver,WebElement locator, long waitTime) {
		FluentWait<WebDriver> wait = new FluentWait<WebDriver>(driver);
		wait.withTimeout(Duration.ofSeconds(waitTime));
		wait.pollingEvery(Duration.ofSeconds(5));
		wait.ignoring(NoSuchElementException.class,TimeoutException.class);
		wait.until(ExpectedConditions.visibilityOf(locator));
	}
	
	public static void enterValueInTextBox(WebDriver driver,WebElement element,String value) {
		waitHelper(driver,element,ConfigClass.waitTime1);
		element.clear();
		element.sendKeys(value);
	}
	
	public static String fetchText(WebDriver driver,WebElement element) {
		waitHelper(driver,element,ConfigClass.waitTime3);
		return element.getText();
	}
	
	public static int fetchSizeOfList(List<WebElement> element) {
		return element.size();
	}
	
	public static void selectValueFromDropDown(WebElement element,String value) {
		Select select = new Select(element);
		select.selectByValue(value);
	}
}
