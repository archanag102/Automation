package subscription;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import java.util.logging.LogManager;
import java.util.logging.Logger;

import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.io.FileHandler;
import org.testng.ITestResult;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeSuite;

import io.github.bonigarcia.wdm.WebDriverManager;

public class BaseTest {

	public static String baseUrl;
	public static String browser;
	public static WebDriver driver;
	public static final Logger logger = Logger.getLogger(BaseTest.class.getName());
	static {
		try {
			LogManager.getLogManager().readConfiguration(new FileInputStream("src/test/resources/log.properties"));
		} catch (IOException e) {
			System.err.println("Could not load logging configuration file");
			e.printStackTrace();
		}
	}

	@BeforeSuite
	public void readProperties() throws IOException {
		String filePath = "src/test/resources/config.properties";
		InputStream inputStream = new FileInputStream(filePath);
		Properties properties = new Properties();
		properties.load(inputStream);
		logger.info("Proerties File values are retrieved");
		baseUrl = properties.getProperty("AppUrl");
		browser = properties.getProperty("browser");
	}

	@BeforeClass
	public void setUp() {
		WebDriverManager.chromedriver().setup();
		driver = new ChromeDriver();
		logger.info("Chrome Browser Launched");
		driver.manage().window().maximize();
		driver.get(baseUrl);
		logger.info("Successfully navigated to website.");
	}

	@AfterClass
	public void closeBrowser() {
		logger.info("Browser Closed");
		driver.quit();
	}

	@AfterMethod
	public void tearDown(ITestResult result) {
		if (result.getStatus() == ITestResult.FAILURE) {
			captureScreenshot(result.getName());
		} else if (result.getStatus() == ITestResult.SUCCESS) {
			captureScreenshot(result.getName());
		} else if (result.getStatus() == ITestResult.SKIP) {
			captureScreenshot(result.getName());
		}
	}

	/**
	 * Capture Screenshot
	 * 
	 * @param screenshotName
	 */
	public void captureScreenshot(String screenshotName) {
		File screenshotFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
		String destinationPath = "src/test/java/test-output/screenshots/" + screenshotName + ".png";
		try {
			FileHandler.copy(screenshotFile, new File(destinationPath));
			System.out.println("Screenshot captured: " + destinationPath);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
