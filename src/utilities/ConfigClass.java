package utilities;

public class ConfigClass {
	
	public static String folderPath = "D:\\Workspace\\Testathon";
	public static String driverPath = folderPath + "\\Drivers\\chromedriver.exe";
	public static String driverType ="webdriver.chrome.driver";
	public static String jsonPath = folderPath + "\\Files\\Data.json";
	public static String excelPath = folderPath + "\\Files\\JSONEXCEL.xlsx";
	
	public static String strMatchId = "1234567";
	public static String strDataTeam = "$.data.team";
	public static String strDataTeamPlayers = "$.data.team[|]";
	public static String strDataTeamName = ".name";
	public static String strDataTeamPlayerslist = ".players";
	public static String strDataTeamPlayersIterator = ".players[|]";
	public static String strDataTeamPlayersId = ".pid";
	public static String strDataTeamPlayersName = ".name";
	
	public static String strBowling = "$.data.bowling";
	public static String strBowlingData = "$.data.bowling[|]";
	public static String strBowlingDataScores = ".scores";
	public static String strBowlingDataScoresIterator = ".scores[|]";
	public static String strBowlingDataPId = ".pid";
	
	public static String strBatting = "$.data.batting";
	public static String strBattingData = "$.data.batting[|]";
	public static String strBattingDataScores = ".scores";
	public static String strBattingDataScoresIterator = ".scores[|]";
	public static String strBattingDataPId = ".pid";
	 
	public static String strFielding = "$.data.fielding";
	public static String strFieldingData = "$.data.fielding[|]";
	public static String strFieldingDataScores = ".scores";
	public static String strFieldingDataScoresIterator = ".scores[|]";
	public static String strFieldingDataId = ".pid";
	
	public static String strBowlingScores = "$.data.bowling[|].scores[|]";
	public static String strBowlingScoresWD = ".WD";
	public static String strBowlingScoresEcon = ".Econ";
	public static String strBowlingScoresM = ".M";
	
	public static String strBattingScores = "$.data.batting[|].scores[|]";
	public static String strBattingScoresSR = ".SR";
	public static String strBattingScores6s = ".6s";
	public static String strBattingScores4s = ".4s";
	public static String strBattingScoresR = ".R";
	
	public static String strFieldingScores = "$.data.fielding[|].scores[|]";
	public static String strFieldingScoresRunout = ".runout";
	public static String strFieldingScoresStumped = ".stumped";
	public static String strFieldingScoresBowled = ".bowled";
	public static String strFieldingScoresLbw = ".lbw";
	public static String strFieldingScoresCatch = ".catch";
	
	public static String[] columsInExcel = {"matchID","TeamName","PlayerId","PlayerName","PlayerRun","PlayerWik", 
			"PlayerCatch","PlayerRunOut","stumped","PlayerEco","PlayerSr","Player6s","Player4s","lbw","bowled","maiden"};
	
	public static String PaymentFailureMessage = "LOGIN ID OR PASSWORD IS INVALID";
	public static String bookingReservedMessage = "Your booking status is Reserved";
	
	public static long waitTime1 = 10;
	public static long waitTime2 = 20;
	public static long waitTime3 = 30;

}
