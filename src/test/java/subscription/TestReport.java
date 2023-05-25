package subscription;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.reporter.ExtentHtmlReporter;
import org.testng.ITestResult;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;

public class TestReport {
	public static ExtentReports extentReports;
	public static ExtentHtmlReporter htmlReporter;
	public static ExtentTest extentTest;

	@BeforeSuite
	public void setUpReport() {
		htmlReporter = new ExtentHtmlReporter("src/test/java/test-output/report.html");
		extentReports = new ExtentReports();
		extentReports.attachReporter(htmlReporter);
	}

	@AfterSuite
	public void tearDownReport() {
		extentReports.flush();
	}

	public static void createTest(String testName) {
		extentTest = extentReports.createTest(testName);
	}

	public static void logStatus(Status status, String message) {
		extentTest.log(status, message);
	}

	public static void logTestResult(ITestResult result) {
		if (result.getStatus() == ITestResult.SUCCESS) {
			extentTest.pass("Test Passed");
		} else if (result.getStatus() == ITestResult.FAILURE) {
			extentTest.fail("Test Failed");
		} else if (result.getStatus() == ITestResult.SKIP) {
			extentTest.skip("Test Skipped");
		}
	}
}
