package stepDefinitions;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.apache.log4j.xml.DOMConfigurator;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.junit.Assert;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

import com.jayway.jsonpath.JsonPath;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import net.minidev.json.JSONArray;
import pageObjects.LoginPage;
import pageObjects.PassengerDetailsPage;
import pageObjects.PaymentPage;
import pageObjects.PaymentSummaryPage;
import pageObjects.SearchFlightsPage;
import pageObjects.VerifyLoginPage;
import utilities.ConfigClass;
import utilities.HelperClass;

public class VerifyDetails {
	
	WebDriver driver;
	VerifyLoginPage detailsPage;
	LoginPage loginPage;
	SearchFlightsPage searchFlights;
	PassengerDetailsPage passDetails;
	PaymentPage paymentPage;
	PaymentSummaryPage summaryPage;
	private BufferedReader reader = null;
	private String jsonContent = "";
	private LinkedHashMap<String, String> allPlayers = null,battingPIdList = null,bowlingPIdList = null,fieldingPIdList = null;
	private ArrayList<ArrayList<String>> matchData =null;
	private File file =null;
	private XSSFWorkbook workbook =null;
	static final Logger logger = LogManager.getLogger(VerifyDetails.class.getName());
	
	@Given("Read JSON File from path")
	public void read_json_file_from_path() throws FileNotFoundException {
		DOMConfigurator.configure("log4j.xml");
	    logger.info("# # # # # # # # # # # # # # # # # # # # # # # # # # # ");
	    logger.info("Start Data Reading From Excel");
		reader = new BufferedReader(new FileReader(ConfigClass.jsonPath));
		logger.info("End Data Reading From Excel");
	}
	
	@When("Store All Data to Map")
	public void store_all_data_to_map() throws IOException {
		String line = null;
		logger.info("Started Storing Data into Map");
        StringBuilder builder = new StringBuilder();
        while((line=reader.readLine())!=null) {
        	builder.append(line);
        }
        jsonContent = builder.toString();
        logger.info("All Data Stored to Map");
	}

	@Then("Store All Players in Map")
	public void store_all_players_in_map() {
		logger.info("Started Storing Players Data into Map");
		 JSONArray teams = readDataUsingJsonPath(jsonContent, ConfigClass.strDataTeam);
		 allPlayers = new LinkedHashMap<String, String>();
		 for (int i = 0; i < teams.size(); i++) {
			String strTeams = splitAndJoinString(ConfigClass.strDataTeamPlayers,i);//"$.data.team[" + i + "]";
			String team = JsonPath.read(jsonContent, strTeams + ConfigClass.strDataTeamName);
			JSONArray players = JsonPath.read(jsonContent, strTeams + ConfigClass.strDataTeamPlayerslist);
			for (int j = 0; j < players.size(); j++) {
				String player = strTeams + splitAndJoinString(ConfigClass.strDataTeamPlayersIterator,j); //".players[" + j + "]";
				String playerID = JsonPath.read(jsonContent, player+ConfigClass.strDataTeamPlayersId);
				String playerName = JsonPath.read(jsonContent, player+ConfigClass.strDataTeamPlayersName);
				allPlayers.put(playerID, team + "|" + playerName);
			}
		}
		 logger.info("Players Data Stored into Map");
	}
	
	@Then("Store Bowling Values to Map")
	public void store_bowling_values_to_map() {
		logger.info("Started Storing Bowling Data into Map");
		bowlingPIdList = storeValuesInMap(ConfigClass.strBowling,ConfigClass.strBowlingData,
		ConfigClass.strBowlingDataScores,ConfigClass.strBowlingDataScoresIterator,ConfigClass.strBowlingDataPId);
		logger.info("Bowling Data Stored into Map");
	}
	
	@Then("Store Batting Values to Map")
	public void store_batting_values_to_map() {
		logger.info("Started Storing Batting Data into Map");
		battingPIdList = storeValuesInMap(ConfigClass.strBatting,ConfigClass.strBattingData,
		ConfigClass.strBattingDataScores,ConfigClass.strBattingDataScoresIterator,ConfigClass.strBattingDataPId);
		logger.info("Batting Data Stored into Map");
	}
	
