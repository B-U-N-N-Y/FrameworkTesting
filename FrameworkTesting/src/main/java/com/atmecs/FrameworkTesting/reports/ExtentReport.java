package com.atmecs.FrameworkTesting.reports;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.testng.IReporter;
import org.testng.IResultMap;
import org.testng.ISuite;
import org.testng.ISuiteResult;
import org.testng.ITestContext;
import org.testng.ITestResult;
import org.testng.Reporter;
import org.testng.TestListenerAdapter;
import org.testng.xml.XmlSuite;

import com.atmecs.FrameworkTesting.constant.FileConstant;
import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;

/**
 * This class create the extent report for the test script
 * 
 * @author arjun.santra
 *
 */
public class ExtentReport  implements IReporter {
	public ExtentReports extent;
	public ExtentTest test;
	public WebDriver driver;

	public void generateReport(List<XmlSuite> xmlSuites, List<ISuite> suites, String outputdirectory) {
		extent = new ExtentReports(FileConstant.USER_HOME + File.separator + "Extent.html", true);

		for (ISuite suite : suites) {
			Map<String, ISuiteResult> result = suite.getResults();

			for (ISuiteResult r : result.values()) {
				ITestContext context = r.getTestContext();

				buildTestNodes(context.getPassedTests(), LogStatus.PASS);
				buildTestNodes(context.getFailedTests(), LogStatus.FAIL);
				buildTestNodes(context.getSkippedTests(), LogStatus.SKIP);
			}
		}

		extent.flush();
		extent.close();
	}

	public void buildTestNodes(IResultMap tests, LogStatus status) {

		if (tests.size() > 0) {
			for (ITestResult result : tests.getAllResults()) {
				test = extent.startTest(result.getMethod().getMethodName());

				test.setStartedTime(getTime(result.getStartMillis()));
				test.setEndedTime(getTime(result.getEndMillis()));

				for (String group : result.getMethod().getGroups())
					test.assignCategory(group);

				String message = "Test " + status.toString().toLowerCase() + "ed";
				if (result.getThrowable() != null)
					message = result.getThrowable().getClass() + ": " + result.getThrowable().getMessage();
				test.log(status, message);
				for (String testMessage : Reporter.getOutput(result)) {
					test.log(LogStatus.INFO, testMessage);
				}
				extent.endTest(test);
			}
		}
	}

	public Date getTime(long millis) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTimeInMillis(millis);
		return calendar.getTime();
	}

//	@Override
//	    public void onTestFailure(ITestResult result) {
//	        Calendar calendar = Calendar.getInstance();
//	        SimpleDateFormat formater = new SimpleDateFormat("dd_MM_yyyy_hh_mm_ss");
//	        String methodName = result.getName();
//	        if(!result.isSuccess()){
//	            File scrFile = ((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);
//	            try {
//	                String reportDirectory = new File(System.getProperty("user.dir")).getAbsolutePath() + "/target/surefire-reports";
//	                File destFile = new File((String) reportDirectory+"/failure_screenshots/"+methodName+"_"+formater.format(calendar.getTime())+".png");
//	                FileUtils.copyFile(scrFile, destFile);
//	                Reporter.log("<a href='"+ destFile.getAbsolutePath() + "'> <img src='"+ destFile.getAbsolutePath() + "' height='100' width='100'/> </a>");
//	            } catch (IOException e) {
//	                e.printStackTrace();
//	            }
//	        }
}
