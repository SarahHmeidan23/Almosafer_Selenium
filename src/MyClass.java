import java.time.Duration;
import java.util.Random;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

public class MyClass {
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
		Assert.assertEquals(LangOfWebsite, "en");

	}

	@Test(enabled = false)
	public void CheckTheCurrency() {
		WebElement Currency = driver.findElement(By.xpath("//button[normalize-space()='SAR']"));
		String ActualCurrency = Currency.getText();
		String ExpectedCurrency = "SAR";
		Assert.assertEquals(ActualCurrency, ExpectedCurrency);

	}

	@Test(enabled = false)
	public void CheckTheContactNumber() {
		WebElement ContactNumber = driver.findElement(By.cssSelector("a[class='sc-hUfwpO bWcsTG'] strong"));
		String ActualContactNum = ContactNumber.getText();
		String ExpectedContactNum = "+966554400000";
		Assert.assertEquals(ActualContactNum, ExpectedContactNum);
	}

	@Test(enabled = false)
	public void CheckTheHotelsTab() {

		WebElement HotelsButtons = driver.findElement(By.id("uncontrolled-tab-example-tab-hotels"));
		String HotelsTab = HotelsButtons.getAttribute("aria-selected");
		Assert.assertEquals(HotelsTab, "false");
	}

	@Test(enabled = false)
	public void CheckTheQitafLogo() {
		WebElement Footer = driver.findElement(By.tagName("footer"));
		boolean QitafLogo = Footer.findElement(By.xpath("//div[@class='sc-dznXNo iZejAw']")).isDisplayed();
		Assert.assertEquals(QitafLogo, true);
	}

	@Test(/*invocationCount = 2*/ )
	public void ChangeLanguageRandomly() throws InterruptedException {

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
			driver.findElement(By.cssSelector(
					".sc-jTzLTM.eJkYKb.sc-1vkdpp9-6.iKBWgG.js-HotelSearchBox__SearchButton.btn.btn-primary.btn-block"))
					.click();
		} else if (myWebsiteURL.contains("en")) {
			WebElement SearchEnglish = driver
					.findElement(By.cssSelector("input[placeholder='Search for hotels or places']"));
			SearchEnglish.sendKeys(EnglishOptions[RandomEnglish]);
			driver.findElement(By.cssSelector(
					".phbroq-5.dbvRBC.AutoComplete__ListItem.AutoComplete__ListItem--highlighted.AutoComplete__ListItem"))
					.click();
		}
		driver.findElement(By.className("HotelSearchBox__SearchButton")).click();
		Thread.sleep(2000);
	}

	@AfterTest
	public void Post() {
	}

}
