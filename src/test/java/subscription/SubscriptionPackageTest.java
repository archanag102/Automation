package subscription;

import java.io.IOException;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import common.ExcelDataProvider;
import pageObjects.HomePage;

public class SubscriptionPackageTest extends BaseTest {

	SoftAssert softAssert = new SoftAssert();

	/**
	 * Description: Validate the Subscription Packages – Type & Price and Currency
	 * for all Countries (SA / Kuwait and Baharin)
	 * 
	 * @param countryName
	 * @param planType
	 * @param price
	 * @param currency
	 * @throws InterruptedException
	 * @author Archana
	 */
	@Test(dataProvider = "excelData")
	public void verify_SubscriptionPackage(String countryName, String planType, String price, String currency)
			throws InterruptedException {
		// Navigate to HomePage
		HomePage homepage = new HomePage(driver);
		// Select Country Name
		homepage.selectCountry(countryName);
		// Verify Plan type of the subscription package
		softAssert.assertTrue(homepage.verifyPlanType(countryName, planType),
				"Verify Plan Type for " + countryName + " , Plan " + planType);
		// Verify Price of the subscription package
		softAssert.assertTrue(homepage.verifyPriceOfPlan(countryName, planType, price),
				"Verify Price and Plan Type for " + countryName + " , Plan " + planType + " Price " + price);
		// Verify Currency of the subscription package
		softAssert.assertTrue(homepage.verifyCurrencyandCountry(countryName, currency),
				"Verify Currency for " + countryName + " , Currency" + currency);
		softAssert.assertAll();
		// Logging message of all the test case
		logger.info("Validated All Cases");
	}

	@DataProvider(name = "excelData")
	public Object[][] excelDataProvider() throws IOException {
		Object[][] arrObj = new ExcelDataProvider().getExcelData("src/test/resources/testdata.xlsx", "Default");
		return arrObj;
	}

}
