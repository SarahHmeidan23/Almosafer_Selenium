import java.time.Duration;
import java.time.LocalDate;
import java.util.Random;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

public class MyClass {
	SoftAssert myAssertion = new SoftAssert();
	String myWebsite = "https://global.almosafer.com/en";
	WebDriver driver = new ChromeDriver();

	@BeforeTest

	public void mySetup() {

		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(3000));
		driver.manage().window().maximize();
		driver.get(myWebsite);
		driver.findElement(By.xpath("//button[normalize-space()='Kingdom of Saudi Arabia, SAR']")).click();
	}

	@Test(enabled = false)

	public void CheckTheLanguage() {
		WebElement Language = driver.findElement(By.tagName("html"));
		String LangOfWebsite = Language.getAttribute("lang");
		Assert.assertEquals(LangOfWebsite, "en", "This is to check the language");

	}

	@Test(enabled = false)
	public void CheckTheCurrency() {
		WebElement Currency = driver.findElement(By.xpath("//button[normalize-space()='SAR']"));
		String ActualCurrency = Currency.getText();
		String ExpectedCurrency = "SAR";
		Assert.assertEquals(ActualCurrency, ExpectedCurrency, "This is to Check the currency");

	}

	@Test(enabled = false)
	public void CheckTheContactNumber() {
		WebElement ContactNumber = driver.findElement(By.cssSelector("a[class='sc-hUfwpO bWcsTG'] strong"));
		String ActualContactNum = ContactNumber.getText();
		String ExpectedContactNum = "+966554400000";
		Assert.assertEquals(ActualContactNum, ExpectedContactNum, "This is to Check the contact number");
	}

	@Test(enabled = false)
	public void CheckTheHotelsTab() {

		WebElement HotelsButtons = driver.findElement(By.id("uncontrolled-tab-example-tab-hotels"));
		String HotelsTab = HotelsButtons.getAttribute("aria-selected");
		Assert.assertEquals(HotelsTab, "false", "This is to make sure the Hotels tab is not selected");
	}

	@Test(enabled = false)
	public void CheckTheQitafLogo() {
		WebElement Footer = driver.findElement(By.tagName("footer"));
		boolean QitafLogo = Footer.findElement(By.xpath("//div[@class='sc-dznXNo iZejAw']")).isDisplayed();
		Assert.assertEquals(QitafLogo, true, "That's to make sure the logo exists");
	}

	@Test(enabled = false)
	public void CheckTheDepartureAndReturnDate() {

		LocalDate TheDate = LocalDate.now();
		int ExpectedDeparture = TheDate.plusDays(1).getDayOfMonth();
		int ExpectedReturn = TheDate.plusDays(2).getDayOfMonth();
		String ActualDeparture = driver
				.findElement(By.cssSelector("div[class='sc-OxbzP sc-lnrBVv gKbptE'] span[class='sc-fvLVrH hNjEjT']"))
				.getText();
		String ActualReturn = driver
				.findElement(By.cssSelector("div[class='sc-OxbzP sc-bYnzgO bojUIa'] span[class='sc-fvLVrH hNjEjT']"))
				.getText();

		myAssertion.assertEquals(Integer.parseInt(ActualDeparture), ExpectedDeparture,
				"This is to Test The Departure Date");
		myAssertion.assertEquals(Integer.parseInt(ActualReturn), ExpectedReturn, "This is to Test The Return Date");
		myAssertion.assertAll();
	}

	@Test(enabled= false)
	public void ChangeLanguageRandomly() {
		String[] myWebsites = { "https://global.almosafer.com/en", "https://global.almosafer.com/ar" };
		Random rand = new Random();
		int RandomNumber = rand.nextInt(myWebsites.length);
		driver.get(myWebsites[RandomNumber]);
		String myWebsiteURL = driver.getCurrentUrl();
		if (myWebsiteURL.contains("ar")) {
			String ActualLanguage = driver.findElement(By.tagName("html")).getAttribute("lang");

			Assert.assertEquals(ActualLanguage, "ar", "this is to test the langauge");
		}else if (myWebsiteURL.contains("en")) {

			String ActualLanguage = driver.findElement(By.tagName("html")).getAttribute("lang");
			Assert.assertEquals(ActualLanguage, "en", "this is to test the langauge");

		}
		
	}
	@Test(/* invocationCount = 2 */ )
	public void HotelSwitch() throws InterruptedException {
		String[] myWebsites = { "https://global.almosafer.com/en", "https://global.almosafer.com/ar" };
		String[] ArabicOptions = { "جدة", "دبي" };
		String[] EnglishOptions = { "Dubai", "Jeddah", "Riyadh" };
		Random rand = new Random();
		int RandomNumber = rand.nextInt(myWebsites.length);
		int RandomArabic = rand.nextInt(ArabicOptions.length);
		int RandomEnglish = rand.nextInt(EnglishOptions.length);
		driver.get(myWebsites[RandomNumber]);
		driver.findElement(By.xpath("//a[@id='uncontrolled-tab-example-tab-hotels']")).click();
		String myWebsiteURL = driver.getCurrentUrl();

		if (myWebsiteURL.contains("ar")) {
			WebElement SearchArabic = driver
					.findElement(By.cssSelector("input[placeholder='البحث عن فنادق أو وجهات']"));
			SearchArabic.sendKeys(ArabicOptions[RandomArabic]);
			driver.findElement(By.cssSelector(
					".phbroq-5.kORbYL.AutoComplete__ListItem.AutoComplete__ListItem--highlighted.AutoComplete__ListItem"))
					.click();
			driver.findElement(By.cssSelector(".tln3e3-1.eFsRGb")).click();
			WebElement option1 = driver.findElement(By.cssSelector("option[value='A']"));
			WebElement option2 = driver.findElement(By.cssSelector("option[value='B']"));
			boolean RandomOptions = rand.nextBoolean();
			if (RandomOptions) {
				option1.click();
			} else {
				option2.click();
			}
			driver.findElement(By.cssSelector(
					".sc-jTzLTM.eJkYKb.sc-1vkdpp9-6.iKBWgG.js-HotelSearchBox__SearchButton.btn.btn-primary.btn-block"))
					.click();
			Thread.sleep(10000);
			String resultsFound = driver
					.findElement(By.xpath("//span[@data-testid='HotelSearchResult__resultsFoundCount']")).getText();
			Assert.assertEquals(resultsFound.contains("وجدنا"), true);
		} else if (myWebsiteURL.contains("en")) {
			WebElement SearchEnglish = driver
					.findElement(By.cssSelector("input[placeholder='Search for hotels or places']"));
			SearchEnglish.sendKeys(EnglishOptions[RandomEnglish]);
			driver.findElement(By.cssSelector(
					".phbroq-5.dbvRBC.AutoComplete__ListItem.AutoComplete__ListItem--highlighted.AutoComplete__ListItem"))
					.click();
			driver.findElement(By.cssSelector(".tln3e3-1.eFsRGb")).click();
			WebElement option1 = driver.findElement(By.cssSelector("option[value='A']"));
			WebElement option2 = driver.findElement(By.cssSelector("option[value='B']"));
			boolean RandomOptions = rand.nextBoolean();
			if (RandomOptions) {
				option1.click();
			} else {
				option2.click();
			}
			driver.findElement(By.xpath("//button[@data-testid='HotelSearchBox__SearchButton']")).click();

			Thread.sleep(10000);
			String resultsFound = driver
					.findElement(By.xpath("//span[@data-testid='HotelSearchResult__resultsFoundCount']")).getText();
			Assert.assertEquals(resultsFound.contains("found"), true);

		}
	}

	@AfterTest
	public void Post() {
	}

}
