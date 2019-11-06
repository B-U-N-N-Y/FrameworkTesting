package com.atmecs.FrameworkTesting.testscripts;

import java.util.Properties;

import org.testng.Assert;
import org.testng.annotations.Test;

import com.atmecs.FrameworkTesting.base.TestBase;
import com.atmecs.FrameworkTesting.constant.FileConstant;
import com.atmecs.FrameworkTesting.helper.Utility;
import com.atmecs.FrameworkTesting.helper.WebTable;
import com.atmecs.FrameworkTesting.reports.LogReport;
import com.atmecs.FrameworkTesting.utils.ReadPropertiesFile;

public class TableTest2 extends TestBase{
	Utility utility;
	WebTable webTable;
	Properties loc;
	LogReport log;
	
		@Test
		public void tableTesting() {
			utility=new Utility(driver);
			webTable=new WebTable(driver);
			//logger=extent.startTest("TableTest2");
			log=new LogReport();
			loc=new ReadPropertiesFile().loadProperty(FileConstant.LOCATOR_FILE);
			log.info("Table reading specific data");
			System.out.println(webTable.getCellDataByColumnAndRowNumber(loc.getProperty("loc.toolqa.table"), 2, 3));
			String actual=webTable.getCellDataByColumnAndRowNumber(loc.getProperty("loc.toolqa.table"), 2, 3);
			Assert.assertEquals(actual, "602m");
			log.info("Table specific data print successfully");
			
			
		}
}