	@Then("Store Fielding Values to Map")
	public void store_fielding_values_to_map() {
		logger.info("Started Storing Fielding Data into Map");
		fieldingPIdList = storeValuesInMap(ConfigClass.strFielding,ConfigClass.strFieldingData,
	    ConfigClass.strFieldingDataScores,ConfigClass.strFieldingDataScoresIterator,ConfigClass.strFieldingDataId);
		logger.info("Fielding Data Stored into Map");
	}
	
	@Then("Compare Data")
	public void compare_data() {
		matchData = new ArrayList<ArrayList<String>>();
		logger.info("Compare Data and add in ArrayList");
		for (Map.Entry<String,String> a : allPlayers.entrySet()) {
			String wicket = "", eco = "", maiden = "", playerSR = "", sixRun = "", fourRun = "", playerRun = "";
			String runout = "", stumped = "", bowled = "", lbw = "", playerCatch = "";

			ArrayList<String> playerData = new ArrayList<String>();
			Object allPlayersKey = a.getKey();

			if (bowlingPIdList.containsKey(allPlayersKey)) {
				for (Map.Entry<String,String> p : bowlingPIdList.entrySet()) {
					if (allPlayersKey.equals(p.getKey())) {
						// System.out.println(a.getKey()+" "+p.getValue());
						//String[] array = ((String) p.getValue()).split("\\|");
						//String strScores = "$.data.bowling[" + array[0] + "].scores[" + array[1] + "]";
						String strScores = splitAndJoinArray(p.getValue(), ConfigClass.strBowlingScores);
						wicket = JsonPath.read(jsonContent, strScores + ConfigClass.strBowlingScoresWD);
						eco = JsonPath.read(jsonContent, strScores + ConfigClass.strBowlingScoresEcon);
						maiden = JsonPath.read(jsonContent, strScores + ConfigClass.strBowlingScoresM);
					}
				}
			}
			if (battingPIdList.containsKey(allPlayersKey)) {
				for (Map.Entry<String,String> b : battingPIdList.entrySet()) {
					if (allPlayersKey.equals(b.getKey())) {
						//String[] array = ((String) b.getValue()).split("\\|");
						//String strScores = "$.data.batting[" + array[0] + "].scores[" + array[1] + "]";
						String strScores = splitAndJoinArray(b.getValue(), ConfigClass.strBattingScores);
						playerSR = JsonPath.read(jsonContent, strScores + ConfigClass.strBattingScoresSR) + "";
						sixRun = JsonPath.read(jsonContent, strScores + ConfigClass.strBattingScores6s) + "";
						fourRun = JsonPath.read(jsonContent, strScores + ConfigClass.strBattingScores4s) + "";
						playerRun = JsonPath.read(jsonContent, strScores + ConfigClass.strBattingScoresR) + "";
					}
				}
			}
			if (fieldingPIdList.containsKey(allPlayersKey)) {
				for (Map.Entry<String,String> f : fieldingPIdList.entrySet()) {
					if (allPlayersKey.equals(f.getKey())) {
						//String[] array = ((String) f.getValue()).split("\\|");
						//String strScores = "$.data.fielding[" + array[0] + "].scores[" + array[1] + "]";
						String strScores = splitAndJoinArray(f.getValue(), ConfigClass.strFieldingScores);
						runout = JsonPath.read(jsonContent, strScores + ConfigClass.strFieldingScoresRunout) + "";
						stumped = JsonPath.read(jsonContent, strScores + ConfigClass.strFieldingScoresStumped) + "";
						bowled = JsonPath.read(jsonContent, strScores + ConfigClass.strFieldingScoresBowled) + "";
						lbw = JsonPath.read(jsonContent, strScores + ConfigClass.strFieldingScoresLbw) + "";
						playerCatch = JsonPath.read(jsonContent, strScores + ConfigClass.strFieldingScoresCatch) + "";
					}
				}
			}
			String[] nameValues = ((String) a.getValue()).split("\\|");
			playerData.add(ConfigClass.strMatchId);
			playerData.add(nameValues[0]);
			playerData.add((String) allPlayersKey);
			playerData.add(nameValues[1]);
			playerData.add(playerRun);
			playerData.add(wicket);
			playerData.add(playerCatch);
			playerData.add(runout);
			playerData.add(stumped);
			playerData.add(eco);
			playerData.add(playerSR);
			playerData.add(sixRun);
			playerData.add(fourRun);
			playerData.add(lbw);
			playerData.add(bowled);
			playerData.add(maiden);
			matchData.add(playerData);
		}
		logger.info("Data Added in ArrayList");
	}
	
