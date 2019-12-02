package stepdefs;

import cucumber.api.java.After;
import cucumber.api.java.Before;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;
import java.util.concurrent.TimeUnit;
import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.Select;
import utils.Constants;

public class UserSearchStepDef {
  WebDriver driver;
  public static Properties properties;

  public UserSearchStepDef() {
    try {
      properties = new Properties();
      FileInputStream fs = new FileInputStream("src/test/resources/configs/config.properties");
      properties.load(fs);
    } catch (IOException e) {
      e.getMessage();
    }
  }

  @Before
  public void setUp() {
    String browser = properties.getProperty("browser");
    int DEFAULT_TIMEOUT = Integer.parseInt(properties.getProperty("timeout"));
    String appUrl = properties.getProperty("url");

    if(browser.equals("chrome")) {
      System.setProperty("webdriver.chrome.driver", "src/test/resources/drivers/chromedriver");
      driver = new ChromeDriver();
    } else {
      System.setProperty("webdriver.gecko.driver", "src/test/resources/drivers/geckodriver");
      driver = new FirefoxDriver();
    }

    driver.manage().window().maximize();
    driver.manage().deleteAllCookies();
    driver.manage().timeouts().implicitlyWait(DEFAULT_TIMEOUT, TimeUnit.SECONDS);
    driver.manage().timeouts().pageLoadTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS);

    driver.get(appUrl);
  }

  @After
  public void tearDown() {
    driver.quit();
  }

  @Given("^I am on registration portal$")
  public void i_am_on_registration_portal() {
    Assert.assertEquals(driver.getTitle(), Constants.SEARCH_PAGE_TITLE);
  }

  @When("^I enter \"([^\"]*)\", \"([^\"]*)\", \"([^\"]*)\", \"([^\"]*)\", \"([^\"]*)\" and \"([^\"]*)\"$")
  public void i_enter_and(String lastname, String month, String day, String year, String postalcode, String street) {
    driver.findElement(By.id("lastName")).sendKeys(lastname);
    driver.findElement(By.id("monthId")).sendKeys(month);
    driver.findElement(By.id("day")).sendKeys(day);
    driver.findElement(By.id("year")).sendKeys(year);
    driver.findElement(By.id("postalCode")).sendKeys(postalcode);
    Select address = new Select(driver.findElement(By.id("streetList")));
    address.selectByIndex(1);
    driver.findElement(By.id("addressNumber")).sendKeys(street);
  }

  @When("^I click search$")
  public void i_click_search() {
    driver.findElement(By.cssSelector("div.search-info button.btn-large-yellow")).click();
  }

  @Then("^I should see the next page with user details$")
  public void i_should_see_the_next_page_with_user_details() {
    // Not implemented this method because of captcha
    // String expectedHeaderText = "Search Results";
    // Assert.assertEquals(expectedHeaderText, driver.findElement(By.cssSelector("div.header-banner")).getText().trim());
  }

  @Then("^I should see Error-messages$")
  public void i_should_see_Error_messages() {
    String ERROR_01 = driver.findElement(By.cssSelector("div.alert-banner-multiple li:nth-of-type(1)")).getText().trim();
    String ERROR_02 = driver.findElement(By.cssSelector("div.alert-banner-multiple li:nth-of-type(2)")).getText().trim();
    String ERROR_03 = driver.findElement(By.cssSelector("div.alert-banner-multiple li:nth-of-type(3)")).getText().trim();
    Assert.assertEquals(ERROR_01, Constants.ERROR_LASTNAME);
    Assert.assertEquals(ERROR_02, Constants.ERROR_DATE_OF_BIRTH);
    Assert.assertEquals(ERROR_03, Constants.ERROR_POSTAL_CODE);
  }

  @When("^I click on I have a different address type hyperlink$")
  public void i_click_on_hyperlink() {
    driver.findElement(By.cssSelector("app-residential-address a")).click();
  }

  @Then("^I should see Lot/concession, Rural and Civic Address options$")
  public void i_should_see_Lot_concession_Rural_and_Civic_Address_options() {
    String ADDRESS_TYPE_01 = driver.findElement(By.cssSelector("div.select-address div.row:nth-of-type(1) h3")).getText().trim();
    String ADDRESS_TYPE_02 = driver.findElement(By.cssSelector("div.select-address div.row:nth-of-type(2) h3")).getText().trim();
    String ADDRESS_TYPE_03 = driver.findElement(By.cssSelector("div.select-address div.row:nth-of-type(3) h3")).getText().trim();
    Assert.assertEquals(ADDRESS_TYPE_01, Constants.EXPECTED_ADDRESS_TYPE_1);
    Assert.assertEquals(ADDRESS_TYPE_02, Constants.EXPECTED_ADDRESS_TYPE_2);
    Assert.assertEquals(ADDRESS_TYPE_03, Constants.EXPECTED_ADDRESS_TYPE_3);
  }

  @When("^I click on Start Over hyperlink$")
  public void i_click_on_Start_Over_hyperlink() {
    driver.findElement(By.cssSelector("div.search-info a")).click();
  }

  @Then("^I should navigate to Homepage$")
  public void i_should_navigate_to_Homepage() {
    Assert.assertEquals(driver.getTitle(), Constants.HOME_PAGE_TITLE);
  }

  @When("^I click on Previous button$")
  public void i_click_on_Previous_button() {
    driver.findElement(By.cssSelector("div.search-info button.btn-large-white")).click();
  }

  @Then("^I should navigate to Important Information page$")
  public void i_should_navigate_to_Important_Information_page() {
    Assert.assertEquals(driver.getTitle(), Constants.INFORMATION_PAGE_TITLE);
  }
}
