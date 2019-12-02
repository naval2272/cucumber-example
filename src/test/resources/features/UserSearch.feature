Feature: User Search

  Scenario Outline: search registration portal for valid user
    Given I am on registration portal
    When I enter "<lastname>", "<month>", "<day>", "<year>", "<postalcode>" and "<street>"
    And I click search
    Then I should see the next page with user details

    Examples:
      | lastname | month | day | year | postalcode | street |
      | test     | January | 12 | 1988 | L1T0A1    | 21     |


  Scenario: error condition when mandatory fields are not filled
    Given I am on registration portal
    And I click search
    Then I should see Error-messages


  Scenario: Clicking on "I have a different address type" shows other address types
    Given I am on registration portal
    When I click on I have a different address type hyperlink
    Then I should see Lot/concession, Rural and Civic Address options


  Scenario: Clicking on Start Over takes to HomePage
    Given I am on registration portal
    When I click on Start Over hyperlink
    Then I should navigate to Homepage


  Scenario: Clicking on Previous button takes to Important Information page
    Given I am on registration portal
    When I click on Previous button
    Then I should navigate to Important Information page