	@Then("Create Excel File")
	public void create_excel_file() {
		file = new File(ConfigClass.excelPath);
		if (file.exists() && !file.isDirectory()) {
			boolean b = file.delete();
			if (b == true) {
				logger.info("File Deleted Successfully");
			}
			else {
				logger.error("Failed to Deleted File");
			}
		}
	}
	
	@Then("Write into Excel File")
	public void write_into_excel_file() throws Exception {
	    workbook = new XSSFWorkbook();
	    logger.info("Create Excel File");
		XSSFSheet sheet = workbook.createSheet("JSONEXCEL");
		logger.info("Create Sheet in Excel File");
		XSSFRow row;
		row = sheet.createRow(0);
		
		for(int i=0;i<ConfigClass.columsInExcel.length-1;i++) {
			Cell cell = row.createCell(i);
			cell.setCellValue(ConfigClass.columsInExcel[i]);
		}
		
		for (int i = 0; i < matchData.size(); i++) {
			row = sheet.createRow(i + 1);
			for (int j = 0; j < matchData.get(i).size(); j++) {
				Cell cell = row.createCell(j);
				
				for (int k = 0; k < ConfigClass.columsInExcel.length-1; k++) {
					if (cell.getColumnIndex() == k) {
						cell.setCellValue(matchData.get(i).get(j));
					}
				}
			}
		}
		FileOutputStream output = new FileOutputStream(new File(ConfigClass.excelPath));
		workbook.write(output);
		output.close();
		logger.info("Close Sheet");
	}
	
	@Then("Close Instance of File")
	public void close_instance_of_file() throws IOException {
		workbook.close();
		logger.info("Close Workbook");
		logger.info("# # # # # # # # # # # # # # # # # # # # # # # # # # # ");
	}

	public JSONArray readDataUsingJsonPath(String jsonContent,String query) {
		JSONArray teams = JsonPath.read(jsonContent, query);
		return teams;
	}
	
	public String readStringUsingJsonPath(String jsonContent,String query) {
		String stringData = JsonPath.read(jsonContent, query);
		return stringData;
	}

	public String splitAndJoinString(String valueToBeSplit,int iterator) {
		String[] splittedArray = valueToBeSplit.split("\\|");
		String joinArray="";
		joinArray = splittedArray[0]+iterator+splittedArray[1];
		//System.out.println("joinArray : "+joinArray);
		return joinArray;
	}
	
	public LinkedHashMap<String, String> storeValuesInMap(String fieldPattern,String iteratorPattern,String arrayPattern,String subIteratorPattern,String subIteratorData) {
		JSONArray arrayData = readDataUsingJsonPath(jsonContent, fieldPattern);
		 LinkedHashMap<String, String> mapData = new LinkedHashMap<String, String>();
		 for (int i = 0; i < arrayData.size(); i++) {
			String strData = splitAndJoinString(iteratorPattern,i);//"$.data.batting[" + k + "]";
			JSONArray players = JsonPath.read(jsonContent, strData + arrayPattern);
			for (int j = 0; j < players.size(); j++) {
				String strSubData = strData + splitAndJoinString(subIteratorPattern,j); //".players[" + j + "]";
				String strSubIteratorData = JsonPath.read(jsonContent, strSubData+subIteratorData);
				mapData.put(strSubIteratorData, i + "|" + j);
			}
		}
		return mapData;
	}
	
	
	
