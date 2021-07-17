Feature: Testathon Problems
  # The first example has two steps
  @Test1
  Scenario: Read JSON and Export to Excel
    Given Read JSON File from path
    When Store All Data to Map
    Then Store All Players in Map
    Then Store Bowling Values to Map
    And Store Batting Values to Map
    And Store Fielding Values to Map
    Then Compare Data
    Then Create Excel File
    Then Write into Excel File
    And Close Instance of File

  @Test2
  Scenario Outline: Verify User and Booking
    Given Launch Browser with URL "https://www.phptravels.net/home"
    When Change Language
    Then Enter UserName "<username>"
    Then Enter Password "<password>"
    Then Click on Login Button
    Then Verify Welcome Message "<title>"
    Then Navigate to Booking Page
    Then Print all Bookings
    Then Logout
    Then Close Browser

    Examples: 
      | username            | password | title         |
      | user@phptravels.com | demouser | Hi, Demo User |

  @Test3
  Scenario Outline: Flight Booking
    Given Launch Browser with URL "https://www.phptravels.net/home"
 #   When Change Language
 #   Then Enter UserName "<username>"
 #   Then Enter Password "<password>"
 #   Then Click on Login Button
 #   Then Verify Welcome Message "<title>"
 #   Then Click on Home Tab
    Then Click on Flights Tab
    Then Select One-way options
    And Select from "<fromCity>"
    And Select to "<toCity>"
    And Select Date "<Date>"
    Then Click on Search Button
    Then Click on Booking Buttton
    Then Enter User Name "<name>"
    And Enter User Age "<age>"
    And Enter User Passport Number "<passport>"
    Then Click on OK Button
    Then Print Booking Details
    Then Pay for the booking Using Details "<name>" "<payVia>" "<masterCard>" "<expiry>" "<cvv>"
    Then Check reserved message
    Then Logout
    Then Close Browser

    Examples: 
      | username            | password | title         | fromCity | toCity | Date        | name      | age | passport |   payVia    | masterCard          | expiry  | cvv |
      | mycar4030@gmail.com | demouser | Hi, Test User | Pune     | Delhi  | 15-Jun-2021 | TEST USER | 25  | LXXXXXXX | Credit Card | 2222 4000 7000 0005 | 03/2030 | 737 |

	 
	
