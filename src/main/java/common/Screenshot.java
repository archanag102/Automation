package common;

import java.io.File;
import java.io.IOException;

import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.io.FileHandler;

import pageObjects.BasePage;

public class Screenshot extends BasePage {

	public Screenshot(WebDriver driver) {
		this.driver = driver;
	}

	// Method to capture a screenshot
	public void captureScreenshot(String screenshotName) {
		File screenshotFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
		String destinationPath = "path/to/save/screenshots/" + screenshotName + ".png";

		try {
			FileHandler.copy(screenshotFile, new File(destinationPath));
			System.out.println("Screenshot captured: " + destinationPath);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