	public String splitAndJoinArray(String value, String patternArray){
		String[] array = ((String) value).split("\\|");//((String) p.getValue()).split("\\|");
		String[] pattern = patternArray.split("\\|");
		String strScores = "";
		for(int i=0;i<array.length;i++) {
			strScores = strScores + pattern[i] + array[i];
		}
		strScores = strScores + pattern[2];
		//System.out.println("Mutiple joinArray : "+strScores);
		return strScores;
	}
	
	@Given("Launch Browser with URL {string}")
	public void launch_browser_with_url(String URL) {
		System.setProperty(ConfigClass.driverType, ConfigClass.driverPath);
		driver = new ChromeDriver();
        driver.get(URL);
        driver.manage().window().maximize();
        detailsPage = new VerifyLoginPage(driver);
        loginPage = new LoginPage(driver);
        searchFlights = new SearchFlightsPage(driver);
        passDetails = new PassengerDetailsPage(driver);
        paymentPage = new PaymentPage(driver);
        summaryPage = new PaymentSummaryPage(driver);
	}

	@When("Change Language")
	public void change_language() {
		loginPage.selectLanguage();
	}

	@Then("Enter UserName {string}")
	public void enter_username(String userName) {
		loginPage.clickOnLoginFromDropDown();
		loginPage.enterUserName(userName);
	}

	@Then("Enter Password {string}")
	public void enter_password_demouser(String password) {
		loginPage.enterPassword(password);
	}

	@Then("^Click on Login Button$")
	public void click_on_login_button() {
		JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("window.scrollBy(0,300)");
        loginPage.clickOnLogin();
	}

	@Then("Verify Welcome Message {string}")
	public void verify_welcome_message(String text) {
	    String message = loginPage.getMessageAfterLogin();
	    Assert.assertEquals(message,text);
	}

	@Then("Navigate to Booking Page")
	public void navigate_to_booking_page() {
		detailsPage.clickOnBookingTab();
	}

	@Then("Print all Bookings")
	public void print_all_bookings() {
	  int size = detailsPage.bookingListSize();
	  for(int i=0;i<size;i++) {
		  int index = (i+1)*2;
		  int strCountOfStars = detailsPage.getCountOfStars(index);
		  String strBookingTourName = detailsPage.getBookingTourName(index);
		  String strBookingTourCity = detailsPage.getBookingTourCity(index);
		  String strBookingPriceInUSD = detailsPage.getBookingPriceInUSD(index);
		  String strBookingDate = detailsPage.getBookingDate(index);
		  String strBookingID = detailsPage.getBookingID(index);
		  String[] details = strBookingID.split("\\s");
		  String strBookingStatus = detailsPage.getBookingStatus(index);
		  
		  System.out.println("Stars : "+strCountOfStars+" star");
		  System.out.println("Tour Name : "+strBookingTourName);
		  System.out.println("Tour City : "+strBookingTourCity);
		  System.out.println("Price in USD : "+strBookingPriceInUSD);
		  System.out.println("Booking Date : "+strBookingDate);
		  System.out.println(details[0]+" "+details[1]+" : "+details[2]);
          System.out.println(details[3]+" "+details[4]+" : "+details[5]);
          System.out.println(details[6]+" "+details[7]+" : "+details[8]);
          System.out.println("Booking Status : "+strBookingStatus);
	  }
	}
	
	@Then("Logout")
	public void logout() {
		detailsPage.clickOnLogoutFromDropDown();
	}

	@Then("Close Browser")
	public void close_browser() {
	   driver.quit();
	}
	
	@Then("Click on Home Tab")
	public void click_on_home_tab() {
		searchFlights.clickOnHomeTab();
	}

	@Then("Click on Flights Tab")
	public void click_on_flights_tab() {
		searchFlights.clickOnFlightTab();
	}

	@Then("Select One-way options")
	public void select_one_way_options() {
		searchFlights.clickOnOneWay();
	}

	@Then("Select from {string}")
	public void select_from(String fromCity) {
		JavascriptExecutor js = (JavascriptExecutor) driver;
		js.executeScript("window.scrollBy(0,200)");
		searchFlights.selectFromCityName(fromCity);
	}

	@Then("Select to {string}")
	public void select_to(String toCity) {
		searchFlights.selectToCityName(toCity);
	}

