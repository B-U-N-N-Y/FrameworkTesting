package com.atmecs.FrameworkTesting.reports;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeSuite;

import com.atmecs.FrameworkTesting.constant.FileConstant;
import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;

public class Extent {
	public  WebDriver driver;
	public  ExtentReports extent;
	public  ExtentTest logger;

	/**
	 * In this method, loding of the Extent Reports configuration file is done
	 */
	@BeforeSuite
	public void startReport() {

		extent = new ExtentReports(FileConstant.EXTENT_REPORT_FILE, true);
		extent.loadConfig(new File(FileConstant.EXTENT_CONFIG));
	}

	/**
	 * In this method, Screenshots of the different test cases results is taken and
	 * save to the file path given
	 * 
	 * @param driver
	 * @param testname
	 */
	public static String getScreenshot(WebDriver driver, String testname) throws Exception {
		String dateName = new SimpleDateFormat("yyyyMMddhhmmss").format(new Date());
		TakesScreenshot ts = (TakesScreenshot) driver;
		File source = ts.getScreenshotAs(OutputType.FILE);
		String destination = FileConstant.FAILED_SCREENSHOT_FILE + testname + dateName + ".png";
		File finalDestination = new File(destination);
		FileUtils.copyFile(source, finalDestination);
		return destination;
	}

	/**
	 * In this method, According to the different scenarios of test results
	 * screenshots are captured
	 * 
	 * @param ITestResult result
	 */
	@AfterMethod
	public  void tearDown(ITestResult result) throws Exception {

		if (result.getStatus() == ITestResult.FAILURE) {
			logger.log(LogStatus.FAIL, "TEST CASE FAILED IS " + result.getName()); // to add name in extent report
			logger.log(LogStatus.FAIL, "TEST CASE FAILED IS " + result.getThrowable()); // to add error/exception in
			// extent report

			String screenshotPath = Extent.getScreenshot(driver, result.getName());
			logger.log(LogStatus.FAIL, logger.addScreenCapture(screenshotPath)); // to add screenshot in extent
			// report

			// //to add screencast/video in extent report
		} else if (result.getStatus() == ITestResult.SKIP) {
			logger.log(LogStatus.SKIP, "Test Case SKIPPED IS " + result.getName());
		} else if (result.getStatus() == ITestResult.SUCCESS) {
			logger.log(LogStatus.PASS, "Test Case PASSED IS " + result.getName());

		}
		extent.endTest(logger);
		System.out.println("logger executed");
	}

//closing all the resources used for the testing
	@AfterSuite
	public void endReport() {
		System.out.println("After all test executed");

		extent.flush();
		//extent.close();
	}

	@AfterTest
	public void closeDriver() {
		driver.quit();
	}
	
	public  void messagePrint(String message) {
		logger.log(LogStatus.INFO, message);
	}

}