	@Then("Select Date {string}")
	public void select_date(String date) throws InterruptedException {
		searchFlights.selectDate(date);
	}

	@Then("Click on Search Button")
	public void click_on_search_button() {
		searchFlights.clickOnSearch();
	}

	@Then("Click on Booking Buttton")
	public void click_on_booking_buttton() {
		JavascriptExecutor js = (JavascriptExecutor) driver;
		js.executeScript("window.scrollBy(0,200)");
		passDetails.clickOnBookingButton();
	}

	@Then("Enter User Name {string}")
	public void enter_user_name(String name) {
		JavascriptExecutor js = (JavascriptExecutor) driver;
		js.executeScript("window.scrollBy(0,400)");
		passDetails.enterPassengerName(name);
	}

	@Then("Enter User Age {string}")
	public void enter_user_age(String age) {
		passDetails.enterPassengerAge(age);
	}

	@Then("Enter User Passport Number {string}")
	public void enter_user_passport_number(String passNo) {
		passDetails.enterPassengerPassportNo(passNo);
	}

	@Then("Click on OK Button")
	public void click_on_ok_button() {
		JavascriptExecutor js = (JavascriptExecutor) driver;
		js.executeScript("window.scrollBy(0,500)");
	}

	@Then("Print Booking Details")
	public void print_booking_details() {
	    int size = paymentPage.sizeOfBookingDetails();
	    
	    for(int i=0;i>size;i++) {
	    	String text = paymentPage.valesInBooking(i, detailsPage);
	    	System.out.println(text);
	    }
	}
	
	@Then("Pay for the booking Using Details {string} {string} {string} {string} {string}")
	public void pay_for_the_booking_using_details(String name, String payVia, String masterCard, String expiry, String CVV){
		switch(payVia) {
			case "Credit Card":
				payViaCreditCard(name,payVia,masterCard,expiry,CVV);
				break;
			default :
				payOnArrival();
		}		
	}

	@Then("Check reserved message")
	public void check_reserved_message() {
		String message = summaryPage.reservedMessageAfterPayment();
		Assert.assertEquals(message, ConfigClass.bookingReservedMessage);
	}
	
	public void payViaCreditCard(String name,String type,String masterCard, String expiry, String CVV) {
		selectValueFromPaymentOption(type);
		JavascriptExecutor js = (JavascriptExecutor) driver;
		js.executeScript("window.scrollBy(0,400)");
		String[] nameSplit = name.split("\\s");
		String[] expirySplit = name.split("\\/");
		paymentPage.enterFirstName(nameSplit[0]);
		if(nameSplit.length>1) {
			paymentPage.enterLastName(nameSplit[1]);
		}
		paymentPage.enterCardNumber(masterCard);
		paymentPage.enterExpiryMonth(expirySplit[0]);
		paymentPage.enterExpiryYear(expirySplit[1]);
		paymentPage.enterCVV(CVV);
		js.executeScript("window.scrollBy(0,200)");
		paymentPage.ClickOnPayButton();
		
		String message = summaryPage.messageAfterPayment();
		if (message.contains(ConfigClass.PaymentFailureMessage)) {
			summaryPage.clickOnPayArrivalAfterFaiure();
			driver.switchTo().alert().accept();
		}
	}
	
	public void selectValueFromPaymentOption(String value) {
		paymentPage.clickOnPayNowButton();
		JavascriptExecutor js = (JavascriptExecutor) driver;
		js.executeScript("window.scrollBy(0,300)");
		paymentPage.clickOnGatewayDropdown();
		int size = paymentPage.sizeOfGatewayDetails();
		
		for (int i = 0; i < size; i++) {
			WebElement gateWay = paymentPage.returnGatewayElement(i);
			String text = HelperClass.fetchText(driver,gateWay);
			
			if (text.equalsIgnoreCase(value))
			{
				((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView();",gateWay);
				gateWay.click();
				break;
			}
		}
	}
	
	public void payOnArrival() {
		summaryPage.clickOnPayArrival();
		driver.switchTo().alert().accept();
	}	
}
